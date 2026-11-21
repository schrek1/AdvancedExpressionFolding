package data;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

public class DurationLiteralTestData {
    public void literals(OrderClient client) throws InterruptedException {
        client.timeout(30.seconds);
        Duration retryDelay = 250.milliseconds;
        Duration cacheTtl = 15.minutes;
        Duration sessionLength = 8L.hours;
        Duration retention = 30.days;
        Duration tick = 1_000.nanoseconds;

        sleep(500.milliseconds);
        sleep(10.microseconds);
        sleep(100.nanoseconds);
        sleep(1.minutes);
        sleep(2.seconds);
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
