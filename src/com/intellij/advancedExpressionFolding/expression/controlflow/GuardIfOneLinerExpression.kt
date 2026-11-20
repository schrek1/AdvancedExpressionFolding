package com.intellij.advancedExpressionFolding.expression.controlflow

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiCodeBlock
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReturnStatement

/**
 * Shows `if (user == null) { return null; }` on one line as `if (user == null) return null;`.
 *
 * Safe in editable files because nothing stays on an indented body line that a new statement could join:
 * End + Enter lands after the hidden `}`, outside the `if`.
 */
class GuardIfOneLinerExpression(
    private val codeBlock: PsiCodeBlock,
    private val returnStatement: PsiReturnStatement
) : Expression(codeBlock, codeBlock.textRange) {

    override fun isNested(): Boolean = true

    override fun supportsFoldRegions(document: Document, parent: Expression?): Boolean =
        codeBlock.lBrace != null && codeBlock.rBrace != null

    override fun buildFoldRegions(
        element: PsiElement,
        document: Document,
        parent: Expression?
    ): Array<FoldingDescriptor> {
        val lBrace = codeBlock.lBrace ?: return emptyArray()
        val rBrace = codeBlock.rBrace ?: return emptyArray()
        val group = FoldingGroup.newGroup(GuardIfOneLinerExpression::class.java.name)
        val start = lBrace.textRange.startOffset
        val spaceBeforeBrace = start > 0 && document.charsSequence[start - 1].isWhitespace()
        return arrayOf(
            FoldingDescriptor(
                element.node,
                TextRange.create(start, returnStatement.textRange.startOffset),
                group,
                if (spaceBeforeBrace) "" else " "
            ),
            FoldingDescriptor(
                element.node,
                TextRange.create(returnStatement.textRange.endOffset, rBrace.textRange.endOffset),
                group,
                ""
            ),
        )
    }
}
