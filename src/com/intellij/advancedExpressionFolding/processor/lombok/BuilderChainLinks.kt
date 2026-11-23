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
import com.intellij.psi.PsiClassType
import com.intellij.psi.PsiJavaToken
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiType

/** The setter links of a builder chain (`builder()` or `toBuilder()` … `build()`) folded into named arguments. */
internal object BuilderChainLinks {

    /**
     * Named arguments for `links`, or null when the chain must stay unfolded.
     *
     * A link is a one-argument builder method returning the builder. Links named like a builder field show as is;
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
        val prefixed = !links.all { builderClass.findFieldByName(it.name(), false) != null }
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
            builderClass.isSame(method.containingClass) &&
            builderClass.isSame(method.returnType.resolveClass()) &&
            // shortcut: a concatenation folded into "…$x" also folds the call's parentheses, so it is left unfolded
            !BuildExpressionExt.getAnyExpression(value, document).isOverflow()
    }

    private fun PsiMethodCallExpression.isPropertySetterOf(targetClass: PsiClass) =
        isSetter(name()) && targetClass.findFieldByName(guessPropertyName(name()), true) != null
}

internal fun PsiMethodCallExpression.name() = methodExpression.referenceName.orEmpty()

internal fun PsiMethodCallExpression.qualifierCall() = methodExpression.qualifierExpression as? PsiMethodCallExpression

internal fun PsiMethodCallExpression.dotOffset() = methodExpression.children
    .firstOrNull { it is PsiJavaToken && it.tokenType == JavaTokenType.DOT }
    ?.textRange?.startOffset

internal fun PsiType?.resolveClass() = (this as? PsiClassType)?.resolve()

internal fun PsiClass.isSame(other: PsiClass?) = manager.areElementsEquivalent(this, other)
