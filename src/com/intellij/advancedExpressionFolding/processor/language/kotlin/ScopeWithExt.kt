package com.intellij.advancedExpressionFolding.processor.language.kotlin

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.semantic.kotlin.ScopeFunctionExpression
import com.intellij.advancedExpressionFolding.processor.declaration.SetterNamedArgumentsExt
import com.intellij.advancedExpressionFolding.processor.methodcall.MethodCallExpressionExt
import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.advancedExpressionFolding.settings.state.IKotlinLanguageState
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.JavaTokenType
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiExpressionStatement
import com.intellij.psi.PsiJavaToken
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiModifier
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.PsiVariable
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.SyntaxTraverser
import com.intellij.psi.util.PsiUtil

/**
 * Shows three or more consecutive calls on the same receiver, `order.validate(); order.save(); order.notify();`,
 * as one line `with(order) { validate(); save(); notify() }`, keeping the source order.
 *
 * The run is single-line `r.call(...);` statements on one unqualified variable, each on the next line: a blank line,
 * a comment, any other statement or a write to `r` ends it. Static receivers (loggers), setters claimed by
 * [SetterNamedArgumentsExt] and calls whose own folding hides `r.` never join; the other calls keep their own folding.
 */
object ScopeWithExt : IKotlinLanguageState by State()() {

    private const val MIN_CALLS = 3

    fun createExpression(statement: PsiExpressionStatement, document: Document): Expression? {
        if (!scopeWith) {
            return null
        }
        val run = run(statement, document) ?: return null
        if (run.first() != statement) {
            return null
        }
        val last = run.last()
        val semicolon = last.lastChild as? PsiJavaToken ?: return null
        if (semicolon.tokenType != JavaTokenType.SEMICOLON) {
            return null
        }
        val receiver = run.first().call.methodExpression.qualifierExpression!!.textRange
        // `with(` takes the place of the last indentation character: the receiver stays real text, no grey padding
        val indent = receiver.startOffset - 1
        if (indent < 0 || document.charsSequence[indent] !in " \t") {
            return null
        }
        val folds = mutableListOf(
            TextRange(indent, receiver.startOffset) to "with(",
            TextRange(receiver.endOffset, run.first().methodNameStart) to ") { ",
        )
        run.zipWithNext { previous, next ->
            folds += TextRange(previous.textRange.endOffset, next.methodNameStart) to " "
        }
        folds += TextRange(semicolon.textRange.startOffset, last.textRange.endOffset) to " }"
        return ScopeFunctionExpression(statement, TextRange(statement.textRange.startOffset, last.textRange.endOffset), folds)
    }

    private fun run(statement: PsiExpressionStatement, document: Document): List<PsiExpressionStatement>? {
        val variable = statement.receiver(document) ?: return null
        val first = generateSequence(statement) { it.adjacent(PsiElement::getPrevSibling, variable, document) }.last()
        return generateSequence(first) { it.adjacent(PsiElement::getNextSibling, variable, document) }
            .toList()
            .takeIf { it.size >= MIN_CALLS }
    }

    /** The statement on the directly neighbouring line when it is a call on the same [variable]. */
    private fun PsiExpressionStatement.adjacent(
        step: (PsiElement) -> PsiElement?,
        variable: PsiVariable,
        document: Document,
    ): PsiExpressionStatement? {
        val whiteSpace = step(this) as? PsiWhiteSpace ?: return null
        if (whiteSpace.text.count { it == '\n' } != 1) {
            return null
        }
        val neighbour = step(whiteSpace) as? PsiExpressionStatement ?: return null
        return neighbour.takeIf { it.receiver(document) == variable }
    }

    private fun PsiExpressionStatement.receiver(document: Document): PsiVariable? {
        if (textContains('\n')) {
            return null
        }
        val call = expression as? PsiMethodCallExpression ?: return null
        val qualifier = call.methodExpression.qualifierExpression as? PsiReferenceExpression ?: return null
        if (qualifier.qualifierExpression != null) {
            return null
        }
        val variable = qualifier.resolve() as? PsiVariable ?: return null
        if (variable.hasModifierProperty(PsiModifier.STATIC)) {
            return null
        }
        val writesReceiver = SyntaxTraverser.psiTraverser(call.argumentList)
            .filter(PsiReferenceExpression::class.java)
            .any { it.isReferenceTo(variable) && PsiUtil.isAccessedForWriting(it) }
        if (writesReceiver || SetterNamedArgumentsExt.isInSetterRun(call, document) || foldsReceiver(call, document)) {
            return null
        }
        return variable
    }

    /** Whether the call's own folding (`list += x`, field shift) covers `receiver.`, which `with` hides. */
    private fun foldsReceiver(call: PsiMethodCallExpression, document: Document): Boolean {
        val expression = MethodCallExpressionExt.getMethodCallExpression(call, document) ?: return false
        val methodNameStart = call.methodExpression.referenceNameElement?.textRange?.startOffset ?: return true
        return expression.supportsFoldRegions(document, null) &&
            expression.buildFoldRegions(call, document, null).any { it.range.startOffset < methodNameStart }
    }

    private val PsiExpressionStatement.call: PsiMethodCallExpression
        get() = expression as PsiMethodCallExpression

    private val PsiExpressionStatement.methodNameStart: Int
        get() = call.methodExpression.referenceNameElement!!.textRange.startOffset
}
