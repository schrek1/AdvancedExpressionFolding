package com.intellij.advancedExpressionFolding.processor.controlflow

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.processor.end
import com.intellij.advancedExpressionFolding.processor.expr
import com.intellij.advancedExpressionFolding.processor.exprWrap
import com.intellij.advancedExpressionFolding.processor.group
import com.intellij.advancedExpressionFolding.processor.start
import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.advancedExpressionFolding.settings.state.IControlFlowState
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.JavaTokenType
import com.intellij.psi.PsiBreakStatement
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiExpressionStatement
import com.intellij.psi.PsiJavaToken
import com.intellij.psi.PsiReturnStatement
import com.intellij.psi.PsiStatement
import com.intellij.psi.PsiSwitchLabelStatement
import com.intellij.psi.PsiSwitchStatement
import com.intellij.psi.PsiThrowStatement
import com.intellij.psi.util.PsiTreeUtil

/**
 * Shows a classic `switch` whose every case is a single statement ended by `break` (or a lone `return`/`throw`)
 * as an arrow switch. Fall-through, multi-statement cases and comments keep the switch as it is.
 */
object SwitchArrowExt : IControlFlowState by State()() {

    private class Case(val labels: List<PsiSwitchLabelStatement>, val body: List<PsiStatement>)

    fun createExpression(element: PsiSwitchStatement, extra: Expression?): Expression? {
        if (!switchArrow) {
            return null
        }
        val switchBody = element.body ?: return null
        if (PsiTreeUtil.findChildOfType(switchBody, PsiComment::class.java) != null) {
            return null
        }
        val cases = cases(switchBody.statements.toList()) ?: return null
        if (cases.isEmpty() || cases.withIndex().any { (index, case) -> !isFoldable(case, index == cases.lastIndex) }) {
            return null
        }

        val group = SwitchArrowExt::class.group()
        val parts = mutableListOf(extra)
        cases.forEach { case -> parts += caseParts(element, case, group).ifEmpty { return null } }
        return parts.exprWrap(element)
    }

    private fun cases(statements: List<PsiStatement>): List<Case>? {
        if (statements.firstOrNull() !is PsiSwitchLabelStatement) {
            return null
        }
        val cases = mutableListOf<Case>()
        var index = 0
        while (index < statements.size) {
            val labels = statements.drop(index)
                .takeWhile { it is PsiSwitchLabelStatement }
                .filterIsInstance<PsiSwitchLabelStatement>()
            index += labels.size
            val body = statements.drop(index).takeWhile { it !is PsiSwitchLabelStatement }
            index += body.size
            cases += Case(labels, body)
        }
        return cases
    }

    private fun isFoldable(case: Case, last: Boolean): Boolean {
        val labelsOk = case.labels.all { label ->
            label.guardExpression == null &&
                if (label.isDefaultCase) {
                    case.labels.size == 1 && label.caseLabelElementList == null
                } else {
                    label.caseLabelElementList?.elements?.all { it is PsiExpression } == true
                }
        }
        if (!labelsOk) {
            return false
        }
        val body = case.body
        return when (body.size) {
            1 -> body[0] is PsiReturnStatement || body[0] is PsiThrowStatement ||
                (last && body[0] is PsiExpressionStatement)
            2 -> body[0] is PsiExpressionStatement && (body[1] as? PsiBreakStatement)?.labelIdentifier == null
            else -> false
        }
    }

    private fun caseParts(switch: PsiSwitchStatement, case: Case, group: FoldingGroup): List<Expression?> {
        val parts = mutableListOf<Expression?>()
        val statement = case.body.first()
        val labels = case.labels
        labels.zipWithNext().forEach { (label, next) ->
            val colon = label.colon() ?: return emptyList()
            val nextValue = next.caseLabelElementList ?: return emptyList()
            parts += switch.expr(", ", group, TextRange(colon.start(), nextValue.start()))
        }
        val colon = labels.last().colon() ?: return emptyList()
        parts += switch.expr(" -> ", group, TextRange(colon.start(), statement.start()))
        if (case.body.size == 2) {
            parts += switch.expr("", group, TextRange(statement.end(), case.body.last().end()))
        }
        return parts
    }

    private fun PsiSwitchLabelStatement.colon(): PsiElement? =
        lastChild?.takeIf { it is PsiJavaToken && it.tokenType == JavaTokenType.COLON }
}
