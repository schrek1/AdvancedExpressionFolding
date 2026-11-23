package com.intellij.advancedExpressionFolding.expression.controlflow

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.processor.language.kotlin.SingleExpressionFunctionExt
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiReturnStatement
import java.util.ArrayList

class ElvisExpression(
    element: PsiElement,
    textRange: TextRange,
    private val thenExpression: Expression,
    private val elseExpression: Expression,
    private val elements: List<TextRange>,
    private val inverted: Boolean = false
) : Expression(element, textRange) {

    override fun buildFoldRegions(
        element: PsiElement,
        document: Document,
        parent: Expression?
    ): Array<FoldingDescriptor> {
        val descriptors = ArrayList<FoldingDescriptor>()
        val group = FoldingGroup.newGroup(ElvisExpression::class.java.name)
        val thenRange = thenExpression.textRange
        val elseRange = elseExpression.textRange
        val elseIsNullLiteral = elseExpression.element.let { it is PsiLiteralExpression && it.text == "null" }
        // the inverted `x == null ? y : x.foo()` hides everything up to the value, which stays real code
        descriptors += FoldingDescriptor(
            element.node,
            TextRange.create(textRange.startOffset, thenRange.startOffset),
            group,
            ""
        )
        // `x?.y ?: null` is just `x?.y`: hide the whole ` : null` tail
        if (elseIsNullLiteral) {
            if (!inverted) {
                descriptors += FoldingDescriptor(
                    element.node,
                    TextRange.create(thenRange.endOffset, elseRange.endOffset),
                    group,
                    ""
                )
            }
        } else if (!inverted) {
            descriptors += FoldingDescriptor(
                element.node,
                TextRange.create(thenRange.endOffset, elseRange.startOffset),
                group,
                " ?: "
            )
        } else if ((element.parent as? PsiReturnStatement)?.let(SingleExpressionFunctionExt::foldsReturnOf) != true) {
            // the fallback moves behind the value; it takes the `;` that IfExt made sure follows
            val semicolon = TextRange.create(textRange.endOffset, textRange.endOffset + 1)
            descriptors += FoldingDescriptor(element.node, semicolon, group, tailBehindValue(document)!!)
        }
        ShortElvisExpression.nullify(
            element,
            document,
            descriptors,
            group,
            elements,
            !(elements.size == 1 && elements[0] == thenExpression.textRange)
        )
        if (thenExpression.supportsFoldRegions(document, this)) {
            descriptors += thenExpression.buildFoldRegions(thenExpression.element, document, this).toList()
        }
        if (!inverted && !elseIsNullLiteral && elseExpression.supportsFoldRegions(document, this)) {
            descriptors += elseExpression.buildFoldRegions(elseExpression.element, document, this).toList()
        }
        return descriptors.toTypedArray()
    }

    override fun supportsFoldRegions(document: Document, parent: Expression?): Boolean = true

    /** The ` ?: y` an inverted Elvis shows over the `;` behind its value; null when nothing goes there. */
    fun tailBehindValue(document: Document): String? {
        val elseIsNullLiteral = elseExpression.element.let { it is PsiLiteralExpression && it.text == "null" }
        return if (inverted && !elseIsNullLiteral) " ?: ${document.getText(elseExpression.textRange)}" else null
    }
}
