package com.intellij.advancedExpressionFolding.processor.language.kotlin

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.property.IGetter
import com.intellij.advancedExpressionFolding.expression.property.INameable
import com.intellij.advancedExpressionFolding.expression.semantic.WrapperExpression
import com.intellij.advancedExpressionFolding.expression.semantic.kotlin.IfNullSafeExpression
import com.intellij.advancedExpressionFolding.processor.asInstance
import com.intellij.advancedExpressionFolding.processor.core.BuildExpressionExt
import com.intellij.advancedExpressionFolding.processor.end
import com.intellij.advancedExpressionFolding.processor.start
import com.intellij.advancedExpressionFolding.processor.toTextRange
import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.advancedExpressionFolding.settings.state.IKotlinLanguageState
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.util.elementType


object IfNullSafeExt : IKotlinLanguageState by State()() {

    @JvmStatic
    fun createExpression(element: PsiPolyadicExpression, document: Document): Expression? {
        if (!ifNullSafe) {
            return null
        }
        if (element.operands.size < 2) {
            return null
        }

        val lists = cutToLists(element,document).takeIf {
            it.isNotEmpty()
        } ?: return null

        val untouched = (element.operands.toList() - lists.flatten().toSet()).mapNotNull {
            BuildExpressionExt.getAnyExpression(it, document)
        }

        return WrapperExpression(element, element.textRange, lists.filter {
            it.size > 1
        }.flatMap {
            buildChain(it, document, element)
        } + untouched)

    }

    private fun buildChain(
        it: LinkedHashSet<PsiElement>,
        document: Document,
        element: PsiPolyadicExpression
    ): Collection<Expression> {
        singleChain(it, document, element)?.let { return listOf(it) }
        return it.map {
            BuildExpressionExt.getAnyExpression(it, document)
        }
    }

    private fun cutToLists(
        element: PsiPolyadicExpression,
        document: Document?
    ): MutableList<LinkedHashSet<PsiElement>> {
        val lists = mutableListOf<LinkedHashSet<PsiElement>>()

        var currentList = LinkedHashSet<PsiElement>()
        element.operands.toList().zipWithNext { prev, curr ->
            val and = element.getTokenBeforeOperand(curr).elementType == JavaTokenType.ANDAND

            val firstNotNullWithAnd = and && prev.isEqualsNotNullBinaryExpression()
            if (firstNotNullWithAnd && (curr.isEqualsNotNullBinaryExpression())) {
                if (isNext((prev as PsiBinaryExpression).lOperand, (curr as PsiBinaryExpression).lOperand)) {
                    currentList += prev
                    currentList += curr
                } else {
                    if (currentList.isNotEmpty()) {
                        lists += currentList
                        currentList = LinkedHashSet()
                    }
                }
            } else {
                if (firstNotNullWithAnd) {
                    val lOperand = (prev as PsiBinaryExpression).lOperand
                    if (curr is PsiPrefixExpression) {
                        // !isActive
                        val isNegation = curr.operationTokenType == JavaTokenType.EXCL
                        val callExpression = curr.operand as? PsiMethodCallExpression
                        if (isNegation && callExpression != null && isNext(lOperand, callExpression)) {
                            if (callExpression.let { BuildExpressionExt.getAnyExpression(it, document) } is IGetter) {
                                currentList += prev
                                currentList += curr
                            }
                        }
                    } else if (BuildExpressionExt.getAnyExpression(curr, document) is IGetter) {
                        // isActive
                        if (isNext(lOperand, curr)) {
                            currentList += prev
                            currentList += curr
                        }
                    }
                }
                if (currentList.isNotEmpty()) {
                    lists += currentList
                    currentList = LinkedHashSet()
                }
            }
            null
        }
        if (currentList.isNotEmpty()) {
            lists += currentList
        }
        return lists
    }

    fun singleChain(
        currentList: LinkedHashSet<PsiElement>,
        document: Document,
        element: PsiPolyadicExpression
    ): Expression? {
        val elementList = currentList
            .map {
                if (it is PsiBinaryExpression) {
                    it.lOperand
                } else if (it is PsiPrefixExpression) {
                    it.operand!!
                } else {
                    it
                }
            }
        val names = elementList
            .map {
                BuildExpressionExt.getAnyExpression(it, document)
            }
            .map {
                if (it is INameable) {
                    it.name
                } else {
                    (it.element as? PsiMethodCallExpression)?.methodExpression?.referenceName ?: return null
                }
            }

        // The last operand already spells the whole chain, so only the joints fold: everything between one link
        // and the same receiver in the next operand becomes `?`, the dots, getters and ` != null` stay real.
        val folds = mutableListOf<Pair<TextRange, String>>()
        elementList.zipWithNext { previous, next ->
            val receiver = (next as? PsiMethodCallExpression)?.methodExpression?.qualifierExpression ?: return null
            // a line break before the next `.` would split the shown chain, so the joint takes it too
            var jointEnd = receiver.end()
            while (document.charsSequence[jointEnd].isWhitespace()) {
                jointEnd++
            }
            folds += (previous.end() to jointEnd).toTextRange() to "?"
        }
        val comparison = when (currentList.last()) {
            is PsiMethodCallExpression -> " == true"
            is PsiPrefixExpression -> " == false"
            else -> null
        }
        if (comparison != null) {
            // nothing follows the getter, so the comparison is shown in place of the `.isActive()` link
            val lastCall = elementList.last() as? PsiMethodCallExpression ?: return null
            val receiver = lastCall.methodExpression.qualifierExpression ?: return null
            folds += (receiver.end() to lastCall.end()).toTextRange() to ".${names.last()}$comparison"
        }

        val startOffset = currentList.first().start()
        val endOffset = currentList.last().end()

        return IfNullSafeExpression(element, (startOffset to endOffset).toTextRange(), folds)
    }


    private fun PsiMethodCallExpression.firstChildQualifierExpression(): PsiExpression? {
        return firstChild.asInstance<PsiReferenceExpression>()?.qualifierExpression
    }


    private val PsiExpression.operandReference: PsiElement?
        get() {
            if (this is PsiReferenceExpression) {
                return this.resolve()
            }
            if (this is PsiMethodCallExpression) {
                return this.firstChildQualifierExpression()
            }
            return null
        }

    private val PsiExpression.operandParentReference: PsiElement?
        get() {
            if (this is PsiMethodCallExpression) {
                val qualifierExpression = firstChildQualifierExpression()
                if (qualifierExpression is PsiReferenceExpression) {
                    return qualifierExpression.operandReference
                } else if (qualifierExpression is PsiMethodCallExpression) {
                    return qualifierExpression.operandReference
                }
            }
            return null
        }

    private fun isNext(
        prev: PsiExpression,
        curr: PsiExpression
    ): Boolean {
        val first = prev.operandReference.toString()
        val second = curr.operandParentReference.toString()
        return first == second
    }


    private fun PsiExpression?.isEqualsNotNullBinaryExpression(): Boolean {
        val psiBinaryExpression = this as? PsiBinaryExpression ?: return false
        (psiBinaryExpression.rOperand as? PsiLiteralExpression)
            ?.let {
                if (it.value == null) {
                    it
                } else {
                    null
                }
            } ?: return false
        val token = psiBinaryExpression.operationTokenType
        return token == JavaTokenType.NE
    }

}



