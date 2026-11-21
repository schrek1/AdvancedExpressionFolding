package com.intellij.advancedExpressionFolding.processor.methodcall.collection

import com.intellij.advancedExpressionFolding.settings.AdvancedExpressionFoldingSettings
import com.intellij.codeInsight.hints.InlayInfo
import com.intellij.codeInsight.hints.ParameterNameHintsSuppressor
import com.intellij.codeInsight.hints.declarative.InlayHintsCollector
import com.intellij.codeInsight.hints.declarative.InlayHintsProvider
import com.intellij.codeInsight.hints.declarative.InlayTreeSink
import com.intellij.codeInsight.hints.declarative.InlineInlayPosition
import com.intellij.codeInsight.hints.declarative.SharedBypassCollector
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiExpressionList
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.PsiMethodCallExpression

/** Shows `1#`, `2#`, ... before each key of a `Map.of(...)` folded into a map literal. */
class MapLiteralPairIndexInlayProvider : InlayHintsProvider {

    override fun createCollector(file: PsiFile, editor: Editor): InlayHintsCollector? =
        if (file is PsiJavaFile && mapLiteralsOn()) Collector else null

    private object Collector : SharedBypassCollector {
        override fun collectFromElement(element: PsiElement, sink: InlayTreeSink) {
            val call = element as? PsiMethodCallExpression ?: return
            if (!call.isFoldedMapOf()) return
            call.argumentList.expressions.toList().chunked(2).forEachIndexed { index, (key) ->
                val beforeKey = InlineInlayPosition(key.textRange.startOffset, relatedToPrevious = false)
                sink.addPresentation(beforeKey, hasBackground = true) {
                    text("${index + 1}#")
                }
            }
        }
    }
}

/** Hides Java's `k1:` / `v1:` parameter name hints inside a folded `Map.of(...)`; the pair index replaces them. */
class MapLiteralParameterHintsSuppressor : ParameterNameHintsSuppressor {

    override fun isSuppressedFor(file: PsiFile, inlayInfo: InlayInfo): Boolean {
        if (!mapLiteralsOn()) return false
        val offset = inlayInfo.offset
        val argument = generateSequence(file.findElementAt(offset)) { it.parent }
            .takeWhile { it.textRange.startOffset == offset }
            .lastOrNull() ?: return false
        val call = (argument.parent as? PsiExpressionList)?.parent as? PsiMethodCallExpression ?: return false
        return call.isFoldedMapOf()
    }
}

private fun mapLiteralsOn(): Boolean {
    val state = AdvancedExpressionFoldingSettings.getInstance().state
    return state.globalOn && state.mapAndEmptyCollectionLiterals
}

// same scope as CollectionLiteralMethodCall: a qualified, non-empty Map.of
private fun PsiMethodCallExpression.isFoldedMapOf(): Boolean =
    methodExpression.referenceName == "of" &&
        methodExpression.qualifierExpression != null &&
        argumentList.expressionCount > 0 &&
        resolveMethod()?.containingClass?.qualifiedName == "java.util.Map"
