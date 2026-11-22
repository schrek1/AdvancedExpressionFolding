package data;

import <fold text='...' expand='false'>java.time.Duration;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;</fold>

public class DurationLiteralTestData {
    public void literals(OrderClient client) throws InterruptedException <fold text='{...}' expand='true'>{
        client.timeout(<fold text='' expand='false'>Duration.ofSeconds(</fold>30<fold text='.seconds' expand='false'>)</fold>);
        <fold text='val' expand='false'>Duration</fold> retryDelay = <fold text='' expand='false'>Duration.ofMillis(</fold>250<fold text='.milliseconds' expand='false'>)</fold>;
        <fold text='val' expand='false'>Duration</fold> cacheTtl = <fold text='' expand='false'>Duration.ofMinutes(</fold>15<fold text='.minutes' expand='false'>)</fold>;
        <fold text='val' expand='false'>Duration</fold> sessionLength = <fold text='' expand='false'>Duration.ofHours(</fold>8L<fold text='.hours' expand='false'>)</fold>;
        <fold text='val' expand='false'>Duration</fold> retention = <fold text='' expand='false'>Duration.ofDays(</fold>30<fold text='.days' expand='false'>)</fold>;
        <fold text='val' expand='false'>Duration</fold> tick = <fold text='' expand='false'>Duration.ofNanos(</fold>1_000<fold text='.nanoseconds' expand='false'>)</fold>;

        <fold text='' expand='false'>TimeUnit.MILLISECONDS.</fold>sleep(500<fold text='.milliseconds)' expand='false'>)</fold>;
        <fold text='' expand='false'>TimeUnit.MICROSECONDS.</fold>sleep(10<fold text='.microseconds)' expand='false'>)</fold>;
        <fold text='' expand='false'>TimeUnit.NANOSECONDS.</fold>sleep(100<fold text='.nanoseconds)' expand='false'>)</fold>;
        <fold text='' expand='false'>TimeUnit.MINUTES.</fold>sleep(1<fold text='.minutes)' expand='false'>)</fold>;
        <fold text='' expand='false'>SECONDS.</fold>sleep(2<fold text='.seconds)' expand='false'>)</fold>;
    }</fold>

    public void staysUnfolded(OrderClient client, long seconds, TimeUnit unit) throws InterruptedException <fold text='{...}' expand='true'>{
        <fold text='' expand='false'>client.</fold>timeout(Duration.ofSeconds(seconds));
        <fold text='' expand='false'>client.</fold>timeout(Duration.ofSeconds(seconds * 2));
        <fold text='' expand='false'>client.</fold>timeout(Duration.ofSeconds(-1));
        <fold text='' expand='false'>client.</fold>timeout(Duration.ofSeconds(30, 500));
        <fold text='' expand='false'>client.</fold>timeout(Duration.of(30, java.time.temporal.ChronoUnit.SECONDS))<fold text='' expand='false'>;</fold>
        unit.sleep(500);
        TimeUnit.SECONDS.sleep(seconds);
        Thread.sleep(500);
    }</fold>

    public interface OrderClient <fold text='{...}' expand='true'>{
        void timeout(Duration timeout);
    }</fold>
}
