## singletonObject

### Singleton as Kotlin `object`
Shows the classic eager singleton as a Kotlin `object`: the `class` keyword reads `object`, and the instance field,
the private constructor and the getter are hidden.

#### Example: SingletonObjectTestData

examples/data/SingletonObjectTestData.java:
```java
    static class Registry {
        private static final Registry INSTANCE = new Registry();

        private Registry() {}

        static Registry get() {
            return INSTANCE;
        }
    }
```

folded/SingletonObjectTestData-folded.java:
```java
    static object Registry {
    }
```

A class folds only when all of these hold:
- one `static final` field of its own type, initialized with `new Self()` (no arguments, no anonymous body);
- exactly one constructor: private, without parameters, with an empty body;
- at least one static getter without parameters whose body is just `return <that field>;`.

Not folded: lazy singletons, a public instance field without a getter, a constructor that does work or is not
private, enum singletons, and utility classes without an instance.

Default: On
Controlled by: `singletonObject`
Related features: (none)
