package com.intellij.advancedExpressionFolding.processor.lombok

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.semantic.HideExpression
import com.intellij.advancedExpressionFolding.expression.semantic.WrapperExpression
import com.intellij.advancedExpressionFolding.processor.exprOnLastChar
import com.intellij.advancedExpressionFolding.processor.group
import com.intellij.advancedExpressionFolding.processor.prevWhiteSpace
import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.advancedExpressionFolding.settings.state.ILombokState
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil

/**
 * Leading null checks of a method's own parameters shown as Lombok `@NonNull` on those parameters.
 *
 * [data.LombokNonNullTestData]
 */
object LombokNonNullExt : ILombokState by State()() {

    private const val NON_NULL = "@NonNull "
    private val CHECK_EXCEPTIONS = setOf("java.lang.NullPointerException", "java.lang.IllegalArgumentException")

    /** Hides a leading parameter check, with the whitespace before it, as one non-nested fold. */
    fun hiddenCheck(statement: PsiStatement): Expression? {
        if (!hides(statement)) {
            return null
        }
        val group = LombokNonNullExt::class.group()
        val hidden = listOfNotNull(statement.prevWhiteSpace(), statement).map { HideExpression(it, group = group) }
        return WrapperExpression(statement, chain = hidden, nested = false)
    }

    /** True when [statement] is a leading parameter check that this fold hides; other folds must leave it alone. */
    fun hides(statement: PsiStatement): Boolean {
        val method = statement.parent?.parent as? PsiMethod ?: return false
        return lombokNonNull && checkedParameter(statement) != null && statement in checkedParameters(method).values
    }

    fun parameterAnnotation(parameter: PsiParameter): Expression? {
        val method = parameter.declarationScope as? PsiMethod ?: return null
        if (parameter !in checkedParameters(method)) {
            return null
        }
        return PsiTreeUtil.prevLeaf(parameter)?.exprOnLastChar(NON_NULL)
    }

    /** The unbroken run of null checks at the start of the body, one per parameter, in body order. */
    private fun checkedParameters(method: PsiMethod): Map<PsiParameter, PsiStatement> {
        val statements = method.body?.statements ?: return emptyMap()
        val parameters = method.parameterList.parameters.toSet()
        val checks = linkedMapOf<PsiParameter, PsiStatement>()
        for (statement in statements) {
            val parameter = checkedParameter(statement)
            if (parameter == null || parameter !in parameters || parameter in checks) {
                break
            }
            checks[parameter] = statement
        }
        return checks
    }

    private fun checkedParameter(statement: PsiStatement): PsiParameter? = when (statement) {
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
        return referencedParameter(arguments[0])
    }

    /** `if (p == null) throw new NullPointerException(...)`, the shape Lombok generates; braces optional. */
    private fun nullGuardedParameter(ifStatement: PsiIfStatement): PsiParameter? {
        if (ifStatement.elseBranch != null) {
            return null
        }
        val thrown = when (val then = ifStatement.thenBranch) {
            is PsiThrowStatement -> then
            is PsiBlockStatement -> then.codeBlock.statements.singleOrNull() as? PsiThrowStatement
            else -> null
        }
        val exception = thrown?.exception as? PsiNewExpression ?: return null
        if (exception.classReference?.qualifiedName !in CHECK_EXCEPTIONS) {
            return null
        }
        val condition = ifStatement.condition as? PsiBinaryExpression ?: return null
        if (condition.operationTokenType != JavaTokenType.EQEQ) {
            return null
        }
        val operands = listOfNotNull(condition.lOperand, condition.rOperand)
        val nullLiteral = operands.firstOrNull { it.isNullLiteral() } ?: return null
        return operands.singleOrNull { it !== nullLiteral }?.let(::referencedParameter)
    }

    private fun PsiExpression.isNullLiteral() = this is PsiLiteralExpression && value == null && text == "null"

    private fun referencedParameter(expression: PsiExpression): PsiParameter? =
        (expression as? PsiReferenceExpression)?.takeIf { it.qualifierExpression == null }?.resolve() as? PsiParameter
}
