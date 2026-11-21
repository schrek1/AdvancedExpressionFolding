package data;

@SuppressWarnings("unused")
public class TodoTestData {

    public void export()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='TODO' expand='false'>throw new UnsupportedOperationException</fold>("Not implemented")<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public void exportWithoutReason()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='TODO' expand='false'>throw new UnsupportedOperationException</fold>()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public String render(String format) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>format.<fold text='empty' expand='false'>isEmpty()</fold><fold text='' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return "";<fold text='' expand='false'>
        }</fold></fold>
        <fold text='TODO' expand='false'>throw new UnsupportedOperationException</fold>("Format not supported: <fold text='$' expand='false'>" + </fold>format<fold text='")' expand='false'>)</fold>;
    }</fold>

    public void qualified()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='TODO' expand='false'>throw new java.lang.UnsupportedOperationException</fold>("Qualified name")<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public void withCause(Exception cause)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold>throw new UnsupportedOperationException(cause)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public void withReasonAndCause(Exception cause) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold>throw new UnsupportedOperationException("Not implemented", cause)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    public void otherException()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='error(' expand='false'>throw new IllegalStateException(</fold>"Not implemented"<fold text=')' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public void subclass()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold>throw new ReadOnlyException("Read only")<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public void anonymousSubclass() <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold>throw new UnsupportedOperationException("Anonymous") <fold text='{...}' expand='true'>{
        }</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    public void rethrow(UnsupportedOperationException existing)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold>throw existing<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public void guard(int size) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='assert' expand='false'>if</fold> <fold text='' expand='false'>(</fold>size <fold text='<=' expand='false'>></fold> 10) <fold text='{...}' expand='true'>{
          <fold text=':' expand='false'> </fold> <fold text='' expand='false'>throw new UnsupportedOperationException(</fold>"Too large"<fold text='' expand='false'>);
        }</fold></fold><fold text=' ' expand='true'>
    </fold>}</fold>

    static class ReadOnlyException extends UnsupportedOperationException <fold text='{...}' expand='true'>{
        ReadOnlyException(String message)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>super(message)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>
}
