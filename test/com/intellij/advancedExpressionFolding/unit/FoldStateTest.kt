package com.intellij.advancedExpressionFolding.unit

import com.intellij.advancedExpressionFolding.FoldingService
import com.intellij.advancedExpressionFolding.folding.BaseTest
import com.intellij.advancedExpressionFolding.isAdvancedExpressionFoldingGroup
import com.intellij.advancedExpressionFolding.settings.AdvancedExpressionFoldingSettings
import com.intellij.codeInsight.folding.CodeFoldingManager
import com.intellij.lang.folding.LanguageFolding
import com.intellij.lang.java.JavaLanguage
import com.intellij.openapi.editor.FoldRegion
import com.intellij.testFramework.DumbModeTestUtils
import com.intellij.testFramework.PlatformTestUtil
import com.intellij.testFramework.runInEdtAndWait
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FoldStateTest : BaseTest() {

    @BeforeEach
    fun enableDestructuringFolds() {
        assignState(state::globalOn, state::destructuring, state::varExpressionsCollapse)
        rebuildMethodCallFactory()
    }

    @AfterEach
    fun restoreSettings() = AdvancedExpressionFoldingSettings.getInstance().restoreDefaults()

    @Test
    fun foldPass_afterIndexingEnds_keepsCollapsedGroupedFoldsCollapsed() {
        // GIVEN - an open file whose grouped destructuring folds are all collapsed
        openFile()
        runInEdtAndWait {
            CodeFoldingManager.getInstance(fixture.project).updateFoldRegions(fixture.editor)
            FoldingService.get().fold(fixture.editor, true)
        }
        val foldsBefore = pluginRegions().size

        // WHEN - the IDE indexes and the editor folds again during and after indexing
        runInEdtAndWait {
            DumbModeTestUtils.runInDumbModeSynchronously(fixture.project) {
                FoldingService.get().refoldOpenEditors()
            }
            FoldingService.get().refoldOpenEditors()
        }

        // THEN - no fold opened up
        val pluginRegions = pluginRegions()
        assertEquals(foldsBefore, pluginRegions.size)
        assertTrue(pluginRegions.none(FoldRegion::isExpanded), "folds opened after indexing: ${describe(pluginRegions)}")
    }

    @Test
    fun foldPass_afterIndexingEnds_foldsBuiltOutsideAFoldPassInBetween_keepsCollapsedFoldsCollapsed() {
        // GIVEN - an open file with its plugin folds collapsed, whose folds the IDE then builds once more outside a
        // fold pass (as it does e.g. to save the folding state)
        openFile()
        runInEdtAndWait {
            CodeFoldingManager.getInstance(fixture.project).updateFoldRegions(fixture.editor)
            FoldingService.get().fold(fixture.editor, true)
            LanguageFolding.buildFoldingDescriptors(
                LanguageFolding.INSTANCE.forLanguage(JavaLanguage.INSTANCE), fixture.file, fixture.editor.document, false
            )
        }

        // WHEN - the IDE indexes and the editor folds again during and after indexing
        runInEdtAndWait {
            DumbModeTestUtils.runInDumbModeSynchronously(fixture.project) {
                FoldingService.get().refoldOpenEditors()
            }
            FoldingService.get().refoldOpenEditors()
        }

        // THEN - no fold opened up
        val pluginRegions = pluginRegions()
        assertTrue(pluginRegions.none(FoldRegion::isExpanded), "folds opened after indexing: ${describe(pluginRegions)}")
    }

    @Test
    fun openFile_firstFoldPassDone_showsPluginFoldsCollapsed() {
        // GIVEN - a newly opened file
        openFile()

        // WHEN - its first fold pass with plugin folds is applied
        runInEdtAndWait {
            CodeFoldingManager.getInstance(fixture.project).updateFoldRegions(fixture.editor)
            PlatformTestUtil.dispatchAllInvocationEventsInIdeEventQueue()
        }

        // THEN - the plugin folds are collapsed at once, not after a delay
        val pluginRegions = pluginRegions()
        assertTrue(pluginRegions.isNotEmpty(), "the file shows no plugin fold")
        assertTrue(pluginRegions.none(FoldRegion::isExpanded), "folds expanded after the first pass: ${describe(pluginRegions)}")
    }

    @Test
    fun foldPass_afterTheFirstOne_leavesAFoldTheUserOpenedOpen() {
        // GIVEN - an opened file whose plugin folds were collapsed on open, then one of them opened by the user
        openFile()
        lateinit var openedByUser: FoldRegion
        runInEdtAndWait {
            CodeFoldingManager.getInstance(fixture.project).updateFoldRegions(fixture.editor)
            PlatformTestUtil.dispatchAllInvocationEventsInIdeEventQueue()
            openedByUser = fixture.editor.foldingModel.allFoldRegions.first(FoldRegion::isAdvancedExpressionFoldingGroup)
            fixture.editor.foldingModel.runBatchFoldingOperation { openedByUser.isExpanded = true }
        }

        // WHEN - the editor folds again
        runInEdtAndWait {
            CodeFoldingManager.getInstance(fixture.project).updateFoldRegions(fixture.editor)
            PlatformTestUtil.dispatchAllInvocationEventsInIdeEventQueue()
        }

        // THEN - the fold stays open
        assertTrue(openedByUser.isValid && openedByUser.isExpanded)
    }

    @Test
    fun foldPass_bringingFoldsAfterTheFirstPassThatHadSome_showsThemCollapsed() {
        // GIVEN - an opened file whose first plugin folds (only `val`) were collapsed on open
        assignState(state::globalOn, state::varExpressionsCollapse)
        openFile()
        runInEdtAndWait {
            CodeFoldingManager.getInstance(fixture.project).updateFoldRegions(fixture.editor)
            PlatformTestUtil.dispatchAllInvocationEventsInIdeEventQueue()
        }
        val foldsOfFirstPass = pluginRegions().size
        assertTrue(foldsOfFirstPass > 0, "the first pass brought no plugin fold")

        // WHEN - a later fold pass brings more plugin folds (the destructuring), as when a fold needs a resolve that
        // is not ready during the first pass
        assignState(state::globalOn, state::varExpressionsCollapse, state::destructuring)
        rebuildMethodCallFactory()
        runInEdtAndWait {
            FoldingService.get().refoldOpenEditors()
            PlatformTestUtil.dispatchAllInvocationEventsInIdeEventQueue()
        }

        // THEN - the late folds are collapsed as well
        val pluginRegions = pluginRegions()
        assertTrue(pluginRegions.size > foldsOfFirstPass, "the later pass brought no new plugin fold")
        assertTrue(pluginRegions.none(FoldRegion::isExpanded), "folds expanded after the later pass: ${describe(pluginRegions)}")
    }

    private fun openFile() {
        fixture.configureByText("Pairs.java", PAIRS)
    }

    private fun pluginRegions(): List<FoldRegion> {
        var regions = emptyList<FoldRegion>()
        runInEdtAndWait {
            regions = fixture.editor.foldingModel.allFoldRegions.filter(FoldRegion::isAdvancedExpressionFoldingGroup)
        }
        return regions
    }

    private fun describe(regions: List<FoldRegion>) =
        regions.joinToString { region -> "'${region.placeholderText}' expanded=${region.isExpanded}" }

    private companion object {
        val PAIRS = """
            class Pairs {
                void split(String[] parts) {
                    String first = parts[0];
                    String second = parts[1];
                    String third = parts[2];
                }
            }
        """.trimIndent()
    }
}
