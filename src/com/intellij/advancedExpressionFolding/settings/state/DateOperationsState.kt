package com.intellij.advancedExpressionFolding.settings.state

interface IDateOperationsState {
    var comparingLocalDatesCollapse: Boolean
    var localDateLiteralCollapse: Boolean
    var localDateLiteralPostfixCollapse: Boolean
    var durationLiteralCollapse: Boolean
}

data class DateOperationsState(
    override var comparingLocalDatesCollapse: Boolean = true,
    override var localDateLiteralCollapse: Boolean = false,
    override var localDateLiteralPostfixCollapse: Boolean = false,
    override var durationLiteralCollapse: Boolean = true,
) : IDateOperationsState
