package com.intellij.advancedExpressionFolding.expression.controlflow

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.advancedExpressionFolding.settings.state.IKotlinLanguageState
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiBlockStatement
import com.intellij.psi.PsiCodeBlock
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.PsiIfStatement
import com.intellij.psi.PsiReturnStatement
import com.intellij.psi.PsiStatement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil

/**
 * Shows a run of guard `if (cond) { return x; }` statements followed by a final `return y;` as a subject-less
 * Kotlin `when`: `when { cond -> return x` ... `else -> return y }`.
 *
 * A fold cannot add a line, so `when {` shares the first branch's line and `}` closes the `else` line. The other
 * branches keep their own indentation as real text. Built for the head `if`; the other links get an instance with no
 * chain, so their own if-foldings step aside.
 */
class GuardReturnWhenExpression private constructor(
    ifStatement: PsiIfStatement,
    private val chain: Chain?,
) : Expression(ifStatement, ifStatement.textRange) {

    override fun isNested(): Boolean = true

    override fun supportsFoldRegions(document: Document, parent: Expression?): Boolean = chain != null

    override fun buildFoldRegions(
        element: PsiElement,
        document: Document,
        parent: Expression?
    ): Array<FoldingDescriptor> {
        val chain = chain ?: return EMPTY_ARRAY
        val group = FoldingGroup.newGroup(GuardReturnWhenExpression::class.java.name)
        val descriptors = mutableListOf<FoldingDescriptor>()
        fun fold(start: Int, end: Int, placeholder: String) {
            descriptors += FoldingDescriptor(element.node, TextRange(start, end), group, placeholder)
        }

        chain.branches.forEachIndexed { index, branch ->
            val ifStatement = branch.ifStatement
            val rParenth = ifStatement.rParenth!!
            val lBrace = branch.block.lBrace!!
            fold(ifStatement.textRange.startOffset, ifStatement.lParenth!!.textRange.endOffset, if (index == 0) WHEN else "")
            val spaceBeforeBrace = lBrace.textRange.startOffset > rParenth.textRange.endOffset
            fold(rParenth.textRange.startOffset, rParenth.textRange.endOffset, if (spaceBeforeBrace) " ->" else " -> ")
            fold(lBrace.textRange.startOffset, branch.returnStatement.textRange.startOffset, "")
            fold(branch.returnStatement.textRange.endOffset - 1, branch.block.rBrace!!.textRange.endOffset, "")
        }
        // No zero-width fold exists, so `else -> ` is anchored on the last indentation character.
        val finalStart = chain.finalReturn.textRange.startOffset
        fold(finalStart - 1, finalStart, "${document.charsSequence[finalStart - 1]}else -> ")
        val finalEnd = chain.finalReturn.textRange.endOffset
        fold(finalEnd - 1, finalEnd, " }")
        return descriptors.toTypedArray()
    }

    private class Branch(
        val ifStatement: PsiIfStatement,
        val block: PsiCodeBlock,
        val returnStatement: PsiReturnStatement,
    )

    private class Chain(val branches: List<Branch>, val finalReturn: PsiReturnStatement)

    companion object : IKotlinLanguageState by State()() {
        private const val WHEN = "when { "

        fun create(element: PsiIfStatement): Expression? {
            if (!guardReturnWhen || branchOf(element) == null) {
                return null
            }
            var head = element
            while (true) {
                head = (previousInChain(head) as? PsiIfStatement)?.takeIf { branchOf(it) != null } ?: break
            }
            val chain = chainFrom(head) ?: return null
            return GuardReturnWhenExpression(element, chain.takeIf { head == element })
        }

        /** True when [ifStatement] is a link of a chain shown as a `when`. */
        fun foldsAsWhen(ifStatement: PsiIfStatement): Boolean = create(ifStatement) != null

        private fun chainFrom(head: PsiIfStatement): Chain? {
            val branches = mutableListOf<Branch>()
            var current: PsiStatement = head
            while (current is PsiIfStatement) {
                branches += branchOf(current) ?: break
                current = nextInChain(current) ?: return null
            }
            val finalReturn = current as? PsiReturnStatement ?: return null
            val indentChar = (finalReturn.prevSibling as? PsiWhiteSpace)?.text?.lastOrNull()
            if (branches.size < 2 || indentChar == '\n' || !finalReturn.text.endsWith(';') ||
                PsiTreeUtil.hasErrorElements(finalReturn)
            ) {
                return null
            }
            return Chain(branches, finalReturn)
        }

        private fun branchOf(ifStatement: PsiIfStatement): Branch? {
            if (ifStatement.parent !is PsiCodeBlock || ifStatement.elseBranch != null ||
                ifStatement.lParenth == null || ifStatement.rParenth == null || ifStatement.condition == null ||
                PsiTreeUtil.findChildOfAnyType(ifStatement, PsiComment::class.java, PsiErrorElement::class.java) != null
            ) {
                return null
            }
            val thenBranch = ifStatement.thenBranch as? PsiBlockStatement ?: return null
            val block = thenBranch.codeBlock
            // `{` on its own line would leave the branch split over two lines.
            if (block.lBrace == null || block.rBrace == null ||
                (thenBranch.prevSibling as? PsiWhiteSpace)?.textContains('\n') == true
            ) {
                return null
            }
            val returnStatement = block.statements.singleOrNull() as? PsiReturnStatement ?: return null
            return returnStatement.takeIf { it.text.endsWith(';') }?.let { Branch(ifStatement, block, it) }
        }

        /** The next statement when only a line break (no comment) separates it from [statement]. */
        private fun nextInChain(statement: PsiStatement): PsiStatement? {
            val gap = statement.nextSibling as? PsiWhiteSpace ?: return null
            return (gap.nextSibling as? PsiStatement)?.takeIf { gap.textContains('\n') }
        }

        private fun previousInChain(statement: PsiStatement): PsiStatement? {
            val gap = statement.prevSibling as? PsiWhiteSpace ?: return null
            return (gap.prevSibling as? PsiStatement)?.takeIf { gap.textContains('\n') }
        }
    }
}
