package com.intellij.advancedExpressionFolding.expression.semantic.kotlin

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.inlay.VirtualLine
import com.intellij.advancedExpressionFolding.inlay.VirtualLines
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

/**
 * A Kotlin scope function shown over Java code: only the synthesized tokens fold, all in one group, and the block's
 * opening and closing lines are [VirtualLines] shown while the group is collapsed.
 */
class ScopeFunctionExpression(
    element: PsiElement,
    textRange: TextRange,
    private val folds: List<Pair<TextRange, String>>,
    private val above: VirtualLine,
    private val below: VirtualLine,
) : Expression(element, textRange) {

    override fun supportsFoldRegions(document: Document, parent: Expression?): Boolean = true

    // the statements inside keep their own foldings through the normal traversal
    override fun isNested(): Boolean = true

    override fun buildFoldRegions(
        element: PsiElement,
        document: Document,
        parent: Expression?
    ): Array<FoldingDescriptor> {
        val group = VirtualLines.group(ScopeFunctionExpression::class.java.name, above, below)
        return folds.map { (range, text) -> FoldingDescriptor(element.node, range, group, text) }.toTypedArray()
    }
}
