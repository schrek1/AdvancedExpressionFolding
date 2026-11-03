package com.intellij.advancedExpressionFolding

import com.intellij.advancedExpressionFolding.extension.clazz.SummaryParentOverrideInlayProvider
import com.intellij.testFramework.utils.inlays.declarative.DeclarativeInlayHintsProviderTestCase

class SummaryParentOverrideInlayTest : DeclarativeInlayHintsProviderTestCase() {

    private val state get() = AdvancedExpressionFoldingSettings.getInstance().state
    private var summaryParentOverrideBeforeTest = false

    override fun setUp() {
        super.setUp()
        summaryParentOverrideBeforeTest = state.summaryParentOverride
        state.summaryParentOverride = true
    }

    override fun tearDown() {
        try {
            state.summaryParentOverride = summaryParentOverrideBeforeTest
        } finally {
            super.tearDown()
        }
    }

    fun testInlay_classWithParents_summaryFollowsEachParentName() {
        doTestProvider(
            "Circle.java",
            """
            interface Shape {
                double area();
                int corners();
            }

            interface Named {
            }

            class Base {
                void draw() {}
            }

            class Circle extends Base/*<# (1-draw) #>*/ implements Shape/*<# (2-area, corners) #>*/, Named/*<# (nothing overridden) #>*/ {
                void draw() {}
                public double area() { return 0; }
                public int corners() { return 0; }
            }
            """.trimIndent(),
            SummaryParentOverrideInlayProvider()
        )
    }

    fun testInlay_optionOff_noSummary() {
        state.summaryParentOverride = false

        doTestProvider(
            "Circle.java",
            """
            interface Named {
            }

            class Circle implements Named {
            }
            """.trimIndent(),
            SummaryParentOverrideInlayProvider()
        )
    }
}
