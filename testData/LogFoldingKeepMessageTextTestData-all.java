package data;

import <fold text='...' expand='false'>org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;</fold>

@SuppressWarnings("ALL")
<fold text='@Log p' expand='false'>p</fold>ublic class LogFoldingKeepMessageTextTestData {<fold text='' expand='false'>

    </fold><fold text='' expand='false'>private <fold text='const' expand='false'>static final </fold><fold text='' expand='false'>Logger</fold> log = LoggerFactory.getLogger(LogFoldingKeepMessageTextTestData.class);</fold>
    private <fold text='const' expand='false'>static final </fold><fold text='' expand='false'>Marker</fold> AUDIT = MarkerFactory.getMarker("AUDIT");

    public void updateSettings(String shopId, Settings previous, Settings updated, String user, long elapsed, int retries) <fold text='{...}' expand='true'>{
        log.info("No pending refund for shop[id='<fold text='$shopId' expand='false'>{}</fold>'], nothing will be done"<fold text='' expand='false'>, shopId</fold>);
        log.info("Updated settings for shop[id='<fold text='$shopId' expand='false'>{}</fold>'] (mode <fold text='${previous.mode}' expand='false'>{}</fold> -> <fold text='${updated.mode}' expand='false'>{}</fold>)"<fold text='' expand='false'>, shopId, previous.<fold text='mode' expand='false'>getMode()</fold>, updated.<fold text='mode' expand='false'>getMode()</fold></fold>);
        log.debug("Settings of shop <fold text='$shopId' expand='false'>{}</fold> saved in <fold text='${elapsed}' expand='false'>{}</fold>ms"<fold text='' expand='false'>, shopId, elapsed</fold>);
        log.warn("Retry <fold text='${retries + 1}' expand='false'>{}</fold> of <fold text='${3}' expand='false'>{}</fold> for shop <fold text='$shopId' expand='false'>{}</fold>"<fold text='' expand='false'>, retries + 1, 3, shopId</fold>);
        log.info(AUDIT, "Shop <fold text='$shopId' expand='false'>{}</fold> closed by <fold text='$user' expand='false'>{}</fold>"<fold text='' expand='false'>, shopId, user</fold>);
        log.info("Shop <fold text='$shopId' expand='false'>{}</fold> is waiting for {}"<fold text='' expand='false'>, shopId</fold>);

        try <fold text='{...}' expand='true'>{
            save(updated);
        }</fold> catch <fold text='' expand='false'>(</fold>Exception e<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            log.error("Saving settings of shop <fold text='$shopId' expand='false'>{}</fold> failed"<fold text='' expand='false'>, shopId</fold>, e);
            log.error("Saving settings of shop <fold text='$shopId' expand='false'>{}</fold> failed: <fold text='${e.message}' expand='false'>{}</fold>"<fold text='' expand='false'>, shopId, e.<fold text='message' expand='false'>getMessage()</fold></fold>, e);
        }</fold>

        // An argument that does not read well inside the message keeps the classic fold
        log.info("Shop <fold text='$' expand='false'>{} has {} open orders", </fold>shopId<fold text=' has ${' expand='false'>, </fold>countOrders(shopId, previous)<fold text='} open orders")' expand='false'>)</fold>;
        log.info("Shop <fold text='$' expand='false'>{} has {} rules", </fold>shopId<fold text=' has ${' expand='false'>, </fold>previous.rules()<fold text='.' expand='false'>.stream().</fold>filter(rule -> rule.<fold text='active' expand='false'>isActive()</fold>).count()<fold text='} rules")' expand='false'>)</fold>;

        // printf style and String.formatted
        <fold text='val' expand='false'>String</fold> summary = String.format("Shop <fold text='$shopId' expand='false'>%s</fold> runs in <fold text='${updated.mode}' expand='false'>%s</fold> mode"<fold text='' expand='false'>, shopId, updated.<fold text='mode' expand='false'>getMode()</fold></fold>);
        <fold text='' expand='false'>System.out.</fold>println("Shop <fold text='$shopId' expand='false'>%s</fold> saved by <fold text='$user' expand='false'>%s</fold>"<fold text='' expand='false'>.formatted(shopId, user)</fold>);
        <fold text='' expand='false'>System.out.</fold>println("Shop <fold text='$shopId' expand='false'>%s</fold> saved".formatted(<fold text='' expand='false'>shopId, </fold>user));
    }</fold>

    private int countOrders(String shopId, Settings settings)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>0<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    private void save(Settings settings) <fold text='{}' expand='true'>{
    }</fold>

    public static class Settings <fold text='{...}' expand='true'>{
        <fold text='@Getter p' expand='false'>p</fold>rivate String mode;
        private java.util.List<Rule> rules;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>public String getMode()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>mode<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>

        public java.util.List<Rule> rules()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>rules<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>

    <fold text='@Getter p' expand='false'>p</fold>ublic static class Rule <fold text='{...}' expand='true'>{
        private boolean active;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>public boolean isActive()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>active<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>
    }</fold>
}
