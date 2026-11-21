package com.intellij.advancedExpressionFolding.processor.lombok

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.semantic.NamedArgumentsCall
import com.intellij.advancedExpressionFolding.expression.semantic.NamedArgumentsCall.NamedArgument
import com.intellij.advancedExpressionFolding.processor.core.BuildExpressionExt
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
        if (!setters.all { it.isFieldSetterOf(builderClass, document) }) {
            return null
        }
        return NamedArgumentsCall(
            element = build,
            openRange = TextRange(builder.dotOffset() ?: return null, builder.textRange.endOffset),
            openText = "(",
            arguments = setters.map { setter ->
                val name = setter.methodExpression.referenceNameElement?.textRange ?: return null
                val value = setter.argumentList.expressions.single().textRange
                NamedArgument(
                    separatorRange = TextRange(setter.dotOffset() ?: return null, name.startOffset),
                    assignRange = TextRange(name.endOffset, value.startOffset),
                    suffixRange = TextRange(value.endOffset, setter.textRange.endOffset),
                )
            },
            closeRange = TextRange(build.dotOffset() ?: return null, build.textRange.endOffset),
        )
    }

    private fun PsiMethodCallExpression.isFieldSetterOf(builderClass: PsiClass, document: Document): Boolean {
        val method = resolveMethod() ?: return false
        val value = argumentList.expressions.singleOrNull() ?: return false
        return method.parameterList.parametersCount == 1 &&
            builderClass.isSame(method.containingClass) &&
            builderClass.isSame(method.returnType.resolveClass()) &&
            builderClass.findFieldByName(method.name, false) != null &&
            // shortcut: a concatenation folded into "…$x" also folds the call's parentheses, so it is left unfolded
            !BuildExpressionExt.getAnyExpression(value, document).isOverflow()
    }

    private fun PsiMethodCallExpression.qualifierCall() = methodExpression.qualifierExpression as? PsiMethodCallExpression

    private fun PsiMethodCallExpression.dotOffset() = methodExpression.children
        .firstOrNull { it is PsiJavaToken && it.tokenType == JavaTokenType.DOT }
        ?.textRange?.startOffset

    private fun PsiType?.resolveClass() = (this as? PsiClassType)?.resolve()

    private fun PsiClass.isSame(other: PsiClass?) = manager.areElementsEquivalent(this, other)
}
