package com.intellij.advancedExpressionFolding

import com.intellij.advancedExpressionFolding.inlay.VirtualLines
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.FoldRegion
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.editor.event.EditorFactoryEvent
import com.intellij.openapi.editor.event.EditorFactoryListener
import com.intellij.openapi.editor.ex.FoldingListener
import com.intellij.openapi.editor.ex.FoldingModelEx
import com.intellij.openapi.editor.ex.util.EditorUtil
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.Key
import java.util.Collections
import java.util.WeakHashMap


class FoldingEditorCreatedListener : EditorFactoryListener {

    override fun editorCreated(event: EditorFactoryEvent) {
        VirtualLines.install(event.editor)
        collapseWhenFoldsFirstAppear(event.editor)
    }

    override fun editorReleased(event: EditorFactoryEvent) = FoldingService.get().clearAllKeys(event.editor)

    companion object {
        private val COLLAPSE_ON_FIRST_FOLDS = Key.create<Disposable>("AEF collapse new folds until first edit")

        // the platform adds a fold that arrives after the editor's first fold pass expanded, so each pass collapses the
        // plugin folds it brought (e.g. once indexing ends, or once a Lombok-generated method resolves). A fold the user
        // opened is no new region in later passes and stays open; the user's first edit ends this, as folds that appear
        // while typing are expanded on purpose
        private fun collapseWhenFoldsFirstAppear(editor: Editor) {
            val foldingModel = editor.foldingModel as? FoldingModelEx ?: return
            val listenerLifetime = Disposer.newDisposable("AEF collapse new folds until first edit")
            EditorUtil.disposeWithEditor(editor, listenerLifetime)
            editor.putUserData(COLLAPSE_ON_FIRST_FOLDS, listenerLifetime)
            val seenRegions = Collections.newSetFromMap(WeakHashMap<FoldRegion, Boolean>())
            foldingModel.addListener(object : FoldingListener {
                override fun onFoldProcessingEnd() {
                    val newRegions = foldingModel.allFoldRegions
                        .filter(FoldRegion::isAdvancedExpressionFoldingGroup)
                        .filter(seenRegions::add)
                    if (newRegions.isEmpty()) {
                        return
                    }
                    // still inside the batch that ended; a nested batch here would run before the platform settles the caret
                    ApplicationManager.getApplication().invokeLater { collapse(editor, newRegions) }
                }
            }, listenerLifetime)
            editor.document.addDocumentListener(object : DocumentListener {
                override fun documentChanged(event: DocumentEvent) = stopCollapsingOnFirstFolds(editor)
            }, listenerLifetime)
        }

        private fun collapse(editor: Editor, regions: List<FoldRegion>) {
            if (editor.isDisposed) {
                return
            }
            editor.foldingModel.runBatchFoldingOperation {
                regions.filter(FoldRegion::isValid).forEach { region -> region.isExpanded = false }
            }
        }

        /** Takes the collapse-on-open listener off an editor, e.g. once the user has started editing it. */
        fun stopCollapsingOnFirstFolds(editor: Editor) {
            editor.getUserData(COLLAPSE_ON_FIRST_FOLDS)?.let(Disposer::dispose)
            editor.putUserData(COLLAPSE_ON_FIRST_FOLDS, null)
        }
    }
}
