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
import com.intellij.psi.PsiInstanceOfExpression
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.PsiStatement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil

/**
 * Shows an `if / else if` chain of `instanceof` checks on one variable as a Java pattern switch:
 * `switch (shape) {` / `case Circle c -> ...` / `default -> ...` / `}`.
 *
 * Built for the head `if` of the chain; the `else if` links get an instance with no chain, so their own
 * if-foldings do not overlap the switch regions while the statements inside still fold.
 */
class InstanceofSwitchExpression private constructor(
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
        val group = FoldingGroup.newGroup(InstanceofSwitchExpression::class.java.name)
        val head = chain.branches.first()
        val headStart = element.textRange.startOffset
        val descriptors = mutableListOf(
            FoldingDescriptor(element.node, TextRange(headStart, headStart + "if".length), group, "switch"),
            FoldingDescriptor(
                element.node,
                TextRange(head.instanceOf.operand.textRange.endOffset, head.instanceOf.textRange.endOffset),
                group,
                ""
            ),
        )
        val firstStatementStart = head.statement.textRange.startOffset
        // The head's type moves from the `switch (x)` line to the branch line, so it can only be shown as a placeholder.
        val lastIndentChar = document.charsSequence[firstStatementStart - 1]
        descriptors += FoldingDescriptor(
            element.node,
            TextRange(firstStatementStart - 1, firstStatementStart),
            group,
            "${lastIndentChar}case ${head.type.text}${head.unnamedBinding} -> "
        )
        var previousBlock = head.block
        for (branch in chain.branches.drop(1)) {
            descriptors += branchStart(
                element, document, group, previousBlock, branch.statement,
                kept = branch.type, keyword = "case ", replacement = null, arrow = branch.unnamedBinding + " ->",
            )
            previousBlock = branch.block
        }
        chain.elseStatement?.let { elseStatement ->
            val elseKeyword = PsiTreeUtil.getParentOfType(elseStatement, PsiIfStatement::class.java)!!.elseElement!!
            descriptors += branchStart(
                element, document, group, previousBlock, elseStatement,
                kept = elseKeyword, keyword = "", replacement = "default", arrow = " ->",
            )
        }
        return descriptors.toTypedArray()
    }

    /**
     * Turns `} else if (x instanceof T t) {` plus the line break after it into `case T t -> `, or `} else {` into
     * `default -> `. [kept] (`T t`, or the `else` keyword shown as [replacement]) and the space before `{` keep their
     * place; only the tokens that change are covered.
     *
     * Split at both braces: the editor drops a region that crosses the platform's `{...}` code-block regions.
     */
    private fun branchStart(
        element: PsiElement,
        document: Document,
        group: FoldingGroup,
        previousBlock: PsiCodeBlock,
        statement: PsiStatement,
        kept: PsiElement,
        keyword: String,
        replacement: String?,
        arrow: String,
    ): List<FoldingDescriptor> {
        val rBrace = previousBlock.rBrace!!
        val lBrace = (statement.parent as PsiCodeBlock).lBrace!!
        val braceIndent = rBrace.prevSibling.text.substringAfterLast('\n')
        val statementIndent = statement.prevSibling.text.substringAfterLast('\n')
        val extraIndent = statementIndent.removePrefix(braceIndent).takeIf { statementIndent.startsWith(braceIndent) }
            ?: "    "
        val prefix = extraIndent + keyword
        val descriptors = mutableListOf<FoldingDescriptor>()
        fun fold(start: Int, end: Int, placeholder: String) {
            descriptors += FoldingDescriptor(element.node, TextRange(start, end), group, placeholder)
        }

        val rBraceEnd = rBrace.textRange.endOffset
        if (kept.textRange.startOffset > rBraceEnd) {
            fold(rBrace.textRange.startOffset, rBraceEnd, "")
            fold(rBraceEnd, kept.textRange.startOffset, prefix)
        } else {
            fold(rBrace.textRange.startOffset, rBraceEnd, prefix)
        }
        replacement?.let { fold(kept.textRange.startOffset, kept.textRange.endOffset, it) }

        val keptEnd = kept.textRange.endOffset
        val lBraceStart = lBrace.textRange.startOffset
        val hidden = document.charsSequence.subSequence(keptEnd, lBraceStart).trimEnd()
        val spaceKept = lBraceStart - keptEnd > hidden.length
        if (hidden.isNotEmpty()) {
            fold(keptEnd, keptEnd + hidden.length, arrow)
        }
        val lBracePlaceholder = when {
            hidden.isNotEmpty() && spaceKept -> ""
            hidden.isNotEmpty() -> " "
            spaceKept -> "-> "
            else -> " -> "
        }
        fold(lBraceStart, statement.textRange.startOffset, lBracePlaceholder)
        return descriptors
    }

    private class Branch(
        val instanceOf: PsiInstanceOfExpression,
        val block: PsiCodeBlock,
        val statement: PsiStatement,
    ) {
        val type: PsiElement = (instanceOf.pattern ?: instanceOf.checkType)!!

        /** A plain type check has no binding, and a `case` label needs one: shown as Java's unnamed `_`. */
        val unnamedBinding: String = if (instanceOf.pattern == null) " _" else ""
    }

    private class Chain(val branches: List<Branch>, val elseStatement: PsiStatement?)

    companion object : IKotlinLanguageState by State()() {

        fun create(element: PsiIfStatement): Expression? {
            if (!instanceofSwitch) {
                return null
            }
            if (isElseIfLink(element)) {
                return if (chainOf(headOf(element)) != null) InstanceofSwitchExpression(element, null) else null
            }
            return chainOf(element)?.let { InstanceofSwitchExpression(element, it) }
        }

        /** True when [ifStatement] is a link (head, `else if` or the one owning the final `else`) of a chain shown as a switch. */
        fun foldsAsSwitch(ifStatement: PsiIfStatement): Boolean = instanceofSwitch && chainOf(headOf(ifStatement)) != null

        private fun isElseIfLink(element: PsiIfStatement): Boolean =
            (element.parent as? PsiIfStatement)?.elseBranch == element

        private fun headOf(element: PsiIfStatement): PsiIfStatement {
            var head = element
            while (isElseIfLink(head)) {
                head = head.parent as PsiIfStatement
            }
            return head
        }

        private fun chainOf(head: PsiIfStatement): Chain? {
            if (PsiTreeUtil.findChildOfAnyType(head, PsiComment::class.java, PsiErrorElement::class.java) != null) {
                return null
            }
            val branches = mutableListOf<Branch>()
            var subject: String? = null
            var current = head
            var elseStatement: PsiStatement? = null
            while (true) {
                val instanceOf = current.condition as? PsiInstanceOfExpression ?: return null
                // A method call would read as evaluated once in `when (...)`, but each Java branch calls it again.
                val operand = instanceOf.operand as? PsiReferenceExpression ?: return null
                if (instanceOf.pattern == null && instanceOf.checkType == null) {
                    return null
                }
                if (subject != null && operand.text != subject) {
                    return null
                }
                subject = operand.text
                val block = (current.thenBranch as? PsiBlockStatement)?.codeBlock ?: return null
                branches += Branch(instanceOf, block, singleStatementOnItsOwnLine(block) ?: return null)
                when (val next = current.elseBranch) {
                    null -> break
                    is PsiIfStatement -> current = next
                    is PsiBlockStatement -> {
                        elseStatement = singleStatementOnItsOwnLine(next.codeBlock) ?: return null
                        break
                    }
                    else -> return null
                }
            }
            return if (branches.size >= 2) Chain(branches, elseStatement) else null
        }

        private fun singleStatementOnItsOwnLine(block: PsiCodeBlock): PsiStatement? {
            val statement = block.statements.singleOrNull() ?: return null
            if (block.lBrace == null || block.rBrace == null) {
                return null
            }
            val before = statement.prevSibling as? PsiWhiteSpace ?: return null
            val after = block.rBrace?.prevSibling as? PsiWhiteSpace ?: return null
            val indentedOnNewLine = before.text.contains('\n') && !before.text.endsWith('\n')
            return statement.takeIf { indentedOnNewLine && after.text.contains('\n') }
        }
    }
}
