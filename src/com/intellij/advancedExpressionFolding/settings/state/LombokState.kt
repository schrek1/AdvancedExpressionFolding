package com.intellij.advancedExpressionFolding.settings.state

interface ILombokState {
    var lombok: Boolean
    var lombokDirtyOff: Boolean
    var lombokBuilder: Boolean
    var lombokPatternOff: String?
    var interfaceExtensionProperties: Boolean
    var pseudoAnnotations: Boolean
    var lombokNonNull: Boolean
    var lombokSneakyThrows: Boolean
    var lombokDelegate: Boolean
    var lombokUtilityClass: Boolean
    var lombokStandardException: Boolean
}

data class LombokState(
    override var lombok: Boolean = true,
    override var lombokDirtyOff: Boolean = false,
    override var lombokBuilder: Boolean = true,
    override var lombokPatternOff: String? = null,
    override var interfaceExtensionProperties: Boolean = true,
    override var pseudoAnnotations: Boolean = true,
    override var lombokNonNull: Boolean = true,
    override var lombokSneakyThrows: Boolean = true,
    override var lombokDelegate: Boolean = true,
    override var lombokUtilityClass: Boolean = true,
    override var lombokStandardException: Boolean = true,
) : ILombokState
