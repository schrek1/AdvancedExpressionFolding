# String.join as joinToString (State field: joinToString)

### String.join(sep, items) as items.joinToString(sep)
Shows `String.join(separator, items)` as the Kotlin-style extension call `items.joinToString(separator)`.
Hovering the folded call shows the real `String.join(...)`.

Default: On
Controlled by: `joinToString`
Related features: (none)

---

#### Detailed Folding Reference

**Collection or array**
examples/data/StringJoinTestData.java:
```java
        String csv = String.join(", ", names);
        String path = String.join("/", parts);
```
folded/StringJoinTestData-folded.java:
```java
        String csv = names.joinToString(", ");
        String path = parts.joinToString("/");
```

**Folds inside the items stay**
examples/data/StringJoinTestData.java:
```java
        String fromGetter = String.join(", ", getNames());
```
folded/StringJoinTestData-folded.java (with getters folded):
```java
        String fromGetter = names.joinToString(", ");
```

**Stays unfolded**
- the varargs form with separate values: `String.join(", ", name)`, `String.join(", ", name, other)`;
- items that are not a simple receiver, e.g. a ternary `String.join(", ", flag ? names : List.of())`;
- a separator written over several lines.
