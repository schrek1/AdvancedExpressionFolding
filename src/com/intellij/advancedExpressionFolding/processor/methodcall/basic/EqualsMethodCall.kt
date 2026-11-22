package com.intellij.advancedExpressionFolding.processor.methodcall.basic

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.operation.basic.Equal
import com.intellij.advancedExpressionFolding.processor.methodcall.AbstractMethodCall
import com.intellij.advancedExpressionFolding.processor.methodcall.Context
import com.intellij.advancedExpressionFolding.processor.methodcall.NeedsQualifier
import com.intellij.psi.CommonClassNames
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiModifier
import com.intellij.psi.PsiTypes

class EqualsMethodCall : AbstractMethodCall(), NeedsQualifier {
    override fun canExecute() = comparingExpressionsCollapse

    override val methodNames by lazy { listOf("equals") }

    override fun onSingleArgument(
        element: PsiMethodCallExpression,
        context: Context,
        argument: PsiExpression,
        argumentExpression: Expression
    ): Expression? = if (overridesObjectEquals(context.method)) {
        Equal(
            element,
            element.textRange,
            context.getOperands()
        )
    } else {
        null
    }

    // Any class may override Object.equals (collections, records, domain types); an equals(Foo) overload is a
    // different method with its own meaning, so it stays unfolded.
    private fun overridesObjectEquals(method: PsiMethod): Boolean =
        !method.hasModifierProperty(PsiModifier.STATIC) &&
            method.returnType == PsiTypes.booleanType() &&
            method.parameterList.parameters.singleOrNull()?.type?.equalsToText(CommonClassNames.JAVA_LANG_OBJECT) == true
}
