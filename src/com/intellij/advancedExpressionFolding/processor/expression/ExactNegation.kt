package com.intellij.advancedExpressionFolding.processor.expression

import com.intellij.advancedExpressionFolding.processor.end
import com.intellij.advancedExpressionFolding.processor.start
import com.intellij.advancedExpressionFolding.processor.toTextRange
import com.intellij.openapi.util.TextRange
import com.intellij.psi.JavaTokenType
import com.intellij.psi.PsiArrayAccessExpression
import com.intellij.psi.PsiBinaryExpression
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiParenthesizedExpression
import com.intellij.psi.PsiPolyadicExpression
import com.intellij.psi.PsiPrefixExpression
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.util.TypeConversionUtil

/**
 * Shows the logical negation of a condition without wrapping it in `!(...)`, when that is exactly equivalent.
 *
 * @property prefix text to show right before the condition (`!` or nothing)
 * @property gaps source ranges to show with other text (flipped operators, removed `!` and parentheses)
 * @property shown sub-expressions shown as written, each folding on its own
 */
class ExactNegation private constructor(
    val prefix: String,
    val gaps: List<Pair<TextRange, String>>,
    val shown: List<PsiExpression>,
) {
    companion object {
        // `!(a == b)` is `a != b` for every operand type.
        private val EQUALITY = mapOf(JavaTokenType.EQEQ to "!=", JavaTokenType.NE to "==")

        // Only exact for integral operands: with a NaN operand `!(x <= 0)` is true but `x > 0` is false.
        private val RELATIONAL = mapOf(
            JavaTokenType.LT to ">=", JavaTokenType.GE to "<",
            JavaTokenType.GT to "<=", JavaTokenType.LE to ">",
        )

        private val DE_MORGAN = mapOf(JavaTokenType.ANDAND to "||", JavaTokenType.OROR to "&&")

        /**
         * @param condition a `boolean` expression
         * @return the exact negation, or `null` when only `!(condition)` is exact
         */
        fun of(condition: PsiExpression): ExactNegation? = negate(condition, topLevel = true)

        private fun negate(expression: PsiExpression, topLevel: Boolean): ExactNegation? = when (expression) {
            is PsiPrefixExpression -> negateNot(expression, topLevel)
            is PsiParenthesizedExpression -> negateParenthesized(expression)
            is PsiBinaryExpression -> negateComparison(expression) ?: negateDeMorgan(expression)
            is PsiPolyadicExpression -> negateDeMorgan(expression)
            is PsiReferenceExpression, is PsiMethodCallExpression, is PsiArrayAccessExpression, is PsiLiteralExpression ->
                ExactNegation("!", emptyList(), listOf(expression))

            else -> null
        }

        private fun negateNot(expression: PsiPrefixExpression, topLevel: Boolean): ExactNegation? {
            val operand = expression.operand
            if (expression.operationTokenType != JavaTokenType.EXCL || operand == null) {
                return null
            }
            val gaps = mutableListOf(expression.operationSign.textRange to "")
            // Nested parentheses stay: `a || !(b || c)` negates to `!a && (b || c)`.
            val parenthesized = (operand as? PsiParenthesizedExpression)
                ?.takeIf { topLevel && it.expression != null && it.closes() }
            if (parenthesized == null) {
                return ExactNegation("", gaps, listOf(operand))
            }
            gaps += parenthesized.firstChild.textRange to ""
            gaps += parenthesized.lastChild.textRange to ""
            return ExactNegation("", gaps, listOf(parenthesized.expression!!))
        }

        private fun negateParenthesized(expression: PsiParenthesizedExpression): ExactNegation? {
            val inner = expression.expression?.takeIf { expression.closes() } ?: return null
            val negated = negate(inner, topLevel = false) ?: return null
            if (negated.prefix.isEmpty()) {
                return negated
            }
            val lParen = expression.firstChild.textRange
            return ExactNegation("", listOf(lParen to "(${negated.prefix}") + negated.gaps, negated.shown)
        }

        private fun negateComparison(expression: PsiBinaryExpression): ExactNegation? {
            val left = expression.lOperand
            val right = expression.rOperand ?: return null
            val token = expression.operationTokenType
            val flipped = EQUALITY[token]
                ?: RELATIONAL[token]?.takeIf { left.isIntegral() && right.isIntegral() }
                ?: return null
            return ExactNegation("", listOf(expression.operationSign.textRange to flipped), listOf(left, right))
        }

        private fun negateDeMorgan(expression: PsiPolyadicExpression): ExactNegation? {
            val flipped = DE_MORGAN[expression.operationTokenType] ?: return null
            val operands = expression.operands
            // `a || b && c` would negate to `!a && (!b || !c)`: the parentheses cannot be added by a fold.
            if (operands.any { it is PsiPolyadicExpression && it.operationTokenType in DE_MORGAN }) {
                return null
            }
            val negated = operands.map { negate(it, topLevel = false) ?: return null }
            val gaps = mutableListOf<Pair<TextRange, String>>()
            negated.forEachIndexed { index, operand ->
                if (index > 0) {
                    gaps += operatorGap(expression, operands[index], flipped, operand.prefix) ?: return null
                }
                gaps += operand.gaps
            }
            return ExactNegation(negated.first().prefix, gaps, negated.flatMap { it.shown })
        }

        /** The operator before [operand], flipped; a `!` for the operand takes the place of the whitespace after it. */
        private fun operatorGap(
            expression: PsiPolyadicExpression,
            operand: PsiExpression,
            flipped: String,
            prefix: String,
        ): Pair<TextRange, String>? {
            val token = expression.getTokenBeforeOperand(operand) ?: return null
            if (prefix.isEmpty()) {
                return token.textRange to flipped
            }
            val separator = if (operand.start() > token.end()) " " else ""
            return (token.start() to operand.start()).toTextRange() to "$flipped$separator$prefix"
        }

        private fun PsiParenthesizedExpression.closes() = lastChild.text == ")"

        private fun PsiExpression.isIntegral(): Boolean = TypeConversionUtil.isIntegralNumberType(type)
    }
}
