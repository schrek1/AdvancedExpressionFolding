package com.intellij.advancedExpressionFolding.inlay

import com.intellij.application.options.CodeStyle
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorCustomElementRenderer
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.editor.Inlay
import com.intellij.openapi.editor.colors.EditorFontType
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.ex.FoldingListener
import com.intellij.openapi.editor.ex.FoldingModelEx
import com.intellij.openapi.editor.ex.util.EditorUIUtil
import com.intellij.openapi.editor.ex.util.EditorUtil
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.openapi.util.Disposer
import java.awt.Font
import java.awt.Graphics
import java.awt.Rectangle
import java.util.Collections
import java.util.WeakHashMap

/** One line of virtual text: segments drawn one after another, each in the colour of its key. */
data class VirtualLine(val segments: List<Pair<String, TextAttributesKey>>)

/**
 * Lines of text above and below a fold group that exist only while the group is collapsed, plus an invisible extra
 * indent on every line the group spans; folds cannot insert line breaks, so Kotlin-style blocks get their own
 * `with(x) {` / `}` lines this way. Nothing is added to the document.
 *
 * A fold opts in by creating its [FoldingGroup] through [group]; [install] keeps an editor's inlays in step with its
 * collapsed groups.
 */
object VirtualLines {

    private class Spec(val above: VirtualLine?, val below: VirtualLine?)

    // written by the folding builder in a background read action, read on the EDT
    private val specs: MutableMap<FoldingGroup, Spec> = Collections.synchronizedMap(WeakHashMap())

    fun group(name: String, above: VirtualLine?, below: VirtualLine?): FoldingGroup =
        FoldingGroup.newGroup(name).also { specs[it] = Spec(above, below) }

    fun install(editor: Editor) {
        val foldingModel = editor.foldingModel as? FoldingModelEx ?: return
        val disposable = Disposer.newDisposable("AEF virtual lines")
        EditorUtil.disposeWithEditor(editor, disposable)
        foldingModel.addListener(object : FoldingListener {
            override fun onFoldProcessingEnd() = sync(editor)
        }, disposable)
        sync(editor)
    }

    /** Adds the inlays of collapsed groups that are missing and removes the ones whose group is gone or expanded. */
    fun sync(editor: Editor) {
        if (editor.isDisposed) {
            return
        }
        val wanted = wanted(editor).toMutableSet()
        val inlayModel = editor.inlayModel
        val length = editor.document.textLength
        val existing = inlayModel.getBlockElementsInRange(0, length, LineRenderer::class.java) +
            inlayModel.getInlineElementsInRange(0, length, IndentRenderer::class.java)
        existing.forEach { inlay ->
            if (!wanted.remove(inlay.placement())) {
                Disposer.dispose(inlay)
            }
        }
        wanted.forEach { placement ->
            when (placement.line) {
                null -> inlayModel.addInlineElement(placement.offset, true, IndentRenderer)
                else -> {
                    val renderer = LineRenderer(placement.line, placement.above, placement.column)
                    inlayModel.addBlockElement(placement.offset, !placement.above, placement.above, 0, renderer)
                }
            }
        }
    }

    /** An indent when [line] is null, otherwise a virtual line [above] or below the offset's line, drawn at [column]. */
    private data class Placement(val offset: Int, val line: VirtualLine?, val above: Boolean = false, val column: Int = 0)

    private fun Inlay<*>.placement(): Placement = when (val renderer = renderer) {
        is LineRenderer -> Placement(offset, renderer.line, renderer.above, renderer.column)
        else -> Placement(offset, null)
    }

    private fun wanted(editor: Editor): Set<Placement> {
        val document = editor.document
        val chars = document.charsSequence
        val placements = mutableSetOf<Placement>()
        editor.foldingModel.allFoldRegions
            .filter { it.isValid && !it.isExpanded && it.group != null }
            .groupBy { it.group!! }
            .forEach { (group, regions) ->
                val spec = specs[group] ?: return@forEach
                val firstLine = document.getLineNumber(regions.minOf { it.startOffset })
                val lastLine = document.getLineNumber(regions.maxOf { it.endOffset })
                val codeStarts = (firstLine..lastLine).associateWith { line ->
                    codeStart(document.getLineStartOffset(line), document.getLineEndOffset(line), chars)
                }
                fun column(line: Int) = codeStarts[line]?.let { editor.offsetToLogicalPosition(it).column } ?: 0
                spec.above?.let { placements += Placement(document.getLineStartOffset(firstLine), it, true, column(firstLine)) }
                spec.below?.let { placements += Placement(document.getLineEndOffset(lastLine), it, false, column(lastLine)) }
                codeStarts.values.filterNotNull().forEach { placements += Placement(it, null) }
            }
        return placements
    }

    private fun codeStart(lineStart: Int, lineEnd: Int, chars: CharSequence): Int? =
        (lineStart until lineEnd).firstOrNull { chars[it] != ' ' && chars[it] != '\t' }

    private fun indentWidth(editor: Editor): Int =
        EditorUtil.getPlainSpaceWidth(editor) * CodeStyle.getSettings(editor).getIndentSize(editor.virtualFile?.fileType)

    /** The extra indent of a real line inside a virtual block: only width, nothing painted. */
    internal object IndentRenderer : EditorCustomElementRenderer {
        override fun calcWidthInPixels(inlay: Inlay<*>): Int = indentWidth(inlay.editor)

        override fun paint(inlay: Inlay<*>, g: Graphics, targetRegion: Rectangle, textAttributes: TextAttributes) {
        }
    }

    /** Draws [line] from logical [column] on (the real line's indentation), in the editor font, without background. */
    internal class LineRenderer(val line: VirtualLine, val above: Boolean, val column: Int) : EditorCustomElementRenderer {

        override fun calcWidthInPixels(inlay: Inlay<*>): Int {
            val editor = inlay.editor
            return indentX(editor) + line.segments.sumOf { (text, key) ->
                editor.contentComponent.getFontMetrics(font(editor, key)).stringWidth(text)
            }
        }

        override fun paint(inlay: Inlay<*>, g: Graphics, targetRegion: Rectangle, textAttributes: TextAttributes) {
            val editor = inlay.editor
            EditorUIUtil.setupAntialiasing(g)
            var x = targetRegion.x + indentX(editor)
            val y = targetRegion.y + editor.ascent
            line.segments.forEach { (text, key) ->
                val font = font(editor, key)
                g.font = font
                g.color = editor.colorsScheme.getAttributes(key)?.foregroundColor ?: editor.colorsScheme.defaultForeground
                g.drawString(text, x, y)
                x += g.getFontMetrics(font).stringWidth(text)
            }
        }

        private fun indentX(editor: Editor): Int = column * EditorUtil.getPlainSpaceWidth(editor)

        private fun font(editor: Editor, key: TextAttributesKey): Font {
            val style = editor.colorsScheme.getAttributes(key)?.fontType ?: Font.PLAIN
            return editor.colorsScheme.getFont(EditorFontType.forJavaStyle(style))
        }
    }
}
