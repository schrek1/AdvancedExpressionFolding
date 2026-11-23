# Log Folding: Keep the Message Text (State field: logFoldingKeepMessageText)

### Log folding: keep the message text, fold only the arguments
The log message stays real, string-coloured source. Only each `{}` / `%s` that has an argument is folded into that
argument, and the argument list after the message is hidden.

#### Example: LogFoldingKeepMessageTextTestData

examples/data/LogFoldingKeepMessageTextTestData.java:
```java
        log.info("Updated settings for shop[id='{}'] (mode {} -> {})", shopId, previous.getMode(), updated.getMode());
        log.error("Saving settings of shop {} failed", shopId, e);
```

folded/LogFoldingKeepMessageTextTestData-folded.java:
```java
        log.info("Updated settings for shop[id='$shopId'] (mode ${previous.mode} -> ${updated.mode})");
        log.error("Saving settings of shop $shopId failed", e);
```

Rules:
- a plain variable reads `$name`; anything else reads `${...}`, with getters shown as properties when the getter fold is on;
- an argument beyond the placeholders (a trailing exception) stays visible;
- an argument may be a call with arguments (`${countOrders(shopId, previous)}`); only a call whose argument holds a
  lambda, spans several lines or is longer than about 40 characters keeps the classic log folding;
- marker loggers, `String.format`, `printf` and `String.formatted` follow the same rules.

Default: Off
Controlled by: `logFoldingKeepMessageText`
Related features: `logFolding`
