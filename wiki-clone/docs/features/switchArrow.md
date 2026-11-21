# Classic Switch as Arrow Switch (State field: switchArrow)

### Classic switch as arrow switch
Shows an old-style `switch` with `case:` and `break` as an arrow switch (`case A, B -> statement;`).

#### Example: SwitchArrowTestData

examples/data/SwitchArrowTestData.java:
```java
        switch (status) {
            case NEW:
            case PAID:
                start();
                break;
            case SHIPPED, DONE:
                archive();
                break;
            default:
                log("unexpected " + status);
        }
```

folded/SwitchArrowTestData-folded.java:
```java
        switch (status) {
            case NEW, PAID -> start();
            case SHIPPED, DONE -> archive();
            default -> log("unexpected " + status);
        }
```

The switch folds only when every case is safe to read as an arrow branch:
- one statement followed by a plain `break`,
- a lone `return` or `throw`,
- or, in the last case, one statement without `break`.

Labels stacked on one body (`case A: case B:`) show as `case A, B ->`, and `break;` is hidden. The `switch`, `case` and
`default` keywords and the statements stay as written. Fall-through, a case with more statements, an empty
`case X: break;`, a labeled `break`, a comment anywhere in the switch, pattern labels and arrow switches keep the switch
as it is.

Default: On
Controlled by: `switchArrow`
Related features: `compactControlFlowSyntaxCollapse`
