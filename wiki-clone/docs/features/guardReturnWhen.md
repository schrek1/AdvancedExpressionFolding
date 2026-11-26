# Guard-If Return Chain as When (State field: guardReturnWhen)

### Guard-if return chain as when
Shows a ladder of early returns as the multi-way branch it is, the way Kotlin writes it.

#### Example: GuardReturnWhenTestData

examples/data/GuardReturnWhenTestData.java:
```java
        if (status.matches(Code.NOT_FOUND)) {
            return notFound(response);
        }
        if (status.matches(Code.CONFLICT)) {
            return conflict(response);
        }
        if (status.matches(Code.RATE_LIMITED)) {
            return retryLater(response);
        }
        return Result.ok(response);
```

In the editor, with the option on:
```java
        when {
            status.matches(Code.NOT_FOUND) -> return notFound(response)
            status.matches(Code.CONFLICT) -> return conflict(response)
            status.matches(Code.RATE_LIMITED) -> return retryLater(response)
            else -> return Result.ok(response)
        }
```

The chain folds when two or more `if` statements follow each other with nothing but line breaks between them, each
has no `else` and its braces hold a single `return`, and the next statement is a `return`. A comment, a second
statement in a branch, an `else` or any other statement before the final `return` keeps the code as it is. The
conditions are kept whole, so any condition works, not only equality checks.

Only `if (`, `)`, the braces and the `;`s are folded. `when {`, `else -> ` and the closing `}` are virtual text drawn
by the editor while the chain is collapsed, and the branches are indented one level the same way; the document is
unchanged and the code keeps its order and syntax colours.

Default: On
Controlled by: `guardReturnWhen`
Related features: `kotlinQuickReturn`, `compactControlFlowSyntaxCollapse`
