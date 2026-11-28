package com.intellij.advancedExpressionFolding.processor.logger

import com.intellij.advancedExpressionFolding.processor.end
import com.intellij.advancedExpressionFolding.processor.start
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiMethodCallExpression

class StringFormattedLoggerBracketsExtension(
        element: PsiMethodCallExpression,
        document: Document
    ) : LoggerBracketsExtensionBase(element, document) {

        // Extra arguments stay real source, so only then the call's head must be shown; otherwise the whole tail hides.
        override fun PsiElement.createLastString(hasTooManyArguments: Boolean) =
            if (hasTooManyArguments) ".formatted(" else ""

        override fun MutableList<PsiExpression>.prepareArguments(): MutableList<PsiExpression> {
            // No need to remove anything
            return this
        }

        override fun hiddenArgumentsRange(
            logMessage: LogMessage,
            lastConsumed: PsiExpression,
            firstExtra: PsiExpression?
        ) = if (firstExtra == null) {
            TextRange(logMessage.end, element.end())
        } else {
            TextRange(element.argumentList.firstChild.end(), firstExtra.start())
        }

        override fun Array<PsiExpression>.hasEnoughElements() = isNotEmpty()

        override fun Array<PsiExpression>.extractMessage(): PsiExpression? = element.methodExpression.qualifierExpression
    }
