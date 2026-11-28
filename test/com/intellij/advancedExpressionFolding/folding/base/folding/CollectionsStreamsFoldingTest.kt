package com.intellij.advancedExpressionFolding.folding.base.folding

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

interface CollectionsStreamsFoldingTest : FoldingTestSection {
    @Test
    fun concatenationTestData() = testCase.runFoldingTest(
        foldingState()::concatenationExpressionsCollapse,
        foldingState()::optional,
        foldingState()::streamSpread,
    )

    @Test
    fun templateInExpressionFunctionTestData() = testCase.runFoldingTest(
        foldingState()::concatenationExpressionsCollapse,
        foldingState()::expressionFunc,
        foldingState()::checkExpressionsCollapse,
        foldingState()::conditionalAsIfElse,
    )

    @Test
    fun optionalTestData() = testCase.runFoldingTest(
        foldingState()::concatenationExpressionsCollapse,
        foldingState()::optional,
        foldingState()::streamSpread,
    )

    @Test
    fun optionalOrElseThrowTestData() = testCase.runFoldingTest(
        foldingState()::concatenationExpressionsCollapse,
        foldingState()::optional,
    )

    @Test
    fun spreadTestData() = testCase.runFoldingTest(
        foldingState()::concatenationExpressionsCollapse,
        foldingState()::optional,
        foldingState()::streamSpread,
    )

    @Test
    fun streamPredicateTestData() = testCase.runFoldingTest(
        foldingState()::concatenationExpressionsCollapse,
        foldingState()::getSetExpressionsCollapse,
        foldingState()::streamSpread,
        foldingState()::streamPredicates,
    )

    @Test
    fun streamSpreadChainTestData() = testCase.runFoldingTest(
        foldingState()::getSetExpressionsCollapse,
        foldingState()::streamSpreadChain,
    )
}

@Disabled("Split from FoldingTest")
open class CollectionsStreamsFoldingTestCase : FoldingFeatureTestCase(), CollectionsStreamsFoldingTest
