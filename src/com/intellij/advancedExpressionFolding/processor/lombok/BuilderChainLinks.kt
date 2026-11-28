package com.intellij.advancedExpressionFolding.processor.lombok

import com.intellij.advancedExpressionFolding.expression.semantic.NamedArgumentsCall.NamedArgument
import com.intellij.advancedExpressionFolding.processor.core.BuildExpressionExt
import com.intellij.advancedExpressionFolding.processor.declaration.SetterNamedArgumentsExt.propertyNameFold
import com.intellij.advancedExpressionFolding.processor.util.Helper.isSetter
import com.intellij.advancedExpressionFolding.processor.util.PropertyUtil.guessPropertyName
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.JavaTokenType
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiCapturedWildcardType
import com.intellij.psi.PsiClassType
import com.intellij.psi.PsiJavaToken
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiType
import com.intellij.psi.PsiTypeParameter

/** The setter links of a builder chain (`builder()` or `toBuilder()` … `build()`) folded into named arguments. */
internal object BuilderChainLinks {

    /**
     * Named arguments for `links`, or null when the chain must stay unfolded.
     *
     * A link is a one-argument method of the builder or of a superclass of it whose call evaluates to the builder, so
     * a self-typed `B setName(…)` of a generic base builder counts. Links named like a builder field show as is;
     * `setName(…)` links (Lombok `setterPrefix = "set"`, hand-written ones) fold only when every link has the prefix
     * and `targetClass` has each property, and then only `set` + the first letter hide.
     */
    fun namedArguments(
        links: List<PsiMethodCallExpression>,
        builderClass: PsiClass,
        targetClass: PsiClass,
        document: Document,
    ): List<NamedArgument>? {
        if (!links.all { it.isBuilderLinkOf(builderClass, document) }) {
            return null
        }
        val prefixed = !links.all { builderClass.findFieldByName(it.name(), true) != null }
        if (prefixed && !links.all { it.isPropertySetterOf(targetClass) }) {
            return null
        }
        return links.map { link ->
            val name = link.methodExpression.referenceNameElement?.textRange ?: return null
            val value = link.argumentList.expressions.single().textRange
            val (nameStart, nameText) = if (prefixed) {
                propertyNameFold(link.name(), guessPropertyName(link.name()), name)
            } else {
                name.startOffset to ""
            }
            NamedArgument(
                separatorRange = TextRange(link.dotOffset() ?: return null, nameStart),
                assignRange = TextRange(name.endOffset, value.startOffset),
                suffixRange = TextRange(value.endOffset, link.textRange.endOffset),
                nameText = nameText,
            )
        }
    }

    private fun PsiMethodCallExpression.isBuilderLinkOf(builderClass: PsiClass, document: Document): Boolean {
        val method = resolveMethod() ?: return false
        val value = argumentList.expressions.singleOrNull() ?: return false
        return method.parameterList.parametersCount == 1 &&
            builderClass.hasMember(method) &&
            builderClass.isSame(type.resolveClass()) &&
            // shortcut: a concatenation folded into "…$x" also folds the call's parentheses, so it is left unfolded
            !BuildExpressionExt.getAnyExpression(value, document).isOverflow()
    }

    /** Whether `build` is a method of `builderClass` (or a superclass of it) whose call evaluates to `targetClass`. */
    fun isBuildOf(build: PsiMethodCallExpression, builderClass: PsiClass, targetClass: PsiClass): Boolean {
        val method = build.resolveMethod() ?: return false
        return builderClass.hasMember(method) && targetClass.isSame(build.type.resolveClass())
    }

    private fun PsiClass.hasMember(method: PsiMethod): Boolean {
        val owner = method.containingClass ?: return false
        return isSame(owner) || isInheritor(owner, true)
    }

    private fun PsiMethodCallExpression.isPropertySetterOf(targetClass: PsiClass) =
        isSetter(name()) && targetClass.findFieldByName(guessPropertyName(name()), true) != null
}

internal fun PsiMethodCallExpression.name() = methodExpression.referenceName.orEmpty()

internal fun PsiMethodCallExpression.qualifierCall() = methodExpression.qualifierExpression as? PsiMethodCallExpression

internal fun PsiMethodCallExpression.dotOffset() = methodExpression.children
    .firstOrNull { it is PsiJavaToken && it.tokenType == JavaTokenType.DOT }
    ?.textRange?.startOffset

/** The class of a type; a captured wildcard or type parameter (Lombok `@SuperBuilder`'s `B`, `C`) resolves to its bound. */
internal fun PsiType?.resolveClass(): PsiClass? = when (this) {
    is PsiCapturedWildcardType -> upperBound.resolveClass()
    is PsiClassType -> when (val resolved = resolve()) {
        is PsiTypeParameter -> resolved.extendsListTypes.firstOrNull().resolveClass()
        else -> resolved
    }
    else -> null
}

internal fun PsiClass.isSame(other: PsiClass?) = manager.areElementsEquivalent(this, other)
