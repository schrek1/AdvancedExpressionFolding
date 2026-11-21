package com.intellij.advancedExpressionFolding.processor.methodcall.stream

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.operation.stream.StreamPredicate
import com.intellij.advancedExpressionFolding.processor.core.BuildExpressionExt
import com.intellij.advancedExpressionFolding.processor.methodcall.Context
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiExpression
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.util.InheritanceUtil

/**
 * Shows `anyMatch` / `allMatch` / `noneMatch` on a stream of a collection or an array as Kotlin's `any` / `all` /
 * `none`: only the method name changes and the stream call is hidden; the predicate keeps its own text.
 */
class StreamPredicateMethodCall : AbstractStreamMethodCall() {
    override fun canExecute() = streamPredicates

    override val methodNames by lazy { listOf("anyMatch", "allMatch", "noneMatch") }

    override fun onSingleArgument(
        element: PsiMethodCallExpression,
        context: Context,
        argument: PsiExpression,
        argumentExpression: Expression
    ): Expression? {
        val qualifier = element.methodExpression.qualifierExpression as? PsiMethodCallExpression ?: return null
        val source = qualifier.streamSource() ?: return null
        val methodName = element.methodExpression.referenceNameElement ?: return null
        val rename = methodName.textRange to context.methodName.removeSuffix("Match")
        // A stream taken right here is hidden by this fold; a longer chain keeps the folds of its steps.
        if (source.call != qualifier) {
            return StreamPredicate(element, element.textRange, listOf(rename), listOf(context.qualifierExpr, argumentExpression))
        }
        val receiver = BuildExpressionExt.getAnyExpression(source.receiver, context.document)
        val callRange = source.call.textRange
        val receiverRange = source.receiver.textRange
        val hideStream = if (callRange.startOffset == receiverRange.startOffset) {
            listOf(TextRange(receiverRange.endOffset, callRange.endOffset) to "")
        } else {
            listOf(TextRange(callRange.startOffset, receiverRange.startOffset) to "", TextRange(receiverRange.endOffset, callRange.endOffset) to "")
        }
        return StreamPredicate(element, element.textRange, hideStream + rename, listOf(receiver, argumentExpression))
    }

    private class StreamSource(val call: PsiMethodCallExpression, val receiver: PsiExpression)

    /**
     * The `collection.stream()` / `Arrays.stream(array)` call this stream chain starts at, if any.
     */
    private fun PsiMethodCallExpression.streamSource(): StreamSource? {
        var call: PsiMethodCallExpression = this
        while (call.methodExpression.referenceName != "stream") {
            call = call.methodExpression.qualifierExpression as? PsiMethodCallExpression ?: return null
        }
        val streamOf = call.methodExpression.qualifierExpression
        val arguments = call.argumentList.expressions
        return when {
            arguments.isEmpty() && InheritanceUtil.isInheritor(streamOf?.type, "java.util.Collection") ->
                StreamSource(call, streamOf!!)
            arguments.size == 1 && call.resolveMethod()?.containingClass?.qualifiedName == "java.util.Arrays" ->
                StreamSource(call, arguments[0])
            else -> null
        }
    }
}
