package data;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

public class DurationLiteralTestData {
    public void literals(OrderClient client) throws InterruptedException {
        client.timeout(Duration.ofSeconds(30));
        Duration retryDelay = Duration.ofMillis(250);
        Duration cacheTtl = Duration.ofMinutes(15);
        Duration sessionLength = Duration.ofHours(8L);
        Duration retention = Duration.ofDays(30);
        Duration tick = Duration.ofNanos(1_000);

        TimeUnit.MILLISECONDS.sleep(500);
        TimeUnit.MICROSECONDS.sleep(10);
        TimeUnit.NANOSECONDS.sleep(100);
        TimeUnit.MINUTES.sleep(1);
        SECONDS.sleep(2);
    }

    public void staysUnfolded(OrderClient client, long seconds, TimeUnit unit) throws InterruptedException {
        client.timeout(Duration.ofSeconds(seconds));
        client.timeout(Duration.ofSeconds(seconds * 2));
        client.timeout(Duration.ofSeconds(-1));
        client.timeout(Duration.ofSeconds(30, 500));
        client.timeout(Duration.of(30, java.time.temporal.ChronoUnit.SECONDS));
        unit.sleep(500);
        TimeUnit.SECONDS.sleep(seconds);
        Thread.sleep(500);
    }

    public interface OrderClient {
        void timeout(Duration timeout);
    }
}
