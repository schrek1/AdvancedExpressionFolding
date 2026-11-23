package com.intellij.advancedExpressionFolding

import com.intellij.advancedExpressionFolding.folding.BaseTest
import com.intellij.advancedExpressionFolding.folding.util.TestDynamicDataProvider
import com.intellij.advancedExpressionFolding.inlay.VirtualLines
import com.intellij.advancedExpressionFolding.processor.methodcall.MethodCallFactory
import com.intellij.codeInsight.folding.CodeFoldingManager
import com.intellij.ide.plugins.IdeaPluginDescriptor
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.FoldRegion
import com.intellij.openapi.util.UserDataHolderBase
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiRecursiveElementWalkingVisitor
import com.intellij.testFramework.EditorTestUtil
import com.intellij.testFramework.PlatformTestUtil
import com.intellij.testFramework.runInEdtAndWait
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PluginUpdateListenerTest : BaseTest() {

    @Language("JAVA")
    private val source = """
        class Shop {
            void checkout(Order order) {
                order.validate();
                order.reserveStock();
                order.notifyCustomer("Thanks");
            }

            interface Order {
                void validate();
                void reserveStock();
                void notifyCustomer(String message);
            }
        }
    """.trimIndent()

    @Test
    fun beforePluginUnload_editorWithVirtualLines_leavesNoPluginObjectOnTheEditorOrItsPsi() {
        runInEdtAndWait {
            // GIVEN - an editor folded as with(order), its virtual lines drawn and its PSI carrying the fold caches
            val editor = foldedEditorWithVirtualLines()
            assertTrue(virtualLineInlays(editor).isNotEmpty(), "no virtual lines before the unload")
            assertTrue(pluginUserDataOnPsi().isNotEmpty(), "no fold cache on the PSI before the unload")

            // WHEN - the plugin is about to unload for an update
            PluginUpdateListener().beforePluginUnload(ownDescriptor(), isUpdate = true)

            // THEN - no virtual line is left and the PSI holds no plugin object
            assertEquals(emptyList<Any>(), virtualLineInlays(editor))
            assertEquals(emptyList<String>(), pluginUserDataOnPsi())
        }
    }

    @Test
    fun beforePluginUnload_foldCollapsedAgainAfterwards_drawsNoVirtualLines() {
        runInEdtAndWait {
            // GIVEN - the plugin unloaded from an editor folded as with(order)
            val editor = foldedEditorWithVirtualLines()
            PluginUpdateListener().beforePluginUnload(ownDescriptor(), isUpdate = true)

            // WHEN - the fold group is expanded and collapsed again
            setFoldsExpanded(editor, true)
            setFoldsExpanded(editor, false)

            // THEN - the editor's folding listener is gone, so nothing draws the virtual lines again
            assertEquals(emptyList<Any>(), virtualLineInlays(editor))
        }
    }

    @Test
    fun beforePluginUnload_editorStillWaitingForItsFirstFolds_leavesNoListenerThatCollapsesThem() {
        runInEdtAndWait {
            // GIVEN - an open editor whose plugin folds have not arrived yet
            assignState(state::scopeWith)
            MethodCallFactory.initialize(TestDynamicDataProvider())
            fixture.configureByText("Shop.java", source)

            // WHEN - the plugin unloads and the folds arrive afterwards
            PluginUpdateListener().beforePluginUnload(ownDescriptor(), isUpdate = true)
            CodeFoldingManager.getInstance(fixture.project).updateFoldRegions(fixture.editor)
            PlatformTestUtil.dispatchAllInvocationEventsInIdeEventQueue()

            // THEN - nothing of the unloaded plugin is left on the editor to collapse them
            val pluginRegions = fixture.editor.foldingModel.allFoldRegions.filter(FoldRegion::isAdvancedExpressionFoldingGroup)
            assertTrue(pluginRegions.isNotEmpty(), "no plugin folds arrived")
            assertTrue(pluginRegions.all(FoldRegion::isExpanded), "a listener left on the editor collapsed the folds")
        }
    }

    @Test
    fun pluginLoaded_editorOpenBeforeTheLoad_drawsItsVirtualLines() {
        runInEdtAndWait {
            // GIVEN - an editor folded as with(order) that the previous version of the plugin left without virtual lines
            val editor = foldedEditorWithVirtualLines()
            PluginUpdateListener().beforePluginUnload(ownDescriptor(), isUpdate = true)

            // WHEN - the new version of the plugin is loaded
            PluginUpdateListener().pluginLoaded(ownDescriptor())

            // THEN - the open editor shows the virtual lines again
            assertTrue(virtualLineInlays(editor).isNotEmpty(), "no virtual lines after the load")
        }
    }

    private fun foldedEditorWithVirtualLines(): Editor {
        assignState(state::scopeWith)
        MethodCallFactory.initialize(TestDynamicDataProvider())
        fixture.configureByText("Shop.java", source)
        val editor = fixture.editor
        EditorTestUtil.buildInitialFoldingsInBackground(editor)
        VirtualLines.install(editor)
        return editor
    }

    private fun ownDescriptor(): IdeaPluginDescriptor = PluginManagerCore.loadedPlugins.single {
        it.pluginClassLoader === PluginUpdateListener::class.java.classLoader && it.pluginId.idString.contains("folding")
    }

    private fun setFoldsExpanded(editor: Editor, expanded: Boolean) {
        editor.foldingModel.runBatchFoldingOperation {
            editor.foldingModel.allFoldRegions.filter { it.group != null }.forEach { it.isExpanded = expanded }
        }
    }

    private fun virtualLineInlays(editor: Editor) = editor.inlayModel.let { inlays ->
        val length = editor.document.textLength
        inlays.getBlockElementsInRange(0, length, VirtualLines.LineRenderer::class.java) +
            inlays.getInlineElementsInRange(0, length, VirtualLines.IndentRenderer::class.java)
    }

    /** User data values on the file's PSI whose class is the plugin's own, as `element -> value class`. */
    private fun pluginUserDataOnPsi(): List<String> = ReadAction.compute<List<String>, RuntimeException> {
        val found = mutableListOf<String>()
        fixture.file.accept(object : PsiRecursiveElementWalkingVisitor() {
            override fun visitElement(element: PsiElement) {
                val userMap = (element as? UserDataHolderBase)?.userMap
                userMap?.keys?.forEach { key ->
                    val value = userMap.get(key)
                    if (value != null && value.javaClass.name.startsWith(PLUGIN_PACKAGE)) {
                        found += "$element -> ${value.javaClass.name}"
                    }
                }
                super.visitElement(element)
            }
        })
        found
    }

    private companion object {
        const val PLUGIN_PACKAGE = "com.intellij.advancedExpressionFolding."
    }
}
