package com.intellij.advancedExpressionFolding.processor.methodcall.optional

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.operation.optional.OptionalMapCall
import com.intellij.advancedExpressionFolding.expression.operation.optional.OptionalMapSafeCall
import com.intellij.advancedExpressionFolding.expression.operation.optional.OptionalMapSafeCallParam
import com.intellij.advancedExpressionFolding.expression.operation.optional.OptionalOf
import com.intellij.advancedExpressionFolding.processor.methodcall.Context
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiReferenceExpression

class OptionalMapMethodCall : AbstractOptionalMethodCall() {
    override val methodNames by lazy { listOf("map", "flatMap") }

    override fun onSingleArgument(
        element: PsiMethodCallExpression,
        context: Context,
        argument: PsiExpression,
        argumentExpression: Expression
    ): Expression? {
        if (argumentExpression is OptionalMapSafeCallParam && !element.endsInOptionalOnlyCall()) {
            val flatMap = context.methodName == "flatMap"
            val qualifier = context.qualifierExpr

            return if (qualifier is OptionalOf) {
                OptionalMapCall(
                    element,
                    element.textRange,
                    context.getOperands(),
                    flatMap
                )
            } else {
                OptionalMapSafeCall(
                    element,
                    element.textRange,
                    context.getOperands(),
                    flatMap
                )
            }
        }
        return null
    }
}

private val optionalOnlyCalls = setOf("isPresent", "isEmpty", "ifPresent", "ifPresentOrElse", "filter")

/**
 * Whether this `map`/`flatMap` chain goes on with a call that only an `Optional` has. A `?.` there would make the
 * reader expect a nullable value where the code still works with the `Optional`.
 */
internal fun PsiMethodCallExpression.endsInOptionalOnlyCall(): Boolean {
    val reference = parent as? PsiReferenceExpression ?: return false
    if (reference.qualifierExpression != this) return false
    val next = reference.parent as? PsiMethodCallExpression ?: return false
    return when (next.methodExpression.referenceName) {
        "map", "flatMap" -> next.endsInOptionalOnlyCall()
        in optionalOnlyCalls -> true
        else -> false
    }
}
