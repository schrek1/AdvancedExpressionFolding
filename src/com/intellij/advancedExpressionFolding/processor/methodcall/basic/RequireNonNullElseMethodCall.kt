package com.intellij.advancedExpressionFolding.processor.methodcall.basic

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.operation.basic.RequireNonNullElseElvis
import com.intellij.advancedExpressionFolding.processor.core.getAnyExpression
import com.intellij.advancedExpressionFolding.processor.methodcall.AbstractMethodCall
import com.intellij.advancedExpressionFolding.processor.methodcall.Context
import com.intellij.advancedExpressionFolding.processor.methodcall.collection.MapGetOrDefaultMethodCall
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiLambdaExpression
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiMethodReferenceExpression
import com.intellij.psi.PsiThisExpression

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
        if (!MapGetOrDefaultMethodCall.standsAlone(element)) {
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
        val callSyntax = listOf(
            TextRange(element.textRange.startOffset, a1.textRange.startOffset) to "",
            TextRange(a1.textRange.endOffset, commaEnd) to if (keepsSpace) " ?:" else " ?: ",
        )
        val fallback = if (context.methodName == REQUIRE_NON_NULL_ELSE) {
            Fallback(listOf(TextRange(a2.textRange.endOffset, element.textRange.endOffset) to ""), listOf(a2Expression))
        } else {
            supplierFallback(a2, element.textRange.endOffset, document) ?: return null
        }
        return RequireNonNullElseElvis(
            element,
            element.textRange,
            callSyntax + fallback.folds,
            listOf(a1Expression) + fallback.operands
        )
    }

    private class Fallback(val folds: List<Pair<TextRange, String>>, val operands: List<Expression>)

    /** `() -> expr` → `expr`, `X::new` → `X()`, a no-arg method reference → the call; any other supplier stays Java. */
    private fun supplierFallback(supplier: PsiExpression, callEnd: Int, document: Document): Fallback? {
        val closingParen = TextRange(callEnd - 1, callEnd)
        return when (supplier) {
            is PsiLambdaExpression -> {
                val body = supplier.body as? PsiExpression ?: return null
                if (!supplier.parameterList.isEmpty) {
                    return null
                }
                Fallback(
                    listOf(
                        TextRange(supplier.textRange.startOffset, body.textRange.startOffset) to "",
                        TextRange(body.textRange.endOffset, callEnd) to "",
                    ),
                    listOf(getAnyExpression(body, document))
                )
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

    private companion object {
        const val REQUIRE_NON_NULL_ELSE = "requireNonNullElse"
    }
}
