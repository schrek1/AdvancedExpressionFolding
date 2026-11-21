# Repeat Loop (State field: repeatLoop)

### Counting for loops with an unused index as repeat(n)
Shows a counting `for` loop the way Kotlin writes it when the loop only counts and never looks at its index.

#### Example: RepeatLoopTestData

examples/data/RepeatLoopTestData.java:
```java
        for (int i = 0; i < retries; i++) {
            ping();
        }
```

folded/RepeatLoopTestData-folded.java:
```java
        repeat(retries) {
            ping();
        }
```

The loop folds when it starts at `0`, compares with `<`, steps with `i++` or `++i`, and the body never reads or writes
the index (`int` or `long`). Otherwise it keeps the range form of `rangeExpressionsCollapse`, e.g. a loop that prints
`i`, assigns to it, starts at `1` or uses `<=`.

Default: On
Controlled by: `repeatLoop`
Related features: `rangeExpressionsCollapse`
