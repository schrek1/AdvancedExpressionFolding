---
title: TODO for UnsupportedOperationException
slug: /features/todo
sidebar_label: TODO
description: Read `throw new UnsupportedOperationException("reason")` as Kotlin's `TODO("reason")`.
---

Stub methods read like Kotlin's `TODO()`: the `throw new UnsupportedOperationException` prefix folds to `TODO`, and the reason stays visible.

```java
// unfolded
public void export() {
    throw new UnsupportedOperationException("Not implemented");
}

// folded
public void export() {
    TODO("Not implemented");
}
```

Only `java.lang.UnsupportedOperationException` itself folds, with no argument (`TODO()`) or a single `String` reason. Subclasses, anonymous subclasses, other exceptions and a `cause` argument stay as written.
