@file:Suppress("DEPRECATION")

package com.intellij.advancedExpressionFolding.processor.declaration

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.controlflow.ControlFlowMultiStatementCodeBlockExpression
import com.intellij.advancedExpressionFolding.expression.controlflow.ControlFlowSingleStatementCodeBlockExpression
import com.intellij.advancedExpressionFolding.expression.controlflow.GuardIfOneLinerExpression
import com.intellij.advancedExpressionFolding.expression.controlflow.IfExpression
import com.intellij.advancedExpressionFolding.expression.controlflow.InstanceofSwitchExpression
import com.intellij.advancedExpressionFolding.processor.language.kotlin.ScopeLetExt
import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.advancedExpressionFolding.settings.state.IControlFlowState
import com.intellij.psi.JavaTokenType
import com.intellij.psi.PsiBlockStatement
import com.intellij.psi.PsiCatchSection
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiCodeBlock
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiIfStatement
import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiLoopStatement
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiPrefixExpression
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.PsiReturnStatement
import com.intellij.psi.PsiSwitchStatement
import com.intellij.psi.PsiTryStatement

object PsiCodeBlockExt : IControlFlowState by State()() {

    fun getCodeBlockExpression(element: PsiCodeBlock): Expression? {
        val parent = element.parent
        if (!isSupportedParent(parent, element)) {
            return null
        }
        guardReturn(element, parent)?.let { return GuardIfOneLinerExpression(element, it) }
        return if (element.statements.size == 1 || parent is PsiSwitchStatement) {
            if (shouldCollapseSingleStatement(element, parent)) {
                ControlFlowSingleStatementCodeBlockExpression(element, element.textRange)
            } else {
                null
            }
        } else {
            if (controlFlowMultiStatementCodeBlockCollapse && !element.isWritable) {
                @Suppress("DEPRECATION")
                ControlFlowMultiStatementCodeBlockExpression(element, element.textRange)
            } else {
                null
            }
        }
    }

    private fun isSupportedParent(parent: PsiElement?, element: PsiCodeBlock): Boolean {
        if (parent is PsiBlockStatement) {
            val grandParent = parent.parent
            val hasBraces = element.rBrace != null && element.lBrace != null
            if ((grandParent is PsiIfStatement || grandParent is PsiLoopStatement) && hasBraces) {
                return true
            }
        }
        return parent is PsiSwitchStatement || parent is PsiTryStatement || parent is PsiCatchSection
    }

    private val emptyFactoryMethods = setOf("of", "empty", "emptyList", "emptySet", "emptyMap")

    /** The `return` of a braced guard `if` (no `else`, a statement of a block) when it is the block's only content. */
    private fun guardReturn(element: PsiCodeBlock, parent: PsiElement?): PsiReturnStatement? {
        if (!guardIfOneLiner || parent !is PsiBlockStatement) {
            return null
        }
        val ifStatement = parent.parent as? PsiIfStatement ?: return null
        if (ifStatement.elseBranch != null || ifStatement.parent !is PsiCodeBlock) {
            return null
        }
        val returnStatement = element.statements.singleOrNull() as? PsiReturnStatement ?: return null
        if (element.children.any { it is PsiComment } || returnStatement.textContains('\n')) {
            return null
        }
        return returnStatement.takeIf { isSimpleValue(it.returnValue) }
    }

    private fun isSimpleValue(value: PsiExpression?): Boolean = when (value) {
        null, is PsiLiteralExpression -> true
        is PsiPrefixExpression -> value.operationTokenType == JavaTokenType.MINUS && value.operand is PsiLiteralExpression
        is PsiReferenceExpression -> value.qualifierExpression == null
        is PsiMethodCallExpression -> value.argumentList.isEmpty &&
            value.methodExpression.referenceName in emptyFactoryMethods &&
            (value.methodExpression.qualifierExpression as? PsiReferenceExpression)?.resolve() is PsiClass
        else -> false
    }

    private fun shouldCollapseSingleStatement(
        element: PsiCodeBlock,
        parent: PsiElement?
    ): Boolean {
        if (!controlFlowSingleStatementCodeBlockCollapse || element.isWritable) {
            return false
        }
        val grandParent = parent?.parent
        return grandParent !is PsiIfStatement ||
            !IfExpression.isAssertExpression(grandParent) && !InstanceofSwitchExpression.foldsAsSwitch(grandParent) &&
            !ScopeLetExt.folds(grandParent)
    }
}
