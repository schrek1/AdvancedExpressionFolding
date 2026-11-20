# Lombok SneakyThrows (State field: lombokSneakyThrows)

### Lombok SneakyThrows
Folds a method whose whole body is a try/catch that only rethrows the caught exception wrapped in an unchecked one
into Lombok's `@SneakyThrows` on the method. The `try` head and the `catch` are hidden and the body moves one level left.

#### Example: LombokSneakyThrowsTestData

examples/data/LombokSneakyThrowsTestData.java:
```java
    void save(Path file) {
        try {
            Files.writeString(file, json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
```

folded/LombokSneakyThrowsTestData-folded.java:
```java
    @SneakyThrows void save(Path file) {
        Files.writeString(file, json);
    }
```

A wrapper other than `RuntimeException` / `IllegalStateException` is shown as a parameter, e.g.
`@SneakyThrows(UncheckedIOException)`.

Stays unfolded when the try/catch is not the whole method body, has a `finally` block, resources or more than one
`catch`, or when the catch does anything else than `throw new X(e)` (logging, a message, wrapping another exception).
A try/catch inside a larger method body is left to the experimental statement-level fold.

Default: On
Controlled by: `lombokSneakyThrows`
Related features: `lombok`, `experimental`
