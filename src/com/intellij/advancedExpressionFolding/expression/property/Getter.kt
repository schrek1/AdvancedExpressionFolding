package com.intellij.advancedExpressionFolding.expression.property

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.processor.declaration.SetterNamedArgumentsExt.propertyNameFold
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
        val nameRange = TextRange.create(nameStart, nameStart + getterName.length)
        val prefix = if (getterName.startsWith("is") && !getterName.startsWith("get")) "is" else "get"
        val (hiddenEnd, shownName) = propertyNameFold(getterName, name, nameRange, prefix)
        if (hiddenEnd == nameRange.endOffset) {
            descriptors += FoldingDescriptor(element.node, getterTextRange, group, name)
        } else {
            // only the characters that change are folded: the prefix (and the first letter's case) and `()`
            descriptors += FoldingDescriptor(element.node, TextRange.create(nameStart, hiddenEnd), group, shownName)
            descriptors += FoldingDescriptor(
                element.node,
                TextRange.create(nameRange.endOffset, getterTextRange.endOffset),
                group,
                ""
            )
        }
        receiver?.takeIf { it.supportsFoldRegions(document, this) }?.let { obj ->
            descriptors += obj.buildFoldRegions(obj.element, document, this).toList()
        }
        return descriptors.toTypedArray()
    }
}
