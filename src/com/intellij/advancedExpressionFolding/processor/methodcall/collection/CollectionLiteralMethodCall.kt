package com.intellij.advancedExpressionFolding.processor.methodcall.collection

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.literal.ListLiteral
import com.intellij.advancedExpressionFolding.expression.literal.MapLiteral
import com.intellij.advancedExpressionFolding.expression.semantic.SimpleExpression
import com.intellij.advancedExpressionFolding.expression.semantic.WrapperExpression
import com.intellij.advancedExpressionFolding.processor.methodcall.AbstractMethodCall
import com.intellij.advancedExpressionFolding.processor.methodcall.Context
import com.intellij.advancedExpressionFolding.processor.methodcall.NeedsQualifier
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiArrayType
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiMethodCallExpression

class CollectionLiteralMethodCall : AbstractMethodCall(), NeedsQualifier {
    override fun canExecute() = mapAndEmptyCollectionLiterals

    override val methodNames by lazy { listOf("of", "emptyList", "emptySet", "emptyMap") }

    override val classNames by lazy { listOf("java.util.Map", "java.util.List", "java.util.Set", "java.util.Collections") }

    override fun onAnyArguments(
        element: PsiMethodCallExpression,
        context: Context,
        expressions: Array<PsiExpression>
    ): Expression? = when {
        context.className == "java.util.Map" || context.methodName == "emptyMap" ->
            MapLiteral(element, element.textRange, context.argumentExpressions)
        expressions.isEmpty() -> ListLiteral(element, element.textRange, emptyList())
        context.className == "java.util.List" ->
            ListLiteral(element, element.textRange, context.argumentExpressions, spread = isVarargsArray(expressions))
        // `[...]` would claim a list, so a set keeps its factory, in Kotlin's name
        context.className == "java.util.Set" -> setOf(element, context, isVarargsArray(expressions))
        else -> null
    }

    private fun setOf(element: PsiMethodCallExpression, context: Context, spread: Boolean): Expression {
        val start = element.textRange.startOffset
        val name = if (spread) {
            SimpleExpression(element, TextRange(start, element.argumentList.textRange.startOffset + 1), "setOf(*")
        } else {
            SimpleExpression(element, TextRange(start, element.methodExpression.textRange.endOffset), "setOf")
        }
        return WrapperExpression(element, chain = listOf(name) + context.argumentExpressions)
    }

    companion object {
        /** `List.of(array)` passes the array as the varargs, so its elements are spread, not one element. */
        fun isVarargsArray(expressions: Array<PsiExpression>) =
            expressions.size == 1 && expressions[0].type is PsiArrayType
    }
}
