package com.intellij.advancedExpressionFolding.processor.methodcall.optional

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.operation.optional.OptionalListOfNotNull
import com.intellij.advancedExpressionFolding.processor.methodcall.Context
import com.intellij.psi.PsiMethodCallExpression

class OptionalStreamMethodCall : AbstractOptionalMethodCall() {
    override val methodNames by lazy { listOf("stream") }

    override fun onNoArguments(element: PsiMethodCallExpression, context: Context): Expression =
        OptionalListOfNotNull(element, context.qualifierExpr)
}
