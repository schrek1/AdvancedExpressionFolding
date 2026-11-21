package com.intellij.advancedExpressionFolding.processor.lombok

import com.intellij.advancedExpressionFolding.processor.guessPropertyName
import com.intellij.advancedExpressionFolding.processor.isVoid
import com.intellij.advancedExpressionFolding.processor.lombok.LombokFieldExt.getMethodsOfType
import com.intellij.advancedExpressionFolding.processor.lombok.LombokFoldingAnnotation.LOMBOK_DELEGATE
import com.intellij.advancedExpressionFolding.processor.lombok.MethodType.*
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil

object LombokDelegateExt {

    /**
     * Folds public methods that only forward their parameters, unchanged and in order, to a same-named method of one
     * field into `@Delegate` on that field.
     */
    fun foldDelegate(
        methodTypeToMethodsMap: Map<MethodType, List<PsiMethod>>,
        fieldsMap: Map<String, PsiField>,
        fieldLevelAnnotations: MutableList<FieldLevelAnnotation>
    ) {
        // `isEmpty()` or `setSize(int)` look like accessors, but without a matching field no other fold takes them
        val accessorsWithoutField = listOf(GETTER, SETTER, WITH).flatMap { methodType ->
            methodTypeToMethodsMap.getMethodsOfType(methodType).filter { fieldsMap[it.guessPropertyName()] == null }
        }
        (methodTypeToMethodsMap.getMethodsOfType(OTHER) + accessorsWithoutField).mapNotNull { method ->
            method.forwardedField(fieldsMap)?.let { field -> field to method }
        }.groupBy({ it.first }, { it.second })
            .forEach { (field, forwardingMethods) ->
                fieldLevelAnnotations += FieldLevelAnnotation(LOMBOK_DELEGATE, field, forwardingMethods)
            }
    }

    private fun PsiMethod.forwardedField(fieldsMap: Map<String, PsiField>): PsiField? {
        if (!hasModifierProperty(PsiModifier.PUBLIC) || isConstructor) return null
        val body = body ?: return null
        val statement = body.statements.singleOrNull() ?: return null
        if (PsiTreeUtil.findChildOfType(body, PsiComment::class.java) != null) return null

        val call = when (statement) {
            is PsiReturnStatement -> statement.returnValue.takeIf { !returnType.isVoid() }
            is PsiExpressionStatement -> statement.expression.takeIf { returnType.isVoid() }
            else -> null
        } as? PsiMethodCallExpression ?: return null

        if (call.methodExpression.referenceName != name) return null
        val qualifier = call.methodExpression.qualifierExpression as? PsiReferenceExpression ?: return null
        val receiverQualifier = qualifier.qualifierExpression
        if (receiverQualifier != null && receiverQualifier !is PsiThisExpression) return null
        val field = qualifier.resolve() as? PsiField ?: return null
        if (fieldsMap[field.name] != field) return null

        val parameters = parameterList.parameters
        val arguments = call.argumentList.expressions
        if (arguments.size != parameters.size) return null
        val forwardsParametersInOrder = arguments.indices.all { index ->
            val argument = arguments[index] as? PsiReferenceExpression
            argument?.qualifierExpression == null && argument?.resolve() == parameters[index]
        }
        return field.takeIf { forwardsParametersInOrder }
    }
}
