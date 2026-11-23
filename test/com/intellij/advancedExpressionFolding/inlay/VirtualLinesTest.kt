package com.intellij.advancedExpressionFolding.inlay

import com.intellij.advancedExpressionFolding.folding.BaseTest
import com.intellij.advancedExpressionFolding.folding.util.TestDynamicDataProvider
import com.intellij.advancedExpressionFolding.processor.methodcall.MethodCallFactory
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.ex.FoldingModelEx
import com.intellij.openapi.editor.ex.util.EditorUtil
import com.intellij.testFramework.EditorTestUtil
import com.intellij.testFramework.runInEdtAndWait
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class VirtualLinesTest : BaseTest() {

    @Language("JAVA")
    private val source = """
        class Shop {
            void checkout(Order order) {
                order.validate();
                order.reserveStock();
                order.notifyCustomer("Thanks");
            }

            interface Order {
                void validate();
                void reserveStock();
                void notifyCustomer(String message);
            }
        }
    """.trimIndent()

    @Test
    fun sync_collapsedWithFold_blockLinesAroundAndCallsIndented() {
        // GIVEN - three calls on the same receiver, folded as with(order)
        runInEdtAndWait {
            val editor = foldedEditor()

            // WHEN - the virtual lines are synced with the collapsed folds
            VirtualLines.sync(editor)

            // THEN - `with(order) {` above the first call, `}` below the last, every call one level deeper
            assertEquals(
                """
                class Shop {
                    void checkout(Order order) {
                [with(order) {]
                       >> order.validate();
                       >> order.reserveStock();
                       >> order.notifyCustomer("Thanks");
                [}]
                    }
                """.trimIndent(),
                dump(editor).lines().take(8).joinToString("\n"),
            )
        }
    }

    @Test
    fun sync_collapsedWithFold_callsDrawnOneIndentRightOfTheBlockLines() {
        runInEdtAndWait {
            // GIVEN - three calls on the same receiver, folded as with(order)
            val editor = foldedEditor()
            val receiverStart = source.indexOf("order.validate")
            val receiverColumn = receiverStart - source.lastIndexOf('\n', receiverStart) - 1

            // WHEN - the virtual lines are synced with the collapsed folds
            VirtualLines.sync(editor)

            // THEN - `validate` (receiver hidden) starts one indent level right of the block lines' column
            val space = EditorUtil.getPlainSpaceWidth(editor)
            val validateX = editor.offsetToXY(source.indexOf("validate();")).x
            assertEquals((receiverColumn + 4) * space, validateX)
        }
    }

    @Test
    fun sync_groupWithLastLinePrefix_prefixOnlyBeforeTheLastLinesCode() {
        runInEdtAndWait {
            // GIVEN - the receivers of three calls hidden by a group whose last line gets an `else -> ` prefix
            assignState()
            fixture.configureByText("Shop.java", source)
            val editor = fixture.editor
            val prefix = VirtualLine(listOf("else -> " to HighlighterColors.TEXT))
            val group = VirtualLines.group("test", above = null, below = null, lastLinePrefix = prefix)
            val foldingModel = editor.foldingModel as FoldingModelEx
            foldingModel.runBatchFoldingOperation {
                listOf("order.validate", "order.reserveStock", "order.notifyCustomer").forEach { call ->
                    val start = source.indexOf(call)
                    foldingModel.createFoldRegion(start, start + "order.".length, "", group, false)!!.isExpanded = false
                }
            }

            // WHEN - the virtual lines are synced with the collapsed folds
            VirtualLines.sync(editor)

            // THEN - the middle call is only indented, the last one also starts after the prefix
            val receiverStart = source.indexOf("order.validate")
            val receiverColumn = receiverStart - source.lastIndexOf('\n', receiverStart) - 1
            val space = EditorUtil.getPlainSpaceWidth(editor)
            assertEquals((receiverColumn + 4) * space, editor.offsetToXY(source.indexOf("reserveStock();")).x)
            assertEquals(
                (receiverColumn + 4 + "else -> ".length) * space,
                editor.offsetToXY(source.indexOf("notifyCustomer(\"Thanks\");")).x,
            )
        }
    }

    @Test
    fun sync_multiLineListOfClosedOnLastElement_closingBracketLineUnderTheStatement() {
        runInEdtAndWait {
            // GIVEN - a List.of with one element per line and `);` glued to the last element
            assignState(state::mapAndEmptyCollectionLiterals)
            MethodCallFactory.initialize(TestDynamicDataProvider())
            @Language("JAVA")
            val listSource = """
                import java.util.List;

                class Shop {
                    List<String> colors = List.of(
                            "red",
                            "blue");
                }
            """.trimIndent()
            fixture.configureByText("Shop.java", listSource)
            val editor = fixture.editor
            EditorTestUtil.buildInitialFoldingsInBackground(editor)

            // WHEN - the virtual lines are synced with the collapsed folds
            VirtualLines.sync(editor)

            // THEN - `];` gets its own line under the statement, the elements keep their lines without extra indent
            assertEquals(
                """
                class Shop {
                    List<String> colors = List.of(
                            "red",
                            "blue");
                [];]
                }
                """.trimIndent(),
                dump(editor).lines().drop(2).joinToString("\n"),
            )
            val closingLine = editor.inlayModel.getBlockElementsInRange(0, listSource.length, VirtualLines.LineRenderer::class.java)
                .single().renderer
            val statementColumn = 4
            assertEquals(statementColumn, closingLine.column)
        }
    }

    @Test
    fun foldingListener_expandThenCollapse_linesFollowTheFold() {
        runInEdtAndWait {
            // GIVEN - a with fold showing its virtual lines
            val editor = foldedEditor()
            VirtualLines.install(editor)
            val withFolded = dump(editor)

            // WHEN - the fold group is expanded
            setFoldsExpanded(editor, true)

            // THEN - no virtual line or indent is left
            assertEquals(source, dump(editor))

            // WHEN - the fold group is collapsed again
            setFoldsExpanded(editor, false)

            // THEN - the virtual lines are back
            assertEquals(withFolded, dump(editor))
        }
    }

    @Language("JAVA")
    private val namedArgumentsSource = """
        import java.util.List;

        class Market {
            void open() {
                var orderBook = OrderBook.builder()
                        .asks(List.of(List.of("12", "14.5")))
                        .bids(null).build();
                OrderBook quiet = OrderBook.builder().asks(null).bids(null).build();
                var ticket = new Ticket();
                ticket.setSymbol("ACME");
                ticket.setVenue("XNAS");
                ticket.setSize(5);
            }

            static class OrderBook {
                private final List<List<String>> asks;
                private final List<List<String>> bids;

                OrderBook(List<List<String>> asks, List<List<String>> bids) {
                    this.asks = asks;
                    this.bids = bids;
                }

                static Builder builder() {
                    return new Builder();
                }

                static class Builder {
                    private List<List<String>> asks;
                    private List<List<String>> bids;

                    Builder asks(List<List<String>> asks) {
                        this.asks = asks;
                        return this;
                    }

                    Builder bids(List<List<String>> bids) {
                        this.bids = bids;
                        return this;
                    }

                    OrderBook build() {
                        return new OrderBook(asks, bids);
                    }
                }
            }

            static class Ticket {
                private String symbol;
                private String venue;
                private int size;

                public void setSymbol(String symbol) {
                    this.symbol = symbol;
                }

                public void setVenue(String venue) {
                    this.venue = venue;
                }

                public void setSize(int size) {
                    this.size = size;
                }
            }
        }
    """.trimIndent()

    @Test
    fun sync_multiLineNamedArguments_closingParenthesisOnItsOwnLine() {
        runInEdtAndWait {
            // GIVEN - a builder chain and a setter run over several lines, plus a one-line builder chain
            val editor = foldedEditor(namedArgumentsSource) { assignState(state::builderNamedArguments, state::setterNamedArguments) }

            // WHEN - the virtual lines are synced with the collapsed folds
            VirtualLines.sync(editor)

            // THEN - a `)` line closes each multi-line call; only the setters get an extra indent, the one-liner nothing
            assertEquals(
                """
                        var orderBook = OrderBook.builder()
                                .asks(List.of(List.of("12", "14.5")))
                                .bids(null).build();
                [)]
                        OrderBook quiet = OrderBook.builder().asks(null).bids(null).build();
                        var ticket = new Ticket();
                       >> ticket.setSymbol("ACME");
                       >> ticket.setVenue("XNAS");
                       >> ticket.setSize(5);
                [)]
                """.trimIndent(),
                dump(editor).lines().drop(4).take(10).joinToString("\n"),
            )
        }
    }

    @Test
    fun sync_collapsedInstanceofSwitch_casesOneLevelInCommentedDefaultBodyTwo() {
        runInEdtAndWait {
            // GIVEN - an instanceof chain with a commented else, folded as a pattern switch
            @Language("JAVA")
            val chain = """
                class Prices {
                    double toAmount(Object value) {
                        if (value instanceof String text) {
                            return Double.parseDouble(text);
                        } else if (value instanceof Integer number) {
                            return number.doubleValue();
                        } else {
                            // only the types above are expected
                            throw new IllegalArgumentException("unexpected type: " + value);
                        }
                    }
                }
            """.trimIndent()
            val editor = foldedEditor(chain) { assignState(state::instanceofSwitch) }

            // WHEN - the virtual lines are synced with the collapsed folds
            VirtualLines.sync(editor)

            // THEN - `switch (value) {` at the if's column, each case one level in (a joined `} else if` line
            // only once), the default's lines two levels in, and the real `}` back at the if's column
            val space = EditorUtil.getPlainSpaceWidth(editor)
            fun column(text: String) = editor.offsetToXY(chain.indexOf(text)).x / space
            val switchLine = editor.inlayModel.getBlockElementsInRange(0, chain.length, VirtualLines.LineRenderer::class.java)
                .map { it.renderer }.single()
            assertEquals("switch (value) {" to 8, switchLine.line.text() to switchLine.column)
            assertEquals(
                listOf(12, 32, 12, 35, 12, 16, 16, 8),
                listOf(
                    column("if (value"),
                    column("return Double"),
                    column("} else if"),
                    column("return number"),
                    column("} else {"),
                    column("// only"),
                    column("throw"),
                    chain.indexOf("        }\n    }").let { editor.offsetToXY(it + 8).x / space },
                ),
            )
        }
    }

    @Test
    fun sync_multiLineNamedArguments_closingParenthesisInTheStatementColumn() {
        runInEdtAndWait {
            // GIVEN - a builder chain and a setter run over several lines
            val editor = foldedEditor(namedArgumentsSource) { assignState(state::builderNamedArguments, state::setterNamedArguments) }

            // WHEN - the virtual lines are synced with the collapsed folds
            VirtualLines.sync(editor)

            // THEN - both `)` lines start where `var` starts, not at the arguments' indentation
            val closingColumns = editor.inlayModel
                .getBlockElementsInRange(0, editor.document.textLength, VirtualLines.LineRenderer::class.java)
                .map { it.renderer.column }
            assertEquals(listOf(8, 8), closingColumns)
        }
    }

    private fun foldedEditor(text: String = source, enableFolds: () -> Unit = { assignState(state::scopeWith) }): Editor {
        enableFolds()
        MethodCallFactory.initialize(TestDynamicDataProvider())
        fixture.configureByText("Shop.java", text)
        val editor = fixture.editor
        EditorTestUtil.buildInitialFoldingsInBackground(editor)
        val regions = editor.foldingModel.allFoldRegions
        check(regions.any { it.group != null && !it.isExpanded }) {
            "no collapsed grouped fold: " + regions.joinToString { "${it.textRange}/${it.placeholderText}/${it.group}/${it.isExpanded}" }
        }
        return editor
    }

    private fun setFoldsExpanded(editor: Editor, expanded: Boolean) {
        editor.foldingModel.runBatchFoldingOperation {
            editor.foldingModel.allFoldRegions.filter { it.group != null }.forEach { it.isExpanded = expanded }
        }
    }

    /** The document with block lines as `[text]` lines and indent inlays as `>>`. */
    private fun dump(editor: Editor): String {
        val document = editor.document
        val inlays = editor.inlayModel
        return (0 until document.lineCount).joinToString("\n") { line ->
            val start = document.getLineStartOffset(line)
            val end = document.getLineEndOffset(line)
            val blocks = inlays.getBlockElementsForVisualLine(editor.offsetToVisualPosition(start).line, true) +
                inlays.getBlockElementsForVisualLine(editor.offsetToVisualPosition(start).line, false)
            val rendered = blocks.mapNotNull { it.renderer as? VirtualLines.LineRenderer }
            val text = StringBuilder(document.charsSequence.subSequence(start, end))
            inlays.getInlineElementsInRange(start, end, VirtualLines.IndentRenderer::class.java)
                .sortedByDescending { it.offset }
                .forEach { text.insert(it.offset - start, ">>") }
            (rendered.filter { it.above }.map { "[${it.line.text()}]" } + text.toString() +
                rendered.filter { !it.above }.map { "[${it.line.text()}]" }).joinToString("\n")
        }
    }

    private fun VirtualLine.text() = segments.joinToString("") { it.first }
}
