package com.intellij.advancedExpressionFolding.expression.semantic.kotlin

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiForStatement
import com.intellij.psi.PsiJavaToken

/// Loop header `for (int i = 0; i < count; i++)` shown as `repeat(count)`; the parentheses stay real and `count`
/// keeps its own foldings.
class RepeatStatement(
    loop: PsiForStatement,
    private val lParenth: PsiJavaToken,
    private val count: PsiExpression,
    private val rParenth: PsiJavaToken,
) : Expression(loop, TextRange.create(loop.textRange.startOffset, rParenth.textRange.endOffset)) {

    override fun supportsFoldRegions(document: Document, parent: Expression?): Boolean = true

    override fun buildFoldRegions(
        element: PsiElement,
        document: Document,
        parent: Expression?
    ): Array<FoldingDescriptor> {
        val group = FoldingGroup.newGroup(RepeatStatement::class.java.name)
        return arrayOf(
            FoldingDescriptor(
                element.node,
                TextRange.create(textRange.startOffset, lParenth.textRange.startOffset),
                group,
                "repeat"
            ),
            FoldingDescriptor(
                element.node,
                TextRange.create(lParenth.textRange.endOffset, count.textRange.startOffset),
                group,
                ""
            ),
            FoldingDescriptor(
                element.node,
                TextRange.create(count.textRange.endOffset, rParenth.textRange.startOffset),
                group,
                ""
            ),
        )
    }
}
