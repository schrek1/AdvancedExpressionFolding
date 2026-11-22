package com.intellij.advancedExpressionFolding.processor.methodcall.basic

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.operation.basic.RequireNonNullElseElvis
import com.intellij.advancedExpressionFolding.processor.core.getAnyExpression
import com.intellij.advancedExpressionFolding.processor.methodcall.AbstractMethodCall
import com.intellij.advancedExpressionFolding.processor.methodcall.Context
import com.intellij.advancedExpressionFolding.processor.methodcall.collection.MapGetOrDefaultMethodCall
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiCodeBlock
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiLambdaExpression
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiMethodReferenceExpression
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.PsiReturnStatement
import com.intellij.psi.PsiThisExpression
import com.intellij.psi.util.PsiTreeUtil

class RequireNonNullElseMethodCall : AbstractMethodCall() {

    override fun canExecute() = requireNonNullElseElvis

    override val methodNames by lazy { listOf(REQUIRE_NON_NULL_ELSE, "requireNonNullElseGet") }

    override val classNames by lazy { listOf("java.util.Objects") }

    override fun onTwoArguments(
        element: PsiMethodCallExpression,
        context: Context,
        a1: PsiExpression,
        a2: PsiExpression,
        a1Expression: Expression,
        a2Expression: Expression
    ): Expression? {
        // `(value ?: fallback).call()` keeps its real parentheses; everywhere else the call must stand on its own
        val receiver = isReceiverOfSingleCall(element)
        if (!receiver && !MapGetOrDefaultMethodCall.standsAlone(element)) {
            return null
        }
        val document = context.document
        val text = document.charsSequence
        val commaEnd = (a1.textRange.endOffset until a2.textRange.startOffset)
            .firstOrNull { text[it] == ',' }
            ?.plus(1)
            ?: return null
        // The source whitespace after the comma stays real text, so only the comma turns into `?:`.
        val keepsSpace = commaEnd < a2.textRange.startOffset && text[commaEnd].isWhitespace()
        val callEnd = element.textRange.endOffset
        // in receiver form the call's own `)` becomes the closing parenthesis of the group
        val foldEnd = if (receiver) callEnd - 1 else callEnd
        val openingEnd = if (receiver) element.argumentList.textRange.startOffset else a1.textRange.startOffset
        val callSyntax = listOf(
            TextRange(element.textRange.startOffset, openingEnd) to "",
            TextRange(a1.textRange.endOffset, commaEnd) to if (keepsSpace) " ?:" else " ?: ",
        )
        val fallback = if (context.methodName == REQUIRE_NON_NULL_ELSE) {
            Fallback(listOf(TextRange(a2.textRange.endOffset, foldEnd) to ""), listOf(a2Expression))
        } else if (receiver && a2 !is PsiLambdaExpression) {
            return null
        } else {
            supplierFallback(a2, foldEnd, document) ?: return null
        }
        return RequireNonNullElseElvis(
            element,
            element.textRange,
            callSyntax + fallback.folds,
            listOf(a1Expression) + fallback.operands
        )
    }

    private class Fallback(val folds: List<Pair<TextRange, String>>, val operands: List<Expression>)

    /**
     * `() -> expr` and `() -> { return expr; }` → `expr`, `X::new` → `X()`, a no-arg method reference → the call;
     * any other supplier stays Java. [callEnd] is where the hidden tail of the call stops.
     */
    private fun supplierFallback(supplier: PsiExpression, callEnd: Int, document: Document): Fallback? {
        val closingParen = TextRange(callEnd - 1, callEnd)
        return when (supplier) {
            is PsiLambdaExpression -> {
                val body = supplier.body
                val value = when (body) {
                    is PsiExpression -> body
                    is PsiCodeBlock -> body.singleReturnedValue()
                    else -> null
                } ?: return null
                if (!supplier.parameterList.isEmpty) {
                    return null
                }
                // The editor keeps its own `{...}` fold on a block body, and a fold may not cross another one,
                // so the hidden text is split at the block's braces.
                val blockStart = body.takeIf { it is PsiCodeBlock }?.textRange?.startOffset
                val blockEnd = body.takeIf { it is PsiCodeBlock }?.textRange?.endOffset
                val folds = if (blockStart != null && blockEnd != null) {
                    listOf(
                        TextRange(supplier.textRange.startOffset, blockStart) to "",
                        TextRange(blockStart, value.textRange.startOffset) to "",
                        TextRange(value.textRange.endOffset, blockEnd) to "",
                        TextRange(blockEnd, callEnd) to "",
                    )
                } else {
                    listOf(
                        TextRange(supplier.textRange.startOffset, value.textRange.startOffset) to "",
                        TextRange(value.textRange.endOffset, callEnd) to "",
                    )
                }
                Fallback(folds, listOf(getAnyExpression(value, document)))
            }
            is PsiMethodReferenceExpression -> {
                val qualifier = supplier.qualifier ?: return null
                if (supplier.isConstructor) {
                    // The call's own `)` closes the synthesized `X(`.
                    Fallback(
                        listOf(
                            TextRange(qualifier.textRange.endOffset, supplier.textRange.endOffset) to "(",
                            TextRange(supplier.textRange.endOffset, closingParen.startOffset) to "",
                        ),
                        emptyList()
                    )
                } else {
                    val name = supplier.referenceNameElement ?: return null
                    val method = supplier.resolve() as? PsiMethod ?: return null
                    if (!method.parameterList.isEmpty) {
                        return null
                    }
                    val qualifierFold = if (qualifier is PsiThisExpression && qualifier.qualifier == null) {
                        TextRange(supplier.textRange.startOffset, name.textRange.startOffset) to ""
                    } else {
                        TextRange(qualifier.textRange.endOffset, name.textRange.startOffset) to "."
                    }
                    Fallback(
                        listOf(qualifierFold, TextRange(name.textRange.endOffset, callEnd) to "()"),
                        listOfNotNull((qualifier as? PsiExpression)?.takeIf { it !is PsiThisExpression }
                            ?.let { getAnyExpression(it, document) })
                    )
                }
            }
            else -> null
        }
    }

    private fun PsiCodeBlock.singleReturnedValue(): PsiExpression? =
        (statements.singleOrNull() as? PsiReturnStatement)
            ?.returnValue
            ?.takeIf { PsiTreeUtil.findChildOfType(this, PsiComment::class.java) == null }

    /** The only call made on the result, with at most one argument: `requireNonNullElse(name, "").length()`. */
    private fun isReceiverOfSingleCall(call: PsiMethodCallExpression): Boolean {
        val reference = call.parent as? PsiReferenceExpression ?: return false
        if (reference.qualifierExpression !== call) {
            return false
        }
        val outer = reference.parent as? PsiMethodCallExpression ?: return false
        val continuesChain = (outer.parent as? PsiReferenceExpression)?.qualifierExpression === outer
        return !continuesChain && outer.argumentList.expressionCount <= 1
    }

    private companion object {
        const val REQUIRE_NON_NULL_ELSE = "requireNonNullElse"
    }
}
