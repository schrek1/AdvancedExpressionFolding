package com.intellij.advancedExpressionFolding.folding.base.folding

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

interface UnclassifiedFeatureFoldingTest : FoldingTestSection {
    @Test
    fun semicolonTestData() = testCase.runReadOnlyFoldingTest(foldingState()::semicolonsCollapse)

    @Test
    fun catchRethrowTestData() = testCase.runFoldingTest(foldingState()::catchRethrow)
}

@Disabled("Split from FoldingTest")
open class UnclassifiedFeatureFoldingTestCase : FoldingFeatureTestCase(), UnclassifiedFeatureFoldingTest
