package com.intellij.advancedExpressionFolding.expression.operation.collection

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

class GetOrDefault(
    element: PsiElement,
    textRange: TextRange,
    val receiver: Expression,
    val key: Expression,
    val defaultValue: Expression
) : Expression(element, textRange) {

    override fun supportsFoldRegions(document: Document, parent: Expression?): Boolean {
        return receiver.textRange.endOffset < key.textRange.startOffset &&
            key.textRange.endOffset < defaultValue.textRange.startOffset &&
            defaultValue.textRange.endOffset < textRange.endOffset
    }

    override fun buildFoldRegions(
        element: PsiElement,
        document: Document,
        parent: Expression?
    ): Array<FoldingDescriptor> {
        val group = FoldingGroup.newGroup(GetOrDefault::class.java.name)
        val text = document.charsSequence
        val separatorStart = key.textRange.endOffset
        val commaEnd = (separatorStart until defaultValue.textRange.startOffset)
            .firstOrNull { text[it] == ',' }
            ?.plus(1)
            ?: defaultValue.textRange.startOffset
        // The source whitespace after the comma stays real text, so only the comma turns into `] ?:`.
        val keepsSpace = commaEnd < defaultValue.textRange.startOffset && text[commaEnd].isWhitespace()
        val descriptors = mutableListOf(
            FoldingDescriptor(
                element.node,
                TextRange.create(receiver.textRange.endOffset, key.textRange.startOffset),
                group,
                "["
            ),
            FoldingDescriptor(
                element.node,
                TextRange.create(separatorStart, commaEnd),
                group,
                if (keepsSpace) "] ?:" else "] ?: "
            ),
            FoldingDescriptor(
                element.node,
                TextRange.create(defaultValue.textRange.endOffset, textRange.endOffset),
                group,
                ""
            ),
        )
        for (operand in listOf(receiver, key, defaultValue)) {
            if (operand.supportsFoldRegions(document, this)) {
                descriptors += operand.buildFoldRegions(operand.element, document, this).toList()
            }
        }
        return descriptors.toTypedArray()
    }
}
