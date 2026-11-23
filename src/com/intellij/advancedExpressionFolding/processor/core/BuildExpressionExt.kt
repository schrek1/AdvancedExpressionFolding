package com.intellij.advancedExpressionFolding.processor.core

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.SyntheticExpressionImpl
import com.intellij.advancedExpressionFolding.processor.cache.CacheExt.getExpression
import com.intellij.advancedExpressionFolding.processor.util.Helper
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.diagnostic.ControlFlowException
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.IndexNotReadyException
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

object BuildExpressionExt {

    @JvmStatic
    fun buildExpression(element: PsiElement, document: Document, synthetic: Boolean): Expression? {
        val expression = tryBuildExpression(element, document, synthetic)
        if (expression != null) {
            return expression
        }
        if (synthetic) {
            // light PSI, e.g. the implicit parameters of a record's compact constructor, has no source text to wrap
            val textRange: TextRange = element.textRange ?: return null
            val children = arrayListOf<Expression>()
            Helper.findChildExpressions(element, children, document)
            return SyntheticExpressionImpl(
                element,
                textRange,
                document.getText(textRange),
                children
            )
        }
        return null
    }

    @JvmStatic
    @Throws(IndexNotReadyException::class)
    fun getAnyExpression(element: PsiElement, document: Document?): Expression {
        return getExpression(element, document ?: element.containingFile.viewProvider.document, true)!!
    }

    @JvmStatic
    @Throws(IndexNotReadyException::class)
    fun getNonSyntheticExpression(element: PsiElement, document: Document?): Expression? {
        return getExpression(element, document ?: element.containingFile.viewProvider.document, false)
    }

    @JvmStatic
    fun collectFoldRegionsRecursively(
        element: PsiElement,
        document: Document,
        uniqueSet: MutableSet<Expression>,
        allDescriptors: MutableList<FoldingDescriptor>
    ) {
        val expression = expressionOrNull(element, document)
        val unique = expression != null && uniqueSet.add(expression)
        if (expression != null && unique && expression.supportsFoldRegions(document, null)) {
            val descriptors = expression.buildFoldRegions(expression.element, document, null)
            if (descriptors.isNotEmpty()) {
                allDescriptors.addAll(descriptors)
            }
        }
        if (expression == null || (unique && expression.isNested())) {
            for (child in element.children) {
                collectFoldRegionsRecursively(child, document, uniqueSet, allDescriptors)
            }
        }
    }

    /**
     * The element's expression, or null when building it fails: one broken element (an IDE error from a resolve, say)
     * then costs only its own fold, and its children are still folded. Cancellation and dumb mode are rethrown.
     */
    private fun expressionOrNull(element: PsiElement, document: Document): Expression? =
        try {
            getNonSyntheticExpression(element, document)
        } catch (e: Throwable) {
            if (e is ControlFlowException || e is IndexNotReadyException) {
                throw e
            }
            LOG.warn("Folding skipped ${element.javaClass.simpleName} at ${element.textRange}", e)
            null
        }

    private val LOG = Logger.getInstance(BuildExpressionExt::class.java)
}
