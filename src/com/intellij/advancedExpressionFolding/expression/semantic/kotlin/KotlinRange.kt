package com.intellij.advancedExpressionFolding.expression.semantic.kotlin

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiWhiteSpace

/** `x >= a && x <= b` shown as `x in a..b`, `x >= a && x < b` as `x in a..<b`. */
class KotlinRange(
    element: PsiElement,
    textRange: TextRange,
    private val lowerBoundSign: PsiElement,
    private val start: PsiExpression,
    private val end: PsiExpression,
    private val endInclusive: Boolean,
) : Expression(element, textRange) {

    override fun supportsFoldRegions(document: Document, parent: Expression?) = true

    override fun buildFoldRegions(element: PsiElement, document: Document, parent: Expression?): Array<FoldingDescriptor> {
        val group = FoldingGroup.newGroup(javaClass.name)
        return arrayOf(
            FoldingDescriptor(
                element.node,
                lowerBoundSign.textRange,
                group,
                spaced(lowerBoundSign.prevSibling) + "in" + spaced(lowerBoundSign.nextSibling)
            ),
            FoldingDescriptor(
                element.node,
                TextRange.create(start.textRange.endOffset, end.textRange.startOffset),
                group,
                if (endInclusive) ".." else "..<"
            ),
        )
    }

    private fun spaced(neighbour: PsiElement?) = if (neighbour is PsiWhiteSpace) "" else " "
}
