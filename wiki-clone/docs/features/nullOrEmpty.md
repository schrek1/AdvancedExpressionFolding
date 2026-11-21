# Null Or Empty (State field: nullOrEmpty)

### Null-or-empty checks as isNullOrEmpty() / isNotEmpty()
Shows the common "null or empty" guard the way Kotlin writes it. Works anywhere as an expression: in an `if`, a `while`,
an assignment, a ternary or inside a longer `||` / `&&` chain.

#### Example: NullOrEmptyTestData

examples/data/NullOrEmptyTestData.java:
```java
        if (name == null || name.isEmpty()) {
            System.out.println("no name");
        }
        if (items != null && !items.isEmpty()) {
            System.out.println(items.get(0));
        }
        boolean blankName = name == null || name.length() == 0;
        boolean hasItems = items != null && items.size() > 0;
```

folded/NullOrEmptyTestData-folded.java:
```java
        if (name.isNullOrEmpty()) {
            System.out.println("no name");
        }
        if (items.isNotEmpty()) {
            System.out.println(items.get(0));
        }
        boolean blankName = name.isNullOrEmpty();
        boolean hasItems = items.isNotEmpty();
```

Recognised forms, where `x` is a local variable, a parameter or a field (`x` or `this.x`):
- `x == null || x.isEmpty()`, `x == null || x.length() == 0`, `x == null || x.size() == 0` → `x.isNullOrEmpty()`
- `x != null && !x.isEmpty()`, `x != null && x.length() != 0` / `> 0`, same with `size()` → `x.isNotEmpty()` (a non-empty value is never null)

The check stays as it is when:
- the two sides use different variables;
- the value comes from a method call (`getName() == null || getName().isEmpty()`) or another object's field;
- the operators do not pair up (`x == null && x.isEmpty()`, `x != null && x.isEmpty()`).

#### Null-safe utility checks

The null-safe `isEmpty(x)` / `isNotEmpty(x)` / `!isEmpty(x)` of these classes fold the same way, because they mean exactly
"null or empty":
- Apache Commons: `StringUtils` (lang3 and lang), `CollectionUtils` and `MapUtils` (collections4 and collections);
- Spring: `org.springframework.util.CollectionUtils`, `org.springframework.util.ObjectUtils`.

```java
        if (CollectionUtils.isEmpty(items)) { ... }    // if (items.isNullOrEmpty()) { ... }
        if (StringUtils.isNotEmpty(name)) { ... }      // if (name.isNotEmpty()) { ... }
        boolean noTitle = StringUtils.isEmpty(getTitle());  // getTitle().isNullOrEmpty()
```

The call is resolved, so a same-named method of another class (e.g. your own `MapUtils.isEmpty`) is left alone.
`isBlank` is not folded (it also treats whitespace as empty), and neither is an argument that would need parentheses
(`StringUtils.isEmpty(a + b)`).

Default: On
Controlled by: `nullOrEmpty`
Related features: `ifNullSafe`, `checkExpressionsCollapse`
