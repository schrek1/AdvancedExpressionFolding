package com.intellij.advancedExpressionFolding.processor.language.kotlin

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.semantic.SimpleExpression
import com.intellij.advancedExpressionFolding.expression.semantic.WrapperExpression
import com.intellij.advancedExpressionFolding.processor.core.BuildExpressionExt
import com.intellij.advancedExpressionFolding.processor.end
import com.intellij.advancedExpressionFolding.processor.group
import com.intellij.advancedExpressionFolding.processor.start
import com.intellij.advancedExpressionFolding.processor.util.Helper
import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.advancedExpressionFolding.settings.state.IKotlinLanguageState
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.JavaTokenType
import com.intellij.psi.PsiBinaryExpression
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiPolyadicExpression
import com.intellij.psi.PsiPrefixExpression
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.PsiThisExpression
import com.intellij.psi.PsiVariable
import com.intellij.psi.tree.IElementType

/**
 * Folds `x == null || x.isEmpty()` into `x.isNullOrEmpty()` and `x != null && !x.isEmpty()` into `x.isNotEmpty()`
 * (a non-empty value is never null); `length()` / `size()` compared with `0` count as the emptiness check too.
 *
 * Only a plain local, parameter or field (`x` or `this.x`) qualifies, so the fold never hides a second call.
 */
object NullOrEmptyExt : IKotlinLanguageState by State()() {

    fun createExpression(element: PsiPolyadicExpression, document: Document): Expression? {
        if (!nullOrEmpty) {
            return null
        }
        val operands = element.operands
        val folds = mutableListOf<Expression>()
        val foldedOperands = mutableSetOf<PsiExpression>()
        var index = 0
        while (index < operands.size - 1) {
            val nullCheck = operands[index]
            val emptinessCheck = operands[index + 1]
            val token = element.getTokenBeforeOperand(emptinessCheck)?.tokenType
            val match = token?.let { match(nullCheck, it, emptinessCheck) }
            if (match == null) {
                index++
                continue
            }
            folds += foldsOf(element, nullCheck, emptinessCheck, match, token == JavaTokenType.ANDAND)
            foldedOperands += nullCheck
            foldedOperands += emptinessCheck
            index += 2
        }
        if (folds.isEmpty()) {
            return null
        }
        // not nested: a fold inside a folded pair (e.g. `isEmpty()` read as `empty`) would cover the new method name
        val untouched = mutableListOf<Expression>()
        operands.filterNot { it in foldedOperands }.forEach { operand ->
            val expression = BuildExpressionExt.getNonSyntheticExpression(operand, document)
            expression?.let { untouched += it }
            if (expression == null || expression.isNested()) {
                Helper.findChildExpressions(operand, untouched, document)
            }
        }
        return WrapperExpression(element, element.textRange, folds + untouched, nested = false)
    }

    /**
     * Hides as little as possible: the null check with a negating `!`, the method name (`isEmpty` / `size` /
     * `length` read as `isNullOrEmpty` / `isNotEmpty`) and a `== 0` / `> 0` comparison. The variable, the dot and the
     * parentheses stay code.
     */
    private fun foldsOf(
        element: PsiPolyadicExpression,
        nullCheck: PsiExpression,
        emptinessCheck: PsiExpression,
        call: PsiMethodCallExpression,
        negated: Boolean
    ): List<Expression> {
        val group = NullOrEmptyExt::class.group()
        val receiver = call.methodExpression.qualifierExpression ?: return emptyList()
        val methodName = call.methodExpression.referenceNameElement ?: return emptyList()
        val folds = mutableListOf<Expression>(
            SimpleExpression(element, TextRange.create(nullCheck.start(), receiver.start()), "", group),
            SimpleExpression(element, methodName.textRange, if (negated) "isNotEmpty" else "isNullOrEmpty", group)
        )
        if (emptinessCheck is PsiBinaryExpression) {
            folds += SimpleExpression(element, TextRange.create(call.end(), emptinessCheck.end()), "", group)
        }
        return folds
    }

    /** @return the emptiness call (`x.isEmpty()`, `x.size()`, `x.length()`) when the pair is a null-or-empty check */
    private fun match(nullCheck: PsiExpression, token: IElementType, emptinessCheck: PsiExpression): PsiMethodCallExpression? {
        val negated = when (token) {
            JavaTokenType.OROR -> false
            JavaTokenType.ANDAND -> true
            else -> return null
        }
        val variable = nullComparedVariable(nullCheck, if (negated) JavaTokenType.NE else JavaTokenType.EQEQ)
            ?: return null
        val call = emptinessCall(emptinessCheck, negated) ?: return null
        val receiver = (call.methodExpression.qualifierExpression as? PsiReferenceExpression)
            ?.takeIf { it.isPlainVariable() } ?: return null
        return call.takeIf { variable.resolve() == receiver.resolve() }
    }

    private fun nullComparedVariable(check: PsiExpression, operator: IElementType): PsiReferenceExpression? {
        val binary = check as? PsiBinaryExpression ?: return null
        if (binary.operationTokenType != operator) {
            return null
        }
        val right = binary.rOperand ?: return null
        val variable = when {
            right.isNullLiteral() -> binary.lOperand
            binary.lOperand.isNullLiteral() -> right
            else -> null
        }
        return (variable as? PsiReferenceExpression)?.takeIf { it.isPlainVariable() }
    }

    private fun emptinessCall(check: PsiExpression, negated: Boolean): PsiMethodCallExpression? =
        when (check) {
            is PsiMethodCallExpression -> check.takeIf { !negated && it.isCallOf("isEmpty") }
            is PsiPrefixExpression -> (check.operand as? PsiMethodCallExpression)?.takeIf {
                negated && check.operationTokenType == JavaTokenType.EXCL && it.isCallOf("isEmpty")
            }
            is PsiBinaryExpression -> sizeCall(check, negated)
            else -> null
        }

    private fun sizeCall(check: PsiBinaryExpression, negated: Boolean): PsiMethodCallExpression? {
        if (check.rOperand?.text != "0") {
            return null
        }
        val operators = if (negated) setOf(JavaTokenType.NE, JavaTokenType.GT) else setOf(JavaTokenType.EQEQ)
        if (check.operationTokenType !in operators) {
            return null
        }
        return (check.lOperand as? PsiMethodCallExpression)?.takeIf { it.isCallOf("length") || it.isCallOf("size") }
    }

    private fun PsiMethodCallExpression.isCallOf(name: String) =
        methodExpression.referenceName == name && argumentList.isEmpty

    private fun PsiExpression.isNullLiteral() = this is PsiLiteralExpression && text == "null"

    private fun PsiReferenceExpression.isPlainVariable(): Boolean {
        val qualifier = qualifierExpression
        val plainQualifier = qualifier == null || qualifier is PsiThisExpression && qualifier.qualifier == null
        return plainQualifier && resolve() is PsiVariable
    }
}
