package data;

@SuppressWarnings("unused")
public class TodoExpressionFuncTestData {

    public void export()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='TODO' expand='false'>throw new UnsupportedOperationException</fold>("Not implemented")<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public String exportWithoutReason()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='TODO' expand='false'>throw new UnsupportedOperationException</fold>()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public void otherException()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold>throw new IllegalStateException("Not implemented")<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>
}
