package com.intellij.advancedExpressionFolding.processor.language.kotlin

import com.intellij.advancedExpressionFolding.expression.semantic.kotlin.LazyPropertyExpression
import com.intellij.advancedExpressionFolding.processor.isFinal
import com.intellij.advancedExpressionFolding.processor.isPrivate
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.PsiUtil

/**
 * Folds a private field and the null-checked lazy getter right below it into `val field by lazy { init }`:
 * ```
 * private Config config;
 * Config config() { if (config == null) { config = load(); } return config; }
 * ```
 */
object LazyPropertyExt {

    fun createExpression(field: PsiField): LazyPropertyExpression? {
        if (!field.isPrivate() || field.isFinal() || field.hasInitializer()) return null
        val typeElement = field.typeElement?.takeIf { it.parent == field && field.text.endsWith(";") } ?: return null
        val clazz = field.containingClass ?: return null
        // the fold keeps the initializer as real text, so it spans field..getter and needs nothing in between
        val getter = PsiTreeUtil.skipWhitespacesForward(field) as? PsiMethod ?: return null
        val initializer = getter.lazyInitializer(field) ?: return null
        // a reset or a setter elsewhere would make `by lazy` lie about the field
        if (field.isAssignedOutside(clazz, getter)) return null
        return LazyPropertyExpression(field, typeElement, field.nameIdentifier, getter, initializer)
    }

    private fun PsiMethod.lazyInitializer(field: PsiField): PsiExpression? {
        if (!parameterList.isEmpty) return null
        val statements = body?.statements?.takeIf { it.size == 2 } ?: return null
        val ifStatement = statements[0] as? PsiIfStatement ?: return null
        val returnStatement = statements[1] as? PsiReturnStatement ?: return null
        if (ifStatement.elseBranch != null || !ifStatement.condition.isNullCheckOf(field)) return null
        if (!returnStatement.returnValue.isReferenceTo(field)) return null
        val assignment = (ifStatement.thenBranch.singleStatement() as? PsiExpressionStatement)
            ?.expression as? PsiAssignmentExpression ?: return null
        if (assignment.operationTokenType != JavaTokenType.EQ || !assignment.lExpression.isReferenceTo(field)) return null
        return assignment.rExpression
    }

    private fun PsiStatement?.singleStatement(): PsiStatement? = when (this) {
        is PsiBlockStatement -> codeBlock.statements.singleOrNull()
        else -> this
    }

    private fun PsiExpression?.isNullCheckOf(field: PsiField): Boolean {
        val binary = PsiUtil.skipParenthesizedExprDown(this) as? PsiBinaryExpression ?: return false
        if (binary.operationTokenType != JavaTokenType.EQEQ) return false
        val left = binary.lOperand
        val right = binary.rOperand
        return (left.isReferenceTo(field) && right.isNullLiteral()) || (left.isNullLiteral() && right.isReferenceTo(field))
    }

    private fun PsiExpression?.isNullLiteral() = (this as? PsiLiteralExpression)?.text == "null"

    // a no-arg two-statement body declares no locals, so the name alone identifies the field
    private fun PsiExpression?.isReferenceTo(field: PsiField): Boolean {
        val reference = PsiUtil.skipParenthesizedExprDown(this) as? PsiReferenceExpression ?: return false
        val qualifier = reference.qualifierExpression
        return reference.referenceName == field.name && (qualifier == null || qualifier is PsiThisExpression)
    }

    private fun PsiField.isAssignedOutside(clazz: PsiClass, getter: PsiMethod): Boolean =
        PsiTreeUtil.findChildrenOfType(clazz, PsiAssignmentExpression::class.java).any { assignment ->
            val target = assignment.lExpression as? PsiReferenceExpression
            target?.referenceName == name &&
                !PsiTreeUtil.isAncestor(getter, assignment, true) &&
                target.resolve() == this
        }
}
