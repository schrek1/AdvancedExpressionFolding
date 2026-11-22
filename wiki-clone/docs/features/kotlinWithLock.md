# Kotlin withLock (State field: kotlinWithLock)

### Lock, try, finally unlock as Kotlin withLock
Shows the explicit lock pattern the way Kotlin's `withLock` writes it.

#### Example: WithLockTestData

examples/data/WithLockTestData.java:
```java
        lock.lock();
        try {
            balance += amount;
        } finally {
            lock.unlock();
        }
```

folded/WithLockTestData-folded.java:
```java
        withLock(lock) {
            balance += amount;
        }
```

Folds only when:
- the `lock()` call stands right before the `try`,
- the `finally` block holds nothing but the `unlock()` of the same lock,
- the `try` has no `catch` and no resources,
- the lock is a field or variable (`lock`, `this.lock`) or a `readLock()` / `writeLock()` view of one.

A `tryLock()` guard, a lock taken inside the `try`, or a lock returned by any other call stays as Java.

Default: On
Controlled by: `kotlinWithLock`
Related features: (none)

---
### Folding catalogue

#### WithLockTestData

##### Scenario 1

**Before**
```java
        stockLock.readLock().lock();
        try {
            int current = balance;
            return current * 2;
        } finally {
            stockLock.readLock().unlock();
        }
```

**After**
```java
        withLock(stockLock.readLock()) {
            int current = balance;
            return current * 2;
        }
```
