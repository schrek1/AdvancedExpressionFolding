## kotlinConstructorCall

### Constructor calls without `new` (Kotlin style)
Hides the `new` keyword of a plain instance creation, so it reads like a Kotlin constructor call.
Only `new` and the space after it are folded; the class name and the arguments keep their highlighting.

#### Example: KotlinConstructorCallTestData

examples/data/KotlinConstructorCallTestData.java:
```java
        var order = new Order(id, total);
// ...
        return new Page<>(items, number);
// ...
        throw new IllegalArgumentException("Unsupported code " + code);
// ...
        Runnable task = new Runnable() {
// ...
        int[] counts = new int[3];
        Line line = outer.new Line();
```

folded/KotlinConstructorCallTestData-folded.java:
```java
        var order = Order(id, total);
// ...
        return Page<>(items, number);
// ...
        throw IllegalArgumentException("Unsupported code " + code);
// ...
        Runnable task = new Runnable() {
// ...
        int[] counts = new int[3];
        Line line = outer.new Line();
```

Rules:
- Folds only a plain `new T(args)`, including diamond `new T<>(args)`.
- Anonymous classes, arrays and qualified `outer.new Inner()` keep `new`.
- Where another folding already rewrites the creation (collection literals, `::new`, builder or setter arguments), that folding wins.

Default: On
Controlled by: `kotlinConstructorCall`
Related features: `constructorReferenceNotation`
