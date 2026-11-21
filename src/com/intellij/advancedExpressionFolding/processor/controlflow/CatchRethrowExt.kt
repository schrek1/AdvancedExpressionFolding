package com.intellij.advancedExpressionFolding.processor.controlflow

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.processor.controlflow.PsiTryStatementExt.isSimpleTryCatch
import com.intellij.advancedExpressionFolding.processor.core.BaseExtension
import com.intellij.advancedExpressionFolding.processor.expr
import com.intellij.advancedExpressionFolding.processor.exprHide
import com.intellij.advancedExpressionFolding.processor.exprList
import com.intellij.advancedExpressionFolding.processor.exprWrap
import com.intellij.advancedExpressionFolding.processor.group
import com.intellij.advancedExpressionFolding.processor.lombok.LombokSneakyThrowsExt.isWholeMethodRethrow
import com.intellij.advancedExpressionFolding.processor.lombok.MethodBodyInspector.rethrownException
import com.intellij.advancedExpressionFolding.processor.prevWhiteSpace
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiCatchSection
import com.intellij.psi.PsiCodeBlock
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiNewExpression
import com.intellij.psi.PsiThrowStatement
import com.intellij.psi.PsiTryStatement
import com.intellij.psi.util.PsiTreeUtil

object CatchRethrowExt : BaseExtension() {

    /**
     * Folds a catch block whose only statement wraps the caught exception, `throw new X(e);`, into `rethrow`
     * (`rethrow as X` when the wrapper is not a plain `RuntimeException`, with `X` kept as real text).
     */
    fun createExpression(block: PsiCodeBlock): Expression? {
        val catchSection = block.parent as? PsiCatchSection ?: return null
        // the experimental @SneakyThrows fold already hides this catch block
        if (experimental && (catchSection.parent as? PsiTryStatement)?.isSimpleTryCatch() == true) return null
        // the whole-method @SneakyThrows fold hides this catch block too
        if ((catchSection.parent as? PsiTryStatement)?.isWholeMethodRethrow() == true) return null
        if (PsiTreeUtil.findChildOfType(block, PsiComment::class.java) != null) return null
        val wrapper = catchSection.rethrownException ?: return null

        val whiteSpace = block.prevWhiteSpace()
        if (wrapper == "RuntimeException") {
            val start = whiteSpace?.textRange?.startOffset ?: block.textRange.startOffset
            return block.expr(" rethrow", textRange = TextRange(start, block.textRange.endOffset))
        }

        val wrapperReference = ((block.statements.single() as PsiThrowStatement).exception as PsiNewExpression)
            .classReference ?: return null
        // both regions stay inside the block, so they nest in the native `{...}` fold instead of crossing it
        return exprList(
            whiteSpace.exprHide(),
            block.expr(" rethrow as ", textRange = TextRange(block.textRange.startOffset, wrapperReference.textRange.startOffset)),
            block.exprHide(textRange = TextRange(wrapperReference.textRange.endOffset, block.textRange.endOffset)),
        ).exprWrap(block, group())
    }
}
