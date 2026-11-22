package com.intellij.advancedExpressionFolding.processor.language.kotlin

import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.advancedExpressionFolding.settings.state.IKotlinLanguageState
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.JavaTokenType
import com.intellij.psi.PsiCodeBlock
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiExpressionList
import com.intellij.psi.PsiJavaToken
import com.intellij.psi.PsiLambdaExpression
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiParameter
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil

/// Shows Java lambdas in Kotlin lambda syntax: `{ it.x }`, `{ a, b -> a + b }`, `call(x) { it.y }`.
///
/// Runs after every other fold of the file is collected: a lambda whose folds would overlap any of them (a call
/// already rewritten by a stream, Optional or similar fold) stays Java. Only the separators, braces and `it` are
/// folded; the body stays real source.
object KotlinLambdaExt : IKotlinLanguageState by State()() {

    private val CLOSING_TOKENS = setOf(
        JavaTokenType.RPARENTH, JavaTokenType.COMMA, JavaTokenType.SEMICOLON, JavaTokenType.COLON,
    )

    /// @param closingPrefix set on the fold of the token right after the lambda: its placeholder is this prefix plus
    ///   the token, so another fold of that same token (a hidden `;`) merges into it instead of conflicting
    private data class LambdaFold(val range: TextRange, val placeholder: String, val closingPrefix: String? = null)

    fun addLambdaFolds(root: PsiElement, document: Document, descriptors: MutableList<FoldingDescriptor>) {
        if (!kotlinLambda) {
            return
        }
        val implicitItLambdas = HashSet<PsiLambdaExpression>()
        // pre-order: an outer lambda is decided before the lambdas nested in it
        for (lambda in PsiTreeUtil.findChildrenOfType(root, PsiLambdaExpression::class.java)) {
            val implicitIt = usesImplicitIt(lambda, implicitItLambdas)
            val folds = buildFolds(lambda, document, implicitIt) ?: continue
            if (mergeInto(descriptors, folds, lambda) && implicitIt) {
                implicitItLambdas += lambda
            }
        }
    }

    private fun usesImplicitIt(lambda: PsiLambdaExpression, implicitItLambdas: Set<PsiLambdaExpression>): Boolean {
        val parameter = lambda.parameterList.parameters.singleOrNull() ?: return false
        val insideItLambda = generateSequence(PsiTreeUtil.getParentOfType(lambda, PsiLambdaExpression::class.java)) {
            PsiTreeUtil.getParentOfType(it, PsiLambdaExpression::class.java)
        }.any { it in implicitItLambdas }
        if (insideItLambda) {
            return false
        }
        val body = lambda.body ?: return false
        return PsiTreeUtil.collectElementsOfType(body, PsiReferenceExpression::class.java).none { reference ->
            reference.qualifierExpression == null && reference.referenceName == "it" && reference.resolve() != parameter
        }
    }

    private fun buildFolds(lambda: PsiLambdaExpression, document: Document, implicitIt: Boolean): List<LambdaFold>? {
        val body = lambda.body ?: return null
        val blockBody = body as? PsiCodeBlock
        val parameters = lambda.parameterList.parameters
        val argumentList = trailingArgumentList(lambda, document)

        val folds = mutableListOf<LambdaFold>()
        var openingStart = lambda.textRange.startOffset
        var openingPrefix = ""
        if (argumentList != null && argumentList.expressions.size == 1) {
            openingStart = argumentList.textRange.startOffset
            openingPrefix = " "
        } else if (argumentList != null) {
            val separatorStart = argumentList.expressions.let { it[it.size - 2] }.textRange.endOffset
            val separator = document.getText(TextRange(separatorStart, openingStart))
            if ('\n' in separator) {
                // the lambda moves up behind the closed call instead of opening a line with `{`
                openingStart = separatorStart
                openingPrefix = ") "
            } else {
                val comma = separatorStart + separator.indexOf(',')
                folds += LambdaFold(TextRange(comma, comma + 1), ")")
            }
        }

        if (implicitIt || parameters.isEmpty()) {
            folds += LambdaFold(
                TextRange(openingStart, body.textRange.startOffset),
                openingPrefix + if (blockBody == null) "{ " else "",
            )
            if (implicitIt) {
                folds += itReferences(parameters.single(), body)
            }
        } else {
            folds += explicitParameterFolds(lambda, parameters, blockBody, openingStart, openingPrefix) ?: return null
        }

        if (argumentList != null) {
            val rParen = argumentList.lastChild
            val adjacent = rParen.textRange.startOffset == lambda.textRange.endOffset
            folds += LambdaFold(rParen.textRange, if (blockBody != null) "" else if (adjacent) " }" else "}")
        } else if (blockBody == null) {
            val next = PsiTreeUtil.nextLeaf(lambda)?.let { if (it is PsiWhiteSpace) PsiTreeUtil.nextLeaf(it) else it }
            if (next !is PsiJavaToken || next.tokenType !in CLOSING_TOKENS) {
                return null
            }
            val prefix = if (next.textRange.startOffset == lambda.textRange.endOffset) " }" else "}"
            folds += LambdaFold(next.textRange, prefix + next.text, prefix)
        }
        return folds.map { fold -> fold.withoutSharedWhitespace(document.charsSequence) }
    }

    /// Leaves whitespace that the placeholder would only repeat (the space after `->`) as real text.
    private fun LambdaFold.withoutSharedWhitespace(text: CharSequence): LambdaFold {
        var start = range.startOffset
        var end = range.endOffset
        var shown = placeholder
        while (end - start > 1 && shown.isNotEmpty() && text[end - 1].isWhitespace() && text[end - 1] == shown.last()) {
            end--
            shown = shown.dropLast(1)
        }
        while (end - start > 1 && shown.isNotEmpty() && text[start].isWhitespace() && text[start] == shown.first()) {
            start++
            shown = shown.drop(1)
        }
        return copy(range = TextRange(start, end), placeholder = shown)
    }

    private fun explicitParameterFolds(
        lambda: PsiLambdaExpression,
        parameters: Array<PsiParameter>,
        blockBody: PsiCodeBlock?,
        openingStart: Int,
        openingPrefix: String,
    ): List<LambdaFold>? {
        val folds = mutableListOf<LambdaFold>()
        val firstName = parameters.first().nameIdentifier ?: return null
        val openingRange = TextRange(openingStart, firstName.textRange.startOffset)
        folds += if (openingRange.isEmpty) {
            LambdaFold(firstName.textRange, "{ " + firstName.text)
        } else {
            LambdaFold(openingRange, "$openingPrefix{ ")
        }
        for (parameter in parameters.drop(1)) {
            val name = parameter.nameIdentifier ?: return null
            if (parameter.textRange.startOffset < name.textRange.startOffset) {
                folds += LambdaFold(TextRange(parameter.textRange.startOffset, name.textRange.startOffset), "")
            }
        }
        val rParen = lambda.parameterList.lastChild
        if (rParen is PsiJavaToken && rParen.tokenType == JavaTokenType.RPARENTH) {
            folds += LambdaFold(rParen.textRange, "")
        }
        if (blockBody != null) {
            // the Kotlin brace opens before the parameters, so the Java one after `->` is hidden
            folds += LambdaFold(blockBody.lBrace?.textRange ?: return null, "")
        }
        return folds
    }

    private fun itReferences(parameter: PsiParameter, body: PsiElement): List<LambdaFold> {
        if (parameter.name == "it") {
            return emptyList()
        }
        return PsiTreeUtil.collectElementsOfType(body, PsiReferenceExpression::class.java)
            .filter { reference ->
                reference.qualifierExpression == null &&
                        reference.referenceName == parameter.name &&
                        reference.resolve() == parameter
            }
            .map { reference -> LambdaFold(reference.textRange, "it") }
    }

    /// The argument list of a method call whose last argument is `lambda`, when only whitespace stands between the
    /// lambda and its separators, so the lambda can move behind the call as a Kotlin trailing lambda.
    private fun trailingArgumentList(lambda: PsiLambdaExpression, document: Document): PsiExpressionList? {
        val argumentList = lambda.parent as? PsiExpressionList ?: return null
        if (argumentList.parent !is PsiMethodCallExpression || argumentList.expressions.last() != lambda) {
            return null
        }
        val rParen = argumentList.lastChild
        if (rParen !is PsiJavaToken || rParen.tokenType != JavaTokenType.RPARENTH) {
            return null
        }
        val expressions = argumentList.expressions
        val separatorStart = if (expressions.size == 1) argumentList.textRange.startOffset
        else expressions[expressions.size - 2].textRange.endOffset
        val separator = document.getText(TextRange(separatorStart, lambda.textRange.startOffset)).filterNot(Char::isWhitespace)
        val closing = document.getText(TextRange(lambda.textRange.endOffset, rParen.textRange.endOffset)).filterNot(Char::isWhitespace)
        return argumentList.takeIf { separator in setOf("(", ",") && closing == ")" }
    }

    /// Adds the lambda's folds unless one of them overlaps a fold already collected; the fold of the token after the
    /// lambda absorbs another fold of exactly that token.
    ///
    /// @return whether the folds were added
    private fun mergeInto(
        descriptors: MutableList<FoldingDescriptor>,
        folds: List<LambdaFold>,
        lambda: PsiLambdaExpression,
    ): Boolean {
        val span = TextRange(folds.minOf { it.range.startOffset }, folds.maxOf { it.range.endOffset })
        val placeholders = folds.map { it.placeholder }.toMutableList()
        val absorbed = mutableListOf<Int>()
        for ((index, descriptor) in descriptors.withIndex()) {
            val range = descriptor.range
            if (!range.intersectsStrict(span)) {
                continue
            }
            for ((foldIndex, fold) in folds.withIndex()) {
                if (!range.intersectsStrict(fold.range)) {
                    continue
                }
                if (range != fold.range || fold.closingPrefix == null) {
                    return false
                }
                placeholders[foldIndex] = fold.closingPrefix + descriptor.placeholderText.orEmpty()
                absorbed += index
            }
        }
        absorbed.asReversed().forEach { descriptors.removeAt(it) }
        val group = FoldingGroup.newGroup(KotlinLambdaExt::class.java.name)
        folds.forEachIndexed { index, fold ->
            descriptors += FoldingDescriptor(lambda.node, fold.range, group, emptySet<Any>(), false, placeholders[index], true)
        }
        return true
    }
}
