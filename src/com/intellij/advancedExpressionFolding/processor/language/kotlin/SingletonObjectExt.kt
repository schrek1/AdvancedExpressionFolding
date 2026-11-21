package com.intellij.advancedExpressionFolding.processor.language.kotlin

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.processor.*
import com.intellij.advancedExpressionFolding.processor.core.BaseExtension
import com.intellij.psi.*

/**
 * Folds the eager singleton (`static final Self INSTANCE = new Self()`, a private empty no-arg constructor and a
 * static getter returning the instance) into a Kotlin-like `object Self`, hiding the field, constructor and getters.
 */
object SingletonObjectExt : BaseExtension() {

    fun createExpression(clazz: PsiClass): Expression? {
        if (clazz.hasModifierProperty(PsiModifier.ABSTRACT)) return null
        // interfaces, enums, records, annotations and anonymous classes have no `class` keyword
        val classKeyword = clazz.children.firstOrNull {
            (it as? PsiKeyword)?.tokenType == JavaTokenType.CLASS_KEYWORD
        } ?: return null

        val instanceField = clazz.fields.filter { it.isStatic() && it.singletonField }.singleOrNull() ?: return null
        if (!instanceField.isFinal() || !instanceField.isNewSelfInstance(clazz)) return null

        val constructor = clazz.constructors.singleOrNull() ?: return null
        if (!constructor.isPrivate() || constructor.hasParameters() || constructor.body?.statements?.isEmpty() != true) {
            return null
        }

        val getters = clazz.methods.filter { it.returnsInstance(clazz, instanceField) }
        if (getters.isEmpty()) return null

        // keeps Lombok's @NoArgsConstructor from also claiming the constructor this fold hides
        constructor.markIgnored()
        val group = group(clazz.qualifiedName ?: clazz.name.orEmpty())
        val hidden = listOf(instanceField, constructor) + getters
        val expressions = hidden.flatMap { member ->
            listOf(member.prevWhiteSpace().exprHide(group), member.exprHide(group))
        } + classKeyword.expr("object", group)
        return expressions.exprWrap(clazz)
    }

    private fun PsiField.isNewSelfInstance(clazz: PsiClass): Boolean {
        val newExpression = initializer as? PsiNewExpression ?: return false
        return newExpression.anonymousClass == null &&
            newExpression.argumentList?.isEmpty == true &&
            newExpression.classReference?.resolve() == clazz
    }

    private fun PsiMethod.returnsInstance(clazz: PsiClass, instanceField: PsiField): Boolean {
        if (!isStatic() || hasParameters() || returnType?.typeResolved != clazz) return false
        val returnStatement = body?.statements?.singleOrNull() as? PsiReturnStatement ?: return false
        val returned = returnStatement.returnValue as? PsiReferenceExpression ?: return false
        return returned.resolve() == instanceField
    }
}
