package com.intellij.advancedExpressionFolding.expression.property

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.controlflow.IfElseConditionalExpression
import com.intellij.advancedExpressionFolding.processor.declaration.SetterNamedArgumentsExt.propertyNameFold
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

class Setter(
    element: PsiElement,
    textRange: TextRange,
    private val setterTextRange: TextRange,
    private val setterName: String,
    private val receiver: Expression?,
    private val name: String,
    private val value: Expression
) : Expression(element, textRange) {

    private val valueIfElse = (value as? IfElseConditionalExpression)?.takeIf { it.foldsBlankBefore }

    override fun supportsFoldRegions(document: Document, parent: Expression?): Boolean = true

    // an if-else value folds its own children (the condition and the branches), which the setter would otherwise
    // skip: the walk goes on into the call's receiver and argument, so they are not built here
    override fun isNested(): Boolean = valueIfElse != null || super.isNested()

    override fun buildFoldRegions(
        element: PsiElement,
        document: Document,
        parent: Expression?
    ): Array<FoldingDescriptor> {
        val group = FoldingGroup.newGroup(Setter::class.java.name)
        val descriptors = mutableListOf<FoldingDescriptor>()
        if (value.isLeftOverflow()) {
            descriptors += FoldingDescriptor(
                element.node,
                TextRange.create(setterTextRange.startOffset, value.textRange.startOffset - 1),
                group,
                "$name = "
            )
        } else {
            // only the characters that change are folded: `set` (and the first letter's case) and ` = `
            val nameStart = setterTextRange.startOffset
            val nameRange = TextRange.create(nameStart, nameStart + setterName.length)
            val (hiddenEnd, shownName) = propertyNameFold(setterName, name, nameRange)
            descriptors += FoldingDescriptor(
                element.node,
                TextRange.create(nameStart, hiddenEnd),
                group,
                shownName
            )
            // an if-else value folds the blank before it to ` if (`: the setter stops short of it
            descriptors += FoldingDescriptor(
                element.node,
                TextRange.create(nameRange.endOffset, value.textRange.startOffset - if (valueIfElse != null) 1 else 0),
                group,
                if (valueIfElse != null) " =" else " = "
            )
        }
        if (!value.isRightOverflow()) {
            if (value.textRange.endOffset < textRange.endOffset) {
                descriptors += FoldingDescriptor(
                    element.node,
                    TextRange.create(value.textRange.endOffset, textRange.endOffset),
                    group,
                    ""
                )
            }
        }
        if (valueIfElse != null) {
            return descriptors.toTypedArray()
        }
        receiver?.takeIf { it.supportsFoldRegions(document, this) }?.let { obj ->
            descriptors += obj.buildFoldRegions(obj.element, document, this).toList()
        }
        if (value.supportsFoldRegions(document, this)) {
            if (value.isOverflow()) {
                descriptors += value.buildFoldRegions(value.element, document, this, group, "", "").toList()
            } else {
                descriptors += value.buildFoldRegions(value.element, document, this).toList()
            }
        }
        return descriptors.toTypedArray()
    }
}
