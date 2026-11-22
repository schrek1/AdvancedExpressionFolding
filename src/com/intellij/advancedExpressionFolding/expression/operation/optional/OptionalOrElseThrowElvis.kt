package com.intellij.advancedExpressionFolding.expression.operation.optional

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.processor.group
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

/**
 * `opt.orElseThrow(() -> new X(args))` shown as `opt ?: throw X(args)` (or `?: error(msg)`).
 *
 * Only the [gaps] are folded; the receiver, the exception class and the arguments stay real text
 * and fold on their own as [children].
 */
class OptionalOrElseThrowElvis(
    element: PsiElement,
    private val gaps: List<Pair<TextRange, String>>,
    private val children: List<Expression>,
) : Expression(element, element.textRange) {

    override fun supportsFoldRegions(document: Document, parent: Expression?): Boolean = true

    override fun buildFoldRegions(
        element: PsiElement,
        document: Document,
        parent: Expression?
    ): Array<FoldingDescriptor> {
        val group = OptionalOrElseThrowElvis::class.group()
        val descriptors = gaps.mapTo(mutableListOf()) { (range, text) ->
            FoldingDescriptor(element.node, range, group, text)
        }
        children.filter { it.supportsFoldRegions(document, this) }.forEach { child ->
            descriptors += child.buildFoldRegions(child.element, document, this)
        }
        return descriptors.toTypedArray()
    }
}
