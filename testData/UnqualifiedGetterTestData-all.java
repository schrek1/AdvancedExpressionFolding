package data;

public class UnqualifiedGetterTestData {
    private String request = "code=42";

    private String getCodeFromRequest()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>request<fold text='[' expand='false'>.substring(</fold>5<fold text=':]' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    private boolean isReady()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>!request.<fold text='e' expand='false'>isE</fold>mpty<fold text='' expand='false'>()</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public String own() <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>String</fold> code = getCodeFromRequest();
        <fold text='val' expand='false'>boolean</fold> ready = isReady();
        return code + ready + this.<fold text='c' expand='false'>getC</fold>odeFromRequest<fold text='' expand='false'>()</fold> + <fold text='' expand='false'>new </fold>UnqualifiedGetterTestData().<fold text='c' expand='false'>getC</fold>odeFromRequest<fold text='' expand='false'>()</fold>;
    }</fold>
}
