# instanceof Chain as Pattern Switch (State field: instanceofSwitch)

### instanceof chain as pattern switch
Shows an `if / else if` chain of `instanceof` checks on one variable the way a Java pattern `switch` reads.

#### Example: InstanceofSwitchTestData

examples/data/InstanceofSwitchTestData.java:
```java
        if (shape instanceof Circle c) {
            area = Math.PI * c.r() * c.r();
        } else if (shape instanceof Square s) {
            area = s.side() * s.side();
        }
```

folded/InstanceofSwitchTestData-folded.java:
```java
        switch (shape) {
            case Circle c -> area = Math.PI * c.r() * c.r();
            case Square s -> area = s.side() * s.side();
        }
```

A final `else` becomes `default -> ...`. Record patterns (`case Circle(double r)`) fold too, and a type check
without a binding shows Java's unnamed variable (`case Shape _`). The folded switch is a reading aid: a chain without a
final `else` shows a switch without `default`, which Java would only accept for an exhaustive (sealed) subject.

The chain folds when it has at least two `instanceof` branches, every condition tests the same variable, and every
branch is a braced block holding exactly one statement on its own line. It stays as it is when a condition is guarded
(`x instanceof String s && !s.isEmpty()`) or is not an `instanceof`, when a branch has more statements or no braces,
when the subject is a method call (each Java branch calls it again), or when the chain contains a comment.

Default: On
Controlled by: `instanceofSwitch`
Related features: `patternMatchingInstanceof`
