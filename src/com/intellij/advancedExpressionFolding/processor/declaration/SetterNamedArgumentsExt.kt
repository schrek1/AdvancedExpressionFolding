package com.intellij.advancedExpressionFolding.processor.declaration

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.semantic.NamedArgumentsCall
import com.intellij.advancedExpressionFolding.inlay.VirtualLines
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
 * one argument per line one level deeper and `)` on a line of its own.
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
        val multiLine = isMultiLine(setters, document, open.startOffset)
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
            val suffix = TextRange(value.endOffset, setter.parent.textRange.endOffset)
            // a one-line run keeps the last setter's real `);` to close the call, a multi-line one a virtual `)` line
            if (index < setters.lastIndex) {
                folds += suffix to ","
            } else if (multiLine) {
                folds += suffix to ""
            }
        }
        return NewObjectNamedArguments(declaration, folds, multiLine)
    }

    /**
     * Where the hidden accessor prefix of a method name ends and what replaces it, so the rest of the name stays real
     * text: `setCustomer` hides `setC` behind `c`, `setURL` hides only `set`; the same for `get` and `is`.
     */
    fun propertyNameFold(
        methodName: String,
        propertyName: String,
        nameRange: TextRange,
        prefix: String = "set"
    ): Pair<Int, String> {
        val afterPrefix = methodName.removePrefix(prefix)
        return when {
            afterPrefix.isEmpty() || afterPrefix == methodName -> nameRange.endOffset to propertyName
            propertyName == afterPrefix -> nameRange.startOffset + prefix.length to ""
            propertyName.drop(1) == afterPrefix.drop(1) ->
                nameRange.startOffset + prefix.length + 1 to propertyName.take(1)
            else -> nameRange.endOffset to propertyName
        }
    }

    /**
     * Setters on a new object shown as named arguments; only the changed tokens fold, all in one group. Over several
     * lines the arguments get a virtual indent and a virtual `)` line closes the call.
     */
    private class NewObjectNamedArguments(
        declaration: PsiDeclarationStatement,
        private val folds: List<Pair<TextRange, String>>,
        private val multiLine: Boolean,
    ) : Expression(declaration, declaration.textRange) {

        override fun supportsFoldRegions(document: Document, parent: Expression?): Boolean = true

        // the argument values keep their own foldings through the normal traversal
        override fun isNested(): Boolean = true

        override fun buildFoldRegions(
            element: PsiElement,
            document: Document,
            parent: Expression?
        ): Array<FoldingDescriptor> {
            val name = NewObjectNamedArguments::class.java.name
            val group = if (multiLine) {
                VirtualLines.group(name, above = null, below = NamedArgumentsCall.CLOSE_LINE, indent = VirtualLines.Indent.AFTER_FIRST_LINE)
            } else {
                FoldingGroup.newGroup(name)
            }
            return folds.map { (range, text) -> FoldingDescriptor(element.node, range, group, text) }.toTypedArray()
        }
    }

    /** Whether `call` is a setter folded into a named argument, so it must not get its own setter folding. */
    fun isInSetterRun(call: PsiMethodCallExpression, document: Document): Boolean =
        setterRunOf(call, document)?.contains(call) == true

    /**
     * Whether [element] sits in a setter statement of a run folded over several lines, where every line after the
     * first gets a virtual extra indent that a re-indented nested call has to account for.
     */
    fun isInMultiLineRun(element: PsiElement, document: Document): Boolean {
        val call = PsiTreeUtil.getParentOfType(element, PsiExpressionStatement::class.java)?.expression
            as? PsiMethodCallExpression ?: return false
        val setters = setterRunOf(call, document)?.takeIf { it.contains(call) } ?: return false
        val declaration = (call.methodExpression.qualifierExpression as PsiReferenceExpression)
            .resolve()?.parent as PsiDeclarationStatement
        val classReference = (declaration.newObjectVariable()!!.initializer as PsiNewExpression).classReference!!
        return isMultiLine(setters, document, classReference.textRange.endOffset)
    }

    private fun isMultiLine(setters: List<PsiMethodCallExpression>, document: Document, openOffset: Int) =
        setters.size > 1 &&
            document.getLineNumber(setters.last().textRange.startOffset) != document.getLineNumber(openOffset)

    private fun setterRunOf(call: PsiMethodCallExpression, document: Document): List<PsiMethodCallExpression>? {
        if (!setterNamedArguments || !Helper.isSetter(call.methodExpression.referenceName ?: return null)) {
            return null
        }
        val qualifier = call.methodExpression.qualifierExpression as? PsiReferenceExpression ?: return null
        val variable = qualifier.resolve() as? PsiLocalVariable ?: return null
        val declaration = variable.parent as? PsiDeclarationStatement ?: return null
        return declaration.takeIf { it.newObjectVariable() == variable }?.let { setterRun(it, variable, document) }
    }

    /** Whether `creation` opens a setter run, whose fold already hides its `new`. */
    fun opensSetterRun(creation: PsiNewExpression, document: Document): Boolean {
        if (!setterNamedArguments) {
            return false
        }
        val variable = creation.parent as? PsiLocalVariable ?: return false
        val declaration = variable.parent as? PsiDeclarationStatement ?: return false
        return declaration.newObjectVariable() == variable && setterRun(declaration, variable, document).isNotEmpty()
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
