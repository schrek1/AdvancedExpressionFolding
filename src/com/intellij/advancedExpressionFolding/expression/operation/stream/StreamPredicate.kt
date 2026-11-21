package com.intellij.advancedExpressionFolding.expression.operation.stream

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

/**
 * `anyMatch` / `allMatch` / `noneMatch` shown as Kotlin's `any` / `all` / `none`: each range is replaced by its text,
 * and the folds of `operands` (the receiver and the predicate) are kept.
 */
class StreamPredicate(
    element: PsiElement,
    textRange: TextRange,
    private val replacements: List<Pair<TextRange, String>>,
    private val operands: List<Expression>,
) : Expression(element, textRange) {

    override fun supportsFoldRegions(document: Document, parent: Expression?): Boolean = true

    override fun buildFoldRegions(
        element: PsiElement,
        document: Document,
        parent: Expression?
    ): Array<FoldingDescriptor> {
        val group = FoldingGroup.newGroup(StreamPredicate::class.java.name)
        val descriptors = replacements.map { (range, text) -> FoldingDescriptor(element.node, range, group, text) }
        val operandDescriptors = operands
            .filter { it.supportsFoldRegions(document, this) }
            .flatMap { it.buildFoldRegions(it.element, document, this).asList() }
        return (descriptors + operandDescriptors).toTypedArray()
    }
}
