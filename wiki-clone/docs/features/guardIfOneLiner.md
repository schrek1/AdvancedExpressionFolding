# Guard If One-Liner (State field: guardIfOneLiner)

### Guard if with a simple return on one line
Shows an early-exit `if` on one line, the way Kotlin writes it, in editable files too.

#### Example: GuardIfOneLinerTestData

examples/data/GuardIfOneLinerTestData.java:
```java
        if (name == null) {
            return null;
        }
        if (items == null) {
            return List.of();
        }
```

folded/GuardIfOneLinerTestData-folded.java:
```java
        if (name == null) return null;
        if (items == null) return List.of();
```

The `if` folds when it has no `else`, its braces hold exactly one `return` and nothing else, and the returned value
is simple: a literal (`null`, `true`/`false`, a number, a string), a variable, `return;`, an empty collection
(`List.of()`, `Collections.emptyMap()`, ...) or `Optional.empty()`. A computed value, a second statement, a comment,
an `else` or an `else if` chain keeps the braces. Guards that `throw` are left alone.

Unlike the read-only brace folding, the whole `if` sits on one line, so there is no indented body line a new
statement could be mistaken for: a line added after it lands outside the `if`, where it looks to be.

Default: On
Controlled by: `guardIfOneLiner`
Related features: `controlFlowSingleStatementCodeBlockCollapse`, `compactControlFlowSyntaxCollapse`
