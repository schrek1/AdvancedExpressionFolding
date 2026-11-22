# Stream map-to-list chain as spread (State field: streamSpreadChain)

### Stream map-to-list chain as spread: items*.id
Shows a whole `stream().map(Type::accessor).toList()` chain as the spread `items*.accessor`. The receiver and a record
accessor's name stay real text; only `.stream()`, `.map(Type::`, `)` and the terminal are folded.

Default: On
Controlled by: `streamSpreadChain`
Related features: `streamSpread` (shows the single `map` step as `*.`)

---

#### Detailed Folding Reference

**Record accessor, getter, List and Set terminals**
examples/data/StreamSpreadChainTestData.java:
```java
        List<String> ids = orders.pending.stream().map(Order::id).toList();
        List<Customer> buyers = history.stream().map(Order::customer).collect(Collectors.toList());
        Set<String> names = customers.stream().map(Customer::getName).collect(Collectors.toSet());
```
folded/StreamSpreadChainTestData-folded.java:
```java
        List<String> ids = orders.pending*.id;
        List<Customer> buyers = history*.customer;
        Set<String> names = customers*.name.toSet();
```

**`collect` with a List or Set collector, any chain**
examples/data/StreamSpreadChainTestData.java:
```java
        Set<String> paidIdSet = history.stream()
                .filter(Order::paid)
                .map(Order::id)
                .collect(toSet());
        List<String> sortedIds = history.stream().map(Order::id).sorted().collect(Collectors.toList());
```
folded/StreamSpreadChainTestData-folded.java:
```java
        Set<String> paidIdSet = history.stream()
                .filter(Order::paid)
                .map(Order::id)
                .toSet();
        List<String> sortedIds = history.stream().map(Order::id).sorted().toList();
```
Only `collect(Collectors.` (or `collect(` for a static import) and the closing `)` are folded; `toSet()` / `toList()`
stays real text.

**Stays unfolded**
- `map` with a lambda: `history.stream().map(order -> order.id().toUpperCase()).toList()`;
- the spread itself when another stream step comes before or after `map` (`filter(...)`, `sorted()`); a List or Set
  collector at its end still shows as `.toList()` / `.toSet()`;
- any other collector, e.g. `Collectors.joining(", ")`; the chain then keeps `collect(...)` as written.
