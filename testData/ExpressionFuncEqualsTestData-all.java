package data;

import java.util.HashMap;

public class ExpressionFuncEqualsTestData {

    private String field;

    <fold text='' expand='true'>public</fold><fold text='' expand='true'> </fold><fold text='' expand='true'>long</fold><fold text='' expand='true'> </fold>findNinjaId<fold text='' expand='true'>()</fold> <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>1L<fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    private void printStatus() <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='[:]' expand='false'>new HashMap<String, String>()</fold><fold text='[' expand='false'>.put(</fold>"a"<fold text='] = ' expand='false'>, </fold>"b"<fold text='' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    private String printStatusReturn() <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='[:]' expand='false'>new HashMap<String, String>()</fold><fold text='[' expand='false'>.put(</fold>"a"<fold text='] = ' expand='false'>, </fold>"b"<fold text='' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    <fold text='' expand='true'>public</fold><fold text='' expand='true'> </fold><fold text='' expand='true'>String</fold><fold text='' expand='true'> </fold>columnName(String column) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>"column1"<fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    public void assignField(String field) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold>this.field = <fold text='<<' expand='false'>field</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    public String methodCall(String field) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>assignFieldAndReturn(field)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    public String assignFieldAndReturn(String field) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>this.field = <fold text='<<' expand='false'>field</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    public String value() <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>name()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    private String name() <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>field<fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>
}
