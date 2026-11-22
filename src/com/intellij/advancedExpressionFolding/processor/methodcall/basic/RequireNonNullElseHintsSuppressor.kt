package com.intellij.advancedExpressionFolding.processor.methodcall.basic

import com.intellij.advancedExpressionFolding.expression.operation.basic.RequireNonNullElseElvis
import com.intellij.advancedExpressionFolding.processor.core.getNonSyntheticExpression
import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.codeInsight.hints.InlayInfo
import com.intellij.codeInsight.hints.ParameterNameHintsSuppressor
import com.intellij.psi.PsiExpressionList
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiMethodCallExpression

/**
 * Hides parameter name hints (`defaultObj:`) on the arguments of a `requireNonNullElse(Get)` call shown as
 * `value ?: fallback`, where a hint would land inside the folded text.
 */
class RequireNonNullElseHintsSuppressor : ParameterNameHintsSuppressor {

    override fun isSuppressedFor(file: PsiFile, inlayInfo: InlayInfo): Boolean {
        val state = State()()
        if (!state.globalOn || !state.requireNonNullElseElvis) {
            return false
        }
        val argument = generateSequence(file.findElementAt(inlayInfo.offset)) { it.parent }
            .takeWhile { it.textRange.startOffset == inlayInfo.offset }
            .firstOrNull { it.parent is PsiExpressionList }
            ?: return false
        val call = argument.parent.parent as? PsiMethodCallExpression ?: return false
        return call.methodExpression.referenceName?.startsWith("requireNonNullElse") == true &&
            getNonSyntheticExpression(call) is RequireNonNullElseElvis
    }
}
