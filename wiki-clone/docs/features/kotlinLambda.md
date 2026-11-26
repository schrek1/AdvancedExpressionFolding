# Kotlin Lambda (State field: kotlinLambda)

### Lambdas in Kotlin syntax: { it.x }
Shows Java lambdas the way Kotlin writes them, with the implicit `it` for a single parameter and a trailing lambda
when the lambda is the last argument of a call.

#### Example: KotlinLambdaTestData

examples/data/KotlinLambdaTestData.java:
```java
        return items.stream().anyMatch(d -> d.getDirection() == Direction.OUT);
        return (a, b) -> a + b;
        withSupplierFirst(() -> item.getPrice(), 0);
        stock.forEach((name, count) -> {
            System.out.println(name + count);
        });
```

folded/KotlinLambdaTestData-folded.java:
```java
        return items.stream().anyMatch { it.direction == Direction.OUT };
        return { a, b -> a + b };
        withSupplierFirst({ item.price }, 0);
        stock.forEach { name, count ->
            System.out.println(name + count);
        };
```

Only the braces, the separators and each `it` are folded; the lambda body stays real code with its highlighting, and
its own folds (getters, streams, interpolated strings) keep working.

- A lambda nested in an `it` lambda keeps its parameter name, so `it` is never ambiguous; so does a lambda whose body
  uses another variable named `it`.
- A lambda inside a call that another fold already rewrites (for example `orElseGet` shown as `?:`) stays Java.
- Method references are not changed.

Default: On
Controlled by: `kotlinLambda`
