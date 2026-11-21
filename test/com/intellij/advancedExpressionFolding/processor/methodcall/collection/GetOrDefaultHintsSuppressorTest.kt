package com.intellij.advancedExpressionFolding.processor.methodcall.collection

import com.intellij.advancedExpressionFolding.folding.BaseTest
import com.intellij.codeInsight.hints.JavaInlayParameterHintsProvider
import com.intellij.codeInsight.hints.ParameterNameHintsSuppressor
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.openapi.application.ReadAction
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.runInEdtAndWait
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetOrDefaultHintsSuppressorTest : BaseTest() {

    private var globalOnBefore = true
    private var getOrDefaultElvisBefore = true

    @BeforeEach
    fun rememberOptions() {
        globalOnBefore = state.globalOn
        getOrDefaultElvisBefore = state.getOrDefaultElvis
        state.globalOn = true
    }

    @AfterEach
    fun restoreOptions() {
        state.globalOn = globalOnBefore
        state.getOrDefaultElvis = getOrDefaultElvisBefore
    }

    @Test
    fun parameterHints_onFoldedGetOrDefault_areHidden() {
        // GIVEN - the call is shown as `counts[word] ?: 0`
        state.getOrDefaultElvis = true

        // WHEN - the IDE asks for the parameter hints of the call
        val hints = visibleHints("int count = counts.getOrDefault(word, 0);")

        // THEN - no hint lands inside the folded text
        assertEquals(emptyList<String>(), hints)
    }

    @Test
    fun parameterHints_onGetOrDefaultInsideArithmetic_stayVisible() {
        // GIVEN - the call stays Java because it is an operand of `+`
        state.getOrDefaultElvis = true

        // WHEN - the IDE asks for the parameter hints of the call
        val hints = visibleHints("int next = counts.getOrDefault(word, 0) + 1;")

        // THEN - the Java hint for the default value is still shown
        assertEquals(listOf("defaultValue"), hints)
    }

    @Test
    fun parameterHints_withOptionOff_stayVisible() {
        // GIVEN - the getOrDefault fold is switched off
        state.getOrDefaultElvis = false

        // WHEN - the IDE asks for the parameter hints of the call
        val hints = visibleHints("int count = counts.getOrDefault(word, 0);")

        // THEN - the Java hint for the default value is still shown
        assertEquals(listOf("defaultValue"), hints)
    }

    private fun visibleHints(statement: String): List<String> {
        var hints = emptyList<String>()
        runInEdtAndWait {
            val file = fixture.configureByText(
                JavaFileType.INSTANCE,
                """
                import java.util.Map;

                class Counter {
                    void count(Map<String, Integer> counts, String word) {
                        $statement
                    }
                }
                """.trimIndent()
            )
            hints = ReadAction.compute<List<String>, RuntimeException> {
                val call = PsiTreeUtil.findChildrenOfType(file, PsiMethodCallExpression::class.java)
                    .single { it.methodExpression.referenceName == "getOrDefault" }
                JavaInlayParameterHintsProvider().getParameterHints(call)
                    .filterNot { ParameterNameHintsSuppressor.All.isSuppressedFor(file, it) }
                    .map { it.text.removeSuffix(":") }
            }
        }
        return hints
    }
}
