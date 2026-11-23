package data;

@SuppressWarnings("ALL")
public class ConditionalAsIfElseNeverTestData {

    // a string branch stays a ternary
    String status(boolean active, String name)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='if (' expand='true'> </fold>active<fold text=')' expand='false'> ?</fold> "active: <fold text='$' expand='false'>" + </fold>name<fold text='" ' expand='false'> </fold><fold text='else' expand='false'>:</fold> "inactive"<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    // a nested ternary stays a ternary
    String size(int count)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='if (' expand='true'> </fold>count == 0<fold text=')' expand='false'> ?</fold> "none" <fold text='else' expand='false'>:</fold><fold text=' if (' expand='false'> </fold>count == 1<fold text=')' expand='false'> ?</fold> "one" <fold text='else' expand='false'>:</fold> "many"<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    // an honest Elvis stays Elvis
    String nameOrUnknown(String name)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>name != null ? </fold>name<fold text=' ?: ' expand='false'> : </fold>"unknown"<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>
}
