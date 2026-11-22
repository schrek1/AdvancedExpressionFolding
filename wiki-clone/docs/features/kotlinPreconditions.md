# Kotlin Preconditions (State field: kotlinPreconditions)

### Preconditions as Kotlin require, check, requireNotNull and error
Shows argument and state checks the way Kotlin writes them.

- An `if` without `else` whose whole body is `throw new IllegalArgumentException(...)` becomes `require(...)`.
- The same with `IllegalStateException` becomes `check(...)`.
- Any other `throw new IllegalStateException(message)` becomes `error(message)`.
- `Objects.requireNonNull(value)` becomes `value!!` (a variable, method call or array element; any other expression keeps `requireNotNull(value)`).
- `Objects.requireNonNull(value, message)` becomes `requireNotNull(value) { message }`.

The condition is shown negated, written out when that is exactly equivalent, otherwise as `!(...)`:

- `!x` → `x`, and `x` → `!x` for a variable, method call or array element;
- `==` ↔ `!=`;
- `<` ↔ `>=` and `<=` ↔ `>` when both sides are integral (`int`, `long`, `short`, `byte`, `char` or their boxes);
  `float` and `double` keep `!(...)`, because with NaN `!(x <= 0)` is not `x > 0`;
- `&&` ↔ `||` when every operand can be negated this way, in the same order (`order == null || order.isEmpty()`
  → `order != null && !order.isEmpty()`); an `&&` inside `||` without parentheses keeps `!(...)`.

The message is kept as written, so it still folds into an interpolated string when that option is on.
Not folded: more statements in the `if`, an `else` branch, other exception types, a cause argument,
and a `Supplier` message for `requireNonNull`.

#### Example: KotlinPreconditionsTestData

examples/data/KotlinPreconditionsTestData.java:
```java
        if (order == null) {
            throw new IllegalArgumentException("order");
        }
        Objects.requireNonNull(customer, "customer");
        if (!connected) {
            throw new IllegalStateException("Not connected");
        }
        if (sku == null || sku.isEmpty()) {
            throw new IllegalArgumentException("sku");
        }
        throw new IllegalStateException("Unknown state " + state);
```

folded/KotlinPreconditionsTestData-folded.java:
```java
        require(order != null) { "order" }
        requireNotNull(customer) { "customer" };
        check(connected) { "Not connected" }
        require(sku != null && !sku.isEmpty()) { "sku" }
        error("Unknown state " + state);
```

Takes precedence over Asserts (`assertsCollapse`) for `IllegalArgumentException` and `IllegalStateException`.

Default: Off
Controlled by: `kotlinPreconditions`
Related features: `assertsCollapse`, `concatenationExpressionsCollapse`
