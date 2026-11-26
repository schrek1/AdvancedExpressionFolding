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

    @Test
    fun conditionalAsIfElseTestData() = testCase.runFoldingTest(
        foldingState()::checkExpressionsCollapse,
        foldingState()::conditionalAsIfElse,
    )

    @Test
    fun conditionalAsIfElseSetterTestData() = testCase.runFoldingTest(
        foldingState()::checkExpressionsCollapse,
        foldingState()::getSetExpressionsCollapse,
        foldingState()::conditionalAsIfElse,
    )

    @Test
    fun conditionalAsIfElseAlwaysTestData() =testCase.runFoldingTest(
        foldingState()::checkExpressionsCollapse,
        foldingState()::conditionalAsIfElseAlways,
    )

    @Test
    fun conditionalAsIfElseNeverTestData() = testCase.runFoldingTest(foldingState()::checkExpressionsCollapse)
}

@Disabled("Split from FoldingTest")
open class ControlFlowFoldingTestCase : FoldingFeatureTestCase(), ControlFlowFoldingTest
