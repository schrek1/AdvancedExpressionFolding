package com.intellij.advancedExpressionFolding.unit

import com.intellij.advancedExpressionFolding.FoldingService
import com.intellij.advancedExpressionFolding.folding.BaseTest
import com.intellij.advancedExpressionFolding.isAdvancedExpressionFoldingGroup
import com.intellij.advancedExpressionFolding.settings.AdvancedExpressionFoldingSettings
import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.codeInsight.folding.CodeFoldingManager
import com.intellij.openapi.actionSystem.*
import com.intellij.testFramework.DumbModeTestUtils
import com.intellij.testFramework.runInEdtAndWait
import com.intellij.util.xmlb.XmlSerializer
import com.intellij.openapi.actionSystem.impl.SimpleDataContext
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.FoldRegion
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.editor.ex.FoldingModelEx
import com.intellij.testFramework.assertInstanceOf
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@Suppress("OverrideOnly")
class FoldingActionsTest : BaseTest() {

    private val settings: AdvancedExpressionFoldingSettings
        get() = AdvancedExpressionFoldingSettings.getInstance()
    private var originalGlobalOn: Boolean = true
    private var originalRequireNonNullElseElvis: Boolean = true

    @BeforeEach
    fun rememberOriginalState() {
        originalGlobalOn = settings.state.globalOn
        originalRequireNonNullElseElvis = settings.state.requireNonNullElseElvis
        settings.state.globalOn = true
    }

    @AfterEach
    fun restoreOriginalState() {
        settings.state.globalOn = originalGlobalOn
        settings.state.requireNonNullElseElvis = originalRequireNonNullElseElvis
    }

    @Test
    fun globalToggleActionFlipsStateThroughActionManager() {
        val action = ActionManager.getInstance().getAction("com.intellij.advancedExpressionFolding.action.GlobalToggleFoldingAction")
        val toggle = assertInstanceOf<ToggleAction>(action)

        val initialEvent = createEvent(toggle)
        assertTrue(settings.state.globalOn, "globalOn should start enabled for toggle test")

        ApplicationManager.getApplication().invokeAndWait {
            toggle.actionPerformed(initialEvent)
        }
        assertFalse(settings.state.globalOn, "Toggle action should disable global folding on first invocation")

        ApplicationManager.getApplication().invokeAndWait {
            toggle.actionPerformed(createEvent(toggle))
        }
        assertTrue(settings.state.globalOn, "Toggle action should re-enable global folding on second invocation")

        ApplicationManager.getApplication().invokeAndWait {
            toggle.actionPerformed(createEvent(toggle))
        }
        assertFalse(settings.state.globalOn, "Third toggle should disable global folding again")

        // Its update must not wait for a busy EDT: the main menu asks for it on every open
        assertEquals(ActionUpdateThread.BGT, toggle.actionUpdateThread)
        assertTrue(toggle.isDumbAware)
    }

    @Test
    fun foldingOnActionEnablesStateAndCollapsesRegionsViaService() {
        val (editor, region) = setUpEditorWithRegion(initiallyExpanded = true)
        settings.state.globalOn = false

        val action = ActionManager.getInstance().getAction("com.intellij.advancedExpressionFolding.action.FoldingOnAction")
        val event = createEvent(action, editor)

        ApplicationManager.getApplication().invokeAndWait {
            action.actionPerformed(event)
        }

        assertTrue(settings.state.globalOn, "FoldingOnAction should enable global folding state")
        assertFalse(region.isExpanded, "FoldingService should collapse plugin regions when enabling folding")

        // Repeating the action should keep the state idempotent
        ApplicationManager.getApplication().invokeAndWait {
            action.actionPerformed(createEvent(action, editor))
        }
        assertTrue(settings.state.globalOn, "Repeated enabling should leave global flag enabled")
        assertFalse(region.isExpanded, "Repeated enabling should keep regions collapsed")
    }

    @Test
    fun foldingOffActionExpandsRegionsWithoutTouchingState() {
        val (editor, region) = setUpEditorWithRegion(initiallyExpanded = false)
        settings.state.globalOn = true

        val action = ActionManager.getInstance().getAction("com.intellij.advancedExpressionFolding.action.FoldingOffAction")
        val event = createEvent(action, editor)

        ApplicationManager.getApplication().invokeAndWait {
            action.actionPerformed(event)
        }

        assertTrue(settings.state.globalOn, "FoldingOffAction must not change the global folding flag")
        assertTrue(region.isExpanded, "FoldingService should expand plugin regions when disabling folding")

        // Repeat the action to ensure idempotent expansion
        ApplicationManager.getApplication().invokeAndWait {
            action.actionPerformed(createEvent(action, editor))
        }
        assertTrue(region.isExpanded, "Repeated disabling should keep regions expanded")
        assertTrue(settings.state.globalOn, "Global flag should stay intact after repeated disabling")
    }

    @Test
    fun foldingOffShortcut_whileIndexing_expandsPluginRegions() {
        // GIVEN - a collapsed plugin fold and the IDE indexing the project
        val (_, region) = setUpEditorWithRegion(initiallyExpanded = false)

        // WHEN - Alt+Shift+D is pressed during indexing (the fixture fails when the action is disabled)
        runInEdtAndWait {
            DumbModeTestUtils.runInDumbModeSynchronously(fixture.project) {
                fixture.performEditorAction(FOLDING_OFF_ACTION)
            }
        }

        // THEN - the fold opens
        assertTrue(region.isExpanded)
    }

    @Test
    fun foldingOnShortcut_whileIndexing_collapsesPluginRegions() {
        // GIVEN - an expanded plugin fold and the IDE indexing the project
        val (_, region) = setUpEditorWithRegion(initiallyExpanded = true)

        // WHEN - Alt+Shift+F is pressed during indexing (the fixture fails when the action is disabled)
        runInEdtAndWait {
            DumbModeTestUtils.runInDumbModeSynchronously(fixture.project) {
                fixture.performEditorAction(FOLDING_ON_ACTION)
            }
        }

        // THEN - the fold closes
        assertFalse(region.isExpanded)
    }

    @Test
    fun foldPass_whileIndexing_keepsCollapsedPluginFolds() {
        // GIVEN - an open editor with its plugin folds collapsed
        openFoldedGreeter()
        runInEdtAndWait { FoldingService.get().fold(fixture.editor, true) }
        val foldsBefore = pluginRegionCount()

        // WHEN - the IDE starts indexing and the editor folds again
        runInEdtAndWait {
            DumbModeTestUtils.runInDumbModeSynchronously(fixture.project) {
                FoldingService.get().refoldOpenEditors()
            }
        }

        // THEN - indexing neither removes nor unfolds them, so Alt+Shift+D/F still have folds to act on
        val pluginRegions = pluginRegions()
        assertEquals(foldsBefore, pluginRegions.size, "plugin folds vanished during indexing")
        assertTrue(pluginRegions.none(FoldRegion::isExpanded), "folds unfolded during indexing: $pluginRegions")
    }

    @Test
    fun globalToggle_inOpenEditor_removesAndRestoresFoldsWithoutAnEdit() {
        // GIVEN - an open editor showing a plugin fold
        openFoldedGreeter()

        // WHEN - folding is switched off globally
        runInEdtAndWait { globalToggle().actionPerformed(createEvent(globalToggle())) }

        // THEN - the open editor drops the plugin folds at once
        assertEquals(0, pluginRegionCount())

        // WHEN - folding is switched on again
        runInEdtAndWait { globalToggle().actionPerformed(createEvent(globalToggle())) }

        // THEN - the folds are back without typing anything
        assertTrue(pluginRegionCount() > 0, "folds did not come back after switching on")
    }

    @Test
    fun foldingOnShortcut_afterGlobalOff_showsCollapsedFolds() {
        // GIVEN - folding switched off globally, so the open editor has no plugin folds
        openFoldedGreeter()
        runInEdtAndWait { globalToggle().actionPerformed(createEvent(globalToggle())) }
        assertFalse(settings.state.globalOn)

        // WHEN - Alt+Shift+F is pressed
        runInEdtAndWait { fixture.performEditorAction(FOLDING_ON_ACTION) }

        // THEN - the plugin folds are back and collapsed
        val pluginRegions = pluginRegions()
        assertTrue(pluginRegions.isNotEmpty(), "Alt+Shift+F did not bring the folds back")
        assertTrue(pluginRegions.none(FoldRegion::isExpanded), "folds came back expanded: $pluginRegions")
    }

    @Test
    fun globalToggle_afterRestoreDefaults_stillSwitchesFolding() {
        // GIVEN - the toggle action already exists and the settings are restored to defaults
        val toggle = globalToggle()
        val settingsBefore = XmlSerializer.serialize(settings.state)
        try {
            settings.restoreDefaults()
            assertTrue(settings.state.globalOn)

            // WHEN - the toggle is used
            runInEdtAndWait { toggle.actionPerformed(createEvent(toggle)) }

            // THEN - the folding the IDE reads is switched off
            assertFalse(settings.state.globalOn, "the toggle changed a settings object nobody reads")
        } finally {
            settings.loadState(XmlSerializer.deserialize(settingsBefore, State::class.java))
        }
    }

    private fun openFoldedGreeter() {
        state.requireNonNullElseElvis = true
        rebuildMethodCallFactory()
        fixture.configureByText("Greeter.java", GREETER)
        var allRegions = emptyList<String>()
        runInEdtAndWait {
            CodeFoldingManager.getInstance(fixture.project).updateFoldRegions(fixture.editor)
            allRegions = fixture.editor.foldingModel.allFoldRegions.map { region -> "${region.placeholderText} [${region.group}]" }
        }
        check(pluginRegionCount() > 0) { "the editor shows no plugin fold to begin with: $allRegions" }
    }

    private fun globalToggle(): ToggleAction =
        ActionManager.getInstance().getAction(GLOBAL_TOGGLE_ACTION) as ToggleAction

    private fun pluginRegions(): List<FoldRegion> {
        var regions = emptyList<FoldRegion>()
        runInEdtAndWait {
            regions = fixture.editor.foldingModel.allFoldRegions.filter(FoldRegion::isAdvancedExpressionFoldingGroup)
        }
        return regions
    }

    private fun pluginRegionCount() = pluginRegions().size

    private fun createEvent(action: AnAction, editor: Editor? = null): AnActionEvent {
        val builder = SimpleDataContext.builder()
            .add(CommonDataKeys.PROJECT, fixture.project)
        if (editor != null) {
            builder.add(CommonDataKeys.EDITOR, editor)
        }
        val dataContext = builder.build()
        return AnActionEvent.createFromAnAction(
            action,
            null,
            ActionPlaces.UNKNOWN,
            dataContext
        )
    }

    private fun setUpEditorWithRegion(initiallyExpanded: Boolean): Pair<Editor, FoldRegion> {
        val fileText = """
            class Toggle {
                void method() {
                    int value = 1 + 2;
                }
            }
        """.trimIndent()
        fixture.configureByText("Toggle.java", fileText)
        val editor = fixture.editor
        val document = editor.document
        val expressionOffset = document.text.indexOf("1 + 2")
        check(expressionOffset >= 0) { "Failed to locate folding expression in the document" }
        val endOffset = document.text.indexOf(';', expressionOffset).takeIf { it >= 0 }?.plus(1)
            ?: error("Failed to locate expression terminator for folding region")
        val group = FoldingGroup.newGroup("com.intellij.advancedExpressionFolding.test")
        var region: FoldRegion? = null
        ApplicationManager.getApplication().invokeAndWait {
            ApplicationManager.getApplication().runWriteAction {
                val foldingModel = editor.foldingModel as FoldingModelEx
                foldingModel.runBatchFoldingOperation {
                    region = foldingModel.createFoldRegion(expressionOffset, endOffset, "...", group, false)
                    requireNotNull(region) { "Failed to create test folding region" }
                    region!!.isExpanded = initiallyExpanded
                }
            }
        }
        return editor to requireNotNull(region)
    }

    private companion object {
        const val GLOBAL_TOGGLE_ACTION = "com.intellij.advancedExpressionFolding.action.GlobalToggleFoldingAction"
        const val FOLDING_ON_ACTION = "com.intellij.advancedExpressionFolding.action.FoldingOnAction"
        const val FOLDING_OFF_ACTION = "com.intellij.advancedExpressionFolding.action.FoldingOffAction"

        val GREETER = """
            import java.util.Objects;

            class Greeter {
                String greet(String name) {
                    return Objects.requireNonNullElse(name, "anonymous");
                }
            }
        """.trimIndent()
    }
}
