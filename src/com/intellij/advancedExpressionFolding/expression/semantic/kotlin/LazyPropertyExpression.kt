package com.intellij.advancedExpressionFolding.expression.semantic.kotlin

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiBlockStatement
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiField
import com.intellij.psi.PsiIdentifier
import com.intellij.psi.PsiIfStatement
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiTypeElement
import com.intellij.psi.util.parentOfType

/**
 * Folds a field and the lazy getter right below it so that the modifiers, the field name, the getter's braces and
 * the initializer stay real text: `private |Type| config|;...()| {|...= |loadConfig()|;...|}`
 * → `private val config by lazy { loadConfig() }`.
 */
class LazyPropertyExpression(
    private val field: PsiField,
    private val typeElement: PsiTypeElement,
    private val name: PsiIdentifier,
    val getter: PsiMethod,
    private val initializer: PsiExpression,
) : Expression(field, TextRange(field.textRange.startOffset, getter.textRange.endOffset)) {

    override fun supportsFoldRegions(document: Document, parent: Expression?): Boolean = true

    override fun buildFoldRegions(element: PsiElement, document: Document, parent: Expression?): Array<FoldingDescriptor> {
        val group = FoldingGroup.newGroup(LazyPropertyExpression::class.java.name)
        val body = getter.body ?: return EMPTY_ARRAY
        val lBrace = body.lBrace ?: return EMPTY_ARRAY
        val rBrace = body.rBrace ?: return EMPTY_ARRAY
        // the `if` block's braces carry Java's own `{...}` region; cutting at them keeps every region nested in it
        val thenBlock = (initializer.parentOfType<PsiIfStatement>()?.thenBranch as? PsiBlockStatement)?.codeBlock
        val cuts = listOfNotNull(thenBlock?.lBrace, thenBlock?.rBrace).map { it.textRange }
        val head = TextRange(lBrace.textRange.endOffset, initializer.textRange.startOffset).split(cuts)
        val tail = TextRange(initializer.textRange.endOffset, rBrace.textRange.startOffset).split(cuts)
        return (
            listOf(
                fold(typeElement, typeElement.textRange, "val", group),
                fold(field, TextRange(name.textRange.endOffset, lBrace.textRange.startOffset), " by lazy ", group),
            ) +
                head.mapIndexed { index, range -> fold(body, range, if (index == 0) " " else "", group) } +
                tail.mapIndexed { index, range -> fold(body, range, if (index == tail.lastIndex) " " else "", group) }
            ).toTypedArray()
    }

    private fun TextRange.split(cuts: List<TextRange>): List<TextRange> {
        val inside = cuts.filter { contains(it) }.sortedBy { it.startOffset }
        val bounds = listOf(startOffset) + inside.flatMap { listOf(it.startOffset, it.endOffset) } + endOffset
        return bounds.zipWithNext { start, end -> TextRange(start, end) }.filterNot { it.isEmpty }
    }

    private fun fold(element: PsiElement, range: TextRange, placeholder: String, group: FoldingGroup) =
        FoldingDescriptor(element.node, range, group, placeholder, true, emptySet<Any>())

    override fun isNested() = true
}
