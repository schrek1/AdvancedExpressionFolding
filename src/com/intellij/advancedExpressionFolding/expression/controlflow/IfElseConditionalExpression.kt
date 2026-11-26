package com.intellij.advancedExpressionFolding.expression.controlflow

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.inlay.VirtualLine
import com.intellij.advancedExpressionFolding.inlay.VirtualLines
import com.intellij.advancedExpressionFolding.processor.language.kotlin.SingleExpressionFunctionExt
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.editor.colors.EditorColors
import com.intellij.openapi.util.TextRange
import com.intellij.psi.JavaTokenType
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiConditionalExpression
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiPolyadicExpression
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.PsiReturnStatement
import com.intellij.psi.PsiThisExpression
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.PsiUtil

/**
 * Shows a Java ternary `cond ? a : b` as Kotlin `if (cond) a else b`. Only ` if (`, `)` and `else` are folds and a
 * trailing `;` is hidden; the condition and both branches stay real code, in their order, with their own folds.
 *
 * ` if (` takes the blank before the ternary. When `?` starts a new line, the `)` closing the condition is drawn at
 * the end of the condition's line ([VirtualLines]), so the lines stay as written.
 */
class IfElseConditionalExpression private constructor(
    element: PsiConditionalExpression,
    private val folds: List<Pair<TextRange, String>>,
    private val closingParenAtLineEnd: Boolean,
) : Expression(element, element.textRange) {

    override fun isNested(): Boolean = true

    override fun supportsFoldRegions(document: Document, parent: Expression?): Boolean = true

    override fun buildFoldRegions(
        element: PsiElement,
        document: Document,
        parent: Expression?
    ): Array<FoldingDescriptor> {
        val name = IfElseConditionalExpression::class.java.name
        val group = if (closingParenAtLineEnd) {
            VirtualLines.group(
                name,
                above = null,
                below = null,
                indent = VirtualLines.Indent.NONE,
                firstLineSuffix = VirtualLine(listOf(")" to EditorColors.FOLDED_TEXT_ATTRIBUTES)),
            )
        } else {
            FoldingGroup.newGroup(name)
        }
        return folds.map { (range, placeholder) -> FoldingDescriptor(element.node, range, group, placeholder) }
            .toTypedArray()
    }

    companion object {
        private const val LONG_CONDITIONAL_LENGTH = 80

        /**
         * @param always every ternary, not only a long or multi-line one with a non-simple branch, a nested one or one with a string-building branch
         * @return null when the ternary stays as it is
         */
        fun create(element: PsiConditionalExpression, document: Document, always: Boolean): Expression? {
            val condition = element.condition
            val thenBranch = element.thenExpression ?: return null
            val elseBranch = element.elseExpression ?: return null
            val quest = element.node.findChildByType(JavaTokenType.QUEST)?.textRange ?: return null
            val colon = element.node.findChildByType(JavaTokenType.COLON)?.textRange ?: return null
            val chars = document.charsSequence
            val start = element.textRange.startOffset
            val end = element.textRange.endOffset
            // ` if (` needs a blank to take; right after `(` there is none
            if (start == 0 || chars[start - 1] != ' ' && chars[start - 1] != '\t' ||
                PsiTreeUtil.hasErrorElements(element) ||
                PsiTreeUtil.findChildOfType(element, PsiComment::class.java) != null
            ) {
                return null
            }
            if (!always && !worthIfElse(element, thenBranch, elseBranch)) {
                return null
            }
            fun text(from: Int, to: Int) = chars.subSequence(from, to)

            // a single-expression function already folds `return `, the blank and the `;`; it shows `if (` itself
            val inFunctionFold =
                (element.parent as? PsiReturnStatement)?.let(SingleExpressionFunctionExt::foldsReturnOf) == true
            val folds = mutableListOf<Pair<TextRange, String>>()
            if (!inFunctionFold) {
                folds += TextRange(start - 1, start) to " if ("
            }
            val conditionEnd = condition.textRange.endOffset
            val thenStart = thenBranch.textRange.startOffset
            val closingParenAtLineEnd = '\n' in text(conditionEnd, quest.startOffset)
            if (!closingParenAtLineEnd) {
                folds += TextRange(conditionEnd, quest.endOffset) to ")"
            } else {
                val conditionLine = document.getLineNumber(conditionEnd)
                // the `)` line is the line of the group's first fold, the ` if (`
                if (inFunctionFold || document.getLineNumber(start) != conditionLine ||
                    text(conditionEnd, document.getLineEndOffset(conditionLine)).isNotBlank()
                ) {
                    return null
                }
                folds += if ('\n' in text(quest.endOffset, thenStart)) {
                    quest to ""
                } else {
                    TextRange(quest.startOffset, thenStart) to ""
                }
            }
            // only the `:`: a template fold of the then branch may put its closing `"` on the blank before it
            folds += colon to if (chars[colon.startOffset - 1].isWhitespace()) "else" else " else"
            // a nested ternary ends where its parent does; the outermost one hides the `;`
            if (!inFunctionFold && element.parent !is PsiConditionalExpression && end < chars.length && chars[end] == ';') {
                folds += TextRange(end, end + 1) to ""
            }
            return IfElseConditionalExpression(element, folds, closingParenAtLineEnd)
        }

        private fun worthIfElse(
            element: PsiConditionalExpression,
            thenBranch: PsiExpression,
            elseBranch: PsiExpression,
        ): Boolean =
            element.parent is PsiConditionalExpression ||
                listOf(thenBranch, elseBranch).any { branch ->
                    val unwrapped = PsiUtil.skipParenthesizedExprDown(branch)
                    unwrapped is PsiConditionalExpression || buildsString(unwrapped)
                } ||
                // length alone does not help: two simple branches read best as a ternary however long it is
                (element.textLength > LONG_CONDITIONAL_LENGTH || '\n' in element.text) &&
                !(isSimple(thenBranch) && isSimple(elseBranch))

        /** A variable, a field or getter chain without arguments (`a.b.getC()`), a literal, `null` or `this`. */
        private fun isSimple(expression: PsiExpression?): Boolean =
            when (val unwrapped = PsiUtil.skipParenthesizedExprDown(expression)) {
                is PsiLiteralExpression, is PsiThisExpression -> true
                is PsiReferenceExpression -> unwrapped.qualifierExpression?.let(::isSimple) != false
                is PsiMethodCallExpression -> unwrapped.argumentList.isEmpty &&
                    unwrapped.methodExpression.qualifierExpression?.let(::isSimple) != false
                else -> false
            }

        /** A concatenation, `.formatted(…)` or `String.format(…)`: the branches the template folds turn into `"…"`. */
        private fun buildsString(expression: PsiExpression?): Boolean = when (expression) {
            is PsiPolyadicExpression -> expression.operationTokenType == JavaTokenType.PLUS &&
                expression.type?.equalsToText("java.lang.String") == true
            is PsiMethodCallExpression -> {
                val methodExpression = expression.methodExpression
                methodExpression.referenceName == "formatted" ||
                    methodExpression.referenceName == "format" && methodExpression.qualifierExpression?.text == "String"
            }
            else -> false
        }
    }
}
