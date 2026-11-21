package com.intellij.advancedExpressionFolding.processor.methodcall.collection

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.operation.collection.GetOrDefault
import com.intellij.advancedExpressionFolding.processor.methodcall.AbstractMethodCall
import com.intellij.advancedExpressionFolding.processor.methodcall.Context
import com.intellij.advancedExpressionFolding.processor.methodcall.NeedsQualifier
import com.intellij.psi.PsiAssignmentExpression
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiExpressionList
import com.intellij.psi.PsiLambdaExpression
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiReturnStatement
import com.intellij.psi.PsiVariable

class MapGetOrDefaultMethodCall : AbstractMethodCall(), NeedsQualifier {

    override fun canExecute() = getOrDefaultElvis

    override val methodNames by lazy { listOf("getOrDefault") }

    override fun onTwoArguments(
        element: PsiMethodCallExpression,
        context: Context,
        a1: PsiExpression,
        a2: PsiExpression,
        a1Expression: Expression,
        a2Expression: Expression
    ): Expression? {
        if (!standsAlone(element)) {
            return null
        }
        return GetOrDefault(element, element.textRange, context.qualifierExpr, a1Expression, a2Expression)
    }

    companion object {
        /**
         * `?:` binds looser than any operator or call around it (`map[k] ?: 0 + 1` would misread), so the fold
         * applies only where the call stands alone. Parentheses do not help: castExpressionsCollapse hides them.
         */
        fun standsAlone(call: PsiMethodCallExpression) = when (call.parent) {
            is PsiVariable, is PsiAssignmentExpression, is PsiReturnStatement, is PsiExpressionList,
            is PsiLambdaExpression -> true
            else -> false
        }
    }
}
