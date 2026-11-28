package com.intellij.advancedExpressionFolding.processor.declaration

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.semantic.HideExpression
import com.intellij.advancedExpressionFolding.expression.semantic.WrapperExpression
import com.intellij.advancedExpressionFolding.processor.declaration.JakartaConstraintExt.Constraint
import com.intellij.advancedExpressionFolding.processor.declaration.JakartaConstraintExt.Marker
import com.intellij.advancedExpressionFolding.processor.declaration.JakartaConstraintExt.Size
import com.intellij.advancedExpressionFolding.processor.expr
import com.intellij.advancedExpressionFolding.processor.group
import com.intellij.advancedExpressionFolding.processor.lombok.LombokNonNullExt
import com.intellij.advancedExpressionFolding.processor.nextWhiteSpace
import com.intellij.advancedExpressionFolding.processor.prevWhiteSpace
import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.advancedExpressionFolding.settings.state.ILombokState
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil

/**
 * The unbroken run of parameter checks at the start of a method body, shown as annotations on the checked parameters:
 * null checks as Lombok `@NonNull` (option `lombokNonNull`), other checks as Jakarta Validation constraints (option
 * `jakartaConstraints`). A check whose option is off stays visible but does not end the run.
 */
object LeadingParameterChecksExt : ILombokState by State()() {

    private val NON_NULL = Marker("NonNull")

    private data class Check(
        val parameter: PsiParameter,
        val statement: PsiStatement,
        val constraint: Constraint,
        val enabled: Boolean,
    )

    /**
     * Hides a leading parameter check as one non-nested fold. Normally the check's own line (indentation, statement,
     * line break): a fold starting right after the `{` would swallow the inlay anchored there. A check sharing its line
     * with other code hides the whitespace before it instead.
     */
    fun hiddenCheck(statement: PsiStatement): Expression? {
        if (!hides(statement)) {
            return null
        }
        val group = LeadingParameterChecksExt::class.group()
        val before = statement.prevWhiteSpace()
        val after = statement.nextWhiteSpace()
        val lineBreakBefore = before?.text?.lastIndexOf('\n') ?: -1
        val lineBreakAfter = after?.text?.indexOf('\n') ?: -1
        val hidden = if (before != null && after != null && lineBreakBefore >= 0 && lineBreakAfter >= 0) {
            val range = TextRange(before.textRange.startOffset + lineBreakBefore + 1, after.textRange.startOffset + lineBreakAfter + 1)
            listOf(HideExpression(statement, range, group))
        } else {
            listOfNotNull(before, statement).map { HideExpression(it, group = group) }
        }
        return WrapperExpression(statement, chain = hidden, nested = false)
    }

    /** True when [statement] is a leading parameter check that this fold hides; other folds must leave it alone. */
    fun hides(statement: PsiStatement): Boolean {
        val method = statement.parent?.parent as? PsiMethod ?: return false
        return leadingChecks(method).any { it.statement == statement && it.enabled }
    }

    fun parameterAnnotation(parameter: PsiParameter): Expression? {
        val method = parameter.declarationScope as? PsiMethod ?: return null
        val constraints = leadingChecks(method).filter { it.parameter == parameter && it.enabled }.map { it.constraint }
        if (constraints.isEmpty()) {
            return null
        }
        val anchor = PsiTreeUtil.prevLeaf(parameter) ?: return null
        return anchor.expr(anchor.text.last() + annotationText(constraints), textRange = TextRange.from(anchor.textRange.endOffset - 1, 1))
    }

    /** One annotation per constraint in body order; a `@Size` min and max check merge into one `@Size`. */
    private fun annotationText(constraints: List<Constraint>): String {
        val sizes = constraints.filterIsInstance<Size>()
        val size = Size(sizes.firstNotNullOfOrNull { it.min }, sizes.firstNotNullOfOrNull { it.max })
        return constraints.distinctBy { if (it is Size) "Size" else it.key }
            .joinToString(" ", postfix = " ") { if (it is Size) size.text else it.text }
    }

    /** Checks in body order until the first statement that is not one, or repeats a parameter's earlier check. */
    private fun leadingChecks(method: PsiMethod): List<Check> {
        val statements = method.body?.statements ?: return emptyList()
        val parameters = method.parameterList.parameters.toSet()
        val checks = mutableListOf<Check>()
        for (statement in statements) {
            val check = check(statement)
            if (check == null || check.parameter !in parameters ||
                checks.any { it.parameter == check.parameter && it.constraint.key == check.constraint.key }
            ) {
                break
            }
            checks += check
        }
        return checks
    }

    private fun check(statement: PsiStatement): Check? {
        LombokNonNullExt.nullCheckedParameter(statement)?.let {
            return Check(it, statement, NON_NULL, lombokNonNull)
        }
        val (parameter, constraint) = JakartaConstraintExt.constraint(statement) ?: return null
        return Check(parameter, statement, constraint, jakartaConstraints)
    }

    /** Qualified name of the exception an `if` without `else` only throws; braces optional. */
    fun guardException(ifStatement: PsiIfStatement): String? {
        if (ifStatement.elseBranch != null) {
            return null
        }
        val thrown = when (val then = ifStatement.thenBranch) {
            is PsiThrowStatement -> then
            is PsiBlockStatement -> then.codeBlock.statements.singleOrNull() as? PsiThrowStatement
            else -> null
        }
        return (thrown?.exception as? PsiNewExpression)?.classReference?.qualifiedName
    }

    fun referencedParameter(expression: PsiExpression?): PsiParameter? =
        (expression as? PsiReferenceExpression)?.takeIf { it.qualifierExpression == null }?.resolve() as? PsiParameter
}
