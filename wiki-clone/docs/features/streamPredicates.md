# Stream Predicates (State field: streamPredicates)

### Display anyMatch / allMatch / noneMatch as Kotlin's any / all / none
Shows the stream predicates under Kotlin's names. The `.stream()` call disappears and only the method name changes;
the predicate keeps the text you wrote.

#### Example: StreamPredicateTestData

examples/data/StreamPredicateTestData.java:
```java
        boolean anyAdmin = users.stream().anyMatch(User::isAdmin);
        boolean allPaid = orders.stream().allMatch(o -> o.isPaid());
        boolean noneEmpty = Arrays.stream(names).noneMatch(String::isEmpty);
        boolean noneBlank = users.stream()
                .map(User::getName)
                .noneMatch(String::isBlank);
        boolean anyActive = users.stream().anyMatch(isActive);
```

folded/StreamPredicateTestData-folded.java:
```java
        boolean anyAdmin = users.any(User::isAdmin);
        boolean allPaid = orders.all(o -> o.paid);
        boolean noneEmpty = names.none(String::isEmpty);
        boolean noneBlank = users*.name()
                .none(String::isBlank);
        boolean anyActive = users.any(isActive);
```

Only the method name is folded (`anyMatch` → `any`, `allMatch` → `all`, `noneMatch` → `none`) and `.stream()` is
hidden; the receiver and the predicate stay real text with their syntax colours. `o.paid` comes from
`getSetExpressionsCollapse`.

Applies only to a stream that starts at a collection's `.stream()` or at `Arrays.stream(array)`, also after other
stream steps (`filter`, `map`). Other streams (`Stream.of`, a `Stream` parameter, `IntStream`) stay unchanged.

Default: On
Controlled by: `streamPredicates`
Related features: `streamSpread`, `getSetExpressionsCollapse`
