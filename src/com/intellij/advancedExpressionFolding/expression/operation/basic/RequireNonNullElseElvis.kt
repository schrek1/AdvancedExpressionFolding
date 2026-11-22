package com.intellij.advancedExpressionFolding.expression.operation.basic

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

/**
 * `Objects.requireNonNullElse(value, fallback)` shown as `value ?: fallback`. Only the call syntax in [folds] turns
 * grey; [operands] stay real text and keep their own folds.
 */
class RequireNonNullElseElvis(
    element: PsiElement,
    textRange: TextRange,
    private val folds: List<Pair<TextRange, String>>,
    private val operands: List<Expression>,
) : Expression(element, textRange) {

    override fun supportsFoldRegions(document: Document, parent: Expression?): Boolean = true

    override fun buildFoldRegions(
        element: PsiElement,
        document: Document,
        parent: Expression?
    ): Array<FoldingDescriptor> {
        val group = FoldingGroup.newGroup(RequireNonNullElseElvis::class.java.name)
        val descriptors = folds
            .filter { (range, _) -> !range.isEmpty }
            .map { (range, placeholder) -> FoldingDescriptor(element.node, range, group, placeholder) }
            .toMutableList()
        for (operand in operands) {
            if (operand.supportsFoldRegions(document, this)) {
                descriptors += operand.buildFoldRegions(operand.element, document, this).toList()
            }
        }
        return descriptors.toTypedArray()
    }
}
