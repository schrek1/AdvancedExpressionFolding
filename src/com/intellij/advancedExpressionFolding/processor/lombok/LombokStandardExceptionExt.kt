package com.intellij.advancedExpressionFolding.processor.lombok

import com.intellij.advancedExpressionFolding.processor.hasComments
import com.intellij.advancedExpressionFolding.processor.lombok.AnnotationExt.ClassLevelAnnotation
import com.intellij.advancedExpressionFolding.processor.lombok.LombokFoldingAnnotation.LOMBOK_STANDARD_EXCEPTION
import com.intellij.psi.CommonClassNames.JAVA_LANG_STRING
import com.intellij.psi.CommonClassNames.JAVA_LANG_THROWABLE
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiExpressionStatement
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiModifier
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.util.InheritanceUtil

object LombokStandardExceptionExt {

    private val STANDARD_SIGNATURES = setOf(
        emptyList(),
        listOf(JAVA_LANG_STRING),
        listOf(JAVA_LANG_THROWABLE),
        listOf(JAVA_LANG_STRING, JAVA_LANG_THROWABLE),
    )

    /**
     * Folds a `Throwable` subclass whose four standard constructors only pass their parameters to `super`;
     * Lombok's `@StandardException` would generate exactly these. Other constructors stay visible.
     */
    fun PsiClass.foldStandardException(): List<ClassLevelAnnotation> {
        if (!InheritanceUtil.isInheritor(this, JAVA_LANG_THROWABLE)) return emptyList()
        val standardConstructors = constructors.filter { it.isStandardExceptionConstructor() }
        if (standardConstructors.map { it.signature() }.toSet() != STANDARD_SIGNATURES) return emptyList()
        return listOf(ClassLevelAnnotation(LOMBOK_STANDARD_EXCEPTION, standardConstructors))
    }

    private fun PsiMethod.signature() = parameterList.parameters.map { it.type.canonicalText }

    private fun PsiMethod.isStandardExceptionConstructor(): Boolean {
        if (!hasModifierProperty(PsiModifier.PUBLIC) || signature() !in STANDARD_SIGNATURES) return false
        val body = body?.takeIf { !it.hasComments() } ?: return false
        val statements = body.statements
        if (statements.isEmpty()) return !hasParameters()
        val superCall = (statements.singleOrNull() as? PsiExpressionStatement)?.expression as? PsiMethodCallExpression
            ?: return false
        if (superCall.methodExpression.text != "super") return false
        val passedParameters = superCall.argumentList.expressions.map { (it as? PsiReferenceExpression)?.resolve() }
        return passedParameters == parameterList.parameters.toList()
    }
}
