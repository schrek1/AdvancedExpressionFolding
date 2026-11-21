# In Operator (State field: inOperator)

### Membership and ranges as in / !in
Shows membership checks and two-sided bound checks the way Kotlin writes them, with `in` and `!in`.

#### Example: InOperatorTestData

examples/data/InOperatorTestData.java:
```java
        if (admins.contains(user) && !banned.contains(user)
                && age >= 18 && age <= 65 && i >= 0 && i < size) {
            System.out.println(user);
        }
        if (cache.containsKey(id)) {
            System.out.println(cache.get(id));
        }
```

folded/InOperatorTestData-folded.java:
```java
        if (user in admins && user !in banned
                && age in 18..65 && i in 0..<size) {
            System.out.println(user);
        }
        if (id in cache) {
            System.out.println(cache.get(id));
        }
```

Recognised forms:
- `c.contains(x)` → `x in c`, `!c.contains(x)` → `x !in c`, where `c` is a `java.util.Collection`
- `m.containsKey(k)` → `k in m`, where `m` is a `java.util.Map`
- `x >= a && x <= b` → `x in a..b` (closed range)
- `x >= a && x < b` → `x in a..<b` (half-open range); every such pair in one `&&` chain folds

The check stays as it is when:
- an operand may have side effects: a method call such as `loadAdmins().contains(user)`, `tags.contains(next())` or
  `i < tags.size()`; only literals, variables, field chains and arithmetic of those fold;
- the two bounds use different variables, or the variable is qualified (`this.age >= 0 && this.age <= 9`);
- the range is open at the start (`x > a && x <= b`) or written as `a < x && x < b`; with
  `rangeExpressionsCollapse` on, the first shows as `x in (a, b]` as before;
- the receiver is not a collection or map (`name.contains("x")` on a `String`).

Default: On
Controlled by: `inOperator`
Related features: `rangeExpressionsCollapse`, `comparingExpressionsCollapse`
