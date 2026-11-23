package com.intellij.advancedExpressionFolding.processor.logger

import com.intellij.advancedExpressionFolding.processor.end
import com.intellij.advancedExpressionFolding.processor.start
import com.intellij.psi.JavaTokenType
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiPolyadicExpression

/** The message of a log call: one string literal, or string literals joined by `+` (a message wrapped over lines). */
class LogMessage private constructor(private val parts: List<PsiLiteralExpression>) {

    /** The message as the source of one literal: the parts' contents between a single pair of quotes. */
    val text: String = parts.singleOrNull()?.text
        ?: parts.joinToString(separator = "", prefix = "\"", postfix = "\"") { it.text.substring(1, it.textLength - 1) }

    val isTextBlock: Boolean get() = parts.any { it.isTextBlock }

    val end: Int get() = parts.last().end()

    /** The document offset of `text[index]`. */
    fun offsetOf(index: Int): Int {
        parts.singleOrNull()?.let { return it.start() + index }
        var remaining = index - 1
        if (remaining < 0) {
            return parts.first().start()
        }
        for (part in parts) {
            val contentLength = part.textLength - 2
            if (remaining < contentLength) {
                return part.start() + 1 + remaining
            }
            remaining -= contentLength
        }
        return end - 1
    }

    companion object {
        fun of(expression: PsiExpression?): LogMessage? = when (expression) {
            is PsiLiteralExpression -> expression.asString()?.let { LogMessage(listOf(it)) }
            is PsiPolyadicExpression -> expression.takeIf { it.operationTokenType == JavaTokenType.PLUS }
                ?.operands
                ?.map { operand -> (operand as? PsiLiteralExpression)?.asString()?.takeUnless { it.isTextBlock } ?: return null }
                ?.let(::LogMessage)
            else -> null
        }

        private fun PsiLiteralExpression.asString() = takeIf { it.value is String }
    }
}
