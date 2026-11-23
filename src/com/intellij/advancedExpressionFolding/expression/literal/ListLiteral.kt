package com.intellij.advancedExpressionFolding.expression.literal

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.inlay.VirtualLine
import com.intellij.advancedExpressionFolding.inlay.VirtualLines
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

/**
 * A list factory call shown as `[a, b]`. When the elements start on the line after the call, each keeps its own line
 * and `]` gets a line of its own: the real `)` when it already stands alone, otherwise a virtual line under the line
 * that opens the list. A [spread] array reads `[*array]`.
 */
class ListLiteral(
    element: PsiElement,
    textRange: TextRange,
    val items: List<Expression>,
    val spread: Boolean = false,
) : Expression(element, textRange) {

    private val openText = if (spread) "[*" else "["

    override fun supportsFoldRegions(document: Document, parent: Expression?): Boolean = true

    override fun buildFoldRegions(
        element: PsiElement,
        document: Document,
        parent: Expression?
    ): Array<FoldingDescriptor> {
        if (items.isEmpty()) {
            return arrayOf(FoldingDescriptor(element.node, textRange, newGroup(), "[]"))
        }
        val first = items.first().textRange
        val last = items.last().textRange
        val descriptors = if (document.getLineNumber(textRange.startOffset) == document.getLineNumber(first.startOffset)) {
            val group = newGroup()
            listOfNotNull(
                FoldingDescriptor(element.node, TextRange(textRange.startOffset, first.startOffset), group, openText),
                closing(last.endOffset, group),
            )
        } else {
            multiLineBrackets(element, document, first, last)
        }.toMutableList()
        for (item in items) {
            descriptors += item.buildFoldRegions(item.element, document, this).toList()
        }
        return descriptors.toTypedArray()
    }

    private fun multiLineBrackets(
        element: PsiElement,
        document: Document,
        first: TextRange,
        last: TextRange,
    ): List<FoldingDescriptor> {
        val text = document.charsSequence
        val openParen = (textRange.startOffset until first.startOffset).last { text[it] == '(' }
        fun open(group: FoldingGroup) =
            FoldingDescriptor(element.node, TextRange(textRange.startOffset, openParen + 1), group, openText)

        val closeParen = textRange.endOffset - 1
        if (document.getLineNumber(closeParen) != document.getLineNumber(last.endOffset)) {
            val group = newGroup()
            return listOf(open(group), FoldingDescriptor(element.node, TextRange(closeParen, textRange.endOffset), group, "]"))
        }
        // `)` glued to the last element: it moves to a virtual `]` line, with the `;` or `,` that follows it, so the
        // order stays `]` then `;`; anything else after it on the line keeps `]` inline
        val tailEnd = textRange.endOffset + if (text.getOrNull(textRange.endOffset) in TAIL) 1 else 0
        val lineEnd = document.getLineEndOffset(document.getLineNumber(closeParen))
        if ((tailEnd until lineEnd).any { !text[it].isWhitespace() }) {
            val group = newGroup()
            return listOfNotNull(open(group), closing(last.endOffset, group))
        }
        val closingLine = "]" + text.subSequence(textRange.endOffset, tailEnd)
        val group = VirtualLines.group(
            ListLiteral::class.java.name,
            above = null,
            below = VirtualLine(listOf(closingLine to HighlighterColors.TEXT)),
            indent = VirtualLines.Indent.NONE,
        )
        return listOf(open(group), FoldingDescriptor(element.node, TextRange(last.endOffset, tailEnd), group, ""))
    }

    private fun closing(lastEnd: Int, group: FoldingGroup): FoldingDescriptor? =
        if (lastEnd < textRange.endOffset) {
            FoldingDescriptor(element.node, TextRange(lastEnd, textRange.endOffset), group, "]")
        } else {
            null
        }

    private fun newGroup() = FoldingGroup.newGroup(ListLiteral::class.java.name)

    private companion object {
        val TAIL = setOf(';', ',')
    }
}
