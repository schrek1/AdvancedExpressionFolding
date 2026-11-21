package com.intellij.advancedExpressionFolding.processor.methodcall.date

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.literal.DurationLiteral
import com.intellij.advancedExpressionFolding.processor.end
import com.intellij.advancedExpressionFolding.processor.methodcall.AbstractMethodCall
import com.intellij.advancedExpressionFolding.processor.methodcall.Context
import com.intellij.advancedExpressionFolding.processor.start
import com.intellij.advancedExpressionFolding.processor.toTextRange
import com.intellij.psi.PsiEnumConstant
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiReferenceExpression

class TimeUnitSleepMethodCall : AbstractMethodCall() {
    override fun canExecute() = durationLiteralCollapse

    override val methodNames by lazy { listOf("sleep") }

    override val classNames by lazy { listOf("java.util.concurrent.TimeUnit") }

    override fun onSingleArgument(
        element: PsiMethodCallExpression,
        context: Context,
        argument: PsiExpression,
        argumentExpression: Expression
    ): Expression? {
        if (!argument.isNumberLiteral()) return null
        val qualifier = element.methodExpression.qualifierExpression as? PsiReferenceExpression ?: return null
        val timeUnit = qualifier.resolve() as? PsiEnumConstant ?: return null
        val unit = UNITS[timeUnit.name] ?: return null
        // A fold cannot be empty, so `.unit` cannot be inserted before a real `)`: the `)` is part of the placeholder.
        return DurationLiteral(
            element,
            element.textRange,
            listOf(
                (qualifier.start() to context.identifier.start()).toTextRange() to "",
                (argument.end() to element.end()).toTextRange() to ".$unit)",
            ),
        )
    }

    companion object {
        private val UNITS = mapOf(
            "NANOSECONDS" to DurationLiteral.NANOS,
            "MICROSECONDS" to DurationLiteral.MICROS,
            "MILLISECONDS" to DurationLiteral.MILLIS,
            "SECONDS" to DurationLiteral.SECONDS,
            "MINUTES" to DurationLiteral.MINUTES,
            "HOURS" to DurationLiteral.HOURS,
            "DAYS" to DurationLiteral.DAYS,
        )
    }
}
