# Objects.requireNonNullElse as Elvis (State field: requireNonNullElseElvis)

### Objects.requireNonNullElse(Get) as value ?: fallback
Shows `Objects.requireNonNullElse(value, fallback)` the way Kotlin reads it: `value ?: fallback`.

`Objects.requireNonNullElseGet(value, supplier)` folds the same way when the supplier can be shown as the value it
produces:
- `X::new` → `X()`
- a method reference to a no-argument method → the call (`this::defaultName` → `defaultName()`,
  `Theme::standard` → `Theme.standard()`)
- a lambda without parameters and with an expression body → that expression (`() -> "guest-" + id` → `"guest-" + id`)
- a lambda whose block body is a single `return expr;` → the same expression

Any other supplier (a variable, a longer block lambda) stays Java. The static-import form folds too.

Like `getOrDefaultElvis`, the fold applies only where the result stands on its own (initializer, assignment, `return`,
argument, lambda body), because `?:` binds looser than any operator or call around it. The one exception is a single
short call made on the result, which gets real parentheses: `requireNonNullElse(name, "").length()` →
`(name ?: "").length()`. A longer chain stays Java.

#### Example: RequireNonNullElseElvisTestData

examples/data/RequireNonNullElseElvisTestData.java:
```java
        String shown = Objects.requireNonNullElse(name, "anonymous");
        return Objects.requireNonNullElseGet(order.getTheme(), Theme::new);
        return Objects.requireNonNullElseGet(name, () -> "guest-" + id);
```

folded/RequireNonNullElseElvisTestData-folded.java:
```java
        String shown = name ?: "anonymous";
        return order.getTheme() ?: Theme();
        return name ?: "guest-" + id;
```

Default: On
Controlled by: `requireNonNullElseElvis`
Related features: `getOrDefaultElvis`
