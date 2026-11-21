# Jakarta Constraints (State field: jakartaConstraints)

### Leading parameter checks as Jakarta Validation constraints
Shows the argument checks a method starts with as Jakarta Validation constraints on the checked parameter.
A check is an `if` without `else` that only throws `IllegalArgumentException`, braces optional.

- `p < n` → `@Min(n)`, `p <= n` → `@Min(n + 1)`, `p > n` → `@Max(n)`, `p >= n` → `@Max(n - 1)` on an integral
  parameter with an integer literal; either operand order.
- `p <= 0` → `@Positive`, `p < 0` → `@PositiveOrZero`, `p >= 0` → `@Negative`, `p > 0` → `@NegativeOrZero` on any number.
- `s.isBlank()` → `@NotBlank`; `s.isEmpty()` on a `CharSequence`, `Collection` or `Map` → `@NotEmpty`.
- `s.length()` / `c.size()` against an integer literal → `@Size(min = …)` / `@Size(max = …)`; a min and a max check
  of one parameter show as one `@Size(min = …, max = …)`.

Only the unbroken run of checks at the start of the body folds; leading null checks (`lombokNonNull`) belong to the
same run, so `@NonNull @Size(max = 12) String coupon` is possible. Not folded: a check after other code, another
exception, a decimal or named-constant bound, `==`, `Optional.isEmpty()`, a field, an `if` with more statements or
an `else`, and a second check of the same kind on one parameter.

#### Example: JakartaConstraintsTestData

examples/data/JakartaConstraintsTestData.java:
```java
    public void reserve(int quantity, String sku) {
        if (quantity < 1) throw new IllegalArgumentException("quantity");
        if (sku.isBlank()) throw new IllegalArgumentException("sku");
        stock -= quantity;
    }
```

folded/JakartaConstraintsTestData-folded.java:
```java
    public void reserve(@Min(1) int quantity, @NotBlank String sku) {
        stock -= quantity;
    }
```

Default: On
Controlled by: `jakartaConstraints`
Related features: `lombokNonNull`
