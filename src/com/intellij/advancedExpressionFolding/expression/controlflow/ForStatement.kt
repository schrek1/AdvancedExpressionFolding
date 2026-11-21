package com.intellij.advancedExpressionFolding.expression.controlflow

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.operation.collection.Range
import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.advancedExpressionFolding.settings.state.IControlFlowState
import com.intellij.advancedExpressionFolding.settings.state.IExpressionCollapseState
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiDeclarationStatement
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiVariable
import com.intellij.psi.PsiForStatement

class ForStatement(
    private val statement: PsiForStatement,
    textRange: TextRange,
    operand: Expression,
    startRange: Expression,
    startInclusive: Boolean,
    endRange: Expression,
    endInclusive: Boolean,
) : Range(statement, textRange, operand, startRange, startInclusive, endRange, endInclusive),
    IControlFlowState by State()(),
    IExpressionCollapseState by State()() {

    init {
        separator = FOR_SEPARATOR
    }

    override fun buildFoldRegions(
        element: PsiElement,
        document: Document,
        parent: Expression?
    ): Array<FoldingDescriptor> {
        val descriptors = mutableListOf(*super.buildFoldRegions(element, document, parent))
        val loopVariable = (statement.initialization as? PsiDeclarationStatement)?.declaredElements?.singleOrNull() as? PsiVariable
        if (kotlinRangeLoops && loopVariable != null && loopVariable.textRange.startOffset < operand.textRange.startOffset) {
            // Kotlin declares the loop variable without a type: `for (i in 0..<n)`
            descriptors += FoldingDescriptor(
                element.node,
                TextRange.create(loopVariable.textRange.startOffset, operand.textRange.startOffset),
                descriptors.first().group,
                ""
            )
        }
        if (compactControlFlowSyntaxCollapse && statement.lParenth != null && statement.rParenth != null) {
            val parenthesesRange = TextRange.create(
                statement.lParenth!!.textRange.startOffset,
                statement.rParenth!!.textRange.endOffset
            )
            if (CompactControlFlowExpression.supportsFoldRegions(document, parenthesesRange)) {
                val group = descriptors.firstOrNull()?.group ?: FoldingGroup.newGroup(CompactControlFlowExpression::class.java.name)
                CompactControlFlowExpression.buildFoldRegions(element, group, descriptors, parenthesesRange)
            }
        }
        return descriptors.toTypedArray()
    }

    override fun openingPrefix(): String = if (kotlinRangeLoops) " in " else super.openingPrefix()

    override fun delimiter(): String = if (kotlinRangeLoops) (if (isEndInclusive()) ".." else "..<") else super.delimiter()

    override fun closingSuffix(): String = if (kotlinRangeLoops) "" else super.closingSuffix()

    companion object {
        const val FOR_SEPARATOR: String = ":"
    }
}
