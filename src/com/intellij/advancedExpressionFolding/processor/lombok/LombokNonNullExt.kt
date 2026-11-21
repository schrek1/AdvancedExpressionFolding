package com.intellij.advancedExpressionFolding.processor.lombok

import com.intellij.advancedExpressionFolding.processor.declaration.LeadingParameterChecksExt
import com.intellij.psi.*

/**
 * Parameter null checks that read as Lombok `@NonNull`; [LeadingParameterChecksExt] folds the leading ones.
 *
 * [data.LombokNonNullTestData]
 */
object LombokNonNullExt {

    private val CHECK_EXCEPTIONS = setOf("java.lang.NullPointerException", "java.lang.IllegalArgumentException")

    fun nullCheckedParameter(statement: PsiStatement): PsiParameter? = when (statement) {
        is PsiExpressionStatement -> requireNonNullArgument(statement.expression)
        is PsiIfStatement -> nullGuardedParameter(statement)
        else -> null
    }

    /** `Objects.requireNonNull(p)` or `Objects.requireNonNull(p, "message")`. */
    private fun requireNonNullArgument(expression: PsiExpression): PsiParameter? {
        val call = expression as? PsiMethodCallExpression ?: return null
        if (call.methodExpression.referenceName != "requireNonNull") {
            return null
        }
        val arguments = call.argumentList.expressions
        val messageIsString = arguments.getOrNull(1)?.type?.equalsToText(CommonClassNames.JAVA_LANG_STRING) ?: true
        if (arguments.size !in 1..2 || !messageIsString) {
            return null
        }
        if (call.resolveMethod()?.containingClass?.qualifiedName != "java.util.Objects") {
            return null
        }
        return LeadingParameterChecksExt.referencedParameter(arguments[0])
    }

    /** `if (p == null) throw new NullPointerException(...)`, the shape Lombok generates; braces optional. */
    private fun nullGuardedParameter(ifStatement: PsiIfStatement): PsiParameter? {
        if (LeadingParameterChecksExt.guardException(ifStatement) !in CHECK_EXCEPTIONS) {
            return null
        }
        val condition = ifStatement.condition as? PsiBinaryExpression ?: return null
        if (condition.operationTokenType != JavaTokenType.EQEQ) {
            return null
        }
        val operands = listOfNotNull(condition.lOperand, condition.rOperand)
        val nullLiteral = operands.firstOrNull { it.isNullLiteral() } ?: return null
        return operands.singleOrNull { it !== nullLiteral }?.let(LeadingParameterChecksExt::referencedParameter)
    }

    private fun PsiExpression.isNullLiteral() = this is PsiLiteralExpression && value == null && text == "null"
}
