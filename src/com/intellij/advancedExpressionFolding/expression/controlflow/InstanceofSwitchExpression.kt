package com.intellij.advancedExpressionFolding.expression.controlflow

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.inlay.VirtualLine
import com.intellij.advancedExpressionFolding.inlay.VirtualLines
import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.advancedExpressionFolding.settings.state.IKotlinLanguageState
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.HighlighterColors
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
 * `switch (shape) {` is a virtual line of the fold group ([VirtualLines]) and the cases are indented one level; the
 * closing `}` is the chain's own last brace.
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
        val head = chain.branches.first()
        val regions = branchStart(
            element, document, previousBlock = null, head.body,
            kept = head.type, keyword = "case ", replacement = null, arrow = head.unnamedBinding + " ->",
        ).toMutableList()
        var previousBlock = head.block
        for (branch in chain.branches.drop(1)) {
            regions += branchStart(
                element, document, previousBlock, branch.body,
                kept = branch.type, keyword = "case ", replacement = null, arrow = branch.unnamedBinding + " ->",
            )
            previousBlock = branch.block
        }
        chain.elseBody?.let { elseBody ->
            val elseKeyword = PsiTreeUtil.getParentOfType(elseBody.statement, PsiIfStatement::class.java)!!.elseElement!!
            regions += branchStart(
                element, document, previousBlock, elseBody,
                kept = elseKeyword, keyword = "", replacement = "default", arrow = " ->",
            )
        }
        val lastBody = chain.elseBody ?: chain.branches.last().body
        val group = VirtualLines.group(
            InstanceofSwitchExpression::class.java.name,
            above = VirtualLine(
                listOf("switch" to DefaultLanguageHighlighterColors.KEYWORD, " (${head.instanceOf.operand.text}) {" to HighlighterColors.TEXT)
            ),
            below = null,
            // a commented last body has lines no region touches; they are still part of the switch
            trailingLines = document.getLineNumber(lastBody.statement.textRange.endOffset) -
                document.getLineNumber(regions.maxOf { it.first.endOffset }),
        )
        return regions.map { (range, placeholder) -> FoldingDescriptor(element.node, range, group, placeholder) }
            .toTypedArray()
    }

    /**
     * Turns `if (x instanceof T t) {` (the head, no [previousBlock]) or `} else if (x instanceof T t) {` plus the line
     * break after it into `case T t -> `, or `} else {` into `default -> `. [kept] (`T t`, or the `else` keyword shown
     * as [replacement]) and the space before `{` keep their place; only the tokens that change are covered.
     *
     * Split at both braces: the editor drops a region that crosses the platform's `{...}` code-block regions.
     */
    private fun branchStart(
        element: PsiElement,
        document: Document,
        previousBlock: PsiCodeBlock?,
        body: Body,
        kept: PsiElement,
        keyword: String,
        replacement: String?,
        arrow: String,
    ): List<Pair<TextRange, String>> {
        val statement = body.statement
        val lBrace = (statement.parent as PsiCodeBlock).lBrace!!
        val regions = mutableListOf<Pair<TextRange, String>>()
        fun fold(start: Int, end: Int, placeholder: String) {
            regions += TextRange(start, end) to placeholder
        }

        val keptStart = kept.textRange.startOffset
        var keywordStart = element.textRange.startOffset
        previousBlock?.rBrace?.let { rBrace ->
            keywordStart = rBrace.textRange.startOffset
            if (keptStart > rBrace.textRange.endOffset) {
                fold(keywordStart, rBrace.textRange.endOffset, "")
                keywordStart = rBrace.textRange.endOffset
            }
        }
        // The space before `T t` stays real text, not part of the grey `case`.
        val keepSpace = keyword.endsWith(' ') && keptStart - keywordStart > 1 && document.charsSequence[keptStart - 1] == ' '
        if (keepSpace) {
            fold(keywordStart, keptStart - 1, keyword.trimEnd())
        } else {
            fold(keywordStart, keptStart, keyword)
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
        if (body.comments.isEmpty()) {
            fold(lBraceStart, statement.textRange.startOffset, lBracePlaceholder)
        } else {
            // A placeholder cannot break a line, so a commented branch drops its braces: `case T t ->`, then its lines.
            fold(lBraceStart, lBraceStart + 1, lBracePlaceholder.trimEnd())
        }
        return regions
    }

    /** A branch block's single statement and the full-line comments above it. */
    private class Body(val comments: List<PsiComment>, val statement: PsiStatement)

    private class Branch(
        val instanceOf: PsiInstanceOfExpression,
        val block: PsiCodeBlock,
        val body: Body,
    ) {
        val type: PsiElement = (instanceOf.pattern ?: instanceOf.checkType)!!

        /** A plain type check has no binding, and a `case` label needs one: shown as Java's unnamed `_`. */
        val unnamedBinding: String = if (instanceOf.pattern == null) " _" else ""
    }

    private class Chain(val branches: List<Branch>, val elseBody: Body?)

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
            if (PsiTreeUtil.findChildOfType(head, PsiErrorElement::class.java) != null) {
                return null
            }
            val branches = mutableListOf<Branch>()
            var subject: String? = null
            var current = head
            var elseBody: Body? = null
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
                branches += Branch(instanceOf, block, bodyOnItsOwnLines(block) ?: return null)
                when (val next = current.elseBranch) {
                    null -> break
                    is PsiIfStatement -> current = next
                    is PsiBlockStatement -> {
                        elseBody = bodyOnItsOwnLines(next.codeBlock) ?: return null
                        break
                    }
                    else -> return null
                }
            }
            if (branches.size < 2) {
                return null
            }
            // A comment outside the branch bodies (in a condition, before `else`) would have no place in the switch.
            val bodyComments = (branches.map { it.body } + listOfNotNull(elseBody)).flatMap { it.comments }.toSet()
            if (PsiTreeUtil.findChildrenOfType(head, PsiComment::class.java).any { it !in bodyComments }) {
                return null
            }
            return Chain(branches, elseBody)
        }

        /** The block's single statement with the full-line comments above it, each on a line of its own. */
        private fun bodyOnItsOwnLines(block: PsiCodeBlock): Body? {
            val statement = block.statements.singleOrNull() ?: return null
            val lBrace = block.lBrace ?: return null
            val rBrace = block.rBrace ?: return null
            val lines = generateSequence(lBrace.nextSibling) { it.nextSibling }
                .takeWhile { it != rBrace }
                .filter { it is PsiComment || it is PsiStatement }
                .toList()
            if (lines.last() != statement || !lines.all(::startsItsOwnLine)) {
                return null
            }
            val after = rBrace.prevSibling as? PsiWhiteSpace ?: return null
            return Body(lines.dropLast(1).filterIsInstance<PsiComment>(), statement).takeIf { after.text.contains('\n') }
        }

        private fun startsItsOwnLine(line: PsiElement): Boolean {
            val before = line.prevSibling as? PsiWhiteSpace ?: return false
            return before.text.contains('\n') && !before.text.endsWith('\n')
        }
    }
}
