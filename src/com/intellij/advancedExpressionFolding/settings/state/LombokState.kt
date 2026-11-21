package com.intellij.advancedExpressionFolding.settings.state

interface ILombokState {
    var lombok: Boolean
    var lombokDirtyOff: Boolean
    var lombokPatternOff: String?
    var interfaceExtensionProperties: Boolean
    var pseudoAnnotations: Boolean
    var lombokUtilityClass: Boolean
    var lombokStandardException: Boolean
}

data class LombokState(
    override var lombok: Boolean = true,
    override var lombokDirtyOff: Boolean = false,
    override var lombokPatternOff: String? = null,
    override var interfaceExtensionProperties: Boolean = true,
    override var pseudoAnnotations: Boolean = true,
    override var lombokUtilityClass: Boolean = true,
    override var lombokStandardException: Boolean = true,
) : ILombokState
