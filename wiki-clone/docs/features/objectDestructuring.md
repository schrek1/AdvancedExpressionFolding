# Object Destructuring (State field: objectDestructuring)

### Destructuring locals read from record accessors & getters
Shows consecutive locals read from accessors of one object the way Kotlin destructures it:
`val (customer, total, currency) = order`.

#### Example: ObjectDestructuringTestData

examples/data/ObjectDestructuringTestData.java:
```java
        String customer = order.customer();
        BigDecimal total = order.total();
        String currency = order.currency();
```

folded/ObjectDestructuringTestData-folded.java:
```java
        val (customer, total, currency) = order;
```

Getters fold the same way: `String name = shop.getName(); boolean open = shop.isOpen();` shows as
`val (name, open) = shop;`. If one of the locals is reassigned later, the fold shows `var (...)`.

The locals stay as they are when:
- there is only one of them;
- a local is not named after its accessor (`String buyer = order.customer();`);
- they read from different objects, or the object is itself a call (`order.copy().customer()`);
- the method takes arguments, or a plain `name()` is not a record accessor;
- the object is a cast right before them (`Order order = (Order) o;`) and `patternMatchingInstanceof` is on, which
  shows these locals as a record pattern `o instanceof Order(...)` instead.

Default: On
Controlled by: `objectDestructuring`
Related features: `destructuring` (arrays and lists), `varExpressionsCollapse` (`val` / `var`)
