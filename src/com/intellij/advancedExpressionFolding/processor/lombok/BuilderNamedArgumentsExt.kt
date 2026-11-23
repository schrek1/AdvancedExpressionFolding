package com.intellij.advancedExpressionFolding.processor.lombok

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.semantic.NamedArgumentsCall
import com.intellij.advancedExpressionFolding.expression.semantic.NamedArgumentsCall.NamedArgument
import com.intellij.advancedExpressionFolding.processor.core.BuildExpressionExt
import com.intellij.advancedExpressionFolding.processor.declaration.SetterNamedArgumentsExt.propertyNameFold
import com.intellij.advancedExpressionFolding.processor.util.Helper.isSetter
import com.intellij.advancedExpressionFolding.processor.util.PropertyUtil.guessPropertyName
import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.advancedExpressionFolding.settings.state.IExpressionCollapseState
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.JavaTokenType
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiClassType
import com.intellij.psi.PsiJavaToken
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiModifier
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.PsiType

/**
 * Folds `Person.builder().name("Alice").age(31).build()` into `Person(name = "Alice", age = 31)`.
 *
 * Only a clean chain folds: a static `builder()` called on its class, then one-argument calls of builder methods
 * that set a builder field of the same name and return the builder, then `build()` returning that class.
 * A chain of `setName(…)` calls folds too when the built class has every property; only `set` + the first letter hide.
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
        if (!setters.all { it.isBuilderLinkOf(builderClass, document) }) {
            return null
        }
        // `setName(…)` builders (Lombok `setterPrefix = "set"`, hand-written ones) fold only when every call has the prefix
        val prefixed = !setters.all { builderClass.findFieldByName(it.name(), false) != null }
        if (prefixed && !setters.all { it.isPropertySetterOf(targetClass) }) {
            return null
        }
        return NamedArgumentsCall(
            element = build,
            openRange = TextRange(builder.dotOffset() ?: return null, builder.textRange.endOffset),
            openText = "(",
            arguments = setters.map { setter ->
                val name = setter.methodExpression.referenceNameElement?.textRange ?: return null
                val value = setter.argumentList.expressions.single().textRange
                val (nameStart, nameText) = if (prefixed) {
                    propertyNameFold(setter.name(), guessPropertyName(setter.name()), name)
                } else {
                    name.startOffset to ""
                }
                NamedArgument(
                    separatorRange = TextRange(setter.dotOffset() ?: return null, nameStart),
                    assignRange = TextRange(name.endOffset, value.startOffset),
                    suffixRange = TextRange(value.endOffset, setter.textRange.endOffset),
                    nameText = nameText,
                )
            },
            closeRange = TextRange(build.dotOffset() ?: return null, build.textRange.endOffset),
        )
    }

    /** Whether `call` is a `setName(…)` link of a chain folded here, so it must not get its own setter folding. */
    fun isInFoldedChain(call: PsiMethodCallExpression, document: Document): Boolean {
        if (!builderNamedArguments || !isSetter(call.name())) {
            return false
        }
        val build = generateSequence(call) { (it.parent as? PsiReferenceExpression)?.parent as? PsiMethodCallExpression }
            .drop(1)
            .firstOrNull { it.name() == "build" } ?: return false
        return createExpression(build, document) != null
    }

    private fun PsiMethodCallExpression.isBuilderLinkOf(builderClass: PsiClass, document: Document): Boolean {
        val method = resolveMethod() ?: return false
        val value = argumentList.expressions.singleOrNull() ?: return false
        return method.parameterList.parametersCount == 1 &&
            builderClass.isSame(method.containingClass) &&
            builderClass.isSame(method.returnType.resolveClass()) &&
            // shortcut: a concatenation folded into "…$x" also folds the call's parentheses, so it is left unfolded
            !BuildExpressionExt.getAnyExpression(value, document).isOverflow()
    }

    private fun PsiMethodCallExpression.isPropertySetterOf(targetClass: PsiClass) =
        isSetter(name()) && targetClass.findFieldByName(guessPropertyName(name()), true) != null

    private fun PsiMethodCallExpression.name() =methodExpression.referenceName.orEmpty()

    private fun PsiMethodCallExpression.qualifierCall() = methodExpression.qualifierExpression as? PsiMethodCallExpression

    private fun PsiMethodCallExpression.dotOffset() = methodExpression.children
        .firstOrNull { it is PsiJavaToken && it.tokenType == JavaTokenType.DOT }
        ?.textRange?.startOffset

    private fun PsiType?.resolveClass() = (this as? PsiClassType)?.resolve()

    private fun PsiClass.isSame(other: PsiClass?) = manager.areElementsEquivalent(this, other)
}
