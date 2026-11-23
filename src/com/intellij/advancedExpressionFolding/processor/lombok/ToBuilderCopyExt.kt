package com.intellij.advancedExpressionFolding.processor.lombok

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.semantic.NamedArgumentsCall
import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.advancedExpressionFolding.settings.state.IExpressionCollapseState
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiMethodCallExpression

/**
 * Folds `person.toBuilder().name("Alice").age(31).build()` into `person.copy(name = "Alice", age = 31)`.
 *
 * Only a clean chain folds: `toBuilder()`, then the setter links [BuilderChainLinks] accepts, then `build()`
 * returning the receiver's class.
 */
object ToBuilderCopyExt : IExpressionCollapseState by State()() {

    fun createExpression(build: PsiMethodCallExpression, document: Document): Expression? {
        if (!toBuilderCopy || build.methodExpression.referenceName != "build" || !build.argumentList.isEmpty) {
            return null
        }
        val setters = generateSequence(build.qualifierCall()) { it.qualifierCall() }
            .takeWhile { it.methodExpression.referenceName != "toBuilder" }
            .toList()
            .asReversed()
        val toBuilder = (setters.firstOrNull() ?: return null).qualifierCall() ?: return null
        if (toBuilder.methodExpression.referenceName != "toBuilder" || !toBuilder.argumentList.isEmpty) {
            return null
        }
        val builderClass = toBuilder.resolveMethod()?.returnType.resolveClass() ?: return null
        val targetClass = toBuilder.resolveMethod()?.containingClass ?: return null
        val buildMethod = build.resolveMethod() ?: return null
        if (!builderClass.isSame(buildMethod.containingClass) || !targetClass.isSame(buildMethod.returnType.resolveClass())) {
            return null
        }
        val arguments = BuilderChainLinks.namedArguments(setters, builderClass, targetClass, document) ?: return null
        toBuilder.dotOffset() ?: return null
        val toBuilderName = toBuilder.methodExpression.referenceNameElement?.textRange ?: return null
        return NamedArgumentsCall(
            element = build,
            openRange = TextRange(toBuilderName.startOffset, toBuilder.textRange.endOffset),
            openText = "copy(",
            arguments = arguments,
            closeRange = TextRange(build.dotOffset() ?: return null, build.textRange.endOffset),
        )
    }
}
