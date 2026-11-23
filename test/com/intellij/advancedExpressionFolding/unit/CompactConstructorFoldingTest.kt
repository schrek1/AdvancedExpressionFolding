package com.intellij.advancedExpressionFolding.unit

import com.intellij.advancedExpressionFolding.AdvancedExpressionFoldingBuilder
import com.intellij.advancedExpressionFolding.folding.BaseTest
import com.intellij.advancedExpressionFolding.folding.util.FoldingDataStorage
import com.intellij.advancedExpressionFolding.settings.AdvancedExpressionFoldingSettings.Companion.getInstance
import com.intellij.advancedExpressionFolding.store
import com.intellij.openapi.application.ReadAction
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CompactConstructorFoldingTest : BaseTest() {

    @Test
    fun buildFoldRegions_recordWithCompactConstructor_keepsTheConstructorBodyFolds() {
        val settings = getInstance()
        val originalState = settings.state.copy()
        val originalStore = store
        try {
            // GIVEN - a record whose compact constructor has implicit parameters (light PSI without source text)
            settings.disableAll()
            settings.state.apply {
                globalOn = true
                optional = true
            }
            rebuildMethodCallFactory()
            store = FoldingDataStorage()
            fixture.configureByText(
                "Shape.java",
                """
                    import java.util.Optional;

                    record Shape(String name, int sides) {
                        Shape {
                            name = Optional.ofNullable(name).orElse("unnamed");
                        }
                    }
                """.trimIndent()
            )
            val file = fixture.file
            val document = fixture.getDocument(file)

            // WHEN - the folding pass builds the file's folds
            val placeholders = ReadAction.compute<List<String>, RuntimeException> {
                AdvancedExpressionFoldingBuilder().buildFoldRegions(file, document, false)
                    .map { descriptor -> descriptor.placeholderText.orEmpty() }
            }

            // THEN - the pass completes and the constructor body keeps its elvis fold
            assertTrue(" ?: " in placeholders, "placeholders: $placeholders")
        } finally {
            settings.loadState(originalState)
            store = originalStore
        }
    }
}
