# Lombok Builder (State field: lombokBuilder)

### Display hand-written Lombok-shaped builder as @Builder
Hides a hand-written builder that has exactly the shape Lombok `@Builder` generates and shows `@Builder` on the class.

#### Example: LombokBuilderTestData

examples/data/LombokBuilderTestData.java:
```java
    static class Person {
        private final String name;
        private final int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        static Builder builder() {
            return new Builder();
        }

        static class Builder {
            private String name;
            private int age;

            Builder name(String name) {
                this.name = name;
                return this;
            }

            Builder age(int age) {
                this.age = age;
                return this;
            }

            Person build() {
                return new Person(name, age);
            }
        }
    }
```

folded/LombokBuilderTestData-folded.java:
```java
    @Builder @RequiredArgsConstructor(default) static class Person {
        private final String name;
        private final int age;
    }
```

The builder folds only when it matches Lombok exactly:
- a static nested class named `Builder` or `<Class>Builder` (another name is shown as `@Builder(Name)`), no type parameters;
- one builder field per instance field of the class, with the same name and type, in the same order, without initializers;
- one method per field, `X x(T x) { this.x = x; return this; }`, plus `build()` that only returns
  `new Class(f1, ..., fn)` with all fields in declaration order; an empty no-arg constructor is allowed;
- a static `builder()` on the class that only returns `new Builder()`.

Anything else, e.g. validation or a default in the builder, an extra method or a different argument order, keeps the
`@HasBuilder` label and the builder stays visible.

Default: On
Controlled by: `lombokBuilder`
Related features: `lombok`
