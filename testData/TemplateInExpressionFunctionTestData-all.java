package data;

import java.time.LocalDateTime;

public class TemplateInExpressionFunctionTestData {
    String changedSuffix(LocalDateTime changedAt)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='if (' expand='true'> </fold>changedAt == null<fold text=')' expand='false'> ?</fold> "" <fold text='else' expand='false'>:</fold> ", changed at <fold text='$' expand='false'>" + </fold>changedAt<fold text='"' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    String greeting(String name)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>"Hello, <fold text='$' expand='false'>" + </fold>name<fold text='' expand='false'> + "</fold>!"<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    String label(String name)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>"Name: <fold text='$' expand='false'>" + </fold>name<fold text='"' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    String prefixed(String name)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>name<fold text='' expand='false'> + "</fold> is here"<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    String trimmed(String name)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>"Name: <fold text='${' expand='false'>" + </fold>name.trim()<fold text='}"' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    String trimmedOrEmpty(String name)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='if (' expand='true'> </fold>name == null<fold text=')' expand='false'> ?</fold> "none" <fold text='else' expand='false'>:</fold> "Name: <fold text='${' expand='false'>" + </fold>name.trim()<fold text='}"' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>
}
