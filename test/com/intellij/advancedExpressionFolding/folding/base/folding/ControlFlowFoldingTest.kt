package com.intellij.advancedExpressionFolding.folding.base.folding

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

interface ControlFlowFoldingTest : FoldingTestSection {
    @Test
    fun controlFlowSingleStatementTestData() = testCase.runReadOnlyFoldingTest(
        foldingState()::controlFlowSingleStatementCodeBlockCollapse,
    )

    @Test
    fun controlFlowMultiStatementTestData() = testCase.runReadOnlyFoldingTest(
        foldingState()::controlFlowMultiStatementCodeBlockCollapse,
    )

    @Test
    fun guardIfOneLinerTestData() = testCase.runFoldingTest(foldingState()::guardIfOneLiner)

    @Test
    fun compactControlFlowTestData() = testCase.runFoldingTest(foldingState()::compactControlFlowSyntaxCollapse)

    @Test
    fun assertTestData() = testCase.runReadOnlyFoldingTest(foldingState()::assertsCollapse)

    @Test
    fun switchArrowTestData() = testCase.runFoldingTest(foldingState()::switchArrow)
}

@Disabled("Split from FoldingTest")
open class ControlFlowFoldingTestCase : FoldingFeatureTestCase(), ControlFlowFoldingTest
