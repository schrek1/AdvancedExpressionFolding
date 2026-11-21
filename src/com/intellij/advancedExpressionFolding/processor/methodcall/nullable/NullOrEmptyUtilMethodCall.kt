package com.intellij.advancedExpressionFolding.processor.methodcall.nullable

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.semantic.SimpleExpression
import com.intellij.advancedExpressionFolding.expression.semantic.WrapperExpression
import com.intellij.advancedExpressionFolding.processor.end
import com.intellij.advancedExpressionFolding.processor.group
import com.intellij.advancedExpressionFolding.processor.methodcall.AbstractMethodCall
import com.intellij.advancedExpressionFolding.processor.methodcall.Context
import com.intellij.advancedExpressionFolding.processor.start
import com.intellij.openapi.util.TextRange
import com.intellij.psi.JavaTokenType
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiPrefixExpression
import com.intellij.psi.PsiReferenceExpression

/**
 * Folds the null-safe `isEmpty(x)` / `isNotEmpty(x)` / `!isEmpty(x)` of Apache Commons and Spring utility classes into
 * `x.isNullOrEmpty()` / `x.isNotEmpty()`; `isBlank` stays, it also treats whitespace as empty.
 */
class NullOrEmptyUtilMethodCall : AbstractMethodCall() {

    override fun canExecute() = nullOrEmpty

    override val methodNames by lazy { listOf("isEmpty", "isNotEmpty") }

    override val classNames by lazy {
        listOf(
            "org.apache.commons.lang3.StringUtils",
            "org.apache.commons.lang.StringUtils",
            "org.apache.commons.collections4.CollectionUtils",
            "org.apache.commons.collections.CollectionUtils",
            "org.apache.commons.collections4.MapUtils",
            "org.apache.commons.collections.MapUtils",
            "org.springframework.util.CollectionUtils",
            "org.springframework.util.ObjectUtils",
        )
    }

    override fun onSingleArgument(
        element: PsiMethodCallExpression,
        context: Context,
        argument: PsiExpression,
        argumentExpression: Expression
    ): Expression? {
        // the argument becomes the receiver of `.isNullOrEmpty()`, so it must bind tighter than a postfix call
        if (argument !is PsiReferenceExpression && argument !is PsiMethodCallExpression) {
            return null
        }
        val group = NullOrEmptyUtilMethodCall::class.group()
        val negation = (element.parent as? PsiPrefixExpression)
            ?.takeIf { it.operationTokenType == JavaTokenType.EXCL && context.methodName == "isEmpty" }
        val start = negation ?: element
        val notEmpty = negation != null || context.methodName == "isNotEmpty"
        val folds = listOf(
            SimpleExpression(start, TextRange.create(start.start(), argument.start()), "", group),
            SimpleExpression(
                element,
                TextRange.create(argument.end(), element.end()),
                if (notEmpty) ".isNotEmpty()" else ".isNullOrEmpty()",
                group
            ),
        )
        // spanning the `!` lets the negation hand its node to this expression (collapsed by default); the traversal then
        // stops at this already-seen call, so the argument's own folds come through the chain
        return WrapperExpression(element, start.textRange, if (negation != null) folds + argumentExpression else folds)
    }
}
