package com.intellij.advancedExpressionFolding.processor.declaration

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.processor.core.BuildExpressionExt
import com.intellij.advancedExpressionFolding.processor.util.Helper
import com.intellij.advancedExpressionFolding.processor.util.PropertyUtil.guessPropertyName
import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.advancedExpressionFolding.settings.state.IExpressionCollapseState
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiDeclarationStatement
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiExpressionStatement
import com.intellij.psi.PsiLocalVariable
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiNewExpression
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.PsiStatement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil

/**
 * Folds `var p = new Person();` followed by setters on `p` into `val p = Person(name = "Alice", age = 31);`,
 * one argument per line at the setters' own indentation.
 *
 * The run is the setter statements right after the declaration: `p.setX(value);` with one argument that does
 * not read `p` and sets a property not set earlier in the run. Any other statement or a comment ends it.
 */
object SetterNamedArgumentsExt : IExpressionCollapseState by State()() {

    fun createExpression(declaration: PsiDeclarationStatement, document: Document): Expression? {
        if (!setterNamedArguments) {
            return null
        }
        val variable = declaration.newObjectVariable() ?: return null
        val newExpression = variable.initializer as PsiNewExpression
        val classReference = newExpression.classReference ?: return null
        val setters = setterRun(declaration, variable, document).ifEmpty { return null }
        val folds = mutableListOf(
            TextRange(newExpression.textRange.startOffset, classReference.textRange.startOffset) to "",
        )
        val open = TextRange(classReference.textRange.endOffset, declaration.textRange.endOffset)
        setters.forEachIndexed { index, setter ->
            val value = setter.argumentList.expressions.single().textRange
            val nameRange = setter.methodExpression.referenceNameElement!!.textRange
            val (hiddenEnd, shownName) = propertyNameFold(
                setter.methodExpression.referenceName!!,
                guessPropertyName(setter.methodExpression.referenceName!!),
                nameRange
            )
            val receiverAndSet = TextRange(setter.parent.textRange.startOffset, hiddenEnd)
            if (setters.size == 1) {
                folds += open.union(receiverAndSet) to "($shownName"
            } else {
                if (index == 0) folds += open to "("
                folds += receiverAndSet to shownName
            }
            folds += TextRange(nameRange.endOffset, value.startOffset) to " = "
            // the last setter keeps its real `);`, which closes the folded call
            if (index < setters.lastIndex) {
                folds += TextRange(value.endOffset, setter.parent.textRange.endOffset) to ","
            }
        }
        return NewObjectNamedArguments(declaration, folds)
    }

    /**
     * Where the hidden `set` part of a setter name ends and what replaces it, so the rest of the name stays real
     * text: `setCustomer` hides `setC` behind `c`, `setURL` hides only `set`.
     */
    fun propertyNameFold(setterName: String, propertyName: String, nameRange: TextRange): Pair<Int, String> {
        val afterSet = setterName.removePrefix("set")
        return when {
            afterSet.isEmpty() || afterSet == setterName -> nameRange.endOffset to propertyName
            propertyName == afterSet -> nameRange.startOffset + 3 to ""
            propertyName.drop(1) == afterSet.drop(1) -> nameRange.startOffset + 4 to propertyName.take(1)
            else -> nameRange.endOffset to propertyName
        }
    }

    /** Setters on a new object shown as named arguments; only the changed tokens fold, all in one group. */
    private class NewObjectNamedArguments(
        declaration: PsiDeclarationStatement,
        private val folds: List<Pair<TextRange, String>>,
    ) : Expression(declaration, declaration.textRange) {

        override fun supportsFoldRegions(document: Document, parent: Expression?): Boolean = true

        // the argument values keep their own foldings through the normal traversal
        override fun isNested(): Boolean = true

        override fun buildFoldRegions(
            element: PsiElement,
            document: Document,
            parent: Expression?
        ): Array<FoldingDescriptor> {
            val group = FoldingGroup.newGroup(NewObjectNamedArguments::class.java.name)
            return folds.map { (range, text) -> FoldingDescriptor(element.node, range, group, text) }.toTypedArray()
        }
    }

    /** Whether `call` is a setter folded into a named argument, so it must not get its own setter folding. */
    fun isInSetterRun(call: PsiMethodCallExpression, document: Document): Boolean {
        if (!setterNamedArguments || !Helper.isSetter(call.methodExpression.referenceName ?: return false)) {
            return false
        }
        val qualifier = call.methodExpression.qualifierExpression as? PsiReferenceExpression ?: return false
        val variable = qualifier.resolve() as? PsiLocalVariable ?: return false
        val declaration = variable.parent as? PsiDeclarationStatement ?: return false
        return declaration.newObjectVariable() == variable &&
            setterRun(declaration, variable, document).contains(call)
    }

    private fun setterRun(
        declaration: PsiDeclarationStatement,
        variable: PsiLocalVariable,
        document: Document
    ): List<PsiMethodCallExpression> {
        val propertyNames = mutableSetOf<String>()
        // a property set twice would be a duplicate named argument, so its second setter ends the run
        return generateSequence(declaration.nextStatement()) { it.nextStatement() }
            .map { it.setterCallOn(variable, document) }
            .takeWhile { it != null && propertyNames.add(it.methodExpression.referenceName!!) }
            .filterNotNull()
            .toList()
    }

    private fun PsiDeclarationStatement.newObjectVariable(): PsiLocalVariable? {
        val variable = declaredElements.singleOrNull() as? PsiLocalVariable ?: return null
        val newExpression = variable.initializer as? PsiNewExpression ?: return null
        return variable.takeIf { newExpression.argumentList?.isEmpty == true && newExpression.anonymousClass == null }
    }

    private fun PsiStatement.setterCallOn(variable: PsiLocalVariable, document: Document): PsiMethodCallExpression? {
        val call = (this as? PsiExpressionStatement)?.expression as? PsiMethodCallExpression ?: return null
        val qualifier = call.methodExpression.qualifierExpression as? PsiReferenceExpression ?: return null
        val value = call.argumentList.expressions.singleOrNull() ?: return null
        val isSetter = Helper.isSetter(call.methodExpression.referenceName ?: return null) &&
            qualifier.qualifierExpression == null &&
            qualifier.isReferenceTo(variable) &&
            call.resolveMethod()?.parameterList?.parametersCount == 1 &&
            PsiTreeUtil.processElements(value) { !(it is PsiReferenceExpression && it.isReferenceTo(variable)) } &&
            // shortcut: a concatenation folded into "…$x" also folds the call's parentheses, so it ends the run
            !BuildExpressionExt.getAnyExpression(value, document).isOverflow()
        return call.takeIf { isSetter }
    }

    private fun PsiStatement.nextStatement(): PsiStatement? {
        var sibling = nextSibling
        while (sibling is PsiWhiteSpace) {
            sibling = sibling.nextSibling
        }
        return sibling as? PsiStatement
    }
}
