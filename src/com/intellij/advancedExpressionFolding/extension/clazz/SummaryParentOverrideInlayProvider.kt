package com.intellij.advancedExpressionFolding.extension.clazz

import com.intellij.advancedExpressionFolding.AdvancedExpressionFoldingSettings
import com.intellij.advancedExpressionFolding.extension.clazz.SummaryParentOverrideExt.parentOverrideLabels
import com.intellij.codeInsight.hints.declarative.InlayHintsCollector
import com.intellij.codeInsight.hints.declarative.InlayHintsProvider
import com.intellij.codeInsight.hints.declarative.InlayTreeSink
import com.intellij.codeInsight.hints.declarative.InlineInlayPosition
import com.intellij.codeInsight.hints.declarative.SharedBypassCollector
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiJavaFile

/**
 * Shows the override summary after each parent name in `extends` / `implements`.
 *
 * An inlay rather than a fold: a fold region cannot be empty, so it would have to swallow the name's last
 * character and repaint it as placeholder text.
 */
class SummaryParentOverrideInlayProvider : InlayHintsProvider {

    override fun createCollector(file: PsiFile, editor: Editor): InlayHintsCollector? {
        val state = AdvancedExpressionFoldingSettings.getInstance().state
        return if (file is PsiJavaFile && state.globalOn && state.summaryParentOverride) Collector else null
    }

    private object Collector : SharedBypassCollector {
        override fun collectFromElement(element: PsiElement, sink: InlayTreeSink) {
            val clazz = element as? PsiClass ?: return
            clazz.parentOverrideLabels().forEach { (nameElement, label) ->
                val afterName = InlineInlayPosition(nameElement.textRange.endOffset, relatedToPrevious = true)
                sink.addPresentation(afterName, hasBackground = true) {
                    text(label)
                }
            }
        }
    }
}
