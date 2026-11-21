# Lombok Utility Class (State field: lombokUtilityClass)

### Lombok Utility Class
Shows a class that holds only static members as Lombok's `@UtilityClass`.

A class folds when it has exactly one constructor, private, without parameters, whose body is empty or a single
`throw`, and every field, method and nested class is static. The constructor and the `static` modifiers are hidden,
and so is the class's `final` unless it is the first modifier.

A class with a static field of its own type (a singleton with an `INSTANCE`) does not fold, nor does a class with an
instance member, a non-private constructor or a constructor with other statements.

#### Example: LombokUtilityClassTestData

examples/data/LombokUtilityClassTestData.java:
```java
    public static final class Money {
        public static final int SCALE = 2;

        private Money() {
            throw new UnsupportedOperationException();
        }

        public static BigDecimal round(BigDecimal value) {
            return value.setScale(SCALE, RoundingMode.HALF_UP);
        }
    }
```

folded/LombokUtilityClassTestData-folded.java:
```java
    @UtilityClass public static class Money {
        public final int SCALE = 2;

        public BigDecimal round(BigDecimal value) {
            return value.setScale(SCALE, RoundingMode.HALF_UP);
        }
    }
```

Default: On (requires `lombok`)
Controlled by: `lombokUtilityClass`
Related features: `lombok`
