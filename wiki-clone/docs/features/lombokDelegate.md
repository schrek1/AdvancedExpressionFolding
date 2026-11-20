# Lombok Delegate (State field: lombokDelegate)

### Forwarding methods as Lombok @Delegate
Hides methods that only pass their call on to a field, and marks that field with `@Delegate`, the way Lombok writes it.

#### Example: LombokDelegateTestData

examples/data/LombokDelegateTestData.java:
```java
        private final List<String> items = new ArrayList<>();

        public int size() {
            return items.size();
        }

        public void add(String item) {
            items.add(item);
        }
```

folded/LombokDelegateTestData-folded.java:
```java
        @Delegate private final List<String> items = new ArrayList<>();
```

A method folds when it is public and its body is a single `return field.sameName(a, b)` (or `field.sameName(a, b);`
for a `void` method): the call goes to a non-static field of the same class (`items` or `this.items`), the called
method has the same name, and the arguments are the method's own parameters, unchanged and in the same order.

A different method name, a changed or reordered argument, a second statement, a comment in the body, a call on
another object's field, or a non-public method keeps the method visible. Getters, setters, `equals`, `hashCode` and
`toString` stay with their own Lombok folds.

Default: On (works together with `lombok`)
Controlled by: `lombokDelegate`
Related features: `lombok`
