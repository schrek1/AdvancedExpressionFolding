# Catch Rethrow (State field: catchRethrow)

### Catch block that only wraps and rethrows as rethrow
Shows a catch block that only wraps the caught exception and throws it on as `rethrow`, the way Scala and Lombok's
`@SneakyThrows` let you skip the wrapping boilerplate.

#### Example: CatchRethrowTestData

examples/data/CatchRethrowTestData.java:
```java
        try {
            return Files.readString(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Files.writeString(file, order);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
```

folded/CatchRethrowTestData-folded.java:
```java
        try {
            return Files.readString(file);
        } catch (IOException e) rethrow
        try {
            Files.writeString(file, order);
        } catch (IOException e) rethrow as UncheckedIOException
```

The catch block folds when its only statement is `throw new X(e);` and `e` is the caught exception. A plain
`RuntimeException` shows as `rethrow`, any other wrapper as `rethrow as X`, so the wrapper type is never hidden: `X`
stays real code with its highlighting, only `rethrow as` is folded text.
A wrapper with a message (`new RuntimeException("...", e)`), a second statement (logging), a comment, a plain
`throw e;` or a different cause keeps the block.

With `experimental` on, a simple try/catch already shows as `@SneakyThrows` and this fold steps aside.

Default: On
Controlled by: `catchRethrow`
Related features: `experimental`, `compactControlFlowSyntaxCollapse`
