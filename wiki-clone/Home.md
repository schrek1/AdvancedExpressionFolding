# Advanced Expression Folding Options

## getSetExpressionsCollapse
### Getters and setters as properties
Collapses getter and setter expressions into property-like syntax.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/GetterSetterTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/GetterSetterTestData-folded.java)

## varExpressionsCollapse
### Variable declarations (var/val)
Collapses variable declarations into more concise forms.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/VarTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/VarTestData-folded.java)

## compactControlFlowSyntaxCollapse
### Compact control flow condition syntax (Golang ifs)
Applies a more compact syntax for control flow conditions, similar to Golang's if statements.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/CompactControlFlowTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/CompactControlFlowTestData-folded.java)

## getExpressionsCollapse
### List.get, List.set, Map.get and Map.put expressions, array and list literals
Simplifies list and map access and modification expressions, as well as array and list literals.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/GetSetPutTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/GetSetPutTestData-folded.java)

## getOrDefaultElvis
### Map.getOrDefault as map[key] ?: default
Shows `map.getOrDefault(key, default)` as `map[key] ?: default`, where the result stands on its own (not as an operand of another operator).
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/GetOrDefaultElvisTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/GetOrDefaultElvisTestData-folded.java)

## requireNonNullElseElvis
### Objects.requireNonNullElse(Get) as value ?: fallback
Shows `Objects.requireNonNullElse(value, fallback)` as `value ?: fallback`, and `requireNonNullElseGet` the same way when the supplier is `X::new`, a no-argument method reference or a lambda with an expression body (or a block that is a single `return`). A single short call on the result gets real parentheses: `(name ?: "").length()`.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/RequireNonNullElseElvisTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/RequireNonNullElseElvisTestData-folded.java)

## mapAndEmptyCollectionLiterals
### Map literals and empty collections: ["key": value], [], [:]
Shows `Map.of(...)` as a `["key": value]` literal, empty lists and sets (`List.of()`, `Set.of()`, `Collections.emptyList()`, `new ArrayList<>()`, ...) as `[]` and empty maps (`Map.of()`, `Collections.emptyMap()`, `new HashMap<>()`) as `[:]`.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/MapAndEmptyCollectionLiteralsTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/MapAndEmptyCollectionLiteralsTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/mapAndEmptyCollectionLiterals)

## concatenationExpressionsCollapse
### StringBuilder.append and Collection.add/remove expressions, interpolated Strings and Stream expressions
Collapses various string and collection operations into more readable forms.
- [StringBuilder Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/StringBuilderTestData.java)
- [StringBuilder Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/StringBuilderTestData-folded.java)
- [Interpolated String Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/InterpolatedStringTestData.java)
- [Interpolated String Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/InterpolatedStringTestData-folded.java)
- [Append Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/AppendSetterInterpolatedStringTestData.java)
- [Append Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/AppendSetterInterpolatedStringTestData-folded.java)
- [Concatenation Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/ConcatenationTestData.java)
- [Concatenation Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/ConcatenationTestData-folded.java)

## slicingExpressionsCollapse
### List.subList and String.substring expressions
Simplifies list and string slicing operations.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/SliceTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/SliceTestData-folded.java)

## comparingExpressionsCollapse
### Object.equals and Comparable.compareTo expressions
Collapses equality and comparison operations into more readable forms.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/EqualsCompareTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/EqualsCompareTestData-folded.java)

## comparingLocalDatesCollapse
### Java.time isBefore/isAfter expressions
Simplifies date comparison operations.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/LocalDateTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/LocalDateTestData-folded.java)

## localDateLiteralCollapse
### LocalDate.of literals (e.g. 2018-02-12)
Collapses LocalDate creation into a more readable format.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/LocalDateLiteralTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/LocalDateLiteralTestData-folded.java)

## localDateLiteralPostfixCollapse
### Postfix LocalDate literals (e.g. 2018Y-02M-12D)
Allows for a postfix notation for LocalDate literals.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/LocalDateLiteralPostfixTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/LocalDateLiteralPostfixTestData-folded.java)

## durationLiteralCollapse
### Duration literals (e.g. 30.seconds, sleep(500.milliseconds))
Shows `Duration.ofSeconds(30)` as `30.seconds` and `TimeUnit.MILLISECONDS.sleep(500)` as `sleep(500.milliseconds)`. Only a plain number literal folds; a variable, a computed or negative value and two-argument factories stay as they are.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/DurationLiteralTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/DurationLiteralTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/durationLiteralCollapse)

## castExpressionsCollapse
### Type cast expressions
Simplifies type casting operations.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/TypeCastTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/TypeCastTestData-folded.java)

## rangeExpressionsCollapse
### For loops, range expressions
Collapses for loops and range expressions into more concise forms.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/ForRangeTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/ForRangeTestData-folded.java)

## inOperator
### Membership and ranges as in / !in
Shows `c.contains(x)` as `x in c`, `!c.contains(x)` as `x !in c`, `m.containsKey(k)` as `k in m`, `x >= a && x <= b` as `x in a..b` and `x >= a && x < b` as `x in a..<b`.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/InOperatorTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/InOperatorTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/inOperator)

## kotlinRangeLoops
### For loops in Kotlin range syntax
Shows the folded counting loop as a Kotlin range, `for (i in 0..<n)` instead of `for (int i : [0, n))`. Needs `rangeExpressionsCollapse`.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/KotlinRangeLoopsTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/KotlinRangeLoopsTestData-folded.java)

## repeatLoop
### Counting for loops with an unused index as repeat(n)
Shows `for (int i = 0; i < n; i++)` as `repeat(n)` when the body never reads or writes `i`. A loop that uses its index keeps the range form.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/RepeatLoopTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/RepeatLoopTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/repeatLoop)

## reverseSteppedLoopsCollapse
### For loops, reverse and stepped ranges
Collapses counting loops that go down or step by more than one: `for (i in n - 1 downTo 0)`, `for (i in 0..<n step 2)`.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/ReverseSteppedLoopsTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/ReverseSteppedLoopsTestData-folded.java)

## checkExpressionsCollapse
### Null-safe calls
Simplifies null-safe call operations.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/ElvisTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/ElvisTestData-folded.java)

## ifNullSafe
### Extended null-safe ifs
Provides extended null-safe if operations.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/IfNullSafeData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/IfNullSafeData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/ifNullSafe)

## nullOrEmpty
### Null-or-empty checks as isNullOrEmpty() / isNotEmpty()
Shows `x == null || x.isEmpty()` as `x.isNullOrEmpty()` and `x != null && !x.isEmpty()` as `x.isNotEmpty()`; `length()` / `size()` compared with `0` count too, and so do the null-safe `isEmpty(x)` / `isNotEmpty(x)` of Apache Commons and Spring utility classes.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/NullOrEmptyTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/NullOrEmptyTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/nullOrEmpty)

## kotlinQuickReturn
### Kotlin quick return
Implements a quick return syntax similar to Kotlin.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/LetReturnIt.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/LetReturnIt-folded.java)

## scopeLet
### Null-guarded single statement as ?.let
Shows `if (email != null) { contact.setEmail(email); }` as `email?.let { contact.setEmail(it) }` and `body != null ? toBytes(body) : null` as `body?.let { toBytes(it) }`, in the source order.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/ScopeLetTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/ScopeLetTestData-folded.java)

## scopeWith
### Calls on the same receiver as with
Shows three or more consecutive calls on the same variable, `order.validate(); order.reserveStock(); order.notifyCustomer("Thanks");`, as a `with(order) {` block: `with(order) {` and `}` get lines of their own (virtual lines shown while the fold is collapsed), the receivers and semicolons disappear and the calls are indented one level.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/ScopeWithTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/ScopeWithTestData-folded.java)

## kotlinPreconditions
### Preconditions as Kotlin require, check, requireNotNull and error
Shows argument and state checks the way Kotlin writes them: an `if` whose whole body throws `IllegalArgumentException` becomes `require(...)`, one that throws `IllegalStateException` becomes `check(...)`, any other `throw new IllegalStateException(message)` becomes `error(message)`, and `Objects.requireNonNull(x)` becomes `x!!` (with a message: `requireNotNull(x) { message }`). Takes precedence over the Asserts folding for these two exceptions.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/KotlinPreconditionsTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/KotlinPreconditionsTestData-folded.java)

## kotlinWithLock
### Lock, try, finally unlock as Kotlin withLock
Shows `lock.lock(); try { ... } finally { lock.unlock(); }` the way Kotlin writes it: `withLock(lock) { ... }`. Folds only when the `lock()` call stands right before the `try`, the `finally` block holds nothing but the `unlock()` of the same lock, and the `try` has no `catch` and no resources.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/WithLockTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/WithLockTestData-folded.java)

## kotlinLambda
### Lambdas in Kotlin syntax: { it.x }
Shows Java lambdas the way Kotlin writes them: `d -> d.getPrice()` as `{ it.price }`, `(a, b) -> a + b` as `{ a, b -> a + b }`, `() -> load()` as `{ load() }`. A lambda that is the last argument of a call moves behind it as a trailing lambda: `items.forEach(item -> { ... })` reads `items.forEach { ... }`. Only the braces, separators and each `it` are folded; the body stays real code and its own folds keep working. A lambda nested in an `it` lambda keeps its parameter name, and a lambda inside a call that another fold already rewrites stays Java.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/KotlinLambdaTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/KotlinLambdaTestData-folded.java)

## assertsCollapse
### Asserts
Simplifies assert statements.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/AssertTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/AssertTestData-folded.java)

## optional
### Display optional as Kotlin null-safe
Represents Java Optional types using Kotlin-style null-safe syntax.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/OptionalTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/OptionalTestData-folded.java)

## streamSpread
### Display stream operations as Groovy's spread operator
Represents Java stream operations using Groovy-style spread operator syntax.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/SpreadTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/SpreadTestData-folded.java)

## streamSpreadChain
### Stream map-to-list chain as spread: items*.id
Shows `orders.stream().map(Order::id).toList()` as `orders*.id`; `collect(Collectors.toSet())` shows as `orders*.id.toSet()`. On any other chain `.collect(Collectors.toList())` / `.collect(toSet())` shows as `.toList()` / `.toSet()`.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/StreamSpreadChainTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/StreamSpreadChainTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/streamSpreadChain)

## streamPredicates
### Display anyMatch / allMatch / noneMatch as Kotlin's any / all / none
Shows `anyMatch` / `allMatch` / `noneMatch` on a collection's or an array's stream as Kotlin's `any` / `all` / `none`; the predicate keeps its own text.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/StreamPredicateTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/StreamPredicateTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/streamPredicates)

## lombok
### Display Java bean as Lombok
Represents Java beans using Lombok-style annotations.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/LombokTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/LombokTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/lombok)

## logFolding
### Log folding
Applies folding to log statements.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/LogBrackets.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/LogBrackets-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/logFolding)

## logFoldingTextBlocks
### Log folding for Java text blocks
Extends log statement folding to Java text blocks so multi-line log messages collapse consistently with single-line strings.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/LogFoldingTextBlocksTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/LogFoldingTextBlocksTestData-folded.java)

## logFoldingKeepMessageText
### Log folding: keep the message text, fold only the arguments
Keeps the log message as real, string-coloured source and folds only each `{}` / `%s` into its argument (`$shopId`, `${previous.mode}`); the argument list after the message is hidden. A trailing exception stays visible. An argument may be a call with arguments (`${countOrders(shopId, previous)}`); only a call whose argument holds a lambda, spans several lines or is longer than about 40 characters keeps the classic log folding. Off by default.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/LogFoldingKeepMessageTextTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/LogFoldingKeepMessageTextTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/logFoldingKeepMessageText)

## fieldShift
### Field initialization and builder patterns
Transforms field initialization and builder patterns into more concise syntax.
- [Builder Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/FieldShiftBuilder.java)
- [Builder Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/FieldShiftBuilder-folded.java)
- [Fields Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/FieldShiftFields.java)
- [Fields Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/FieldShiftFields-folded.java)
- [Setters Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/FieldShiftSetters.java)
- [Setters Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/FieldShiftSetters-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/fieldShift)

## toBuilderCopy
### Lombok toBuilder() chain as copy(...)
Shows `person.toBuilder().name("Alice").age(31).build()` as `person.copy(name = "Alice", age = 31)`, keeping one argument per line in a multi-line chain.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/ToBuilderCopyTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/ToBuilderCopyTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/toBuilderCopy)

## builderNamedArguments
### Builder chain as constructor call Person(name = ...)
Shows `Person.builder().name("Alice").age(31).build()` as `Person(name = "Alice", age = 31)`, keeping one argument per line in a multi-line chain. Builders with `set`-prefixed methods (`Order.builder().setNote("gift").build()`) fold too, as `Order(note = "gift")`.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/BuilderNamedArgumentsTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/BuilderNamedArgumentsTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/builderNamedArguments)

## setterNamedArguments
### Setters on a new object as named arguments
Shows `var p = new Person();` followed by `p.setName("Alice"); p.setAge(31);` as `val p = Person(name = "Alice", age = 31);`, one argument per line.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/SetterNamedArgumentsTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/SetterNamedArgumentsTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/setterNamedArguments)

## destructuring
### Destructuring assignment for array & list
Implements destructuring assignments for arrays and lists.
- [Array Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/DestructuringAssignmentArrayTestData.java)
- [Array Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/DestructuringAssignmentArrayTestData-folded.java)
- [List Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/DestructuringAssignmentListTestData.java)
- [List Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/DestructuringAssignmentListTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/destructuring)

## mapEntryDestructuring
### Destructuring for-each over Map.entrySet() as (key, value)
Shows `for (Map.Entry<K, V> e : map.entrySet())` as `for ((key, value) in map)` and each `e.getKey()` / `e.getValue()` as `key` / `value`, when the entry is used only through those two calls.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/MapEntryDestructuringTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/MapEntryDestructuringTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/mapEntryDestructuring)

## objectDestructuring
### Destructuring locals read from record accessors & getters
Shows consecutive locals read from accessors of one object, such as `String customer = order.customer();` and `BigDecimal total = order.getTotal();`, as `val (customer, total) = order`, when each local is named after its accessor.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/ObjectDestructuringTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/ObjectDestructuringTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/objectDestructuring)

## println
### Simplify System.out.println to println
Simplifies System.out.println calls to just println.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/PrintlnTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/PrintlnTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/println)

## todo
### Simplify throw new UnsupportedOperationException to TODO
Folds `throw new UnsupportedOperationException("reason")` into Kotlin-style `TODO("reason")`, keeping the reason.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/TodoTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/TodoTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/todo)

## controlFlowSingleStatementCodeBlockCollapse
### Control flow single-statement code block braces (read-only files)
Simplifies single-statement code blocks in control flow statements for read-only files.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/ControlFlowSingleStatementTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/ControlFlowSingleStatementTestData-folded.java)

## guardIfOneLiner
### Guard if with a simple return on one line
Shows a guard `if` whose braces hold only `return` of a simple value (a literal, a variable, an empty collection or `Optional.empty()`) on one line, in editable files too. An `if` with `else`, more statements, a comment or a computed value stays as it is.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/GuardIfOneLinerTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/GuardIfOneLinerTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/guardIfOneLiner)

## controlFlowMultiStatementCodeBlockCollapse
### Control flow multi-statement code block braces (read-only files, deprecated)
Simplifies multi-statement code blocks in control flow statements for read-only files (deprecated).
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/ControlFlowMultiStatementTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/ControlFlowMultiStatementTestData-folded.java)

## semicolonsCollapse
### Semicolons (read-only files)
Removes semicolons in read-only files.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/SemicolonTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/SemicolonTestData-folded.java)

## const
### Simplify static final fields into const declarations
Converts eligible `static final` fields into concise Kotlin-style `const` (or `econst` for enum constants) declarations and hides redundant type information where possible.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/ConstTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/ConstTestData-folded.java)

## nullable
### Collapse nullability annotations to Kotlin suffixes
Replaces `@NotNull`/`@Nonnull` and `@Nullable` annotations with the Kotlin-style `!!` and `?` suffixes and folds matching `Preconditions.checkNotNull` patterns into readable guard clauses.
- [Annotations Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/NullableAnnotationTestData.java)
- [Annotations Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/NullableAnnotationTestData-folded.java)
- [checkNotNull Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/NullableAnnotationCheckNotNullTestData.java)
- [checkNotNull Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/NullableAnnotationCheckNotNullTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/nullable)

## finalRemoval
### Remove the 'final' modifier from all elements except fields
Removes the 'final' modifier from all elements except fields.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/FinalRemovalTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/FinalRemovalTestData-folded.java)

## finalEmoji
### Replace the 'final' modifier with 🔒
Replaces the 'final' modifier with a lock emoji.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/FinalEmojiTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/FinalEmojiTestData-folded.java)

## lombokDirtyOff
### Don't fold lombok dirty getters/setters
Prevents folding of Lombok-generated getters and setters that have been modified.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/LombokDirtyOffTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/LombokDirtyOffTestData-folded.java)

## lombokNonNull
### Leading parameter null checks as Lombok @NonNull
Hides the null checks at the start of a method body and shows `@NonNull` on each checked parameter instead: `Objects.requireNonNull(p[, "message"])` and `if (p == null) throw new NullPointerException(...)` (or `IllegalArgumentException`).
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/LombokNonNullTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/LombokNonNullTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/lombokNonNull)

## jakartaConstraints
### Leading parameter checks as Jakarta Validation constraints
Hides the argument checks at the start of a method body that only throw `IllegalArgumentException` and shows the matching constraint on the parameter instead: `if (quantity < 1) throw ...` → `@Min(1) int quantity`, `sku.isBlank()` → `@NotBlank`, `items.isEmpty()` → `@NotEmpty`, `coupon.length() > 12` → `@Size(max = 12)`, `amount <= 0` → `@Positive`.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/JakartaConstraintsTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/JakartaConstraintsTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/jakartaConstraints)

## lombokBuilder
### Display hand-written Lombok-shaped builder as @Builder
Hides a hand-written builder that has exactly the shape Lombok `@Builder` generates and shows `@Builder` on the class. A builder with anything extra (validation or defaults, an extra method, a different argument order) keeps the `@HasBuilder` label.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/LombokBuilderTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/LombokBuilderTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/lombokBuilder)

## lombokPatternOff
### Pattern to turn off Lombok folding
Prevents all Lombok folding for classes matching the pattern
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/LombokPatternOffTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/LombokPatternOffTestData-folded.java)

## lombokSneakyThrows
### Whole-method try/catch that only rethrows as @SneakyThrows
Folds a method whose whole body is a try/catch that only wraps the caught exception (`throw new RuntimeException(e)`) into `@SneakyThrows` on the method.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/LombokSneakyThrowsTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/LombokSneakyThrowsTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/lombokSneakyThrows)

## lombokDelegate
### Forwarding methods as Lombok @Delegate
Hides public methods that only pass their parameters on to the same-named method of a field, and marks that field with @Delegate.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/LombokDelegateTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/LombokDelegateTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/lombokDelegate)

## lombokUtilityClass
### Display a class of static members as Lombok @UtilityClass
Shows a class with only static members and a private constructor as `@UtilityClass`, hiding the constructor and the `static` and `final` modifiers. A singleton with an `INSTANCE` of its own type stays unfolded.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/LombokUtilityClassTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/LombokUtilityClassTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/lombokUtilityClass)

## lombokStandardException
### Lombok: exception with standard constructors as @StandardException
Folds an exception whose four standard constructors only pass their arguments to `super(...)` into `@StandardException`.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/LombokStandardExceptionTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/LombokStandardExceptionTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/lombokStandardException)

## expressionFunc
### Single-Expression Function
Simplifies single-expression functions.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/ExpressionFuncTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/ExpressionFuncTestData-folded.java)

## dynamic
### Dynamic names for methods based on $user.home/dynamic-ajf2.toml
Applies dynamic naming to methods based on a configuration file.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/DynamicTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/DynamicTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/dynamic)

## arithmeticExpressions
### BigDecimal, BigInteger and Math
Simplifies operations involving BigDecimal, BigInteger, and Math functions.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/ArithmeticExpressionsTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/ArithmeticExpressionsTestData-folded.java)

## emojify
### Emojify code
Replaces certain code elements with emojis.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/EmojifyTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/EmojifyTestData-folded.java)

## interfaceExtensionProperties
### Converts traditional getter and setter methods in interfaces into extension properties
Applies conversions to interface extension properties.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/InterfaceExtensionPropertiesTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/InterfaceExtensionPropertiesTestData-folded.java)

## patternMatchingInstanceof
### Pattern Matching for `instanceof` (JEP 394)
Applies pattern matching to `instanceof` checks for more concise and readable code.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/PatternMatchingInstanceofTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/PatternMatchingInstanceofTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/patternMatchingInstanceof)

## instanceofSwitch
### instanceof chain as pattern switch
Shows an `if / else if` chain of `instanceof` checks on one variable as a Java pattern switch: `case Circle c -> ...`, with a final `else` as `default -> ...`. Every branch must hold exactly one statement; a chain with a guarded condition, another condition, a comment or a different variable stays as it is.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/InstanceofSwitchTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/InstanceofSwitchTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/instanceofSwitch)

## kotlinIsOperator
### instanceof as Kotlin is / !is
Shows `x instanceof Foo` as `x is Foo`, `!(x instanceof Foo)` as `x !is Foo` and `x instanceof Foo f` as `x is Foo f`. Only the keyword (and the `!(` / `)` of a negation) folds; operand, type and binding keep their syntax colours.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/IsOperatorTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/IsOperatorTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/kotlinIsOperator)

## summaryParentOverride
### Shows a summary of overridden methods after each parent class and interface, and the parent at each overriding method (inlay hints)
Provides a concise view of inherited method overrides, reducing redundant code duplication.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/SummaryParentOverrideTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/SummaryParentOverrideTestData-folded.java)

## constructorReferenceNotation
### Constructor reference notation `::new` and compact field initialization
Simplifies constructor references and inline field initialization.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/ConstructorReferenceNotationTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/ConstructorReferenceNotationTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/constructorReferenceNotation)

## kotlinConstructorCall
### Constructor calls without `new` (Kotlin style)
Hides the `new` keyword of a plain instance creation, so `throw new IllegalArgumentException(...)` reads as `throw IllegalArgumentException(...)` and `var order = new Order(id, total)` as `var order = Order(id, total)`. Only `new` and the space after it are folded; anonymous classes, arrays and qualified `outer.new Inner()` stay as Java, and folds that already rewrite a creation (collection literals, `::new`) win.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/KotlinConstructorCallTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/KotlinConstructorCallTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/kotlinConstructorCall)

## methodDefaultParameters
### Default parameter values inline for overloaded method
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/MethodDefaultParametersTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/MethodDefaultParametersTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/methodDefaultParameters)

## singletonObject
### Singleton as Kotlin `object`
Shows an eager singleton (static instance, private empty constructor, static getter) as `object`, hiding the instance field, the constructor and the getter.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/SingletonObjectTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/SingletonObjectTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/singletonObject)

## lazyProperty
### Null-checked lazy getter as by lazy property
Shows a private field and the null-checked lazy getter right below it (`if (field == null) { field = init; } return field;`) as one Kotlin-style `val field by lazy { init }` line; the field name and `init` stay real code. A field with an initializer, a getter elsewhere in the class, a getter with more statements or an `else`, or a field assigned anywhere else stays as it is.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/LazyPropertyTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/LazyPropertyTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/lazyProperty)

## guardReturnWhen
### Guard-if return chain as when
Shows two or more guard `if (cond) { return x; }` statements followed by a final `return y;` as a subject-less Kotlin `when`: `when { cond -> return x` ... `else -> return y }`. Order and the conditions and returned values stay as written; a comment, an `else`, or a branch with more than the `return` keeps the code as it is.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/GuardReturnWhenTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/GuardReturnWhenTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/guardReturnWhen)

## pseudoAnnotations
### Pseudo-annotations for main method generation
Provides pseudo-annotations like @Main that automatically generate main methods for testing and prototyping.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/PseudoAnnotationsMainTestData.java)
- [Test](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/test/com/intellij/advancedExpressionFolding/pseudo/MainAnnotationCompletionContributorTest.kt)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/pseudoAnnotations)

## overrideHide
### Hide @Override annotations
Hides @Override annotations to reduce visual clutter and focus on method implementation.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/OverrideHideTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/OverrideHideTestData-folded.java)

## suppressWarningsHide
### Hide @SuppressWarnings annotations on methods
Hides @SuppressWarnings annotations on methods to reduce visual clutter. Currently implemented only for methods.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/SuppressWarningsHideTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/SuppressWarningsHideTestData-folded.java)

## joinToString
### String.join(sep, items) as items.joinToString(sep)
Shows `String.join(", ", names)` as Kotlin-style `names.joinToString(", ")`.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/StringJoinTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/StringJoinTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/joinToString)

## catchRethrow
### Catch block that only wraps and rethrows as rethrow
Shows a catch block whose only statement wraps the caught exception, `throw new RuntimeException(e);`, as `rethrow` (`rethrow as X` for another wrapper type). A block with a message, more statements, a comment or a different cause stays as it is.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/CatchRethrowTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/CatchRethrowTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/catchRethrow)

## switchArrow
### Classic switch as arrow switch
Shows a classic `switch` whose every case is one statement ended by `break` (or a lone `return`/`throw`) as an arrow switch (`case A, B -> statement;`). Fall-through, a case with more statements, an empty case or a comment keeps the switch as it is.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/SwitchArrowTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/SwitchArrowTestData-folded.java)
- [Documentation](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/wiki/docs/features/switchArrow)

## experimental
### Experimental features
Various experimental features for advanced code folding.
- [Example](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/examples/data/ExperimentalTestData.java)
- [Folded](https://github.com/AntoniRokitnicki/AdvancedExpressionFolding/blob/main/folded/ExperimentalTestData-folded.java)
