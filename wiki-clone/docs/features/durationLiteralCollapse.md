# Duration Literal Collapse (State field: durationLiteralCollapse)

### Duration Literal Collapse
Shows `Duration.ofX(n)` and `TimeUnit.X.sleep(n)` as unit literals, inspired by Kotlin's `kotlin.time`.

#### Example: DurationLiteralTestData

examples/data/DurationLiteralTestData.java:
```java
        client.timeout(Duration.ofSeconds(30));
        Duration retryDelay = Duration.ofMillis(250);
        Duration cacheTtl = Duration.ofMinutes(15);
// ...
        TimeUnit.MILLISECONDS.sleep(500);
        TimeUnit.MINUTES.sleep(1);
        SECONDS.sleep(2);
```

folded/DurationLiteralTestData-folded.java:
```java
        client.timeout(30.seconds);
        Duration retryDelay = 250.milliseconds;
        Duration cacheTtl = 15.minutes;
// ...
        sleep(500.milliseconds);
        sleep(1.minutes);
        sleep(2.seconds);
```

Units: `nanoseconds`, `microseconds`, `milliseconds`, `seconds`, `minutes`, `hours`, `days`.

Only a plain number literal folds. These stay as they are:
```java
        client.timeout(Duration.ofSeconds(seconds));
        client.timeout(Duration.ofSeconds(-1));
        client.timeout(Duration.ofSeconds(30, 500));
        unit.sleep(500);
        Thread.sleep(500);
```

Default: On
Controlled by: `durationLiteralCollapse`
Related features: `localDateLiteralCollapse`
