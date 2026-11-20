# Lombok Standard Exception (State field: lombokStandardException)

### Lombok Standard Exception
Shows an exception class with the four standard constructors as Lombok's `@StandardException` and hides those
constructors.

#### Example: LombokStandardExceptionTestData

examples/data/LombokStandardExceptionTestData.java:
```java
    public static class PaymentException extends RuntimeException {
        public PaymentException() { super(); }
        public PaymentException(String message) { super(message); }
        public PaymentException(Throwable cause) { super(cause); }
        public PaymentException(String message, Throwable cause) { super(message, cause); }
    }
```

folded/LombokStandardExceptionTestData-folded.java:
```java
    @StandardException public static class PaymentException extends RuntimeException {
    }
```

#### When it folds
- The class extends `Throwable` (directly or through `Exception`, `RuntimeException`, ...).
- It has all four constructors `()`, `(String)`, `(Throwable)` and `(String, Throwable)`, each `public`.
- Each of them only calls `super(...)` with its own parameters in the same order (the no-argument one may also be
  empty), with no comment in its body.

Any other constructor (a different signature) stays visible next to the annotation.

#### When it does not fold
- A standard constructor does anything else, e.g. `super("Shipping failed: " + message)`, or holds a comment.
- One of the four constructors is missing, or is not `public`.
- The class is not a `Throwable`.

Default: On (requires `lombok`)
Controlled by: `lombokStandardException`
Related features: `lombok`
