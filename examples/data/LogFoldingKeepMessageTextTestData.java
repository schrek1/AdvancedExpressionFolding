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
        log.info("No pending refund for shop[id='{}'], nothing will be done", shopId);
        log.info("Updated settings for shop[id='{}'] (mode {} -> {})", shopId, previous.getMode(), updated.getMode());
        log.debug("Settings of shop {} saved in {}ms", shopId, elapsed);
        log.warn("Retry {} of {} for shop {}", retries + 1, 3, shopId);
        log.info(AUDIT, "Shop {} closed by {}", shopId, user);
        log.info("Shop {} is waiting for {}", shopId);

        try {
            save(updated);
        } catch (Exception e) {
            log.error("Saving settings of shop {} failed", shopId, e);
            log.error("Saving settings of shop {} failed: {}", shopId, e.getMessage(), e);
        }

        // A call with arguments reads inside the message; a lambda keeps the classic fold
        log.info("Shop {} has {} open orders", shopId, countOrders(shopId, previous));
        log.info("Shop {} has {} rules", shopId, previous.rules().stream().filter(rule -> rule.isActive()).count());

        // printf style and String.formatted
        String summary = String.format("Shop %s runs in %s mode", shopId, updated.getMode());
        System.out.println("Shop %s saved by %s".formatted(shopId, user));
        System.out.println("Shop %s saved".formatted(shopId, user));
    }

    public void wrappedMessages(String shopId, Settings previous, String user, Exception exception) {
        log.info("Shop {} kept in {}: it has no owner yet, so the"
                + " order cannot move; owner {} was asked", shopId, previous.getMode(), user);
        log.warn("Shop '{}' could not be closed by {}:"
                + " it still has orders", shopId, user, exception);
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
