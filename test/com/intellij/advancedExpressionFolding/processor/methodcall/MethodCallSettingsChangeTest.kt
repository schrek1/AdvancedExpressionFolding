package com.intellij.advancedExpressionFolding.processor.methodcall

import com.intellij.advancedExpressionFolding.AdvancedExpressionFoldingBuilder
import com.intellij.advancedExpressionFolding.FoldingService
import com.intellij.advancedExpressionFolding.folding.BaseTest
import com.intellij.codeInsight.folding.CodeFoldingManager
import com.intellij.openapi.application.ReadAction
import com.intellij.testFramework.runInEdtAndWait
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MethodCallSettingsChangeTest : BaseTest() {

    private var globalOnBefore = true
    private var requireNonNullElseElvisBefore = true

    @BeforeEach
    fun rememberOptions() {
        globalOnBefore = state.globalOn
        requireNonNullElseElvisBefore = state.requireNonNullElseElvis
        state.globalOn = true
    }

    @AfterEach
    fun restoreOptions() {
        state.globalOn = globalOnBefore
        state.requireNonNullElseElvis = requireNonNullElseElvisBefore
    }

    @Test
    fun requireNonNullElse_afterOptionSwitchedOn_foldsWithoutRestart() {
        // GIVEN - the IDE started with the Elvis option off
        state.requireNonNullElseElvis = false
        rebuildMethodCallFactory()

        // WHEN - the option is switched on and the file folds again
        state.requireNonNullElseElvis = true
        val placeholders = previewPlaceholders()

        // THEN - the call is shown as `name ?: "anonymous"`
        assertTrue(" ?:" in placeholders, "placeholders: $placeholders")
    }

    @Test
    fun requireNonNullElse_afterOptionSwitchedOff_staysJava() {
        // GIVEN - the IDE started with the Elvis option on
        state.requireNonNullElseElvis = true
        rebuildMethodCallFactory()

        // WHEN - the option is switched off and the file folds again
        state.requireNonNullElseElvis = false
        val placeholders = previewPlaceholders()

        // THEN - the call stays plain Java
        assertFalse(" ?:" in placeholders, "placeholders: $placeholders")
    }

    @Test
    fun openEditor_afterOptionSwitchedOnAndRefold_showsTheNewFold() {
        // GIVEN - an editor is open and folded while the Elvis option is off
        state.requireNonNullElseElvis = false
        rebuildMethodCallFactory()
        fixture.configureByText("Greeter.java", GREETER)
        runInEdtAndWait {
            CodeFoldingManager.getInstance(fixture.project).buildInitialFoldings(fixture.editor)
        }

        // WHEN - the option is switched on and the open editors are re-folded, as Settings Apply does
        state.requireNonNullElseElvis = true
        var placeholders = emptyList<String>()
        runInEdtAndWait {
            FoldingService.get().refoldOpenEditors()
            placeholders = fixture.editor.foldingModel.allFoldRegions.map { it.placeholderText }
        }

        // THEN - the open editor shows the call as `name ?: "anonymous"`
        assertTrue(" ?:" in placeholders, "placeholders: $placeholders")
    }

    private fun previewPlaceholders(): List<String> {
        fixture.configureByText("Greeter.java", GREETER)
        val file = fixture.file
        val document = fixture.getDocument(file)
        return ReadAction.compute<List<String>, RuntimeException> {
            AdvancedExpressionFoldingBuilder().preview(file, document)
        }.map { summary -> summary.substringAfter(" => ").substringBefore("[") }
    }

    private companion object {
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
