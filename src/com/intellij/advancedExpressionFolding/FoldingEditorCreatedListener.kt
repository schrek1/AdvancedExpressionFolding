package com.intellij.advancedExpressionFolding

import com.intellij.advancedExpressionFolding.inlay.VirtualLines
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.FoldRegion
import com.intellij.openapi.editor.event.EditorFactoryEvent
import com.intellij.openapi.editor.event.EditorFactoryListener
import com.intellij.openapi.editor.ex.FoldingListener
import com.intellij.openapi.editor.ex.FoldingModelEx
import com.intellij.openapi.editor.ex.util.EditorUtil
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.Key


class FoldingEditorCreatedListener : EditorFactoryListener {

    override fun editorCreated(event: EditorFactoryEvent) {
        VirtualLines.install(event.editor)
        collapseWhenFoldsFirstAppear(event.editor)
    }

    override fun editorReleased(event: EditorFactoryEvent) = FoldingService.get().clearAllKeys(event.editor)

    companion object {
        private val COLLAPSE_ON_FIRST_FOLDS = Key.create<Disposable>("AEF collapse on first folds")

        // the plugin's folds can arrive after the editor's first fold pass (e.g. once indexing ends), and the platform
        // adds such late folds expanded, so collapse them on the first pass that brings them, once per editor
        private fun collapseWhenFoldsFirstAppear(editor: Editor) {
            val foldingModel = editor.foldingModel as? FoldingModelEx ?: return
            val listenerLifetime = Disposer.newDisposable("AEF collapse on first folds")
            EditorUtil.disposeWithEditor(editor, listenerLifetime)
            editor.putUserData(COLLAPSE_ON_FIRST_FOLDS, listenerLifetime)
            foldingModel.addListener(object : FoldingListener {
                override fun onFoldProcessingEnd() {
                    if (foldingModel.allFoldRegions.none(FoldRegion::isAdvancedExpressionFoldingGroup)) {
                        return
                    }
                    stopCollapsingOnFirstFolds(editor)
                    // still inside the batch that ended; a nested batch here would run before the platform settles the caret
                    ApplicationManager.getApplication().invokeLater { FoldingService.get().fold(editor, true) }
                }
            }, listenerLifetime)
        }

        /** Takes the collapse-on-open listener off an editor still waiting for its first plugin folds. */
        fun stopCollapsingOnFirstFolds(editor: Editor) {
            editor.getUserData(COLLAPSE_ON_FIRST_FOLDS)?.let(Disposer::dispose)
            editor.putUserData(COLLAPSE_ON_FIRST_FOLDS, null)
        }
    }
}
