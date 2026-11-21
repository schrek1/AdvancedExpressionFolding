# Map Literals and Empty Collections (State field: mapAndEmptyCollectionLiterals)

### Map literals and empty collections: ["key": value], [], [:]
Shows map factories and empty collections in the bracket style the plugin already uses for list literals, as in
Groovy and Swift.

#### Example: MapAndEmptyCollectionLiteralsTestData

examples/data/MapAndEmptyCollectionLiteralsTestData.java:
```java
        Map<String, Integer> limits = Map.of("cpu", 4, "ram", 16);
        Map<String, Long> counts = new HashMap<>();
        List<String> names = List.of();
        Set<String> noIds = Collections.emptySet();
```

folded/MapAndEmptyCollectionLiteralsTestData-folded.java:
```java
        Map<String, Integer> limits = ["cpu": 4, "ram": 16];
        Map<String, Long> counts = [:];
        List<String> names = [];
        Set<String> noIds = [];
```

Folds:
- `Map.of(k1, v1, ...)` → `[k1: v1, ...]`
- `[:]`: `Map.of()`, `Collections.emptyMap()`, `new HashMap<>()`
- `[]`: `List.of()`, `Set.of()`, `Collections.emptyList()`, `Collections.emptySet()`, `new ArrayList<>()`, `new HashSet<>()`

Stays as written: constructors with arguments (`new HashMap<>(other)`), double-brace subclasses
(`new HashMap<>() {{ ... }}`), `Map.ofEntries(...)` and other map or list types (`TreeMap`, `LinkedList`, ...).

Default: On
Controlled by: `mapAndEmptyCollectionLiterals`
Related features: `getExpressionsCollapse`
