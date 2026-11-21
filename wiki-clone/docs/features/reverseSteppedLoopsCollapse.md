# Reverse and Stepped Loops Collapse (State field: reverseSteppedLoopsCollapse)

### Reverse and Stepped Loops Collapse
Folds counting loops that go down or step by more than one. With `kotlinRangeLoops` on (the default) they read
as Kotlin ranges with `downTo` and `step`; with it off, in the interval style of `rangeExpressionsCollapse` with a
`step` after the interval. Only the changed tokens are folded: the variable, the bounds and a written step amount
stay real text.

#### Example: ReverseSteppedLoopsTestData (Kotlin syntax)

| Before | After |
|---|---|
| `for (int i = n - 1; i >= 0; i--)` | `for (i in n - 1 downTo 0)` |
| `for (int i = 0; i < n; i += 2)` | `for (i in 0..<n step 2)` |
| `for (long i = 0; i <= n; i += step)` | `for (i in 0..n step step)` |
| `for (int i = 100; i >= 0; i -= 10)` | `for (i in 100 downTo 0 step 10)` |

#### Example: ReverseSteppedLoopsIntervalTestData (`kotlinRangeLoops` off)

| Before | After |
|---|---|
| `for (int i = n - 1; i >= 0; i--)` | `for (int i : [n - 1, 0] step -1)` |
| `for (int i = n; i > 0; --i)` | `for (int i : [n, 0) step -1)` |
| `for (int i = 0; i < n; i += 2)` | `for (int i : [0, n) step 2)` |

Stays unfolded:
- a loop that goes down to an excluded end (`i > 0`) in Kotlin syntax: `downTo` always includes its end;
- the condition goes the other way than the step (`i < n; i--`);
- the update is not `--`, `+=` or `-=` (`i *= 2`);
- the step is not a single literal or variable (`i += step + 1`);
- the condition checks another variable than the loop variable.

Default: On
Controlled by: `reverseSteppedLoopsCollapse`
Related features: `rangeExpressionsCollapse` (forward `i++` loops), `kotlinRangeLoops` (Kotlin syntax)
