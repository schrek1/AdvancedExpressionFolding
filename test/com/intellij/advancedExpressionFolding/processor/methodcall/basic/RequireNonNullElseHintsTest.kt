package com.intellij.advancedExpressionFolding.processor.methodcall.basic

import com.intellij.advancedExpressionFolding.folding.BaseTest
import com.intellij.codeInsight.hints.JavaInlayParameterHintsProvider
import com.intellij.codeInsight.hints.ParameterNameHintsSuppressor
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.openapi.application.ReadAction
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.runInEdtAndWait
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RequireNonNullElseHintsTest : BaseTest() {

    private var globalOnBefore = true
    private var requireNonNullElseElvisBefore = true

    @BeforeEach
    fun turnOptionOn() {
        globalOnBefore = state.globalOn
        requireNonNullElseElvisBefore = state.requireNonNullElseElvis
        state.globalOn = true
        state.requireNonNullElseElvis = true
    }

    @AfterEach
    fun restoreOptions() {
        state.globalOn = globalOnBefore
        state.requireNonNullElseElvis = requireNonNullElseElvisBefore
    }

    @Test
    fun parameterHints_onFoldedRequireNonNullElse_areHidden() {
        // GIVEN - the call is shown as `name ?: "anonymous"`

        // WHEN - the IDE asks for the parameter hints of the call
        val hints = visibleHints("String shown = java.util.Objects.requireNonNullElse(name, \"anonymous\");")

        // THEN - no hint lands inside the folded text
        assertEquals(emptyList<String>(), hints)
    }

    @Test
    fun parameterHints_onRequireNonNullElseAsReceiverOfOneCall_areHidden() {
        // GIVEN - the call is shown as `(name ?: "").length()`

        // WHEN - the IDE asks for the parameter hints of the call
        val hints = visibleHints("int length = java.util.Objects.requireNonNullElse(name, \"\").length();")

        // THEN - no hint lands inside the folded text
        assertEquals(emptyList<String>(), hints)
    }

    @Test
    fun parameterHints_onRequireNonNullElseInsideLongerCallChain_stayVisible() {
        // GIVEN - the call stays Java because two calls are made on its result

        // WHEN - the IDE asks for the parameter hints of the call
        val hints = visibleHints("int length = java.util.Objects.requireNonNullElse(name, \"\").trim().length();")

        // THEN - the Java hint for the fallback is still shown
        assertEquals(listOf("defaultObj"), hints)
    }

    private fun visibleHints(statement: String): List<String> {
        var hints = emptyList<String>()
        runInEdtAndWait {
            val file = fixture.configureByText(
                JavaFileType.INSTANCE,
                """
                class Greeter {
                    void greet(String name) {
                        $statement
                    }
                }
                """.trimIndent()
            )
            hints = ReadAction.compute<List<String>, RuntimeException> {
                val call = PsiTreeUtil.findChildrenOfType(file, PsiMethodCallExpression::class.java)
                    .single { it.methodExpression.referenceName == "requireNonNullElse" }
                JavaInlayParameterHintsProvider().getParameterHints(call)
                    .filterNot { ParameterNameHintsSuppressor.All.isSuppressedFor(file, it) }
                    .map { it.text.removeSuffix(":") }
            }
        }
        return hints
    }
}
