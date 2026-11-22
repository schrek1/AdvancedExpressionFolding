package com.intellij.advancedExpressionFolding.settings.state

interface ILogFoldingState {
    var logFolding: Boolean
    var logFoldingTextBlocks: Boolean
    var logFoldingKeepMessageText: Boolean
}

data class LogFoldingState(
    override var logFolding: Boolean = true,
    override var logFoldingTextBlocks: Boolean = true,
    override var logFoldingKeepMessageText: Boolean = false,
) : ILogFoldingState
