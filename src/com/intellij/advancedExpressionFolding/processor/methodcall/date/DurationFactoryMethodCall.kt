package com.intellij.advancedExpressionFolding.processor.methodcall.date

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.literal.DurationLiteral
import com.intellij.advancedExpressionFolding.processor.end
import com.intellij.advancedExpressionFolding.processor.methodcall.AbstractMethodCall
import com.intellij.advancedExpressionFolding.processor.methodcall.Context
import com.intellij.advancedExpressionFolding.processor.start
import com.intellij.advancedExpressionFolding.processor.toTextRange
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiMethodCallExpression

class DurationFactoryMethodCall : AbstractMethodCall() {
    override fun canExecute() = durationLiteralCollapse

    override val methodNames by lazy { UNITS.keys.toList() }

    override val classNames by lazy { listOf("java.time.Duration") }

    override fun onSingleArgument(
        element: PsiMethodCallExpression,
        context: Context,
        argument: PsiExpression,
        argumentExpression: Expression
    ): Expression? {
        if (!argument.isNumberLiteral()) return null
        val unit = UNITS[context.methodName] ?: return null
        return DurationLiteral(
            element,
            element.textRange,
            listOf(
                (element.start() to argument.start()).toTextRange() to "",
                (argument.end() to element.end()).toTextRange() to ".$unit",
            ),
        )
    }

    companion object {
        private val UNITS = mapOf(
            "ofNanos" to DurationLiteral.NANOS,
            "ofMillis" to DurationLiteral.MILLIS,
            "ofSeconds" to DurationLiteral.SECONDS,
            "ofMinutes" to DurationLiteral.MINUTES,
            "ofHours" to DurationLiteral.HOURS,
            "ofDays" to DurationLiteral.DAYS,
        )
    }
}

internal fun PsiExpression.isNumberLiteral() = (this as? PsiLiteralExpression)?.value is Number
