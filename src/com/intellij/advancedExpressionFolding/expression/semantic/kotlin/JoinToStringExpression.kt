package com.intellij.advancedExpressionFolding.expression.semantic.kotlin

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.semantic.AbstractSingleChildExpression
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

class JoinToStringExpression(
    element: PsiElement,
    textRange: TextRange,
    text: String,
    child: Expression?,
) : AbstractSingleChildExpression(element, textRange, text, child)
