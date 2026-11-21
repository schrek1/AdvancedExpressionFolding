package com.intellij.advancedExpressionFolding.processor.language.kotlin

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.semantic.kotlin.KotlinMembership
import com.intellij.advancedExpressionFolding.expression.semantic.kotlin.KotlinRange
import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.advancedExpressionFolding.settings.state.IKotlinLanguageState
import com.intellij.openapi.util.TextRange
import com.intellij.psi.CommonClassNames
import com.intellij.psi.JavaTokenType
import com.intellij.psi.PsiBinaryExpression
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiParenthesizedExpression
import com.intellij.psi.PsiPolyadicExpression
import com.intellij.psi.PsiPrefixExpression
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.PsiThisExpression
import com.intellij.psi.PsiVariable
import com.intellij.psi.util.InheritanceUtil

/**
 * Kotlin `in` / `!in`: collection and map membership, and ranges built from two bounds on one variable.
 *
 * Folds only side-effect-free operands (literals, variables, field chains, arithmetic of those): the folded text
 * reorders them, which must not hide a change in evaluation order.
 */
object InOperatorExt : IKotlinLanguageState by State()() {

    fun createMembership(call: PsiMethodCallExpression): Expression? = createMembership(call, call, "in")

    fun createNegatedMembership(negation: PsiPrefixExpression, call: PsiMethodCallExpression): Expression? =
        createMembership(negation, call, "!in")

    fun createRange(parent: PsiElement, lowerBound: PsiBinaryExpression, upperBound: PsiBinaryExpression): Expression? {
        if (!inOperator || lowerBound.operationTokenType != JavaTokenType.GE) {
            return null
        }
        val endInclusive = when (upperBound.operationTokenType) {
            JavaTokenType.LE -> true
            JavaTokenType.LT -> false
            else -> return null
        }
        val start = lowerBound.rOperand ?: return null
        val end = upperBound.rOperand ?: return null
        if (!isSameVariable(lowerBound.lOperand, upperBound.lOperand) || !isSideEffectFree(start) || !isSideEffectFree(end)) {
            return null
        }
        return KotlinRange(
            parent,
            TextRange.create(lowerBound.textRange.startOffset, upperBound.textRange.endOffset),
            lowerBound.operationSign,
            start,
            end,
            endInclusive
        )
    }

    private fun createMembership(folded: PsiExpression, call: PsiMethodCallExpression, operator: String): Expression? {
        if (!inOperator) {
            return null
        }
        val containerClass = when (call.methodExpression.referenceName) {
            "contains" -> CommonClassNames.JAVA_UTIL_COLLECTION
            "containsKey" -> CommonClassNames.JAVA_UTIL_MAP
            else -> return null
        }
        val container = call.methodExpression.qualifierExpression ?: return null
        val element = call.argumentList.expressions.singleOrNull() ?: return null
        val method = call.resolveMethod() ?: return null
        if (!InheritanceUtil.isInheritor(method.containingClass, containerClass)) {
            return null
        }
        if (!isSideEffectFree(container) || !isSideEffectFree(element)) {
            return null
        }
        return KotlinMembership(folded, folded.textRange, element, " $operator ${container.text}")
    }

    private fun isSameVariable(first: PsiExpression, second: PsiExpression): Boolean {
        if (first !is PsiReferenceExpression || second !is PsiReferenceExpression) {
            return false
        }
        if (first.qualifierExpression != null || second.qualifierExpression != null) {
            return false
        }
        val variable = first.resolve() as? PsiVariable ?: return false
        return variable == second.resolve()
    }

    private fun isSideEffectFree(expression: PsiExpression?): Boolean = when (expression) {
        is PsiLiteralExpression, is PsiThisExpression -> true
        is PsiReferenceExpression -> expression.qualifierExpression.let { it == null || isSideEffectFree(it) }
        is PsiParenthesizedExpression -> isSideEffectFree(expression.expression)
        is PsiPrefixExpression -> expression.operationTokenType in PURE_PREFIX_OPERATORS && isSideEffectFree(expression.operand)
        is PsiPolyadicExpression -> expression.operands.all(::isSideEffectFree)
        else -> false
    }

    private val PURE_PREFIX_OPERATORS = setOf(JavaTokenType.MINUS, JavaTokenType.PLUS, JavaTokenType.EXCL, JavaTokenType.TILDE)
}
