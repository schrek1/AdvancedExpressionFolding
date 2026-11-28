package data;

public class UnqualifiedGetterTestData {
    private String request = "code=42";

    private String getCodeFromRequest()<fold text=' { ' expand='false'> {
        </fold>return request.substring(5);<fold text=' }' expand='false'>
    }</fold>

    private boolean isReady()<fold text=' { ' expand='false'> {
        </fold>return !request.<fold text='e' expand='false'>isE</fold>mpty<fold text='' expand='false'>()</fold>;<fold text=' }' expand='false'>
    }</fold>

    public String own() <fold text='{...}' expand='true'>{
        String code = getCodeFromRequest();
        boolean ready = isReady();
        return code + ready + this.<fold text='c' expand='false'>getC</fold>odeFromRequest<fold text='' expand='false'>()</fold> + new UnqualifiedGetterTestData().<fold text='c' expand='false'>getC</fold>odeFromRequest<fold text='' expand='false'>()</fold>;
    }</fold>
}
