package com.intellij.advancedExpressionFolding.settings.state

interface ICollectionsStreamsState {
    var optional: Boolean
    var streamSpread: Boolean
    var streamPredicates: Boolean
}

data class CollectionsStreamsState(
    override var optional: Boolean = true,
    override var streamSpread: Boolean = true,
    override var streamPredicates: Boolean = true,
) : ICollectionsStreamsState
