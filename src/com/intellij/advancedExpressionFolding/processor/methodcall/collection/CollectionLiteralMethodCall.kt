package com.intellij.advancedExpressionFolding.processor.methodcall.collection

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.literal.ListLiteral
import com.intellij.advancedExpressionFolding.expression.literal.MapLiteral
import com.intellij.advancedExpressionFolding.processor.methodcall.AbstractMethodCall
import com.intellij.advancedExpressionFolding.processor.methodcall.Context
import com.intellij.advancedExpressionFolding.processor.methodcall.NeedsQualifier
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
        else -> null
    }
}
