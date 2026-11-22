package com.intellij.advancedExpressionFolding.expression.property

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

class Getter(
    element: PsiElement,
    textRange: TextRange,
    override val getterTextRange: TextRange,
    private val getterName: String,
    override val receiver: Expression?,
    override val name: String
) : Expression(element, textRange), IGetter {

    override fun supportsFoldRegions(document: Document, parent: Expression?): Boolean = true

    override fun buildFoldRegions(
        element: PsiElement,
        document: Document,
        parent: Expression?
    ): Array<FoldingDescriptor> {
        val group = FoldingGroup.newGroup(Getter::class.java.name)
        val descriptors = mutableListOf<FoldingDescriptor>()
        val nameStart = getterTextRange.startOffset
        val nameEnd = nameStart + getterName.length
        val hiddenPrefix = hiddenPrefix()
        if (hiddenPrefix == null) {
            descriptors += FoldingDescriptor(element.node, getterTextRange, group, name)
        } else {
            // only the characters that change are folded: the prefix (and the first letter's case) and `()`
            val (hiddenLength, shownName) = hiddenPrefix
            descriptors += FoldingDescriptor(
                element.node,
                TextRange.create(nameStart, nameStart + hiddenLength),
                group,
                shownName
            )
            descriptors += FoldingDescriptor(element.node, TextRange.create(nameEnd, getterTextRange.endOffset), group, "")
        }
        receiver?.takeIf { it.supportsFoldRegions(document, this) }?.let { obj ->
            descriptors += obj.buildFoldRegions(obj.element, document, this).toList()
        }
        return descriptors.toTypedArray()
    }

    /**
     * How many leading characters of the getter name are hidden and what replaces them, so the rest of the name stays
     * real text: `getCity` hides `getC` behind `c`, `getURL` hides only `get`; `null` when the whole name changes.
     */
    private fun hiddenPrefix(): Pair<Int, String>? {
        val prefix = if (getterName.startsWith("is") && !getterName.startsWith("get")) "is" else "get"
        val afterPrefix = getterName.removePrefix(prefix)
        return when {
            afterPrefix.isEmpty() || afterPrefix == getterName -> null
            name == afterPrefix -> prefix.length to ""
            name.drop(1) == afterPrefix.drop(1) -> prefix.length + 1 to name.take(1)
            else -> null
        }
    }
}
