package com.intellij.advancedExpressionFolding.processor.lombok

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.semantic.NamedArgumentsCall
import com.intellij.advancedExpressionFolding.processor.util.Helper.isSetter
import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.advancedExpressionFolding.settings.state.IExpressionCollapseState
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiModifier
import com.intellij.psi.PsiReferenceExpression

/**
 * Folds `Person.builder().name("Alice").age(31).build()` into `Person(name = "Alice", age = 31)`.
 *
 * Only a clean chain folds: a static `builder()` called on its class, then the setter links [BuilderChainLinks]
 * accepts, then `build()` returning that class.
 */
object BuilderNamedArgumentsExt : IExpressionCollapseState by State()() {

    fun createExpression(build: PsiMethodCallExpression, document: Document): Expression? {
        if (!builderNamedArguments || build.methodExpression.referenceName != "build" || !build.argumentList.isEmpty) {
            return null
        }
        val setters = generateSequence(build.qualifierCall()) { it.qualifierCall() }
            .takeWhile { it.methodExpression.referenceName != "builder" }
            .toList()
            .asReversed()
        val builder = (setters.firstOrNull() ?: return null).qualifierCall() ?: return null
        if (builder.methodExpression.referenceName != "builder" || !builder.argumentList.isEmpty) {
            return null
        }
        val builderMethod = builder.resolveMethod() ?: return null
        val targetClass = builderMethod.containingClass ?: return null
        val qualifierClass = (builder.methodExpression.qualifierExpression as? PsiReferenceExpression)?.resolve()
        if (!builderMethod.hasModifierProperty(PsiModifier.STATIC) || !targetClass.isSame(qualifierClass as? PsiClass)) {
            return null
        }
        val builderClass = builderMethod.returnType.resolveClass() ?: return null
        val buildMethod = build.resolveMethod() ?: return null
        if (!builderClass.isSame(buildMethod.containingClass) || !targetClass.isSame(buildMethod.returnType.resolveClass())) {
            return null
        }
        val arguments = BuilderChainLinks.namedArguments(setters, builderClass, targetClass, document) ?: return null
        return NamedArgumentsCall(
            element = build,
            openRange = TextRange(builder.dotOffset() ?: return null, builder.textRange.endOffset),
            openText = "(",
            arguments = arguments,
            closeRange = TextRange(build.dotOffset() ?: return null, build.textRange.endOffset),
        )
    }

    /**
     * Whether `call` is a `setName(…)` link of a chain folded here or by [ToBuilderCopyExt], so it must not get its
     * own setter folding.
     */
    fun isInFoldedChain(call: PsiMethodCallExpression, document: Document): Boolean {
        if (!(builderNamedArguments || toBuilderCopy) || !isSetter(call.name())) {
            return false
        }
        val build = generateSequence(call) { (it.parent as? PsiReferenceExpression)?.parent as? PsiMethodCallExpression }
            .drop(1)
            .firstOrNull { it.name() == "build" } ?: return false
        return createExpression(build, document) != null || ToBuilderCopyExt.createExpression(build, document) != null
    }
}
