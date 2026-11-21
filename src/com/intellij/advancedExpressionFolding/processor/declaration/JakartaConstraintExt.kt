package com.intellij.advancedExpressionFolding.processor.declaration

import com.intellij.psi.*
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.InheritanceUtil
import com.intellij.psi.util.PsiUtil

/**
 * Leading argument checks that read as a Jakarta Validation constraint on the checked parameter.
 *
 * [data.JakartaConstraintsTestData]
 */
object JakartaConstraintExt {

    sealed interface Constraint {
        /** Two checks with the same key on one parameter are ambiguous; the leading run stops at the second. */
        val key: String
        val text: String
    }

    data class Marker(val name: String) : Constraint {
        override val key get() = name
        override val text get() = "@$name"
    }

    data class Bound(val name: String, val value: Long) : Constraint {
        override val key get() = name
        override val text get() = "@$name($value)"
    }

    data class Size(val min: Long? = null, val max: Long? = null) : Constraint {
        override val key get() = if (min != null) "Size.min" else "Size.max"
        override val text get() = "@Size(" + listOfNotNull(min?.let { "min = $it" }, max?.let { "max = $it" }).joinToString(", ") + ")"
    }

    private val INTEGRAL_TYPES = setOf(PsiTypes.intType(), PsiTypes.longType(), PsiTypes.shortType(), PsiTypes.byteType())
    private val FLOATING_TYPES = setOf(PsiTypes.doubleType(), PsiTypes.floatType())
    private val FLIPPED = mapOf(
        JavaTokenType.LT to JavaTokenType.GT,
        JavaTokenType.LE to JavaTokenType.GE,
        JavaTokenType.GT to JavaTokenType.LT,
        JavaTokenType.GE to JavaTokenType.LE,
    )

    /** `if (<violation of p>) throw new IllegalArgumentException(...)`, the violation being a comparison or emptiness check. */
    fun constraint(statement: PsiStatement): Pair<PsiParameter, Constraint>? {
        val ifStatement = statement as? PsiIfStatement ?: return null
        if (LeadingParameterChecksExt.guardException(ifStatement) != "java.lang.IllegalArgumentException") {
            return null
        }
        return when (val condition = PsiUtil.skipParenthesizedExprDown(ifStatement.condition)) {
            is PsiMethodCallExpression -> emptiness(condition)
            is PsiBinaryExpression -> comparison(condition)
            else -> null
        }
    }

    /** `s.isBlank()` → `@NotBlank`; `s.isEmpty()` on a char sequence, collection or map → `@NotEmpty`. */
    private fun emptiness(call: PsiMethodCallExpression): Pair<PsiParameter, Constraint>? {
        if (!call.argumentList.isEmpty) {
            return null
        }
        val parameter = LeadingParameterChecksExt.referencedParameter(call.methodExpression.qualifierExpression)
            ?: return null
        val type = parameter.type
        val constraint = when (call.methodExpression.referenceName) {
            "isBlank" -> Marker("NotBlank").takeIf { type.equalsToText(CommonClassNames.JAVA_LANG_STRING) }
            "isEmpty" -> Marker("NotEmpty").takeIf { type.isSized() }
            else -> null
        }
        return constraint?.let { parameter to it }
    }

    /** `p < n` and friends on a number, or `p.length()` / `p.size()` against a literal; either operand order. */
    private fun comparison(condition: PsiBinaryExpression): Pair<PsiParameter, Constraint>? {
        val left = PsiUtil.skipParenthesizedExprDown(condition.lOperand)
        val right = PsiUtil.skipParenthesizedExprDown(condition.rOperand)
        val operator = condition.operationTokenType
        if (operator !in FLIPPED) {
            return null
        }
        integerLiteral(right)?.let { bound -> return measured(left, operator, bound) }
        integerLiteral(left)?.let { bound -> return measured(right, FLIPPED.getValue(operator), bound) }
        if (isZero(right)) return numberSign(left, operator)
        if (isZero(left)) return numberSign(right, FLIPPED.getValue(operator))
        return null
    }

    /** `measured <operator> bound` with an integral bound. */
    private fun measured(measured: PsiExpression?, operator: IElementType, bound: Long): Pair<PsiParameter, Constraint>? {
        if (bound == 0L) {
            numberSign(measured, operator)?.let { return it }
        }
        LeadingParameterChecksExt.referencedParameter(measured)?.let { parameter ->
            if (PsiPrimitiveType.getOptionallyUnboxedType(parameter.type) !in INTEGRAL_TYPES) {
                return null
            }
            val constraint = when (operator) {
                JavaTokenType.LT -> Bound("Min", bound)
                JavaTokenType.LE -> Bound("Min", bound + 1)
                JavaTokenType.GT -> Bound("Max", bound)
                else -> Bound("Max", bound - 1)
            }
            return parameter to constraint
        }
        val parameter = sizedParameter(measured) ?: return null
        val constraint = when (operator) {
            JavaTokenType.LT -> Size(min = bound)
            JavaTokenType.LE -> Size(min = bound + 1)
            JavaTokenType.GT -> Size(max = bound)
            else -> Size(max = bound - 1)
        }
        return parameter to constraint
    }

    /** `p <operator> 0` on any number: `p <= 0` → `@Positive`, `p < 0` → `@PositiveOrZero`, and the negative pair. */
    private fun numberSign(measured: PsiExpression?, operator: IElementType): Pair<PsiParameter, Constraint>? {
        val parameter = LeadingParameterChecksExt.referencedParameter(measured) ?: return null
        val type = PsiPrimitiveType.getOptionallyUnboxedType(parameter.type)
        if (type !in INTEGRAL_TYPES && type !in FLOATING_TYPES) {
            return null
        }
        val name = when (operator) {
            JavaTokenType.LE -> "Positive"
            JavaTokenType.LT -> "PositiveOrZero"
            JavaTokenType.GE -> "Negative"
            else -> "NegativeOrZero"
        }
        return parameter to Marker(name)
    }

    /** `p.length()` on a char sequence or `p.size()` on a collection or map. */
    private fun sizedParameter(expression: PsiExpression?): PsiParameter? {
        val call = expression as? PsiMethodCallExpression ?: return null
        if (!call.argumentList.isEmpty || call.methodExpression.referenceName !in setOf("length", "size")) {
            return null
        }
        return LeadingParameterChecksExt.referencedParameter(call.methodExpression.qualifierExpression)
            ?.takeIf { it.type.isSized() }
    }

    private fun PsiType.isSized() = listOf(
        CommonClassNames.JAVA_LANG_CHAR_SEQUENCE,
        CommonClassNames.JAVA_UTIL_COLLECTION,
        CommonClassNames.JAVA_UTIL_MAP,
    ).any { InheritanceUtil.isInheritor(this, it) }

    /** An `int` or `long` literal, optionally negated; a constant name or arithmetic is left alone. */
    private fun integerLiteral(expression: PsiExpression?): Long? {
        val negated = expression is PsiPrefixExpression && expression.operationTokenType == JavaTokenType.MINUS
        val literal = (if (negated) (expression as PsiPrefixExpression).operand else expression) as? PsiLiteralExpression
        val value = when (val raw = literal?.value) {
            is Int -> raw.toLong()
            is Long -> raw
            else -> return null
        }
        return if (negated) -value else value
    }

    private fun isZero(expression: PsiExpression?) =
        ((expression as? PsiLiteralExpression)?.value as? Number)?.toDouble() == 0.0
}
