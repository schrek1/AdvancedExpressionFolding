package com.intellij.advancedExpressionFolding.processor.methodcall.optional

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.operation.optional.OptionalOrElseThrowElvis
import com.intellij.advancedExpressionFolding.processor.core.BuildExpressionExt.getAnyExpression
import com.intellij.advancedExpressionFolding.processor.end
import com.intellij.advancedExpressionFolding.processor.methodcall.Context
import com.intellij.advancedExpressionFolding.processor.start
import com.intellij.advancedExpressionFolding.processor.toTextRange
import com.intellij.openapi.util.TextRange
import com.intellij.psi.JavaTokenType
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiCodeBlock
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiJavaCodeReferenceElement
import com.intellij.psi.PsiLambdaExpression
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiMethodReferenceExpression
import com.intellij.psi.PsiNewExpression
import com.intellij.psi.PsiReturnStatement
import com.intellij.psi.util.PsiTreeUtil

/**
 * `opt.orElseThrow(() -> new X(args))` / `opt.orElseThrow(X::new)` as `opt ?: throw X(args)` / `opt ?: throw X()`.
 * A one-`String`-argument `IllegalStateException` becomes `opt ?: error(msg)`, which throws the same type.
 * A block lambda folds the same when its only statement is `return new X(...)`. Any other supplier (a longer block,
 * a method call, an array constructor) stays unfolded.
 */
class OptionalOrElseThrowMethodCall : AbstractOptionalMethodCall() {
    override val methodNames by lazy { listOf("orElseThrow") }

    override fun onSingleArgument(
        element: PsiMethodCallExpression,
        context: Context,
        argument: PsiExpression,
        argumentExpression: Expression
    ): Expression? {
        val dot = element.methodExpression.node.findChildByType(JavaTokenType.DOT)?.psi ?: return null
        val document = context.document
        // The receiver may end a line; then the line break stays and needs no space before `?:`.
        val space = if (dot.start() > 0 && document.charsSequence[dot.start() - 1].isWhitespace()) "" else " "
        val gaps = mutableListOf<Pair<TextRange, String>>()
        val children = mutableListOf(context.qualifierExpr)
        when (argument) {
            is PsiLambdaExpression -> {
                if (argument.parameterList.parametersCount != 0) return null
                val block = argument.body as? PsiCodeBlock
                val exception = if (block != null) block.singleReturnedNew() else argument.body as? PsiNewExpression
                if (exception == null || exception.anonymousClass != null) return null
                val classReference = exception.classReference ?: return null
                val arguments = exception.argumentList ?: return null
                val message = arguments.expressions.singleOrNull()
                val (shownFrom, text) = if (exception.type?.canonicalText == ILLEGAL_STATE && message?.isString() == true) {
                    arguments.start() to "$space?: error"
                } else {
                    classReference.start() to "$space?: throw "
                }
                if (block == null) {
                    gaps += (dot.start() to shownFrom).toTextRange() to text
                    gaps += (exception.end() to element.end()).toTextRange() to ""
                } else {
                    // Separate spans for the braces, so none of them cuts through the block's own `{...}` fold.
                    gaps += (dot.start() to block.start()).toTextRange() to text
                    gaps += (block.start() to shownFrom).toTextRange() to ""
                    gaps += (exception.end() to block.end()).toTextRange() to ""
                    gaps += (block.end() to element.end()).toTextRange() to ""
                }
                arguments.expressions.mapTo(children) { getAnyExpression(it, document) }
            }

            is PsiMethodReferenceExpression -> {
                val exceptionClass = argument.qualifier as? PsiJavaCodeReferenceElement ?: return null
                if (!argument.isConstructor || exceptionClass.resolve() !is PsiClass) return null
                gaps += (dot.start() to exceptionClass.start()).toTextRange() to "$space?: throw "
                gaps += (exceptionClass.end() to element.end()).toTextRange() to "()"
            }

            else -> return null
        }
        return OptionalOrElseThrowElvis(element, gaps, children)
    }

    /** `{ return new X(...); }`; a comment would end up inside a hidden span, so it keeps the block as Java. */
    private fun PsiCodeBlock.singleReturnedNew(): PsiNewExpression? {
        if (PsiTreeUtil.findChildOfType(this, PsiComment::class.java) != null) return null
        val returnStatement = statements.singleOrNull() as? PsiReturnStatement ?: return null
        return returnStatement.returnValue as? PsiNewExpression
    }

    private fun PsiExpression.isString() = type?.canonicalText == "java.lang.String"

    private companion object {
        const val ILLEGAL_STATE = "java.lang.IllegalStateException"
    }
}
