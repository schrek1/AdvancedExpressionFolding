package com.intellij.advancedExpressionFolding.expression.semantic

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.application.options.CodeStyle
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

/**
 * Shows a call chain as one call with named arguments, e.g. `person.copy(name = "Alice", age = 31)`.
 *
 * Only the tokens that change fold: argument names, values, the closing `)` and the shared indentation stay real
 * text. A single argument joins one line; several keep one argument per line, re-indented the Kotlin way. The values
 * are not folded here: the expression is nested, so their own foldings come from the normal traversal.
 */
class NamedArgumentsCall(
    element: PsiElement,
    private val openRange: TextRange,
    private val openText: String,
    private val arguments: List<NamedArgument>,
    private val closeRange: TextRange,
) : Expression(element, element.textRange) {

    /**
     * @param separatorRange the text before the name (`.`)
     * @param assignRange the text between the name and the value, shown as ` = `
     * @param suffixRange the text after the value, ending with the call's `)`
     * @param nameText shown at the end of the separator, for a name whose start `separatorRange` hides (`setF` → `f`)
     */
    class NamedArgument(
        val separatorRange: TextRange,
        val assignRange: TextRange,
        val suffixRange: TextRange,
        val nameText: String = "",
    )

    override fun supportsFoldRegions(document: Document, parent: Expression?): Boolean = true

    override fun isNested(): Boolean = true

    override fun buildFoldRegions(
        element: PsiElement,
        document: Document,
        parent: Expression?
    ): Array<FoldingDescriptor> {
        val group = FoldingGroup.newGroup(NamedArgumentsCall::class.java.name)
        val descriptors = mutableListOf<FoldingDescriptor>()
        fun fold(range: TextRange, text: String) {
            if (!range.isEmpty && document.getText(range) != text) {
                descriptors += FoldingDescriptor(element.node, range, group, text)
            }
        }

        // Re-indents a range that starts its line; the indentation both layouts share stays real text.
        fun foldIndented(range: TextRange, indent: String, suffix: String = "") {
            val lineStart = document.getLineStartOffset(document.getLineNumber(range.startOffset))
            if (!document.isBlank(lineStart, range.startOffset)) {
                fold(range, suffix)
                return
            }
            val shared = document.getText(TextRange(lineStart, range.startOffset)).commonPrefixWith(indent).length
            fold(TextRange(lineStart + shared, range.endOffset), indent.substring(shared) + suffix)
        }

        val single = arguments.singleOrNull()
        if (single != null &&
            document.isBlank(openRange.endOffset, single.separatorRange.startOffset) &&
            document.isBlank(single.suffixRange.endOffset, closeRange.startOffset)
        ) {
            fold(openRange.union(single.separatorRange), openText + single.nameText)
            fold(single.assignRange, " = ")
            fold(single.suffixRange.withoutLastCharacter(), "")
            fold(TextRange(single.suffixRange.endOffset, closeRange.endOffset), "")
            return descriptors.toTypedArray()
        }

        // Kotlin layout: arguments one indent level deeper than the line the call starts on, `)` back on that level
        val baseIndent = document.indentOf(openRange.startOffset)
        val argumentIndent = baseIndent + indentUnit(element)
        fold(openRange, openText)
        var previousEnd = openRange.endOffset
        arguments.forEachIndexed { index, argument ->
            if (document.sameLine(previousEnd, argument.separatorRange.startOffset)) {
                fold(argument.separatorRange, (if (index > 0) " " else "") + argument.nameText)
            } else {
                foldIndented(argument.separatorRange, argumentIndent, argument.nameText)
            }
            fold(argument.assignRange, " = ")
            if (index < arguments.lastIndex) {
                fold(argument.suffixRange, ",")
            }
            previousEnd = argument.suffixRange.endOffset
        }
        val lastSuffix = arguments.last().suffixRange
        if (lastSuffix.endOffset == closeRange.startOffset) {
            fold(lastSuffix.withoutLastCharacter(), "")
            fold(closeRange, "")
        } else {
            fold(lastSuffix, "")
            // the `)` of `build()` becomes the `)` of the call
            val closeWithoutParenthesis = closeRange.withoutLastCharacter()
            if (document.sameLine(lastSuffix.endOffset, closeRange.startOffset)) {
                fold(closeWithoutParenthesis, "")
            } else {
                foldIndented(closeWithoutParenthesis, baseIndent)
            }
        }
        return descriptors.toTypedArray()
    }

    private fun TextRange.withoutLastCharacter() = TextRange(startOffset, endOffset - 1)

    private fun Document.sameLine(before: Int, after: Int) = getLineNumber(before) == getLineNumber(after)

    private fun Document.isBlank(start: Int, end: Int) = charsSequence.subSequence(start, end).isBlank()

    private fun Document.indentOf(offset: Int): String {
        val lineStart = getLineStartOffset(getLineNumber(offset))
        return charsSequence.subSequence(lineStart, offset).takeWhile { it == ' ' || it == '\t' }.toString()
    }

    private fun indentUnit(element: PsiElement): String {
        val options = CodeStyle.getIndentOptions(element.containingFile)
        return if (options.USE_TAB_CHARACTER) "\t" else " ".repeat(options.INDENT_SIZE)
    }
}
