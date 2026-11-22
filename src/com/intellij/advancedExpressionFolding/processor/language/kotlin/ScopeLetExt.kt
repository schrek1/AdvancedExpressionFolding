package com.intellij.advancedExpressionFolding.processor.language.kotlin

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.semantic.kotlin.ScopeFunctionExpression
import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.advancedExpressionFolding.settings.state.IKotlinLanguageState
import com.intellij.openapi.util.TextRange
import com.intellij.psi.JavaTokenType
import com.intellij.psi.PsiBinaryExpression
import com.intellij.psi.PsiBlockStatement
import com.intellij.psi.PsiCodeBlock
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiConditionalExpression
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiExpressionStatement
import com.intellij.psi.PsiIfStatement
import com.intellij.psi.PsiJavaToken
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.PsiStatement
import com.intellij.psi.PsiVariable
import com.intellij.psi.SyntaxTraverser
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.PsiUtil

/**
 * Shows a null guard around one use of a variable as `?.let`, keeping the source order:
 * `if (email != null) { info.setEmail(email); }` as `email?.let { info.setEmail(it) }` and
 * `body != null ? toBytes(body) : null` as `body?.let { toBytes(it) }`.
 *
 * The guarded code must read the variable and never write it. Code whose call chain starts at the variable
 * (`email.trim()`) is left to the null-safe `?.` folding.
 */
object ScopeLetExt : IKotlinLanguageState by State()() {

    fun createExpression(ifStatement: PsiIfStatement): Expression? {
        if (!scopeLet) {
            return null
        }
        val match = match(ifStatement) ?: return null
        val semicolon = match.body.lastChild as? PsiJavaToken ?: return null
        if (semicolon.tokenType != JavaTokenType.SEMICOLON) {
            return null
        }
        val lBrace = (ifStatement.thenBranch as? PsiBlockStatement)?.codeBlock?.lBrace
        return expression(
            ifStatement,
            match,
            TextRange(semicolon.textRange.startOffset, ifStatement.textRange.endOffset),
            lBrace,
        )
    }

    fun createExpression(conditional: PsiConditionalExpression): Expression? {
        if (!scopeLet || conditional.elseExpression?.text != "null") {
            return null
        }
        val thenExpression = conditional.thenExpression ?: return null
        val match = match(conditional.condition, thenExpression, thenExpression) ?: return null
        return expression(conditional, match, TextRange(thenExpression.textRange.endOffset, conditional.textRange.endOffset))
    }

    /** Whether [ifStatement] folds as `?.let`, so its braces must not get the single-statement block folding. */
    fun folds(ifStatement: PsiIfStatement): Boolean = scopeLet && match(ifStatement) != null

    private class Match(
        val guarded: PsiReferenceExpression,
        val body: PsiElement,
        val uses: List<PsiReferenceExpression>,
    )

    private fun expression(element: PsiElement, match: Match, closing: TextRange, lBrace: PsiElement? = null): Expression {
        val range = element.textRange
        val guarded = match.guarded.textRange
        val bodyStart = match.body.textRange.startOffset
        // the block's own `{...}` fold starts at `{`, so the opening is split there to nest inside it
        val opening = if (lBrace == null) {
            listOf(TextRange(guarded.endOffset, bodyStart) to "?.let { ")
        } else {
            listOf(
                TextRange(guarded.endOffset, lBrace.textRange.startOffset) to "?.let ",
                TextRange(lBrace.textRange.startOffset, bodyStart) to "{ ",
            )
        }
        val folds = listOf(TextRange(range.startOffset, guarded.startOffset) to "") + opening +
            match.uses.map { it.textRange to "it" } + (closing to " }")
        return ScopeFunctionExpression(element, range, folds.filterNot { (foldRange, _) -> foldRange.isEmpty })
    }

    private fun match(ifStatement: PsiIfStatement): Match? {
        if (ifStatement.elseBranch != null || ifStatement.parent !is PsiCodeBlock ||
            PsiTreeUtil.findChildOfType(ifStatement, PsiComment::class.java) != null
        ) {
            return null
        }
        val statement = singleStatement(ifStatement.thenBranch) ?: return null
        return match(ifStatement.condition, statement, statement.expression)
    }

    private fun match(condition: PsiExpression?, body: PsiElement, bodyExpression: PsiExpression): Match? {
        if (condition !is PsiBinaryExpression || condition.operationTokenType != JavaTokenType.NE ||
            condition.rOperand?.text != "null"
        ) {
            return null
        }
        val guarded = condition.lOperand as? PsiReferenceExpression ?: return null
        if (guarded.qualifierExpression != null) {
            return null
        }
        val variable = guarded.resolve() as? PsiVariable ?: return null
        val uses = SyntaxTraverser.psiTraverser(body)
            .filter(PsiReferenceExpression::class.java)
            .filter { it.qualifierExpression == null && it.isReferenceTo(variable) }
            .toList()
        if (uses.isEmpty() || uses.any { PsiUtil.isAccessedForWriting(it) } || chainRoot(bodyExpression) in uses) {
            return null
        }
        return Match(guarded, body, uses)
    }

    private fun singleStatement(branch: PsiStatement?): PsiExpressionStatement? = when (branch) {
        is PsiBlockStatement -> branch.codeBlock.statements.singleOrNull()
        else -> branch
    } as? PsiExpressionStatement

    private tailrec fun chainRoot(expression: PsiExpression): PsiExpression {
        val qualifier = when (expression) {
            is PsiMethodCallExpression -> expression.methodExpression.qualifierExpression
            is PsiReferenceExpression -> expression.qualifierExpression
            else -> null
        } ?: return expression
        return chainRoot(qualifier)
    }
}
