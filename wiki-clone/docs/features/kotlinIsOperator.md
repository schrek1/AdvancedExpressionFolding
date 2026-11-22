## kotlinIsOperator

### instanceof as Kotlin is / !is
Shows Java type checks the way Kotlin writes them.

| Java | Shown as |
|---|---|
| `x instanceof Foo` | `x is Foo` |
| `!(x instanceof Foo)` | `x !is Foo` |
| `x instanceof Foo f` | `x is Foo f` |

Only `instanceof` (and, for a negation, the `!(` and `)` around the check) is folded. The operand, the type and the
pattern binding stay source text with their syntax colours; the binding keeps its name, so later usages read the same.

#### Example: IsOperatorTestData

examples/data/IsOperatorTestData.java:
```java
        if (shape instanceof Circle c) {
// ...
        if (!(shape instanceof Square square)) {
```

folded/IsOperatorTestData-folded.java:
```java
        if (shape is Circle c) {
// ...
        if (shape !is Square square) {
```

Default: Off
Controlled by: `kotlinIsOperator`
Related features: `patternMatchingInstanceof`, `emojify`
