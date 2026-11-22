package com.intellij.advancedExpressionFolding.folding.base.folding

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

interface LogFoldingTest : FoldingTestSection {
    @Test
    fun logBrackets() = testCase.runFoldingTest(
        foldingState()::getSetExpressionsCollapse,
        foldingState()::logFolding,
    )

    @Test
    fun logFoldingTextBlocksTestData() = testCase.runFoldingTest(
        foldingState()::getSetExpressionsCollapse,
        foldingState()::logFolding,
        foldingState()::logFoldingTextBlocks,
    )

    @Test
    fun logFoldingKeepMessageTextTestData() = testCase.runFoldingTest(
        foldingState()::getSetExpressionsCollapse,
        foldingState()::logFolding,
        foldingState()::logFoldingKeepMessageText,
    )
}

@Disabled("Split from FoldingTest")
open class LogFoldingTestCase : FoldingFeatureTestCase(), LogFoldingTest
