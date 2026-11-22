package com.intellij.advancedExpressionFolding.expression.operation.stream

import com.google.common.collect.Sets
import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.processor.core.BuildExpressionExt
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

/** Covers the whole chain, so the inner `map`/`stream` folds are skipped; the receiver keeps its own folds. */
class StreamSpreadChain(
    element: PsiElement,
    private val receiver: PsiElement,
    private val folds: List<Pair<TextRange, String>>,
) : Expression(element, element.textRange) {

    override fun supportsFoldRegions(document: Document, parent: Expression?): Boolean = true

    override fun buildFoldRegions(
        element: PsiElement,
        document: Document,
        parent: Expression?
    ): Array<FoldingDescriptor> {
        val group = FoldingGroup.newGroup(StreamSpreadChain::class.java.name)
        val descriptors = folds.mapTo(mutableListOf()) { (range, placeholder) ->
            FoldingDescriptor(element.node, range, group, placeholder)
        }
        BuildExpressionExt.collectFoldRegionsRecursively(receiver, document, Sets.newIdentityHashSet(), descriptors)
        return descriptors.toTypedArray()
    }
}
