package com.intellij.advancedExpressionFolding.expression.operation.optional

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.JavaTokenType
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.PsiUtilCore

/**
 * `optional.stream()` shown as `listOfNotNull(optional)`: only `listOfNotNull(` and the closing `)` are folded,
 * the receiver keeps its own folds and colours.
 */
class OptionalListOfNotNull(
    private val call: PsiMethodCallExpression,
    private val receiver: Expression,
) : Expression(call, call.textRange) {

    override fun buildFoldRegions(
        element: PsiElement,
        document: Document,
        parent: Expression?
    ): Array<FoldingDescriptor> {
        val qualifier = call.methodExpression.qualifierExpression ?: return EMPTY_ARRAY
        val group = FoldingGroup.newGroup(OptionalListOfNotNull::class.java.name)
        val start = call.textRange.startOffset
        val descriptors = receiver.takeIf { it.supportsFoldRegions(document, this) }
            ?.buildFoldRegions(receiver.element, document, this)
            ?.toMutableList()
            ?: mutableListOf()

        // No zero-width fold exists, so `listOfNotNull(` joins the receiver's leading fold (`Optional.ofNullable(`),
        // else the space before the call (after `=` or `->`), else the receiver's first token. The space after
        // `return` is not used: the one-line method fold already hides it.
        val leading = descriptors.indexOfFirst { it.range.startOffset == start }
        val whitespaceBefore = PsiTreeUtil.prevLeaf(call) as? PsiWhiteSpace
        if (leading >= 0) {
            val folded = descriptors[leading]
            descriptors[leading] =
                FoldingDescriptor(folded.element, folded.range, folded.group, LIST_OF_NOT_NULL + folded.placeholderText)
        } else if (whitespaceBefore != null && whitespaceBefore.text.endsWith(' ') &&
            PsiUtilCore.getElementType(PsiTreeUtil.prevLeaf(whitespaceBefore)) != JavaTokenType.RETURN_KEYWORD
        ) {
            descriptors += FoldingDescriptor(call.node, TextRange(start - 1, start), group, " $LIST_OF_NOT_NULL")
        } else {
            val firstToken = call.containingFile.findElementAt(start) ?: return EMPTY_ARRAY
            descriptors += FoldingDescriptor(call.node, firstToken.textRange, group, LIST_OF_NOT_NULL + firstToken.text)
        }
        val streamCall = TextRange(qualifier.textRange.endOffset, call.textRange.endOffset)
        descriptors += FoldingDescriptor(call.node, streamCall, group, ")")
        return descriptors.toTypedArray()
    }

    override fun isCollapsedByDefault(): Boolean = true

    override fun supportsFoldRegions(document: Document, parent: Expression?): Boolean = true

    private companion object {
        const val LIST_OF_NOT_NULL = "listOfNotNull("
    }
}
