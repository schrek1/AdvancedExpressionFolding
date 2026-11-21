package com.intellij.advancedExpressionFolding.expression.literal

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

/** `Map.of(k1, v1, k2, v2)` shown as `[k1: v1, k2: v2]`; [items] alternate keys and values. */
class MapLiteral(
    element: PsiElement,
    textRange: TextRange,
    private val items: List<Expression>
) : Expression(element, textRange) {

    override fun supportsFoldRegions(document: Document, parent: Expression?): Boolean = true

    override fun buildFoldRegions(
        element: PsiElement,
        document: Document,
        parent: Expression?
    ): Array<FoldingDescriptor> {
        val group = FoldingGroup.newGroup(MapLiteral::class.java.name)
        if (items.isEmpty()) {
            return arrayOf(FoldingDescriptor(element.node, textRange, group, "[:]"))
        }
        val descriptors = mutableListOf(
            FoldingDescriptor(
                element.node,
                TextRange.create(textRange.startOffset, items.first().textRange.startOffset),
                group,
                "["
            )
        )
        val text = document.charsSequence
        items.chunked(2).forEach { (key, value) ->
            val comma = (key.textRange.endOffset until value.textRange.startOffset).first { text[it] == ',' }
            descriptors += FoldingDescriptor(element.node, TextRange.create(comma, comma + 1), group, ":")
        }
        descriptors += FoldingDescriptor(
            element.node,
            TextRange.create(items.last().textRange.endOffset, textRange.endOffset),
            group,
            "]"
        )
        items.filter { it.supportsFoldRegions(document, this) }.forEach { item ->
            descriptors += item.buildFoldRegions(item.element, document, this)
        }
        return descriptors.toTypedArray()
    }
}
