package com.intellij.advancedExpressionFolding.processor.methodcall.collection

import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.codeInsight.hints.InlayInfo
import com.intellij.codeInsight.hints.ParameterNameHintsSuppressor
import com.intellij.psi.PsiExpressionList
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiMethodCallExpression

/**
 * Hides parameter name hints (`defaultValue:`) on the arguments of a `getOrDefault` call shown as `map[key] ?: default`,
 * where a hint would land inside the folded text.
 */
class GetOrDefaultHintsSuppressor : ParameterNameHintsSuppressor {

    override fun isSuppressedFor(file: PsiFile, inlayInfo: InlayInfo): Boolean {
        val state = State()()
        if (!state.globalOn || !state.getOrDefaultElvis) {
            return false
        }
        val argument = generateSequence(file.findElementAt(inlayInfo.offset)) { it.parent }
            .takeWhile { it.textRange.startOffset == inlayInfo.offset }
            .firstOrNull { it.parent is PsiExpressionList }
            ?: return false
        val call = argument.parent.parent as? PsiMethodCallExpression ?: return false
        return call.methodExpression.referenceName == "getOrDefault" &&
            call.methodExpression.qualifierExpression != null &&
            call.argumentList.expressionCount == 2 &&
            MapGetOrDefaultMethodCall.standsAlone(call)
    }
}
