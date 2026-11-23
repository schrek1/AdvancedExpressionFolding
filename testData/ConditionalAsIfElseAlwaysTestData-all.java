package data;

@SuppressWarnings("ALL")
public class ConditionalAsIfElseAlwaysTestData {

    // a short ternary becomes if-else too
    int pageSize(int requested)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>requested > 0 ? requested : 20<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    // an honest Elvis stays Elvis
    String nameOrUnknown(String name)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>name != null ? </fold>name<fold text=' ?: ' expand='false'> : </fold>"unknown"<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    // a ternary right after `(` has no space for ` if (`, so it stays a ternary
    void printMode(boolean verbose)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='false'>System.out.</fold>println(verbose ? "verbose" : "quiet")<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>
}
