package com.intellij.advancedExpressionFolding.expression.semantic.kotlin

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiExpression

/**
 * `c.contains(x)` shown as `x in c`, `!c.contains(x)` as `x !in c`.
 *
 * The tested element stays real code, so it keeps its syntax colours; only the text around it is folded and the
 * container moves into the placeholder after it.
 */
class KotlinMembership(
    element: PsiElement,
    textRange: TextRange,
    private val member: PsiExpression,
    private val suffix: String,
) : Expression(element, textRange) {

    override fun supportsFoldRegions(document: Document, parent: Expression?) = true

    override fun buildFoldRegions(element: PsiElement, document: Document, parent: Expression?): Array<FoldingDescriptor> {
        val group = FoldingGroup.newGroup(javaClass.name)
        return arrayOf(
            FoldingDescriptor(element.node, TextRange.create(textRange.startOffset, member.textRange.startOffset), group, ""),
            FoldingDescriptor(element.node, TextRange.create(member.textRange.endOffset, textRange.endOffset), group, suffix),
        )
    }
}
