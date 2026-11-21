# Builder Named Arguments (State field: builderNamedArguments)

### Builder chain as constructor call Person(name = ...)
Shows a builder chain that creates a new object as a constructor call with named arguments, like in Kotlin or
Groovy. A single field folds to one line; several fields keep one argument per line, indented one level deeper than
the line the call starts on, with `)` back on that level.

#### Example: BuilderNamedArgumentsTestData

examples/data/BuilderNamedArgumentsTestData.java:
```java
        var alice = Person.builder()
                .name("Alice")
                .age(31)
                .build();
        Person bob = Person.builder().name("Bob").build();
```

folded/BuilderNamedArgumentsTestData-folded.java:
```java
        var alice = Person(
            name = "Alice",
            age = 31
        );
        Person bob = Person(name = "Bob");
```

Only a clean chain folds: a static `builder()` called on its class, then one-argument builder calls that set a
builder field of the same name, then `build()`. A chain with any other call (`greet()`, `nameUpperCase(...)`),
without `build()`, or without any field (`Person.builder().build()`) stays as it is. Argument values keep their own
foldings, so a nested builder folds too: `manager = Person(name = "Frank")`.

Default: On
Controlled by: `builderNamedArguments`
Related features: `fieldShift`, `lombok`
