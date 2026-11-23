package com.intellij.advancedExpressionFolding.inlay

import com.intellij.application.options.CodeStyle
import com.intellij.openapi.Disposable
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
import com.intellij.openapi.util.Key
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

    private class Spec(
        val above: VirtualLine?,
        val below: VirtualLine?,
        val lastLinePrefix: VirtualLine?,
        val indentBody: Boolean,
    )

    // written by the folding builder in a background read action, read on the EDT
    private val specs: MutableMap<FoldingGroup, Spec> = Collections.synchronizedMap(WeakHashMap())

    private val INSTALLED = Key.create<Disposable>("AEF virtual lines")

    /**
     * @param above line drawn above the line of the group's first region
     * @param below line drawn below the line of the group's last region
     * @param lastLinePrefix text drawn after the extra indent of the last line, before its code (e.g. `else -> `)
     * @param indentBody false keeps the spanned lines where they are and draws [below] at the indentation of the
     *   group's first line, as a closing `]` under the line that opens the list
     */
    fun group(
        name: String,
        above: VirtualLine?,
        below: VirtualLine?,
        lastLinePrefix: VirtualLine? = null,
        indentBody: Boolean = true,
    ): FoldingGroup = FoldingGroup.newGroup(name).also { specs[it] = Spec(above, below, lastLinePrefix, indentBody) }

    fun install(editor: Editor) {
        val foldingModel = editor.foldingModel as? FoldingModelEx ?: return
        if (editor.getUserData(INSTALLED) != null) {
            return
        }
        val disposable = Disposer.newDisposable("AEF virtual lines")
        EditorUtil.disposeWithEditor(editor, disposable)
        editor.putUserData(INSTALLED, disposable)
        foldingModel.addListener(object : FoldingListener {
            override fun onFoldProcessingEnd() = sync(editor)
        }, disposable)
        sync(editor)
    }

    /** Takes the listener and the inlays of [install] off the editor; both are plugin code the editor would keep alive. */
    fun uninstall(editor: Editor) {
        editor.getUserData(INSTALLED)?.let(Disposer::dispose)
        editor.putUserData(INSTALLED, null)
        if (editor.isDisposed) {
            return
        }
        val inlayModel = editor.inlayModel
        val length = editor.document.textLength
        (inlayModel.getBlockElementsInRange(0, length, LineRenderer::class.java) +
            inlayModel.getInlineElementsInRange(0, length, IndentRenderer::class.java)).forEach(Disposer::dispose)
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
            // the renderer keeps its creation offset; the inlay's own offset follows edits
            val current = when (val renderer = inlay.renderer) {
                is IndentRenderer -> renderer.copy(offset = inlay.offset)
                is LineRenderer -> renderer.copy(offset = inlay.offset)
                else -> renderer
            }
            if (!wanted.remove(current)) {
                Disposer.dispose(inlay)
            }
        }
        wanted.forEach { renderer ->
            when (renderer) {
                is IndentRenderer -> inlayModel.addInlineElement(renderer.offset, true, renderer)
                is LineRenderer -> inlayModel.addBlockElement(renderer.offset, !renderer.above, renderer.above, 0, renderer)
            }
        }
    }

    /** The renderers the collapsed groups need; renderers are data classes, so equal ones mean an inlay can stay. */
    private fun wanted(editor: Editor): Set<Any> {
        val document = editor.document
        val chars = document.charsSequence
        val wanted = mutableSetOf<Any>()
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
                spec.above?.let { wanted += LineRenderer(document.getLineStartOffset(firstLine), it, true, column(firstLine)) }
                val belowColumn = column(if (spec.indentBody) lastLine else firstLine)
                spec.below?.let { wanted += LineRenderer(document.getLineEndOffset(lastLine), it, false, belowColumn) }
                if (!spec.indentBody) {
                    return@forEach
                }
                codeStarts.forEach { (line, codeStart) ->
                    if (codeStart == null) {
                        return@forEach
                    }
                    // an inline inlay on the edge of a collapsed fold is hidden with it, so anchor inside the indentation
                    val inIndent = codeStart > 0 && chars[codeStart - 1].let { it == ' ' || it == '\t' }
                    val prefix = spec.lastLinePrefix.takeIf { line == lastLine }
                    wanted += IndentRenderer(if (inIndent) codeStart - 1 else codeStart, prefix, inIndent)
                }
            }
        return wanted
    }

    private fun codeStart(lineStart: Int, lineEnd: Int, chars: CharSequence): Int? =
        (lineStart until lineEnd).firstOrNull { chars[it] != ' ' && chars[it] != '\t' }

    private fun indentWidth(editor: Editor): Int =
        EditorUtil.getPlainSpaceWidth(editor) * CodeStyle.getSettings(editor).getIndentSize(editor.virtualFile?.fileType)

    private fun font(editor: Editor, key: TextAttributesKey): Font {
        val style = editor.colorsScheme.getAttributes(key)?.fontType ?: Font.PLAIN
        return editor.colorsScheme.getFont(EditorFontType.forJavaStyle(style))
    }

    private fun VirtualLine.width(editor: Editor): Int = segments.sumOf { (text, key) ->
        editor.contentComponent.getFontMetrics(font(editor, key)).stringWidth(text)
    }

    /** Draws the segments from [x] on, in the editor font and each key's colour, without background. */
    private fun VirtualLine.paint(editor: Editor, g: Graphics, x: Int, lineTop: Int) {
        EditorUIUtil.setupAntialiasing(g)
        var segmentX = x
        segments.forEach { (text, key) ->
            val font = font(editor, key)
            g.font = font
            g.color = editor.colorsScheme.getAttributes(key)?.foregroundColor ?: editor.colorsScheme.defaultForeground
            g.drawString(text, segmentX, lineTop + editor.ascent)
            segmentX += g.getFontMetrics(font).stringWidth(text)
        }
    }

    /**
     * The extra indent of a real line inside a virtual block, then the optional [prefix]. When anchored before the
     * line's last indentation character ([inIndent]), the prefix is painted over that blank character.
     */
    internal data class IndentRenderer(
        val offset: Int,
        val prefix: VirtualLine?,
        val inIndent: Boolean,
    ) : EditorCustomElementRenderer {

        override fun calcWidthInPixels(inlay: Inlay<*>): Int =
            indentWidth(inlay.editor) + (prefix?.width(inlay.editor) ?: 0)

        override fun paint(inlay: Inlay<*>, g: Graphics, targetRegion: Rectangle, textAttributes: TextAttributes) {
            val editor = inlay.editor
            // shifted past the blank indentation character that follows the inlay, so the prefix touches the code
            val shift = if (inIndent) EditorUtil.getPlainSpaceWidth(editor) else 0
            prefix?.paint(editor, g, targetRegion.x + indentWidth(editor) + shift, targetRegion.y)
        }
    }

    /** Draws [line] [above] or below the line of [offset], from logical [column] on (the real line's indentation). */
    internal data class LineRenderer(
        val offset: Int,
        val line: VirtualLine,
        val above: Boolean,
        val column: Int,
    ) : EditorCustomElementRenderer {

        override fun calcWidthInPixels(inlay: Inlay<*>): Int = indentX(inlay.editor) + line.width(inlay.editor)

        override fun paint(inlay: Inlay<*>, g: Graphics, targetRegion: Rectangle, textAttributes: TextAttributes) {
            line.paint(inlay.editor, g, targetRegion.x + indentX(inlay.editor), targetRegion.y)
        }

        private fun indentX(editor: Editor): Int = column * EditorUtil.getPlainSpaceWidth(editor)
    }
}
