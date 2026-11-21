package com.intellij.advancedExpressionFolding.processor.methodcall.basic

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.semantic.kotlin.JoinToStringExpression
import com.intellij.advancedExpressionFolding.processor.end
import com.intellij.advancedExpressionFolding.processor.methodcall.AbstractMethodCall
import com.intellij.advancedExpressionFolding.processor.methodcall.Context
import com.intellij.advancedExpressionFolding.processor.start
import com.intellij.openapi.util.TextRange
import com.intellij.psi.CommonClassNames
import com.intellij.psi.PsiArrayAccessExpression
import com.intellij.psi.PsiArrayType
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiParenthesizedExpression
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.util.InheritanceUtil

/** `String.join(sep, items)` → `items.joinToString(sep)`. */
class StringJoinMethodCall : AbstractMethodCall() {
    override fun canExecute() = joinToString

    override val methodNames by lazy { listOf("join") }

    override val classNames by lazy { listOf("java.lang.String") }

    override fun onTwoArguments(
        element: PsiMethodCallExpression,
        context: Context,
        a1: PsiExpression,
        a2: PsiExpression,
        a1Expression: Expression,
        a2Expression: Expression
    ): Expression? {
        val separator = a1.text
        if (separator.contains('\n') || !a2.isJoinable() || !a2.isReceiverSafe()) {
            return null
        }
        val joinToStringSuffix = JoinToStringExpression(
            element,
            TextRange(a2.end(), element.end()),
            ".joinToString($separator)",
            a2Expression
        )
        return JoinToStringExpression(element, TextRange(element.start(), a2.start()), "", joinToStringSuffix)
    }

    // A single String argument is the varargs overload with one element, not a collection.
    private fun PsiExpression.isJoinable(): Boolean {
        val type = type ?: return false
        return type is PsiArrayType || InheritanceUtil.isInheritor(type, CommonClassNames.JAVA_LANG_ITERABLE)
    }

    // `.joinToString` is appended after the argument, so it must bind tighter than any operator inside it.
    private fun PsiExpression.isReceiverSafe(): Boolean =
        this is PsiReferenceExpression ||
            this is PsiMethodCallExpression ||
            this is PsiArrayAccessExpression ||
            this is PsiParenthesizedExpression
}
