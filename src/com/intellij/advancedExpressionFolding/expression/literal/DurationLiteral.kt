package com.intellij.advancedExpressionFolding.expression.literal

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

/// Shows a number literal with a time unit, e.g. `Duration.ofSeconds(30)` as `30.seconds`.
/// Each of `regions` is folded to its text; the number literal itself stays real code.
class DurationLiteral(
    element: PsiElement,
    textRange: TextRange,
    private val regions: List<Pair<TextRange, String>>,
) : Expression(element, textRange) {

    override fun supportsFoldRegions(document: Document, parent: Expression?): Boolean = true

    override fun buildFoldRegions(
        element: PsiElement,
        document: Document,
        parent: Expression?
    ): Array<FoldingDescriptor> {
        val group = FoldingGroup.newGroup(DurationLiteral::class.java.name)
        return regions.map { (range, text) ->
            FoldingDescriptor(element.node, range, group, text)
        }.toTypedArray()
    }

    companion object {
        const val NANOS = "nanoseconds"
        const val MICROS = "microseconds"
        const val MILLIS = "milliseconds"
        const val SECONDS = "seconds"
        const val MINUTES = "minutes"
        const val HOURS = "hours"
        const val DAYS = "days"
    }
}
