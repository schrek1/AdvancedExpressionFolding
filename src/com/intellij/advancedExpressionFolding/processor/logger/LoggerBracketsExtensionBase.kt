package com.intellij.advancedExpressionFolding.processor.logger

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.operation.basic.Variable
import com.intellij.advancedExpressionFolding.expression.semantic.logging.LoggerBracketExpression
import com.intellij.advancedExpressionFolding.expression.semantic.logging.LoggerBracketParentExpression
import com.intellij.advancedExpressionFolding.processor.asInstance
import com.intellij.advancedExpressionFolding.processor.argumentExpressions
import com.intellij.advancedExpressionFolding.processor.core.BuildExpressionExt
import com.intellij.advancedExpressionFolding.processor.end
import com.intellij.advancedExpressionFolding.processor.start
import com.intellij.advancedExpressionFolding.processor.toTextRange
import com.intellij.advancedExpressionFolding.processor.util.Helper
import com.intellij.advancedExpressionFolding.processor.util.PropertyUtil
import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.advancedExpressionFolding.settings.state.ILogFoldingState
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiLambdaExpression
import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.PsiSwitchExpression
import com.intellij.psi.util.PsiTreeUtil


open class LoggerBracketsExtensionBase(
    protected val element: PsiMethodCallExpression,
    protected val document: Document
) : ILogFoldingState by State()() {

    fun processExpression(): Expression? {
        val logLiteral = element.argumentExpressions.takeIf {
            it.hasEnoughElements()
        }?.extractLiteral() ?: return null
        if (!logFoldingTextBlocks && logLiteral.isTextBlock) return null
        val logText = logLiteral.text ?: return null
        val split = logText.splitTextPattern() ?: return null

        val arguments = element.argumentExpressions.toMutableList().prepareArguments()
        if (arguments.isEmpty()) return null
        if (logFoldingKeepMessageText) {
            foldArgumentsIntoMessage(logLiteral, arguments)?.let { return it }
        }
        var nextStringAddon = ""
        val hasTooManyArguments = split.size <= arguments.size
        var hasLast = false
        return split.mapIndexedNotNull { index, nextStringIn ->
            val nextString = if (nextStringAddon.isNotEmpty()) {
                val joined = nextStringAddon + nextStringIn
                nextStringAddon = ""
                joined
            } else {
                nextStringIn
            }

            val argument = arguments.getOrNull(index)
            val lastIndex = index == split.size - 1
            if (argument == null || lastIndex) {
                if (!hasLast) {
                    hasLast = true
                    val restAsString = if (!lastIndex && !hasTooManyArguments) {
                        calculateMissingString(split, index, logText)
                    } else {
                        nextString
                    }
                    val bracket = arguments[index - 1].nextSibling
                    val lastString = bracket.createLastString(hasTooManyArguments)
                    LoggerBracketExpression(element, bracket.textRange, restAsString + lastString, null)
                } else {
                    null
                }
            } else {
                val expression = BuildExpressionExt.getAnyExpression(argument, document)
                val text = if (expression is Variable) {
                    "\$"
                } else {
                    nextStringAddon += "}"
                    "\${"
                }
                val prevSibling = argument.prevSibling
                if (index == 0) {
                    val countChars = logLiteral.start() + nextString.length
                    val textRange = (countChars..prevSibling.end()).toTextRange()
                    LoggerBracketExpression(element, textRange, text, expression)
                } else {
                    val textRange = (prevSibling.prevSibling.start()..prevSibling.end()).toTextRange()
                    LoggerBracketExpression(element, textRange, nextString + text, expression)
                }
            }
        }.takeIf {
            it.isNotEmpty()
        }?.createParentExpression(arguments, split, hasTooManyArguments)
    }

    private fun List<LoggerBracketExpression>.createParentExpression(
        arguments: MutableList<PsiExpression>,
        split: List<String>,
        hasTooManyArguments: Boolean
    ): LoggerBracketParentExpression {
        val finalExpressions = if (hasTooManyArguments) {
            this + arguments.subList(split.size - 1, arguments.size).map { expr ->
                BuildExpressionExt.getAnyExpression(expr, document)
            }
        } else {
            this
        }
        return LoggerBracketParentExpression(element, element.textRange, finalExpressions)
    }

    private fun String.splitTextPattern(): List<String>? {
        val splitBrackets = this.split("{}")
        return if (splitBrackets.size == 1) {
            this.split(PRINTF_PATTERN)
        } else {
            splitBrackets
        }.takeIf {
            it.size > 1
        }
    }

    /**
     * Keeps the message as real source: each `{}` / `%s` that has an argument becomes `$arg` / `${arg}`, and the
     * consumed arguments are hidden. Arguments beyond the placeholders (a trailing exception) stay real.
     *
     * @return `null` when an argument would not read well inside the message; the caller then folds as before
     */
    private fun foldArgumentsIntoMessage(logLiteral: PsiLiteralExpression, arguments: List<PsiExpression>): Expression? {
        val logText = logLiteral.text
        val placeholders = logText.placeholderRanges()
        val consumed = arguments.take(placeholders.size)
        val placeholderFolds = consumed.zip(placeholders) { argument, placeholder ->
            val argumentText = argument.inlineText() ?: return null
            val followedByIdentifierPart = logText.getOrNull(placeholder.last + 1)?.isJavaIdentifierPart() == true
            val text = if (argumentText.isJavaIdentifier() && !followedByIdentifierPart) {
                "\$$argumentText"
            } else {
                "\${$argumentText}"
            }
            val range = TextRange(logLiteral.start() + placeholder.first, logLiteral.start() + placeholder.last + 1)
            LoggerBracketExpression(element, range, text, null)
        }
        val extraArguments = arguments.drop(consumed.size)
        val hiddenArguments = LoggerBracketExpression(
            element,
            hiddenArgumentsRange(logLiteral, consumed.last(), extraArguments.firstOrNull()),
            "",
            null
        )
        val extraExpressions = extraArguments.map { BuildExpressionExt.getAnyExpression(it, document) }
        return LoggerBracketParentExpression(element, element.textRange, placeholderFolds + hiddenArguments + extraExpressions)
    }

    /** The source span of the arguments that went into the message; the literal's closing quote stays real. */
    open fun hiddenArgumentsRange(
        logLiteral: PsiLiteralExpression,
        lastConsumed: PsiExpression,
        firstExtra: PsiExpression?
    ) = TextRange(logLiteral.end(), lastConsumed.end())

    private fun PsiExpression.inlineText(): String? {
        if (textContains('\n') || hasPartUnreadableInMessage()) return null
        return foldedText()?.takeIf { it.length <= MAX_INLINE_ARGUMENT_LENGTH }
    }

    private fun PsiExpression.hasPartUnreadableInMessage() =
        PsiTreeUtil.findChildOfAnyType(
            this,
            false,
            PsiLambdaExpression::class.java,
            PsiSwitchExpression::class.java
        ) != null

    /** The argument as the getter fold shows it (`previous.getMode()` -> `previous.mode`), otherwise its source text. */
    private fun PsiExpression.foldedText(): String? = when (this) {
        is PsiMethodCallExpression -> {
            val name = methodExpression.referenceName ?: return null
            val qualifierText = methodExpression.qualifierExpression?.let { qualifier -> qualifier.foldedText() ?: return null }
            val callArguments = argumentList.expressions
            val member = if (callArguments.isEmpty() && State()().getSetExpressionsCollapse && Helper.isGetter(name)) {
                PropertyUtil.guessPropertyName(name)
            } else {
                val argumentTexts = callArguments.map { callArgument -> callArgument.foldedText() ?: return null }
                "$name(${argumentTexts.joinToString(", ")})"
            }
            listOfNotNull(qualifierText, member).joinToString(".")
        }
        is PsiReferenceExpression -> {
            val qualifierText = qualifierExpression?.let { qualifier -> qualifier.foldedText() ?: return null }
            listOfNotNull(qualifierText, referenceName ?: return null).joinToString(".")
        }
        else -> text
    }

    private fun String.isJavaIdentifier() = StringUtil.isJavaIdentifier(this)

    private fun String.placeholderRanges(): List<IntRange> =
        if (contains("{}")) {
            "\\{}".toRegex().findAll(this).map { it.range }.toList()
        } else {
            PRINTF_PATTERN.findAll(this).map { it.range }.toList()
        }

    open fun Array<PsiExpression>.hasEnoughElements() = size > 1

    open fun Array<PsiExpression>.extractLiteral(): PsiLiteralExpression? = firstOrNull().asStringLiteral()

    open fun MutableList<PsiExpression>.prepareArguments(): MutableList<PsiExpression> {
        removeAt(0) // remove logLiteral
        return this
    }

    open fun PsiElement.createLastString(hasTooManyArguments: Boolean) = text.trim()


    private fun calculateMissingString(
        split: List<String>,
        index: Int,
        logLiteral: String
    ): String {
        val startIndex = split.subList(0, index).map {
            it.length + 2
        }.reduce(Int::plus)
        return logLiteral.substring(startIndex)
    }


    internal fun PsiExpression?.asStringLiteral() =
        asInstance<PsiLiteralExpression>()?.takeIf {
            it.value is String
        }

}

private val PRINTF_PATTERN = "%(?!n)\\w".toRegex()
private const val MAX_INLINE_ARGUMENT_LENGTH = 40
