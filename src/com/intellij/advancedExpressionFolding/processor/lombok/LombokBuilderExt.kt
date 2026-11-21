package com.intellij.advancedExpressionFolding.processor.lombok

import com.intellij.advancedExpressionFolding.processor.asInstance
import com.intellij.advancedExpressionFolding.processor.isStatic
import com.intellij.psi.*

/**
 * Recognizes a hand-written builder with exactly the shape Lombok `@Builder` generates.
 * Anything extra or different (validation, defaults, an extra member, generics) is not a match.
 */
object LombokBuilderExt {

    /** @return the static `builder()` factory of [outer] when [builder] is an exact Lombok-shaped builder, else `null` */
    fun exactBuilderFactory(outer: PsiClass, builder: PsiClass): PsiMethod? {
        if (builder.containingClass != outer || !builder.isStatic() || builder.isInterface || builder.isEnum ||
            builder.isRecord || outer.hasTypeParameters() || builder.hasTypeParameters() ||
            builder.initializers.isNotEmpty() || builder.innerClasses.isNotEmpty()
        ) return null

        val outerFields = outer.fields.filterNot { it.isStatic() }
        val builderFields = builder.fields.toList()
        if (outerFields.size != builderFields.size || outerFields.any { it.hasInitializer() }) return null
        val fieldsMatch = outerFields.zip(builderFields).all { (outerField, builderField) ->
            outerField.name == builderField.name && outerField.type == builderField.type &&
                !builderField.isStatic() && !builderField.hasModifierProperty(PsiModifier.FINAL) &&
                !builderField.hasInitializer()
        }
        if (!fieldsMatch) return null

        if (!builder.constructors.all { it.isEmptyNoArgs() }) return null
        val methods = builder.methods.filterNot { it.isConstructor }
        if (methods.size != builderFields.size + 1) return null
        if (!builderFields.all { field -> methods.any { it.isBuilderSetter(field, builder) } }) return null
        if (methods.none { it.isBuildCall(outer, builderFields) }) return null

        return outer.findMethodsByName("builder", false).singleOrNull()?.takeIf { it.isBuilderFactory(builder) }
    }

    fun isExactBuilder(builder: PsiClass): Boolean {
        val outer = builder.containingClass ?: return false
        return builder.name?.endsWith("Builder") == true && exactBuilderFactory(outer, builder) != null
    }

    private fun PsiMethod.isEmptyNoArgs() = !hasParameters() && body?.isEmpty == true

    private fun PsiMethod.isBuilderSetter(field: PsiField, builder: PsiClass): Boolean {
        val param = parameterList.parameters.singleOrNull() ?: return false
        if (name != field.name || isStatic() || param.type != field.type || returnType.resolvesTo() != builder) {
            return false
        }
        val statements = body?.statements ?: return false
        if (statements.size != 2) return false
        val assignment = statements[0].asInstance<PsiExpressionStatement>()?.expression
            .asInstance<PsiAssignmentExpression>() ?: return false
        return assignment.operationTokenType == JavaTokenType.EQ &&
            assignment.lExpression.asInstance<PsiReferenceExpression>()
                ?.takeIf { it.qualifierExpression == null || it.qualifierExpression is PsiThisExpression }
                ?.resolve() == field &&
            assignment.rExpression.asInstance<PsiReferenceExpression>()?.resolve() == param &&
            statements[1].asInstance<PsiReturnStatement>()?.returnValue.asInstance<PsiThisExpression>()
                ?.qualifier == null
    }

    private fun PsiMethod.isBuildCall(outer: PsiClass, builderFields: List<PsiField>): Boolean {
        if (name != "build" || hasParameters() || isStatic() || returnType.resolvesTo() != outer) return false
        val newExpression = singleReturnValue().asInstance<PsiNewExpression>() ?: return false
        if (newExpression.anonymousClass != null || newExpression.classReference?.resolve() != outer) return false
        val arguments = newExpression.argumentList?.expressions ?: return false
        return arguments.size == builderFields.size && arguments.zip(builderFields).all { (argument, field) ->
            argument.asInstance<PsiReferenceExpression>()?.resolve() == field
        }
    }

    private fun PsiMethod.isBuilderFactory(builder: PsiClass): Boolean {
        if (!isStatic() || hasParameters() || returnType.resolvesTo() != builder) return false
        val newExpression = singleReturnValue().asInstance<PsiNewExpression>() ?: return false
        return newExpression.anonymousClass == null && newExpression.classReference?.resolve() == builder &&
            newExpression.argumentList?.isEmpty == true
    }

    private fun PsiMethod.singleReturnValue(): PsiExpression? =
        body?.statements?.singleOrNull().asInstance<PsiReturnStatement>()?.returnValue

    private fun PsiType?.resolvesTo(): PsiClass? = asInstance<PsiClassType>()?.resolve()
}
