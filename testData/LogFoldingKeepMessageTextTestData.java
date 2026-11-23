package data;

import <fold text='...' expand='false'>org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;</fold>

@SuppressWarnings("ALL")
public class LogFoldingKeepMessageTextTestData {

    private static final Logger log = LoggerFactory.getLogger(LogFoldingKeepMessageTextTestData.class);
    private static final Marker AUDIT = MarkerFactory.getMarker("AUDIT");

    public void updateSettings(String shopId, Settings previous, Settings updated, String user, long elapsed, int retries) <fold text='{...}' expand='true'>{
        log.info("No pending refund for shop[id='<fold text='$shopId' expand='false'>{}</fold>'], nothing will be done"<fold text='' expand='false'>, shopId</fold>);
        log.info("Updated settings for shop[id='<fold text='$shopId' expand='false'>{}</fold>'] (mode <fold text='${previous.mode}' expand='false'>{}</fold> -> <fold text='${updated.mode}' expand='false'>{}</fold>)"<fold text='' expand='false'>, shopId, previous.<fold text='m' expand='false'>getM</fold>ode<fold text='' expand='false'>()</fold>, updated.<fold text='m' expand='false'>getM</fold>ode<fold text='' expand='false'>()</fold></fold>);
        log.debug("Settings of shop <fold text='$shopId' expand='false'>{}</fold> saved in <fold text='${elapsed}' expand='false'>{}</fold>ms"<fold text='' expand='false'>, shopId, elapsed</fold>);
        log.warn("Retry <fold text='${retries + 1}' expand='false'>{}</fold> of <fold text='${3}' expand='false'>{}</fold> for shop <fold text='$shopId' expand='false'>{}</fold>"<fold text='' expand='false'>, retries + 1, 3, shopId</fold>);
        log.info(AUDIT, "Shop <fold text='$shopId' expand='false'>{}</fold> closed by <fold text='$user' expand='false'>{}</fold>"<fold text='' expand='false'>, shopId, user</fold>);
        log.info("Shop <fold text='$shopId' expand='false'>{}</fold> is waiting for {}"<fold text='' expand='false'>, shopId</fold>);

        try <fold text='{...}' expand='true'>{
            save(updated);
        }</fold> catch (Exception e) <fold text='{...}' expand='true'>{
            log.error("Saving settings of shop <fold text='$shopId' expand='false'>{}</fold> failed"<fold text='' expand='false'>, shopId</fold>, e);
            log.error("Saving settings of shop <fold text='$shopId' expand='false'>{}</fold> failed: <fold text='${e.message}' expand='false'>{}</fold>"<fold text='' expand='false'>, shopId, e.<fold text='m' expand='false'>getM</fold>essage<fold text='' expand='false'>()</fold></fold>, e);
        }</fold>

        // A call with arguments reads inside the message; a lambda keeps the classic fold
        log.info("Shop <fold text='$shopId' expand='false'>{}</fold> has <fold text='${countOrders(shopId, previous)}' expand='false'>{}</fold> open orders"<fold text='' expand='false'>, shopId, countOrders(shopId, previous)</fold>);
        log.info("Shop <fold text='$' expand='false'>{} has {} rules", </fold>shopId<fold text=' has ${' expand='false'>, </fold>previous.rules().stream().filter(rule -> rule.<fold text='a' expand='false'>isA</fold>ctive<fold text='' expand='false'>()</fold>).count()<fold text='} rules")' expand='false'>)</fold>;

        // printf style and String.formatted
        String summary = String.format("Shop <fold text='$shopId' expand='false'>%s</fold> runs in <fold text='${updated.mode}' expand='false'>%s</fold> mode"<fold text='' expand='false'>, shopId, updated.<fold text='m' expand='false'>getM</fold>ode<fold text='' expand='false'>()</fold></fold>);
        System.out.println("Shop <fold text='$shopId' expand='false'>%s</fold> saved by <fold text='$user' expand='false'>%s</fold>"<fold text='' expand='false'>.formatted(shopId, user)</fold>);
        System.out.println("Shop <fold text='$shopId' expand='false'>%s</fold> saved".formatted(<fold text='' expand='false'>shopId, </fold>user));
    }</fold>

    public void wrappedMessages(String shopId, Settings previous, String user, Exception exception) <fold text='{...}' expand='true'>{
        log.info("Shop <fold text='$shopId' expand='false'>{}</fold> kept in <fold text='${previous.mode}' expand='false'>{}</fold>: it has no owner yet, so the"<fold text=' + ' expand='false'>
                + </fold>" order cannot move; owner <fold text='$user' expand='false'>{}</fold> was asked"<fold text='' expand='false'>, shopId, previous.<fold text='m' expand='false'>getM</fold>ode<fold text='' expand='false'>()</fold>, user</fold>);
        log.warn("Shop '<fold text='$shopId' expand='false'>{}</fold>' could not be closed by <fold text='$user' expand='false'>{}</fold>:"<fold text=' + ' expand='false'>
                + </fold>" it still has orders"<fold text='' expand='false'>, shopId, user</fold>, exception);
        log.info("Shop {} " + 1 + " stays", shopId);
    }</fold>

    private int countOrders(String shopId, Settings settings)<fold text=' { ' expand='false'> {
        </fold>return 0;<fold text=' }' expand='false'>
    }</fold>

    private void save(Settings settings) <fold text='{}' expand='true'>{
    }</fold>

    public static class Settings <fold text='{...}' expand='true'>{
        private String mode;
        private java.util.List<Rule> rules;

        public String getMode()<fold text=' { ' expand='false'> {
            </fold>return mode;<fold text=' }' expand='false'>
        }</fold>

        public java.util.List<Rule> rules()<fold text=' { ' expand='false'> {
            </fold>return rules;<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    public static class Rule <fold text='{...}' expand='true'>{
        private boolean active;

        public boolean isActive()<fold text=' { ' expand='false'> {
            </fold>return active;<fold text=' }' expand='false'>
        }</fold>
    }</fold>
}
