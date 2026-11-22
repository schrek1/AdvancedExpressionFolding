# Setters as Named Arguments (State field: setterNamedArguments)

### Setters on a new object as Person(name = ...)
Shows a new object built with a no-argument constructor and then filled by setters as one constructor call with
named arguments, like a Kotlin constructor. A single setter folds to one line; several setters keep one argument
per line at the setters' own indentation. Only the changed tokens fold (`new `, `();`, `p.set` with the name's first
letter, `(` as ` = `, `);` as `,`): the class name, the rest of each property name, the values and the last `);`
stay real text.

#### Example: SetterNamedArgumentsTestData

examples/data/SetterNamedArgumentsTestData.java:
```java
        var alice = new Person();
        alice.setName("Alice");
        alice.setAge(31);
        alice.setActive(true);

        Person bob = new Person();
        bob.setName("Bob");
```

folded/SetterNamedArgumentsTestData-folded.java:
```java
        val alice = Person(
        name = "Alice",
        age = 31,
        active = true);

        val bob = Person(name = "Bob");
```

The setters that fold are the ones right after the declaration: `p.setX(value);` with one argument. The run ends
at the first other statement, at a comment, at a setter whose value reads `p` itself (`p.setAge(p.getAge() + 1)`),
and at a second setter of the same property; those stay setters below the folded call. A constructor with
arguments (`new Person("Dave")`) or an anonymous class stays as it is. Argument values keep their own foldings,
e.g. `name = source<<` with `fieldShift`.

Default: On
Controlled by: `setterNamedArguments`
Related features: `getSetExpressionsCollapse`, `varExpressionsCollapse`, `fieldShift`
