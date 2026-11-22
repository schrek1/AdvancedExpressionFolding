package com.intellij.advancedExpressionFolding.settings.view

import com.intellij.advancedExpressionFolding.processor.util.Consts.Emoji
import com.intellij.advancedExpressionFolding.settings.State
import data.*
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.editor.impl.EditorImpl
import com.intellij.openapi.editor.highlighter.EditorHighlighterFactory
import com.intellij.openapi.ui.ComponentValidator
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.components.JBLabel
import com.intellij.ui.dsl.builder.Panel
import org.intellij.lang.regexp.RegExpFileType
import java.awt.Dimension
import java.util.function.Supplier
import java.util.regex.PatternSyntaxException
import kotlin.reflect.KMutableProperty0

abstract class CheckboxesProvider {

    @Suppress("DEPRECATION")
    fun Panel.initialize(state: State) {
        registerCheckbox(state::getSetExpressionsCollapse, "Getters and setters as properties") {
            example(GetterSetterTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#getsetexpressionscollapse")
        }

        registerCheckbox(state::varExpressionsCollapse, "Variable declarations (var/val)") {
            example(VarTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#varexpressionscollapse")
        }

        registerCheckbox(
            state::compactControlFlowSyntaxCollapse,
            "Compact control flow condition syntax (Golang ifs)"
        ) {
            example(CompactControlFlowTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#compactcontrolflowsyntaxcollapse")
        }

        registerCheckbox(
            state::getExpressionsCollapse,
            "List.get, List.set, Map.get and Map.put expressions, array and list literals"
        ) {
            example(GetSetPutTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#getexpressionscollapse")
        }

        registerCheckbox(state::getOrDefaultElvis, "Map.getOrDefault as map[key] ?: default") {
            example(GetOrDefaultElvisTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#getordefaultelvis")
        }

        registerCheckbox(state::requireNonNullElseElvis, "Objects.requireNonNullElse(Get) as value ?: fallback") {
            example(RequireNonNullElseElvisTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#requirenonnullelseelvis")
        }

        registerCheckbox(
            state::mapAndEmptyCollectionLiterals,
            "Map literals and empty collections: [\"key\": value], [], [:]"
        ) {
            example(MapAndEmptyCollectionLiteralsTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#mapandemptycollectionliterals")
        }

        registerCheckbox(
            state::concatenationExpressionsCollapse,
            "StringBuilder.append and Collection.add/remove expressions, interpolated Strings and Stream expressions"
        ) {
            example(StringBuilderTestData::class, "StringBuilder")
            example(InterpolatedStringTestData::class, "Interpolate")
            example(AppendSetterInterpolatedStringTestData::class, "Append")
            example(ConcatenationTestData::class, "Concatenation")
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#concatenationexpressionscollapse")
        }

        registerCheckbox(state::slicingExpressionsCollapse, "List.subList and String.substring expressions") {
            example(SliceTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#slicingexpressionscollapse")
        }

        registerCheckbox(state::comparingExpressionsCollapse, "Object.equals and Comparable.compareTo expressions") {
            example(EqualsCompareTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#comparingexpressionscollapse")
        }

        registerCheckbox(state::comparingLocalDatesCollapse, "Java.time isBefore/isAfter expressions") {
            example(LocalDateTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#comparinglocaldatescollapse")
        }

        registerCheckbox(state::localDateLiteralCollapse, "LocalDate.of literals (e.g. 2018-02-12)") {
            example(LocalDateLiteralTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#localdateliteralcollapse")
        }

        registerCheckbox(state::localDateLiteralPostfixCollapse, "Postfix LocalDate literals (e.g. 2018Y-02M-12D)") {
            example(LocalDateLiteralPostfixTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#localdateliteralpostfixcollapse")
        }

        registerCheckbox(state::durationLiteralCollapse, "Duration literals (e.g. 30.seconds, sleep(500.milliseconds))") {
            example(DurationLiteralTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#durationliteralcollapse")
        }

        registerCheckbox(state::castExpressionsCollapse, "Type cast expressions") {
            example(TypeCastTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#castexpressionscollapse")
        }

        registerCheckbox(state::rangeExpressionsCollapse, "For loops, range expressions") {
            example(ForRangeTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#rangeexpressionscollapse")
        }

        registerCheckbox(state::inOperator, "Membership and ranges as in / !in") {
            example(InOperatorTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#inoperator")
        }

        registerCheckbox(state::kotlinRangeLoops, "For loops in Kotlin range syntax: for (i in 0..<n)") {
            example(KotlinRangeLoopsTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#kotlinrangeloops")
        }

        registerCheckbox(state::repeatLoop, "Counting for loops with an unused index as repeat(n)") {
            example(RepeatLoopTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#repeatloop")
        }

        registerCheckbox(state::reverseSteppedLoopsCollapse, "For loops, reverse and stepped ranges") {
            example(ReverseSteppedLoopsTestData::class)
            example(ReverseSteppedLoopsIntervalTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#reversesteppedloopscollapse")
        }

        registerCheckbox(state::checkExpressionsCollapse, "Null-safe calls") {
            example(ElvisTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#checkexpressionscollapse")
        }

        registerCheckbox(state::ifNullSafe, "Extended null-safe ifs") {
            example(IfNullSafeData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#ifnullsafe")
        }

        registerCheckbox(state::nullOrEmpty, "Null-or-empty checks as isNullOrEmpty() / isNotEmpty()") {
            example(NullOrEmptyTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#nullorempty")
        }

        registerCheckbox(state::kotlinQuickReturn, "Kotlin quick return") {
            example(LetReturnIt::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#kotlinquickreturn")
        }

        registerCheckbox(state::kotlinPreconditions, "Preconditions as Kotlin require, check, requireNotNull and error") {
            example(KotlinPreconditionsTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#kotlinpreconditions")
        }

        registerCheckbox(state::assertsCollapse, "Asserts") {
            example(AssertTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#asserts")
        }

        registerCheckbox(state::optional, "Display optional as Kotlin null-safe") {
            example(OptionalTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#optional")
        }

        registerCheckbox(state::streamSpread, "Display stream operations as Groovy's spread operator") {
            example(SpreadTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#streamspread")
        }

        registerCheckbox(state::streamPredicates, "Display anyMatch / allMatch / noneMatch as Kotlin's any / all / none") {
            example(StreamPredicateTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#streampredicates")
        }

        registerCheckbox(state::logFolding, "Log folding") {
            example(LogBrackets::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#logfolding")
        }
        registerCheckbox(state::logFoldingTextBlocks, "Log folding: collapse Text Blocks") {
            example(LogFoldingTextBlocksTestData::class)
        }

        registerCheckbox(
            state::fieldShift,
            "Display mapping of field with same name as << (for builders, setters and assignments)"
        ) {
            example(FieldShiftBuilder::class, "builders")
            example(FieldShiftSetters::class, "setters")
            example(FieldShiftFields::class, "fields")
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#fieldshift")
        }

        registerCheckbox(state::toBuilderCopy, "Lombok toBuilder() chain as copy(name = ...)") {
            example(ToBuilderCopyTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#tobuildercopy")
        }

        registerCheckbox(state::builderNamedArguments, "Builder chain as constructor call Person(name = ...)") {
            example(BuilderNamedArgumentsTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#buildernamedarguments")
        }

        registerCheckbox(state::setterNamedArguments, "Setters on a new object as Person(name = ...)") {
            example(SetterNamedArgumentsTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#setternamedarguments")
        }

        registerCheckbox(state::destructuring, "Destructuring assignment for array & list") {
            example(DestructuringAssignmentArrayTestData::class, "array")
            example(DestructuringAssignmentListTestData::class, "list")
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#destructuring")
        }

        registerCheckbox(state::mapEntryDestructuring, "Destructuring for-each over Map.entrySet() as (key, value)") {
            example(MapEntryDestructuringTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#mapentrydestructuring")
        }

        registerCheckbox(state::objectDestructuring, "Destructuring locals read from record accessors & getters") {
            example(ObjectDestructuringTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#objectdestructuring")
        }

        registerCheckbox(state::println, "Simplify System.out.println to println") {
            example(PrintlnTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#println")
        }

        registerCheckbox(state::todo, "Simplify throw new UnsupportedOperationException to TODO") {
            example(TodoTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#todo")
        }

        registerCheckbox(
            state::controlFlowSingleStatementCodeBlockCollapse,
            "Control flow single-statement code block braces (read-only files)"
        ) {
            example(ControlFlowSingleStatementTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#controlflowsinglestatementcodeblockcollapse")
        }

        registerCheckbox(state::guardIfOneLiner, "Guard if with a simple return on one line") {
            example(GuardIfOneLinerTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#guardifoneliner")
        }

        registerCheckbox(
            state::controlFlowMultiStatementCodeBlockCollapse,
            "Control flow multi-statement code block braces (read-only files, deprecated)"
        ) {
            example(ControlFlowMultiStatementTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#controlflowmultistatementcodeblockcollapse")
        }

        registerCheckbox(state::semicolonsCollapse, "Semicolons (read-only files)") {
            example(SemicolonTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#semicolonscollapse")
        }

        registerCheckbox(state::const, "Simplify * static final to const") {
            example(ConstTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#const")
        }

        registerCheckbox(state::nullable, "Simplify @NotNull to Type!! and @Nullable to Type?") {
            example(NullableAnnotationTestData::class, "annotations")
            example(NullableAnnotationCheckNotNullTestData::class, "checkNotNull")
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#nullable")
        }

        registerCheckbox(state::finalRemoval, "Remove the 'final' modifier from all elements except fields") {
            example(FinalRemovalTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#finalremoval")
        }

        registerCheckbox(state::finalEmoji, "Replace the 'final' modifier with ${Emoji.FINAL_LOCK}") {
            example(FinalEmojiTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#finalemoji")
        }

        registerCheckbox(state::lombok, "Display Java bean as Lombok") {
            example(LombokTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#lombok")
        }

        registerCheckbox(state::lombokDirtyOff, "Don't fold Lombok dirty getters/setters") {
            example(LombokDirtyOffTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#lombokdirtyoff")
        }

        registerCheckbox(state::lombokNonNull, "Leading parameter null checks as Lombok @NonNull") {
            example(LombokNonNullTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#lombokNonNull")
        }

        registerCheckbox(state::jakartaConstraints, "Leading parameter checks as Jakarta Validation constraints") {
            example(JakartaConstraintsTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#jakartaConstraints")
        }

        registerCheckbox(state::lombokBuilder, "Display hand-written Lombok-shaped builder as @Builder") {
            example(LombokBuilderTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#lombokbuilder")
        }

        registerCheckbox(state::lombokDelegate, "Forwarding methods as Lombok @Delegate") {
            example(LombokDelegateTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#lombokdelegate")
        }

        registerCheckbox(state::lombokUtilityClass, "Display a class of static members as Lombok @UtilityClass") {
            example(LombokUtilityClassTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#lombokutilityclass")
        }

        registerCheckbox(state::lombokStandardException, "Lombok: exception with standard constructors as @StandardException") {
            example(LombokStandardExceptionTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#lombokstandardexception")
        }

        row {
            cell(JBLabel("Regex to disable Lombok folding (matched classes won’t be folded)"))
        }
        row {
            cell(createEditor(state::lombokPatternOff).component)
        }

        registerCheckbox(state::lombokSneakyThrows, "Whole-method try/catch that only rethrows as @SneakyThrows") {
            example(LombokSneakyThrowsTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#lombokSneakyThrows")
        }

        registerCheckbox(state::expressionFunc, "Single-Expression Function") {
            example(ExpressionFuncTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#expressionfunc")
        }

        registerCheckbox(state::expressionFuncEquals, "Single-Expression Function with = instead of braces (needs Code Folding › One-line methods off)") {
            example(ExpressionFuncEqualsTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#expressionfuncequals")
        }

        registerCheckbox(state::dynamic, "Dynamic names for methods based on \$user.home/dynamic-ajf2.toml") {
            example(DynamicTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#dynamic")
        }

        registerCheckbox(state::arithmeticExpressions, "BigDecimal, BigInteger and Math") {
            example(ArithmeticExpressionsTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#arithmeticexpressions")
        }

        registerCheckbox(state::emojify, "Emojify code") {
            example(EmojifyTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#emojify")
        }

        registerCheckbox(
            state::interfaceExtensionProperties,
            "Converts traditional getter and setter methods in interfaces into extension properties"
        ) {
            example(InterfaceExtensionPropertiesTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#interfaceextensionproperties")
        }

        registerCheckbox(state::patternMatchingInstanceof, "Pattern Matching for instanceof (JEP 394)") {
            example(PatternMatchingInstanceofTestData::class)
            example(PatternMatchingRecordPatternTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#patternmatchinginstanceof")
        }

        registerCheckbox(state::instanceofSwitch, "instanceof chain as pattern switch") {
            example(InstanceofSwitchTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#instanceofswitch")
        }

        registerCheckbox(
            state::summaryParentOverride,
            "Displays a folded summary of overridden methods from parent classes and interfaces."
        ) {
            example(SummaryParentOverrideTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#summaryparentoverride")
        }

        registerCheckbox(
            state::constructorReferenceNotation,
            "Constructor reference notation ::new and compact field initialization"
        ) {
            example(ConstructorReferenceNotationTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#constructorReferenceNotation")
        }

        registerCheckbox(state::methodDefaultParameters, "Default parameter values inline for overloaded method") {
            example(MethodDefaultParametersTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#methodDefaultParameters")
        }

        registerCheckbox(state::lazyProperty, "Null-checked lazy getter as by lazy property") {
            example(LazyPropertyTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#lazyproperty")
        }
        registerCheckbox(state::overrideHide, "Hide @Override annotation") {
            example(OverrideHideTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#overrideHide")
        }
        registerCheckbox(state::suppressWarningsHide, "Hide @SuppressWarnings annotation") {
            example(SuppressWarningsHideTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#suppressWarningsHide")
        }
        registerCheckbox(state::pseudoAnnotations, "Pseudo-annotations: @Main, @Loggable") {
            example(PseudoAnnotationsMainTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#pseudoAnnotations")
        }
        registerCheckbox(state::singletonObject, "Singleton as Kotlin object") {
            example(SingletonObjectTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#singletonObject")
        }
        registerCheckbox(state::joinToString, "String.join(sep, items) as items.joinToString(sep)") {
            example(StringJoinTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#joinToString")
        }
        registerCheckbox(state::catchRethrow, "Catch block that only wraps and rethrows as rethrow") {
            example(CatchRethrowTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#catchRethrow")
        }
        registerCheckbox(state::switchArrow, "Classic switch as arrow switch") {
            example(SwitchArrowTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#switchArrow")
        }
        // NEW OPTION
        registerCheckbox(state::memoryImprovement, "Memory improvements")
        registerCheckbox(state::experimental, "Experimental features") {
            example(ExperimentalTestData::class)
            link("https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki#experimental")
        }
    }

    abstract fun Panel.registerCheckbox(
        property: KMutableProperty0<Boolean>,
        title: String,
        block: (CheckboxBuilder.() -> Unit)? = null
    )
}


private fun createEditor(property: KMutableProperty0<String?>): EditorEx {
    val factory = com.intellij.openapi.editor.EditorFactory.getInstance()
    val document = factory.createDocument(property.get() ?: "")

    val editor = factory.createEditor(document, null) as EditorEx
    val application = ApplicationManager.getApplication()

    fun validateDocument(): ValidationInfo? {
        val text = document.text.trim()
        if (text.isEmpty()) {
            property.set(null)
            return null
        }

        return try {
            text.toPattern()
            property.set(text)
            null
        } catch (exception: PatternSyntaxException) {
            property.set(null)
            ValidationInfo(
                exception.description ?: exception.message ?: "Invalid regular expression",
                editor.component
            )
        }
    }

    val validator = application?.let {
        ComponentValidator(it)
            .withValidator(Supplier { validateDocument() })
            .installOn(editor.component)
    }

    val documentListener = object : DocumentListener {
        override fun documentChanged(event: DocumentEvent) {
            if (validator != null) {
                validator.revalidate()
            } else {
                validateDocument()
            }
        }
    }
    @Suppress("DEPRECATION")
    document.addDocumentListener(documentListener, (editor as EditorImpl).disposable)
    if (validator != null) {
        validator.revalidate()
    } else {
        validateDocument()
    }
    return editor.apply {
        settings.apply {
            isUseSoftWraps = true
            isUseCustomSoftWrapIndent = true
            isLineNumbersShown = false
            isLineMarkerAreaShown = false
        }
        highlighter = EditorHighlighterFactory.getInstance().createEditorHighlighter(null, RegExpFileType.INSTANCE)
        component.preferredSize = Dimension(500, 50)
    }
}
