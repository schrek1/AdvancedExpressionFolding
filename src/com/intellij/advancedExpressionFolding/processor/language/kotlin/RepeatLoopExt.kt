package com.intellij.advancedExpressionFolding.processor.language.kotlin

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.semantic.kotlin.RepeatStatement
import com.intellij.advancedExpressionFolding.processor.core.BaseExtension
import com.intellij.psi.JavaTokenType
import com.intellij.psi.PsiBinaryExpression
import com.intellij.psi.PsiDeclarationStatement
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiExpressionStatement
import com.intellij.psi.PsiForStatement
import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiLocalVariable
import com.intellij.psi.PsiPostfixExpression
import com.intellij.psi.PsiPrefixExpression
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.PsiTypes
import com.intellij.psi.SyntaxTraverser

object RepeatLoopExt : BaseExtension() {

    /// Folds `for (int i = 0; i < n; i++)` into `repeat(n)` when the body never references `i`.
    fun getRepeatExpression(element: PsiForStatement): Expression? {
        if (!repeatLoop) return null
        val lParenth = element.lParenth ?: return null
        val rParenth = element.rParenth ?: return null
        val index = ((element.initialization as? PsiDeclarationStatement)
            ?.declaredElements?.singleOrNull() as? PsiLocalVariable) ?: return null
        if (index.type != PsiTypes.intType() && index.type != PsiTypes.longType()) return null
        if (((index.initializer as? PsiLiteralExpression)?.value as? Number)?.toLong() != 0L) return null

        val condition = element.condition as? PsiBinaryExpression ?: return null
        val count = condition.rOperand ?: return null
        if (condition.operationTokenType != JavaTokenType.LT || !condition.lOperand.refersTo(index)) return null
        if (!(element.update as? PsiExpressionStatement)?.expression.isIncrementOf(index)) return null

        val body = element.body ?: return null
        val indexUsed = SyntaxTraverser.psiTraverser(body)
            .any { it is PsiReferenceExpression && it.isReferenceTo(index) }
        if (indexUsed) return null
        return RepeatStatement(element, lParenth, count, rParenth)
    }

    private fun PsiExpression?.isIncrementOf(index: PsiLocalVariable): Boolean = when (this) {
        is PsiPostfixExpression -> operationTokenType == JavaTokenType.PLUSPLUS && operand.refersTo(index)
        is PsiPrefixExpression -> operationTokenType == JavaTokenType.PLUSPLUS && operand.refersTo(index)
        else -> false
    }

    private fun PsiExpression?.refersTo(index: PsiLocalVariable): Boolean =
        this is PsiReferenceExpression && qualifierExpression == null && isReferenceTo(index)
}
