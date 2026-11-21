# TODO (State field: todo)

### TODO
Folds `throw new UnsupportedOperationException(...)` into Kotlin-style `TODO(...)`, keeping the reason.

#### Example: TodoTestData

examples/data/TodoTestData.java:
```java
    public void export() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void exportWithoutReason() {
        throw new UnsupportedOperationException();
    }
```

folded/TodoTestData-folded.java:
```java
    public void export() {
        TODO("Not implemented");
    }

    public void exportWithoutReason() {
        TODO();
    }
```

Only `java.lang.UnsupportedOperationException` itself folds, with no argument or a single `String` reason.
Subclasses, anonymous subclasses, other exceptions and a `cause` argument stay as written.
Together with `expressionFunc`, a stub method reads `public void export() { TODO("Not implemented") }`.

Default: On
Controlled by: `todo`
Related features: expressionFunc
