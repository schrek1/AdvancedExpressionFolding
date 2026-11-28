package com.intellij.advancedExpressionFolding.processor.language.kotlin

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.controlflow.ElvisExpression
import com.intellij.advancedExpressionFolding.expression.controlflow.IfElseConditionalExpression
import com.intellij.advancedExpressionFolding.expression.literal.InterpolatedString
import com.intellij.advancedExpressionFolding.processor.*
import com.intellij.advancedExpressionFolding.processor.core.BaseExtension
import com.intellij.advancedExpressionFolding.processor.controlflow.IfExt
import com.intellij.advancedExpressionFolding.processor.core.getAnyExpression
import com.intellij.openapi.editor.Document
import com.intellij.psi.*

object SingleExpressionFunctionExt : BaseExtension() {
    private const val MAX_BODY_LENGTH = 145

    /** Whether [statement] is the whole body of a method shown as a single-expression function. */
    fun foldsReturnOf(statement: PsiReturnStatement): Boolean {
        val body = statement.parent as? PsiCodeBlock ?: return false
        return expressionFunc && body.parent is PsiMethod && body.statements.singleOrNull() == statement &&
            body.text.length < MAX_BODY_LENGTH && !isDebugSessionActive(statement)
    }

    fun createSingleExpressionFunctions(method: PsiMethod, document: Document): Expression? {
        if (isDebugSessionActive(method)) {
            return null
        }
        val single = method.body?.statements?.singleOrNull()
        val statement = single?.takeIf {
            method.body!!.text.length < MAX_BODY_LENGTH
        } ?: return null

        val singleExpression = getAnyExpression(single.children.first(), document)

        return statement.asInstance<PsiReturnStatement>()?.let {
            onSingleExpressionReturn(it, method, singleExpression, document)
        } ?: onSingleExpressionNoReturn(statement, method, singleExpression)
    }

    private fun onSingleExpressionNoReturn(
        statement: PsiStatement,
        method: PsiMethod,
        singleExpression: Expression
    ): Expression? {
        val body = method.body!!
        val whitespaces = body.children.filter {
            it.isWhitespace()
        }
        val endingSemicolon = statement.children.lastOrNull().asInstance<PsiJavaToken>()//;
        val exprList = arrayOf<PsiElement?>(endingSemicolon).mapNotNull {
            it.exprHide()
        }.toMutableList<Expression?>()
        whitespaces.forEach {
            exprList.add(it.expr(if (expressionFuncEquals && it == whitespaces.last()) "" else " "))
        }
        exprList.addEqualsInsteadOfBraces(body)

        exprList.add(getAnyExpression(body))
        exprList.add(singleExpression)
        return exprList.exprWrap(method, group())
    }

    private fun onSingleExpressionReturn(
        statement: PsiReturnStatement,
        method: PsiMethod,
        singleExpression: Expression,
        document: Document,
    ): Expression? {
        val keyword = statement.children.firstOrNull().asInstance<PsiKeyword>()
        val keywordNextWhitespace = keyword?.nextWhiteSpace()
        val returnPrevSpace = statement.prevWhiteSpace()

        val preBracketSpace = statement.nextSibling
        val endingSemicolon = statement.children.lastOrNull().asInstance<PsiJavaToken>()//;

        val exprList = mutableListOf<Expression?>(keyword.exprHide())
        // a returned ternary folded as if-else or inverted Elvis leaves ` if (` / ` ?: y` to the blank and `;` this
        // group owns
        val returnedTernary = statement.returnValue.asInstance<PsiConditionalExpression>()
            ?.let { IfExt.getConditionalExpression(it, document) }
        exprList.add(
            if (returnedTernary is IfElseConditionalExpression) {
                keywordNextWhitespace?.expr("if (")
            } else {
                keywordNextWhitespace.exprHide()
            }
        )
        val semicolonText = (returnedTernary as? ElvisExpression)?.tailBehindValue(document)
            ?: closingQuoteBehindValue(statement.returnValue, document)
        exprList.add(preBracketSpace.expr(if (expressionFuncEquals) "" else " "))
        method.body?.let { exprList.addEqualsInsteadOfBraces(it) }
        exprList.add(returnPrevSpace?.expr(" "))
        exprList.add(if (semicolonText != null) endingSemicolon?.expr(semicolonText) else endingSemicolon.exprHide())
        exprList.add(singleExpression)

        statement.returnValue.asLiteral()?.let {
            method.foldForLiteral(exprList)
        }
        return exprList.exprWrap(method, group())
    }

    /** The `;` hidden here is the character a template at the end of the value takes for its closing quote. */
    private fun closingQuoteBehindValue(value: PsiExpression?, document: Document): String? {
        var last = value
        while (last is PsiConditionalExpression) {
            last = last.elseExpression
        }
        return last?.let { getAnyExpression(it, document) }.asInstance<InterpolatedString>()
            ?.closingForHiddenSemicolon(document)
    }

    private fun MutableList<Expression?>.addEqualsInsteadOfBraces(body: PsiCodeBlock) {
        if (expressionFuncEquals) {
            add(body.lBrace?.expr("="))
            add(body.rBrace?.exprHide())
        }
    }

    private fun PsiMethod.foldForLiteral(
        exprList: MutableList<Expression?>
    ) {
        if (modifier().isPublic() && !isStatic()) {
            foldKeywords(exprList)

            exprList.add(returnTypeElement.prevWhiteSpace().exprHide())
            exprList.add(returnTypeElement.exprHide())
            exprList.add(returnTypeElement.nextWhiteSpace().exprHide())
            if (parameterList.parameters.isEmpty()) {
                exprList.add(parameterList.exprHide())
                exprList.add(parameterList.nextWhiteSpace().exprHide())
            }
        }
    }

    private fun PsiMethod.foldKeywords(exprList: MutableList<Expression?>) {
        keywords.takeIfSizeNot(0)?.let {
            exprList.add(it.exprHide())
        }
    }

}
