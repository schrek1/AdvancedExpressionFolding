package com.intellij.advancedExpressionFolding.settings.state

interface IKotlinLanguageState {
    var const: Boolean
    var ifNullSafe: Boolean
    var nullOrEmpty: Boolean
    var kotlinQuickReturn: Boolean
    var nullable: Boolean
    var println: Boolean
    var todo: Boolean
    var destructuring: Boolean
    var mapEntryDestructuring: Boolean
    var objectDestructuring: Boolean
    var expressionFunc: Boolean
    var expressionFuncEquals: Boolean
    var methodDefaultParameters: Boolean
    var patternMatchingInstanceof: Boolean
    var instanceofSwitch: Boolean
    var constructorReferenceNotation: Boolean
    var arithmeticExpressions: Boolean
    var kotlinPreconditions: Boolean
    var inOperator: Boolean
    var singletonObject: Boolean
    var joinToString: Boolean
    var repeatLoop: Boolean
    var lazyProperty: Boolean
}

data class KotlinLanguageState(
    override var const: Boolean = true,
    override var ifNullSafe: Boolean = true,
    override var nullOrEmpty: Boolean = true,
    override var kotlinQuickReturn: Boolean = true,
    override var nullable: Boolean = false,
    override var println: Boolean = true,
    override var todo: Boolean = true,
    override var destructuring: Boolean = false,
    override var mapEntryDestructuring: Boolean = true,
    override var objectDestructuring: Boolean = true,
    override var expressionFunc: Boolean = true,
    override var expressionFuncEquals: Boolean = false,
    override var methodDefaultParameters: Boolean = true,
    override var patternMatchingInstanceof: Boolean = true,
    override var instanceofSwitch: Boolean = true,
    override var constructorReferenceNotation: Boolean = true,
    override var arithmeticExpressions: Boolean = false,
    override var kotlinPreconditions: Boolean = false,
    override var inOperator: Boolean = true,
    override var singletonObject: Boolean = true,
    override var joinToString: Boolean = true,
    override var repeatLoop: Boolean = true,
    override var lazyProperty: Boolean = true,
) : IKotlinLanguageState
