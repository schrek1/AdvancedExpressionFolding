package com.intellij.advancedExpressionFolding.processor.language.kotlin

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.controlflow.IfExpression
import com.intellij.advancedExpressionFolding.expression.semantic.WrapperExpression
import com.intellij.advancedExpressionFolding.processor.core.BaseExtension
import com.intellij.advancedExpressionFolding.processor.core.getAnyExpression
import com.intellij.advancedExpressionFolding.processor.expr
import com.intellij.advancedExpressionFolding.processor.asNewInstance
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.CommonClassNames
import com.intellij.psi.JavaTokenType
import com.intellij.psi.PsiCodeBlock
import com.intellij.psi.PsiIfStatement
import com.intellij.psi.PsiJavaToken
import com.intellij.psi.PsiThrowStatement

object TodoExt : BaseExtension() {

    private const val UNSUPPORTED_OPERATION_EXCEPTION = "java.lang.UnsupportedOperationException"

    /**
     * Folds `throw new UnsupportedOperationException("reason");` into `TODO("reason");`.
     *
     * Only the exact JDK class with no argument or a single `String` reason; a cause argument stays unfolded,
     * since `TODO(...)` reads as a reason.
     */
    fun createExpression(element: PsiThrowStatement, document: Document): Expression? {
        if (isFoldedAsAssert(element)) {
            return null
        }
        val newExpression = element.exception.asNewInstance()
            ?.takeIf {
                it.anonymousClass == null && it.type?.canonicalText == UNSUPPORTED_OPERATION_EXCEPTION
            } ?: return null
        val argumentList = newExpression.argumentList ?: return null
        val arguments = argumentList.expressions
        if (arguments.size > 1 || arguments.any { it.type?.equalsToText(CommonClassNames.JAVA_LANG_STRING) != true }) {
            return null
        }

        val todo = element.expr(
            text = "TODO",
            textRange = TextRange.create(element.textRange.startOffset, argumentList.textRange.startOffset),
        ) ?: return null
        val chain = listOfNotNull(
            todo,
            getAnyExpression(argumentList, document),
            element.lastChild
                ?.takeIf { it is PsiJavaToken && it.tokenType == JavaTokenType.SEMICOLON }
                ?.let { getAnyExpression(it, document) },
        )
        return WrapperExpression(element, chain = chain, nested = false)
    }

    // the assert fold already rewrites this throw into `assert … : reason`
    private fun isFoldedAsAssert(element: PsiThrowStatement): Boolean {
        val ifStatement = when (val parent = element.parent) {
            is PsiIfStatement -> parent
            is PsiCodeBlock -> parent.parent?.parent as? PsiIfStatement
            else -> null
        } ?: return false
        return IfExpression.isAssertExpression(ifStatement)
    }
}
