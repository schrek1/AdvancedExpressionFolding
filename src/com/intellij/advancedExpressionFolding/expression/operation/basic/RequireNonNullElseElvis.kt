package com.intellij.advancedExpressionFolding.expression.operation.basic

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.literal.InterpolatedString
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

/**
 * `Objects.requireNonNullElse(value, fallback)` shown as `value ?: fallback`. Only the call syntax in [folds] turns
 * grey; [operands] stay real text and keep their own folds.
 *
 * An operand that borrows the character next to it (an interpolated string turns `)` into `"`) takes that character
 * out of the fold it sits in and gets that fold's text as its overflow placeholder.
 */
class RequireNonNullElseElvis(
    element: PsiElement,
    textRange: TextRange,
    private val folds: List<Pair<TextRange, String>>,
    private val operands: List<Expression>,
) : Expression(element, textRange) {

    override fun supportsFoldRegions(document: Document, parent: Expression?): Boolean = true

    override fun buildFoldRegions(
        element: PsiElement,
        document: Document,
        parent: Expression?
    ): Array<FoldingDescriptor> {
        val group = FoldingGroup.newGroup(RequireNonNullElseElvis::class.java.name)
        val remainingFolds = folds.toMutableList()
        val borrowed = operands.map { operand ->
            val left = operand.takeIf { it.isLeftOverflow() }
                ?.let { remainingFolds.takeCharacter(document, it.textRange.startOffset - 1, fromEnd = true) }
            val right = operand.takeIf { it.isRightOverflow() }
                ?.let { remainingFolds.takeCharacter(document, it.textRange.endOffset, fromEnd = false) }
            left to right
        }
        val descriptors = remainingFolds
            .filter { (range, _) -> !range.isEmpty }
            .map { (range, placeholder) -> FoldingDescriptor(element.node, range, group, placeholder) }
            .toMutableList()
        operands.zip(borrowed).filter { (operand, _) -> operand.supportsFoldRegions(document, this) }
            .forEach { (operand, characters) ->
                val (left, right) = characters
                descriptors += if (left != null || right != null) {
                    operand.buildFoldRegions(operand.element, document, this, group, left, right)
                } else {
                    operand.buildFoldRegions(operand.element, document, this)
                }
            }
        return descriptors.toTypedArray()
    }

    /** Returns the text the character at [offset] is shown as, or null when no fold covers a borrowable character. */
    private fun MutableList<Pair<TextRange, String>>.takeCharacter(
        document: Document,
        offset: Int,
        fromEnd: Boolean,
    ): String? {
        if (offset < 0 || offset >= document.textLength ||
            document.getText(TextRange(offset, offset + 1)) !in InterpolatedString.OVERFLOW_CHARACTERS
        ) {
            return null
        }
        val index = indexOfFirst { (range, _) ->
            if (fromEnd) range.endOffset == offset + 1 else range.startOffset == offset
        }
        if (index < 0) {
            return null
        }
        val (range, text) = this[index]
        if (range.length == 1) {
            removeAt(index)
            return text
        }
        this[index] = if (fromEnd) {
            TextRange(range.startOffset, range.endOffset - 1) to text
        } else {
            TextRange(range.startOffset + 1, range.endOffset) to text
        }
        return ""
    }
}
