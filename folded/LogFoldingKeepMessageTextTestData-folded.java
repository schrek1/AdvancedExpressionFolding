package data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

@SuppressWarnings("ALL")
public class LogFoldingKeepMessageTextTestData {

    private static final Logger log = LoggerFactory.getLogger(LogFoldingKeepMessageTextTestData.class);
    private static final Marker AUDIT = MarkerFactory.getMarker("AUDIT");

    public void updateSettings(String shopId, Settings previous, Settings updated, String user, long elapsed, int retries) {
        log.info("No pending refund for shop[id='$shopId'], nothing will be done");
        log.info("Updated settings for shop[id='$shopId'] (mode ${previous.mode} -> ${updated.mode})");
        log.debug("Settings of shop $shopId saved in ${elapsed}ms");
        log.warn("Retry ${retries + 1} of ${3} for shop $shopId");
        log.info(AUDIT, "Shop $shopId closed by $user");
        log.info("Shop $shopId is waiting for {}");

        try {
            save(updated);
        } catch (Exception e) {
            log.error("Saving settings of shop $shopId failed", e);
            log.error("Saving settings of shop $shopId failed: ${e.message}", e);
        }

        // A call with arguments reads inside the message; a lambda keeps the classic fold
        log.info("Shop $shopId has ${countOrders(shopId, previous)} open orders");
        log.info("Shop $shopId has ${previous.rules().stream().filter(rule -> rule.active).count()} rules");

        // printf style and String.formatted
        String summary = String.format("Shop $shopId runs in ${updated.mode} mode");
        System.out.println("Shop $shopId saved by $user");
        System.out.println("Shop $shopId saved".formatted(user));
    }

    public void wrappedMessages(String shopId, Settings previous, String user, Exception exception) {
        log.info("Shop $shopId kept in ${previous.mode}: it has no owner yet, so the" + " order cannot move; owner $user was asked");
        log.warn("Shop '$shopId' could not be closed by $user:" + " it still has orders", exception);
        log.info("Shop {} " + 1 + " stays", shopId);
    }

    private int countOrders(String shopId, Settings settings) {
        return 0;
    }

    private void save(Settings settings) {
    }

    public static class Settings {
        private String mode;
        private java.util.List<Rule> rules;

        public String getMode() {
            return mode;
        }

        public java.util.List<Rule> rules() {
            return rules;
        }
    }

    public static class Rule {
        private boolean active;

        public boolean isActive() {
            return active;
        }
    }
}
