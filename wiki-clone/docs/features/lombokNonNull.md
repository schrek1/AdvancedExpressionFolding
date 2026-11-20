# Lombok NonNull (State field: lombokNonNull)

### Leading parameter null checks as Lombok @NonNull
Shows the null checks a method starts with the way Lombok writes them: as `@NonNull` on the parameter.

- `Objects.requireNonNull(p)` and `Objects.requireNonNull(p, "message")`, statically imported too.
- `if (p == null) throw new NullPointerException(...)`, the check Lombok itself generates, with or without braces;
  `null == p` and `IllegalArgumentException` too.

Only the unbroken run of checks at the start of the body folds, one check per parameter of that method.
Not folded: a check after other code, a check of a field or local variable, a `Supplier` message, a second
check of the same parameter, an `if` with more statements or an `else`, and `this.x = requireNonNull(x)`.

#### Example: LombokNonNullTestData

examples/data/LombokNonNullTestData.java:
```java
    public void ship(String order, String address, int quantity) {
        requireNonNull(order);
        if (address == null) {
            throw new NullPointerException("address is marked non-null but is null");
        }
        System.out.println(order + address + quantity);
    }
```

folded/LombokNonNullTestData-folded.java:
```java
    public void ship(@NonNull String order, @NonNull String address, int quantity) {
        System.out.println(order + address + quantity);
    }
```

Default: On
Controlled by: `lombokNonNull`
Related features: `lombok`, `nullable`
