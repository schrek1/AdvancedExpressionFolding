package com.intellij.advancedExpressionFolding.expression.operation.collection

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

/** Several ranges found in one `&&` chain, e.g. `x >= 0 && x < w && y >= 0 && y < h`. */
class RangeChain(
    element: PsiElement,
    textRange: TextRange,
    private val ranges: List<Expression>,
) : Expression(element, textRange) {

    override fun supportsFoldRegions(document: Document, parent: Expression?) =
        ranges.any { it.supportsFoldRegions(document, this) }

    override fun buildFoldRegions(element: PsiElement, document: Document, parent: Expression?): Array<FoldingDescriptor> =
        ranges.filter { it.supportsFoldRegions(document, this) }
            .flatMap { it.buildFoldRegions(it.element, document, this).asList() }
            .toTypedArray()
}
