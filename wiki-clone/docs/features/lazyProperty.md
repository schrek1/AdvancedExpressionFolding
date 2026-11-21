# Lazy Property (State field: lazyProperty)

### Null-checked lazy getter as by lazy property
Shows a field and the getter that initializes it on first use as one Kotlin-style `by lazy` property, the way Kotlin
writes `by lazy` and Scala `lazy val`.

#### Example: LazyPropertyTestData

examples/data/LazyPropertyTestData.java:
```java
    private Properties config;

    Properties config() {
        if (config == null) {
            config = loadConfig();
        }
        return config;
    }
```

folded/LazyPropertyTestData-folded.java:
```java
    private val config by lazy { loadConfig() }
```

The field folds when it is private, not final and has no initializer, and the method right below it (nothing but
blank lines in between) takes no arguments and consists of exactly `if (field == null) { field = <init>; }` and
`return field;` (braces, `this.` and `null == field` are optional). The getter disappears into the field line, and
the field name and `<init>` stay real code with their own colours and folds. A field with an initializer, a getter
elsewhere in the class, a getter with another statement or an `else`, a non-private field, or a field assigned
anywhere else in the class (a reset, a setter) stays as it is.

Default: On
Controlled by: `lazyProperty`
Related features: `getSetExpressionsCollapse`, `lombok`
