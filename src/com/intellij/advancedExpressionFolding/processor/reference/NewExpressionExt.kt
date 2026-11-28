package com.intellij.advancedExpressionFolding.processor.reference

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.literal.ArrayLiteral
import com.intellij.advancedExpressionFolding.expression.literal.ListLiteral
import com.intellij.advancedExpressionFolding.expression.literal.MapLiteral
import com.intellij.advancedExpressionFolding.expression.literal.NumberLiteral
import com.intellij.advancedExpressionFolding.expression.literal.SetLiteral
import com.intellij.advancedExpressionFolding.expression.literal.StringLiteral
import com.intellij.advancedExpressionFolding.processor.core.BuildExpressionExt
import com.intellij.advancedExpressionFolding.processor.argumentExpressions
import com.intellij.advancedExpressionFolding.processor.argumentCount
import com.intellij.advancedExpressionFolding.processor.typeResolved
import com.intellij.advancedExpressionFolding.processor.expression.LiteralExpressionExt
import com.intellij.advancedExpressionFolding.processor.methodcall.MethodCallExpressionExt
import com.intellij.advancedExpressionFolding.processor.util.Consts
import com.intellij.advancedExpressionFolding.processor.util.Helper
import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.advancedExpressionFolding.settings.state.IExpressionCollapseState
import com.intellij.advancedExpressionFolding.settings.state.IKotlinLanguageState
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiAnonymousClass
import com.intellij.psi.PsiArrayInitializerExpression
import com.intellij.psi.PsiArrayType
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiExpressionList
import com.intellij.psi.PsiExpressionStatement
import com.intellij.psi.PsiField
import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiNewExpression
import com.intellij.psi.PsiPrimitiveType
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.PsiStatement
import java.util.ArrayList
import java.math.BigDecimal
import java.math.BigInteger

object NewExpressionExt :
    IExpressionCollapseState by State()(),
    IKotlinLanguageState by State()() {

    fun getNewExpression(element: PsiNewExpression, document: Document): Expression? {
        val type = element.type
        val erasedType = type?.canonicalText?.let { Helper.eraseGenerics(it) }
        if (type != null && Consts.SUPPORTED_CLASSES.contains(erasedType)) {
            val argumentList = element.argumentList
            handleConstructorArguments(element, document, erasedType, argumentList)?.let { return it }
        }
        val arrayInitializer = element.arrayInitializer
        if (type is PsiArrayType && arrayInitializer != null && getExpressionsCollapse) {
            return createArrayLiteral(element, document, type, arrayInitializer)
        }
        val anonymousClass = element.anonymousClass
        if (type != null && anonymousClass != null && anonymousClass.lBrace != null && anonymousClass.rBrace != null) {
            handleAnonymousClass(element, document, erasedType, anonymousClass)?.let { return it }
        }
        return null
    }

    private fun handleConstructorArguments(
        element: PsiNewExpression,
        document: Document,
        erasedType: String?,
        argumentList: PsiExpressionList?
    ): Expression? {
        val expressions = argumentList?.expressions ?: return null
        return when (expressions.size) {
            1 -> handleSingleArgument(element, document, erasedType, expressions[0])
            0 -> handleEmptyConstructor(element, erasedType)
            else -> null
        }
    }

    private fun handleSingleArgument(
        element: PsiNewExpression,
        document: Document,
        erasedType: String?,
        arg: PsiExpression
    ): Expression? {
        return when (arg) {
            is PsiLiteralExpression -> getConstructorExpression(element, arg, erasedType)
            is PsiReferenceExpression -> ReferenceExpressionExt.getReferenceExpression(arg, true)
            is PsiMethodCallExpression -> {
                if (erasedType == "java.util.ArrayList") {
                    val methodCallExpression = MethodCallExpressionExt.getMethodCallExpression(arg, document)
                    if (methodCallExpression is ListLiteral && getExpressionsCollapse) {
                        ListLiteral(element, element.textRange, methodCallExpression.items, methodCallExpression.spread)
                    } else {
                        null
                    }
                } else {
                    null
                }
            }
            else -> null
        }
    }

    private fun handleEmptyConstructor(
        element: PsiNewExpression,
        erasedType: String?
    ): Expression? {
        return when (erasedType) {
            "java.lang.String", "java.lang.StringBuilder" -> StringLiteral(element, element.textRange, "")
            "java.util.ArrayList" -> if (getExpressionsCollapse || emptyCollectionLiteralApplies(element)) {
                ListLiteral(element, element.textRange, emptyList())
            } else {
                null
            }
            "java.util.HashSet" -> if (emptyCollectionLiteralApplies(element)) {
                ListLiteral(element, element.textRange, emptyList())
            } else {
                null
            }
            "java.util.HashMap" -> if (emptyCollectionLiteralApplies(element)) {
                MapLiteral(element, element.textRange, emptyList())
            } else {
                null
            }
            else -> null
        }
    }

    // a double-brace subclass carries content, so it is not an empty collection
    private fun emptyCollectionLiteralApplies(element: PsiNewExpression) =
        mapAndEmptyCollectionLiterals && element.anonymousClass == null && !isConstructorReferenceField(element)

    // constructorReferenceNotation already shows `HashMap<K, V> field = new HashMap<>()` as `::new`
    private fun isConstructorReferenceField(element: PsiNewExpression): Boolean {
        val field = element.parent as? PsiField ?: return false
        return constructorReferenceNotation && element.classReference?.resolve() == field.typeResolved
    }

    // a nested `{...}` of a multi-dimensional array has no Kotlin form, so those stay Java
    private fun createArrayLiteral(
        element: PsiNewExpression,
        document: Document,
        type: PsiArrayType,
        arrayInitializer: PsiArrayInitializerExpression
    ): Expression? {
        val factory = when (val component = type.componentType) {
            is PsiArrayType -> return null
            is PsiPrimitiveType -> "${component.name}ArrayOf"
            else -> "arrayOf"
        }
        val items = arrayInitializer.initializers.map { BuildExpressionExt.getAnyExpression(it, document) }
        return ArrayLiteral(element, element.textRange, items, factory)
    }

    private fun handleAnonymousClass(
        element: PsiNewExpression,
        document: Document,
        erasedType: String?,
        anonymousClass: PsiAnonymousClass
    ): Expression? {
        if (erasedType != "java.util.HashSet" || anonymousClass.initializers.size != 1) {
            return null
        }
        val initializer = anonymousClass.initializers[0]
        val statements = initializer.body.statements
        if (statements.isEmpty()) {
            return null
        }
        val arguments = collectHashSetArguments(statements) ?: return null
        if (!getExpressionsCollapse) {
            return null
        }
        val items = arguments.map { BuildExpressionExt.getAnyExpression(it, document) }
        return SetLiteral(
            element,
            element.textRange,
            TextRange.create(anonymousClass.lBrace!!.textRange.startOffset, anonymousClass.rBrace!!.textRange.endOffset),
            initializer.textRange,
            items
        )
    }

    private fun collectHashSetArguments(statements: Array<PsiStatement>): MutableList<PsiElement>? {
        val arguments = ArrayList<PsiElement>()
        for (statement in statements) {
            if (statement is PsiExpressionStatement && statement.expression is PsiMethodCallExpression) {
                val methodCall = statement.expression as PsiMethodCallExpression
                if (methodCall.methodExpression.text == "add" && methodCall.argumentCount == 1) {
                    val method = methodCall.methodExpression.resolve() as? PsiMethod
                    if (method != null && method.containingClass?.qualifiedName == "java.util.HashSet") {
                        arguments.add(methodCall.argumentExpressions[0])
                        continue
                    }
                }
            }
            return null
        }
        return arguments
    }

    fun getConstructorExpression(parent: PsiElement, argument: PsiLiteralExpression, classQualifiedNameNoGenerics: String?): Expression? {
        // only constructors that wrap their argument read as that literal; `ArrayList(10)` is a capacity
        val isStringBuilderCapacity = classQualifiedNameNoGenerics == "java.lang.StringBuilder" && argument.value !is String
        if (classQualifiedNameNoGenerics !in VALUE_WRAPPING_TYPES || isStringBuilderCapacity) {
            return null
        }
        val literalExpression = LiteralExpressionExt.getLiteralExpression(argument)
        if (literalExpression is NumberLiteral) {
            return NumberLiteral(parent, parent.textRange, literalExpression.textRange, literalExpression.number, false)
        }
        val valueText = argument.text
        val unquoted = if (valueText.startsWith('"') && valueText.endsWith('"')) {
            valueText.substring(1, valueText.length - 1)
        } else {
            valueText
        }
        return when (classQualifiedNameNoGenerics) {
            "java.lang.Long" -> NumberLiteral(parent, parent.textRange, argument.textRange, unquoted.toLong(), argument.value !is Number)
            "java.lang.Integer" -> NumberLiteral(parent, parent.textRange, argument.textRange, unquoted.toInt(), argument.value !is Number)
            "java.lang.Float" -> NumberLiteral(parent, parent.textRange, argument.textRange, unquoted.toFloat(), argument.value !is Number)
            "java.lang.Double" -> NumberLiteral(parent, parent.textRange, argument.textRange, unquoted.toDouble(), argument.value !is Number)
            "java.math.BigDecimal" -> NumberLiteral(parent, parent.textRange, argument.textRange, BigDecimal(unquoted), argument.value !is Number)
            "java.math.BigInteger" -> NumberLiteral(parent, parent.textRange, argument.textRange, BigInteger(unquoted), argument.value !is Number)
            "java.lang.StringBuilder", "java.lang.String" -> StringLiteral(parent, parent.textRange, unquoted)
            else -> null
        }
    }

    private val VALUE_WRAPPING_TYPES = setOf(
        "java.lang.Long",
        "java.lang.Integer",
        "java.lang.Float",
        "java.lang.Double",
        "java.lang.Short",
        "java.lang.Byte",
        "java.math.BigDecimal",
        "java.math.BigInteger",
        "java.lang.StringBuilder",
        "java.lang.String",
    )
}
