package com.intellij.advancedExpressionFolding.processor.methodcall.collection

import com.intellij.advancedExpressionFolding.folding.BaseTest
import com.intellij.codeInsight.hints.InlayDumpUtil
import com.intellij.codeInsight.hints.InlayInfo
import com.intellij.codeInsight.hints.declarative.impl.DeclarativeInlayHintsPassFactory
import com.intellij.codeInsight.hints.declarative.impl.util.DeclarativeHintsDumpUtil
import com.intellij.codeInsight.hints.declarative.impl.views.TextInlayPresentationEntry
import com.intellij.codeInsight.multiverse.codeInsightContext
import com.intellij.openapi.application.runReadAction
import com.intellij.openapi.progress.EmptyProgressIndicator
import com.intellij.testFramework.runInEdtAndGet
import com.intellij.testFramework.runInEdtAndWait
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MapLiteralHintsTest : BaseTest() {

    private var mapLiteralsBeforeTest = false

    @BeforeEach
    fun enableMapLiterals() {
        mapLiteralsBeforeTest = state.mapAndEmptyCollectionLiterals
        state.mapAndEmptyCollectionLiterals = true
    }

    @AfterEach
    fun restoreMapLiterals() {
        state.mapAndEmptyCollectionLiterals = mapLiteralsBeforeTest
    }

    @Test
    fun inlay_mapOfWithPairs_indexBeforeEachKey() {
        // GIVEN - a Map.of with two key/value pairs
        @Language("JAVA")
        val withExpectedInlays = """
            import java.util.Map;

            class Limits {
                Map<String, Integer> limits = Map.of(/*<# 1# #>*/"cpu", 4, /*<# 2# #>*/"ram", 16);
            }
        """.trimIndent()

        // WHEN - inlay hints are collected for the file
        val rendered = renderPairIndexInlays(withExpectedInlays)

        // THEN - each key is preceded by its pair number, values get none
        assertEquals(withExpectedInlays, rendered)
    }

    @Test
    fun inlay_optionOff_noIndex() {
        // GIVEN - map literals are off, so Map.of stays as written
        state.mapAndEmptyCollectionLiterals = false
        @Language("JAVA")
        val withoutInlays = """
            import java.util.Map;

            class Limits {
                Map<String, Integer> limits = Map.of("cpu", 4);
            }
        """.trimIndent()

        // WHEN - inlay hints are collected for the file
        val rendered = renderPairIndexInlays(withoutInlays)

        // THEN - no pair index is shown
        assertEquals(withoutInlays, rendered)
    }

    private fun renderPairIndexInlays(sourceWithExpectedInlays: String): String {
        runInEdtAndWait {
            fixture.configureByText("Limits.java", InlayDumpUtil.removeInlays(sourceWithExpectedInlays))
        }
        // the platform builds and collects the pass off the EDT, then applies it on the EDT
        val pass = runReadAction {
            DeclarativeInlayHintsPassFactory.createPassForPreview(
                fixture.file,
                fixture.editor,
                MapLiteralPairIndexInlayProvider(),
                "advancedExpressionFolding.mapLiteralPairIndex",
                emptyMap(),
                false
            ).also { pass ->
                pass.setContext(fixture.file.codeInsightContext)
                pass.doCollectInformation(EmptyProgressIndicator())
            }
        }
        return runInEdtAndGet {
            pass.applyInformationToEditor()
            DeclarativeHintsDumpUtil.dumpHints(fixture.editor.document.text, fixture.editor) { hint ->
                hint.getEntries().joinToString("") { entry -> (entry as TextInlayPresentationEntry).text }
            }
        }
    }

    @Test
    fun suppressed_mapOfArguments_keyAndValueHintsHidden() {
        // GIVEN - a Map.of folded into a map literal
        configure("""Map<String, Integer> limits = Map.of("cpu", 4);""")

        // WHEN - the k1: and v1: hints of its arguments are checked
        val keyHint = isSuppressedAt("\"cpu\"")
        val valueHint = isSuppressedAt("4)")

        // THEN - both are hidden, the pair index takes their place
        assertEquals(listOf(true, true), listOf(keyHint, valueHint))
    }

    @Test
    fun suppressed_callInsideMapOfValue_ownHintsStay() {
        // GIVEN - a value of Map.of is itself a call with a parameter hint
        configure("""Map<String, Integer> limits = Map.of("cpu", Integer.valueOf(4));""")

        // WHEN - the hint of the inner call's argument is checked
        val innerHint = isSuppressedAt("4)")

        // THEN - it stays, only Map.of's own arguments lose their hints
        assertEquals(false, innerHint)
    }

    @Test
    fun suppressed_listOfArguments_elementHintsHidden() {
        // GIVEN - a nested List.of folded into list literals
        configure("""java.util.List<java.util.List<String>> asks = java.util.List.of(java.util.List.of("12", "14.5"));""")

        // WHEN - the e1: hint of the outer list and the e1: / e2: hints of the inner list are checked
        val outerHint = isSuppressedAt("java.util.List.of(\"12\"")
        val innerHints = listOf(isSuppressedAt("\"12\""), isSuppressedAt("\"14.5\""))

        // THEN - all are hidden
        assertEquals(listOf(true, true, true), listOf(outerHint) + innerHints)
    }

    @Test
    fun suppressed_listOfArray_elementsHintHidden() {
        // GIVEN - List.of(array) spreads the array, folded as [*array]
        configure("""java.util.List<String> names = java.util.List.of(new String[]{"a"});""")

        // WHEN - its elements: hint is checked
        val arrayHint = isSuppressedAt("new String")

        // THEN - it is hidden
        assertEquals(true, arrayHint)
    }

    @Test
    fun suppressed_setOf_elementHintsHidden() {
        // GIVEN - Set.of folded as setOf(...)
        configure("""java.util.Set<String> names = java.util.Set.of("a", "b");""")

        // WHEN - its e1: hint is checked
        val setHint = isSuppressedAt("\"a\"")

        // THEN - it is hidden
        assertEquals(true, setHint)
    }

    @Test
    fun suppressed_otherCall_hintsStay() {
        // GIVEN - a call that is not a collection factory
        configure("""String joined = String.join(",", "a", "b");""")

        // WHEN - its delimiter: hint is checked
        val joinHint = isSuppressedAt("\",\"")

        // THEN - it stays
        assertEquals(false, joinHint)
    }

    @Test
    fun suppressed_optionOff_hintsStay() {
        // GIVEN - map literals are off, so Map.of stays as written
        state.mapAndEmptyCollectionLiterals = false
        configure("""Map<String, Integer> limits = Map.of("cpu", 4);""")

        // WHEN - its k1: hint is checked
        val keyHint = isSuppressedAt("\"cpu\"")

        // THEN - Java's hint stays
        assertEquals(false, keyHint)
    }

    private fun configure(field: String) {
        runInEdtAndWait {
            fixture.configureByText("Limits.java", "import java.util.Map;\n\nclass Limits {\n    $field\n}\n")
        }
    }

    private fun isSuppressedAt(textAtHint: String): Boolean = runReadAction {
        val offset = fixture.file.text.indexOf(textAtHint)
        CollectionLiteralParameterHintsSuppressor().isSuppressedFor(fixture.file, InlayInfo("name", offset))
    }
}
