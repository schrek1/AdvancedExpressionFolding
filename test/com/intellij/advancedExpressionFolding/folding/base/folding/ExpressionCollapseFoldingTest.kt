package com.intellij.advancedExpressionFolding.folding.base.folding

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

interface ExpressionCollapseFoldingTest : FoldingTestSection {
    @Test
    fun elvisTestData() = testCase.runFoldingTest(foldingState()::checkExpressionsCollapse)

    @Test
    fun forRangeTestData() = testCase.runFoldingTest(foldingState()::rangeExpressionsCollapse)

    @Test
    fun kotlinRangeLoopsTestData() = testCase.runFoldingTest(
        foldingState()::rangeExpressionsCollapse,
        foldingState()::kotlinRangeLoops,
    )

    @Test
    fun reverseSteppedLoopsTestData() = testCase.runFoldingTest(
        foldingState()::reverseSteppedLoopsCollapse,
        foldingState()::kotlinRangeLoops,
    )

    @Test
    fun reverseSteppedLoopsIntervalTestData() = testCase.runFoldingTest(foldingState()::reverseSteppedLoopsCollapse)

    @Test
    fun stringBuilderTestData() = testCase.runFoldingTest(foldingState()::concatenationExpressionsCollapse)

    @Test
    fun interpolatedStringTestData() = testCase.runFoldingTest(foldingState()::concatenationExpressionsCollapse)

    @Test
    fun getSetPutTestData() = testCase.runFoldingTest(foldingState()::getExpressionsCollapse)

    @Test
    fun getOrDefaultElvisTestData() = testCase.runFoldingTest(foldingState()::getOrDefaultElvis)

    @Test
    fun mapAndEmptyCollectionLiteralsTestData() =
        testCase.runFoldingTest(foldingState()::mapAndEmptyCollectionLiterals)

    @Test
    fun sliceTestData() = testCase.runFoldingTest(foldingState()::slicingExpressionsCollapse)

    @Test
    fun appendSetterInterpolatedStringTestData() = testCase.runFoldingTest(
        foldingState()::concatenationExpressionsCollapse,
        foldingState()::getSetExpressionsCollapse,
    )

    @Test
    fun equalsCompareTestData() = testCase.runFoldingTest(foldingState()::comparingExpressionsCollapse)

    @Test
    fun typeCastTestData() = testCase.runFoldingTest(foldingState()::castExpressionsCollapse)

    @Test
    fun varTestData() = testCase.runFoldingTest(foldingState()::varExpressionsCollapse)

    @Test
    fun getterSetterTestData() = testCase.runFoldingTest(foldingState()::getSetExpressionsCollapse)

    @Test
    fun fieldShiftBuilder() = testCase.runFoldingTest(
        foldingState()::fieldShift,
        foldingState()::getSetExpressionsCollapse,
    )

    @Test
    fun fieldShiftSetters() = testCase.runFoldingTest(
        foldingState()::fieldShift,
        foldingState()::getSetExpressionsCollapse,
    )

    @Test
    fun fieldShiftFields() = testCase.runFoldingTest(
        foldingState()::getSetExpressionsCollapse,
        foldingState()::fieldShift,
    )

    @Test
    fun toBuilderCopyTestData() = testCase.runFoldingTest(foldingState()::toBuilderCopy)

    @Test
    fun builderNamedArgumentsTestData() = testCase.runFoldingTest(foldingState()::builderNamedArguments)
}

@Disabled("Split from FoldingTest")
open class ExpressionCollapseFoldingTestCase : FoldingFeatureTestCase(), ExpressionCollapseFoldingTest
