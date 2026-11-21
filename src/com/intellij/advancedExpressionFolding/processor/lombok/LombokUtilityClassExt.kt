package com.intellij.advancedExpressionFolding.processor.lombok

import com.intellij.advancedExpressionFolding.processor.isIgnored
import com.intellij.advancedExpressionFolding.processor.isStatic
import com.intellij.advancedExpressionFolding.processor.lombok.AnnotationExt.ClassLevelAnnotation
import com.intellij.advancedExpressionFolding.processor.lombok.LombokFoldingAnnotation.LOMBOK_UTILITY_CLASS
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil

object LombokUtilityClassExt {

    /**
     * A class with only a private no-arg constructor (empty or a single `throw`) and only static members.
     * A static field of the class's own type marks a singleton, which is left to the `object` folding.
     */
    fun PsiClass.foldUtilityClass(): ClassLevelAnnotation? {
        if (isInterface || isEnum || isRecord || isAnnotationType) return null
        if (extendsList?.referenceElements?.isNotEmpty() == true) return null
        val constructor = constructors.singleOrNull()?.takeIf { it.isPrivateBlockingConstructor() } ?: return null

        val members: List<PsiModifierListOwner> = fields.toList() + methods.filterNot { it.isConstructor } + innerClasses
        if (fields.isEmpty() && members.none { it is PsiMethod }) return null
        if (members.any { !it.isStatic() }) return null
        if (fields.any { (it.type as? PsiClassType)?.resolve() == this }) return null

        val hiddenStatics = members
            .filterNot { it.isIgnored() }
            .mapNotNull { it.keyword(PsiModifier.STATIC) }
        val hiddenFinal = keyword(PsiModifier.FINAL)?.takeIf { it != modifierList?.firstKeyword() }

        val keywords = hiddenStatics + listOfNotNull(hiddenFinal)
        return ClassLevelAnnotation(LOMBOK_UTILITY_CLASS, listOf(constructor) + keywords + keywords.mapNotNull { it.nextWhiteSpaceLeaf() })
    }

    private fun PsiMethod.isPrivateBlockingConstructor(): Boolean {
        if (!hasModifierProperty(PsiModifier.PRIVATE) || hasParameters()) return false
        val statements = body?.statements ?: return false
        return statements.isEmpty() || statements.singleOrNull() is PsiThrowStatement
    }

    // An implicit modifier (e.g. `static` of a nested enum) has no keyword, so nothing is hidden for it.
    private fun PsiModifierListOwner.keyword(text: String): PsiKeyword? =
        modifierList?.children?.firstOrNull { it is PsiKeyword && it.text == text } as? PsiKeyword

    // The class-level annotation placeholder is folded onto the first keyword, so that keyword stays visible.
    private fun PsiModifierList.firstKeyword(): PsiElement? = children.firstOrNull { it is PsiKeyword }

    // A method's empty type-parameter list sits between its modifiers and the whitespace.
    private fun PsiElement.nextWhiteSpaceLeaf(): PsiWhiteSpace? =
        generateSequence(PsiTreeUtil.nextLeaf(this)) { PsiTreeUtil.nextLeaf(it) }
            .firstOrNull { it.textLength > 0 } as? PsiWhiteSpace
}
