package com.intellij.advancedExpressionFolding.expression.semantic.kotlin

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.controlflow.InstanceofSwitchExpression
import com.intellij.advancedExpressionFolding.processor.language.kotlin.PreconditionExt
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.JavaTokenType
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiIfStatement
import com.intellij.psi.PsiInstanceOfExpression
import com.intellij.psi.PsiJavaToken
import com.intellij.psi.PsiParenthesizedExpression
import com.intellij.psi.PsiPrefixExpression

/**
 * `x instanceof Foo` shown as `x is Foo`, `!(x instanceof Foo)` as `x !is Foo`.
 *
 * Only the keyword and, for the negation, the `!(` and `)` around the check fold; operand, type and pattern binding
 * stay source text with their syntax colours.
 */
class KotlinIsOperator private constructor(
    keyword: PsiJavaToken,
    private val negation: PsiPrefixExpression?,
) : Expression(keyword, keyword.textRange) {

    override fun supportsFoldRegions(document: Document, parent: Expression?) = true

    override fun buildFoldRegions(element: PsiElement, document: Document, parent: Expression?): Array<FoldingDescriptor> {
        val group = FoldingGroup.newGroup(javaClass.name)
        val negation = negation
            ?: return arrayOf(FoldingDescriptor(element.node, textRange, group, "is"))
        val check = element.parent.textRange
        return arrayOf(
            FoldingDescriptor(negation.node, TextRange.create(negation.textRange.startOffset, check.startOffset), group, ""),
            FoldingDescriptor(element.node, textRange, group, "!is"),
            FoldingDescriptor(negation.node, TextRange.create(check.endOffset, negation.textRange.endOffset), group, ""),
        )
    }

    companion object {
        fun create(keyword: PsiJavaToken): KotlinIsOperator? {
            val check = keyword.parent as? PsiInstanceOfExpression ?: return null
            if ((check.parent as? PsiIfStatement)?.let(InstanceofSwitchExpression::foldsAsSwitch) == true) {
                return null
            }
            val negation = ((check.parent as? PsiParenthesizedExpression)?.parent as? PsiPrefixExpression)
                ?.takeIf { it.operationTokenType == JavaTokenType.EXCL && !isPreconditionCondition(it) }
            return KotlinIsOperator(keyword, negation)
        }

        // `require` / `check` already hide the `!(` and `)` of a negated guard condition, so the check reads as `is`.
        private fun isPreconditionCondition(negation: PsiPrefixExpression): Boolean {
            val ifStatement = negation.parent as? PsiIfStatement ?: return false
            return PreconditionExt.kotlinPreconditions && ifStatement.condition == negation &&
                PreconditionExt.guardThrow(ifStatement) != null
        }
    }
}
