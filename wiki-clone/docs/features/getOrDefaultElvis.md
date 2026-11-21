# Map.getOrDefault as Elvis (State field: getOrDefaultElvis)

### Map.getOrDefault as map[key] ?: default
Shows `map.getOrDefault(key, default)` the way Kotlin reads it: the map index the plugin already uses for `Map.get`,
followed by the Elvis operator.

The fold applies where the result stands on its own: a variable initializer, an assignment, a `return`, a method
argument or a lambda body. As an operand of another operator, a cast, a condition or a call on the result it stays
Java, because `?:` binds looser than all of these (`map[key] ?: 0 + 1` would misread). Parentheses do not help,
because the type cast folding hides them.

Where the fold applies, the IDE's parameter name hints (`defaultValue:`) on the call are hidden, so they do not land
inside `map[key] ?: default`.

#### Example: GetOrDefaultElvisTestData

examples/data/GetOrDefaultElvisTestData.java:
```java
        int count = counts.getOrDefault(word, 0);
        return labels.getOrDefault(code, "unknown");
        int incremented = stock.getOrDefault(item, 0) + 1;
```

folded/GetOrDefaultElvisTestData-folded.java:
```java
        int count = counts[word] ?: 0;
        return labels[code] ?: "unknown";
        int incremented = stock.getOrDefault(item, 0) + 1;
```

Default: On
Controlled by: `getOrDefaultElvis`
Related features: `getExpressionsCollapse`, `checkExpressionsCollapse`
