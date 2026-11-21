package com.intellij.advancedExpressionFolding.settings.state

interface IUnclassifiedFeatureState {
    var semicolonsCollapse: Boolean
    var catchRethrow: Boolean
    // NEW OPTION VAR
}

data class UnclassifiedFeatureState(
    override var semicolonsCollapse: Boolean = false,
    override var catchRethrow: Boolean = true,
    // NEW OPTION VAR
) : IUnclassifiedFeatureState
