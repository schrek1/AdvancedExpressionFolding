package com.intellij.advancedExpressionFolding.processor.lombok

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.semantic.lombok.MethodAnnotationExpression
import com.intellij.advancedExpressionFolding.processor.controlflow.PsiTryStatementExt.isSimpleTryCatch
import com.intellij.advancedExpressionFolding.processor.controlflow.PsiTryStatementExt.selectivelyAddException
import com.intellij.advancedExpressionFolding.processor.core.BaseExtension
import com.intellij.advancedExpressionFolding.processor.exprHide
import com.intellij.advancedExpressionFolding.processor.exprList
import com.intellij.advancedExpressionFolding.processor.exprWrap
import com.intellij.advancedExpressionFolding.processor.group
import com.intellij.advancedExpressionFolding.processor.lombok.MethodBodyInspector.rethrownException
import com.intellij.advancedExpressionFolding.processor.prevWhiteSpace
import com.intellij.advancedExpressionFolding.processor.trailingCharsRange
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiTryStatement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil

/**
 * Folds a method whose whole body is a `try` that only wraps and rethrows the caught exception into
 * `@SneakyThrows` on the method; the `try` head and the `catch` are hidden and the body is dedented.
 */
object LombokSneakyThrowsExt : BaseExtension() {

    fun methodAnnotation(method: PsiMethod): Expression? {
        val tryStatement = method.wholeBodyRethrowingTry() ?: return null
        val rethrown = tryStatement.catchSections.single().rethrownException ?: return null
        val tryBlock = tryStatement.tryBlock ?: return null
        val lBrace = tryBlock.lBrace ?: return null
        val rBrace = tryBlock.rBrace ?: return null
        val firstContent = PsiTreeUtil.skipWhitespacesForward(lBrace) ?: return null
        val lastContent = PsiTreeUtil.skipWhitespacesBackward(rBrace) ?: return null

        val annotationText = if (rethrown.selectivelyAddException()) "@SneakyThrows($rethrown)" else "@SneakyThrows"
        val group = group("@SneakyThrows-${method.hashCode()}")
        // Hidden in pieces split at the try block's braces, so they nest inside its own `{...}` fold instead of crossing it.
        val list = exprList(
            MethodAnnotationExpression(method, listOf(annotationText), emptyList(), group = group),
            tryStatement.exprHide(textRange = TextRange(tryStatement.textRange.startOffset, lBrace.textRange.startOffset)),
            lBrace.exprHide(),
            tryBlock.exprHide(textRange = TextRange(lBrace.textRange.endOffset, firstContent.textRange.startOffset)),
            tryBlock.exprHide(textRange = TextRange(lastContent.textRange.endOffset, rBrace.textRange.startOffset)),
            rBrace.exprHide(),
            tryStatement.exprHide(textRange = TextRange(tryBlock.textRange.endOffset, tryStatement.textRange.endOffset)),
        )
        list += tryStatement.dedentBody(firstContent.textRange.startOffset, lastContent.textRange.endOffset)
        return list.exprWrap(method, group)
    }

    fun PsiTryStatement.isWholeMethodRethrow(): Boolean =
        lombokSneakyThrows && PsiTreeUtil.getParentOfType(this, PsiMethod::class.java)?.wholeBodyRethrowingTry() == this

    private fun PsiMethod.wholeBodyRethrowingTry(): PsiTryStatement? =
        (body?.statements?.singleOrNull() as? PsiTryStatement)?.takeIf {
            it.isSimpleTryCatch() && it.catchSections.single().rethrownException != null
        }

    // Moves every line of the try block one indentation level left, so the body reads as the method body.
    private fun PsiTryStatement.dedentBody(contentStart: Int, contentEnd: Int): List<Expression?> {
        val contentIndent = lineIndent(firstLineOf(contentStart)) ?: return emptyList()
        val tryIndent = lineIndent(prevWhiteSpace()?.text) ?: return emptyList()
        val extraIndent = contentIndent - tryIndent
        if (extraIndent <= 0) return emptyList()
        return PsiTreeUtil.findChildrenOfType(tryBlock, PsiWhiteSpace::class.java)
            .filter { it.textRange.startOffset >= contentStart && it.textRange.endOffset <= contentEnd }
            .filter { (lineIndent(it.text) ?: 0) >= extraIndent }
            .map { it.exprHide(textRange = it.trailingCharsRange(extraIndent)) }
    }

    private fun PsiTryStatement.firstLineOf(contentStart: Int): String? =
        PsiTreeUtil.findChildrenOfType(tryBlock, PsiWhiteSpace::class.java)
            .firstOrNull { it.textRange.endOffset == contentStart }?.text

    private fun lineIndent(whiteSpace: String?): Int? =
        whiteSpace?.takeIf { it.contains('\n') }?.substringAfterLast('\n')?.length
}
