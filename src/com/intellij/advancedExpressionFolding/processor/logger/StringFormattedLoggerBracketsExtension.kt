package com.intellij.advancedExpressionFolding.processor.logger

import com.intellij.advancedExpressionFolding.processor.end
import com.intellij.advancedExpressionFolding.processor.start
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiMethodCallExpression

class StringFormattedLoggerBracketsExtension(
        element: PsiMethodCallExpression,
        document: Document
    ) : LoggerBracketsExtensionBase(element, document) {

        override fun PsiElement.createLastString(hasTooManyArguments: Boolean) = buildString {
            append(".formatted(")
            if (!hasTooManyArguments) {
                append(")")
            }
        }

        override fun MutableList<PsiExpression>.prepareArguments(): MutableList<PsiExpression> {
            // No need to remove anything
            return this
        }

        override fun hiddenArgumentsRange(
            logLiteral: PsiLiteralExpression,
            lastConsumed: PsiExpression,
            firstExtra: PsiExpression?
        ) = if (firstExtra == null) {
            TextRange(logLiteral.end(), element.end())
        } else {
            TextRange(element.argumentList.firstChild.end(), firstExtra.start())
        }

        override fun Array<PsiExpression>.hasEnoughElements() = isNotEmpty()

        override fun Array<PsiExpression>.extractLiteral(): PsiLiteralExpression? =
            element.methodExpression.qualifierExpression.asStringLiteral()
    }
