package com.intellij.advancedExpressionFolding.expression.semantic.kotlin

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

/// A `x != null && x.getY() != null` chain shown as `x?.y != null`; `folds` cover only the joints between the links.
class IfNullSafeExpression(
    element: PsiElement,
    textRange: TextRange,
    private val folds: List<Pair<TextRange, String>>,
) : Expression(element, textRange) {

    override fun buildFoldRegions(
        element: PsiElement,
        document: Document,
        parent: Expression?
    ): Array<FoldingDescriptor> {
        val group = FoldingGroup.newGroup(IfNullSafeExpression::class.java.name)
        return folds.map { (range, text) -> FoldingDescriptor(element.node, range, group, text) }.toTypedArray()
    }

    override fun isCollapsedByDefault(): Boolean {
        return true
    }

    override fun supportsFoldRegions(
        document: Document,
        parent: Expression?
    ): Boolean {
        return true
    }
}
