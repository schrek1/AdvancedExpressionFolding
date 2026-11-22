package com.intellij.advancedExpressionFolding.processor.methodcall.stream

import com.intellij.advancedExpressionFolding.expression.Expression
import com.intellij.advancedExpressionFolding.expression.operation.collection.Collect
import com.intellij.advancedExpressionFolding.expression.operation.stream.StreamSpreadChain
import com.intellij.advancedExpressionFolding.processor.core.BuildExpressionExt
import com.intellij.advancedExpressionFolding.processor.end
import com.intellij.advancedExpressionFolding.processor.start
import com.intellij.advancedExpressionFolding.processor.util.Helper
import com.intellij.advancedExpressionFolding.processor.util.PropertyUtil
import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.advancedExpressionFolding.settings.state.ICollectionsStreamsState
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.CommonClassNames
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiMethodReferenceExpression
import com.intellij.psi.util.InheritanceUtil
import com.intellij.psi.util.PsiTreeUtil

/**
 * `items.stream().map(Type::accessor).toList()` → `items*.accessor`; a `Collectors.toSet()` terminal stays as `.toSet()`.
 *
 * Only a getter or a record accessor qualifies, and `map` must follow `stream()` directly. Any other chain still shows
 * `.collect(Collectors.toList())` / `.collect(toSet())` as `.toList()` / `.toSet()`.
 */
object StreamSpreadChainExt : ICollectionsStreamsState by State()() {

    private const val STREAM = "java.util.stream.Stream"
    private const val COLLECTORS = "java.util.stream.Collectors"

    fun createExpression(terminal: PsiMethodCallExpression, document: Document): Expression? {
        val terminalName = terminal.methodExpression.referenceName
        if (!streamSpreadChain || (terminalName != "toList" && terminalName != "collect") || !terminal.isCallOf(STREAM)) {
            return null
        }
        return spread(terminal) ?: collectAsTerminal(terminal, document)
    }

    private fun spread(terminal: PsiMethodCallExpression): Expression? {
        val map = terminal.qualifierCall()?.takeIf { it.methodExpression.referenceName == "map" } ?: return null
        val reference = map.argumentList.expressions.singleOrNull() as? PsiMethodReferenceExpression ?: return null
        val stream = map.qualifierCall()
            ?.takeIf { it.methodExpression.referenceName == "stream" && it.argumentList.isEmpty }
            ?: return null
        val receiver = stream.methodExpression.qualifierExpression ?: return null
        if (!InheritanceUtil.isInheritor(receiver.type, CommonClassNames.JAVA_UTIL_COLLECTION)) {
            return null
        }
        val accessor = reference.referenceNameElement ?: return null
        val property = (reference.resolve() as? PsiMethod)?.propertyName() ?: return null
        val terminalFolds = terminalFolds(terminal, reference.end()) ?: return null
        // folds would hide a comment written inside the chain
        if (PsiTreeUtil.findChildOfType(terminal, PsiComment::class.java) != null) {
            return null
        }
        val spread = when (property) {
            accessor.text -> TextRange(receiver.end(), accessor.start()) to "*."
            else -> TextRange(receiver.end(), accessor.end()) to "*.$property"
        }
        return StreamSpreadChain(terminal, receiver, listOf(spread) + terminalFolds)
    }

    private fun terminalFolds(terminal: PsiMethodCallExpression, mapEnd: Int): List<Pair<TextRange, String>>? {
        if (terminal.methodExpression.referenceName == "toList") {
            return if (terminal.argumentList.isEmpty) listOf(TextRange(mapEnd, terminal.end()) to "") else null
        }
        val collector = terminal.listOrSetCollector() ?: return null
        val collectorName = collector.methodExpression.referenceNameElement ?: return null
        return when (collectorName.text) {
            "toList" -> listOf(TextRange(mapEnd, terminal.end()) to "")
            else -> listOf(
                TextRange(mapEnd, collectorName.start()) to ".",
                TextRange(collector.end(), terminal.end()) to "",
            )
        }
    }

    private fun collectAsTerminal(terminal: PsiMethodCallExpression, document: Document): Expression? {
        val collector = terminal.listOrSetCollector() ?: return null
        val qualifier = terminal.methodExpression.qualifierExpression ?: return null
        val collectIdentifier = terminal.methodExpression.referenceNameElement ?: return null
        val collectorName = collector.methodExpression.referenceNameElement ?: return null
        return Collect(
            terminal,
            TextRange(collectIdentifier.start(), terminal.end()),
            BuildExpressionExt.getAnyExpression(qualifier, document),
            TextRange(collectorName.start(), collector.end()),
        )
    }

    /** The argument of `collect(Collectors.toList())` / `collect(toSet())`, a static import included. */
    private fun PsiMethodCallExpression.listOrSetCollector(): PsiMethodCallExpression? {
        val collector = argumentList.expressions.singleOrNull() as? PsiMethodCallExpression ?: return null
        val name = collector.methodExpression.referenceName
        return collector.takeIf {
            (name == "toList" || name == "toSet") && it.argumentList.isEmpty && it.isCallOf(COLLECTORS)
        }
    }

    private fun PsiMethod.propertyName(): String? {
        val owner = containingClass ?: return null
        return when {
            !parameterList.isEmpty -> null
            owner.isRecord && owner.recordComponents.any { it.name == name } -> name
            Helper.isGetter(name) -> PropertyUtil.guessPropertyName(name)
            else -> null
        }
    }

    private fun PsiMethodCallExpression.qualifierCall() = methodExpression.qualifierExpression as? PsiMethodCallExpression

    private fun PsiMethodCallExpression.isCallOf(className: String) =
        resolveMethod()?.containingClass?.qualifiedName == className
}
