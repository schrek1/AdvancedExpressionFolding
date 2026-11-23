package data;

@SuppressWarnings("ALL")
public class ConditionalAsIfElseAlwaysTestData {

    // a short ternary becomes if-else too
    int pageSize(int requested)<fold text=' { ' expand='false'> {
        </fold>return<fold text=' if (' expand='false'> </fold>requested > 0<fold text=')' expand='false'> ?</fold> requested <fold text='else' expand='false'>:</fold> 20<fold text='' expand='false'>;</fold><fold text=' }' expand='false'>
    }</fold>

    // an honest Elvis stays Elvis
    String nameOrUnknown(String name)<fold text=' { ' expand='false'> {
        </fold>return <fold text='' expand='false'>name != null ? </fold>name<fold text=' ?: ' expand='false'> : </fold>"unknown";<fold text=' }' expand='false'>
    }</fold>

    // a ternary right after `(` has no space for ` if (`, so it stays a ternary
    void printMode(boolean verbose)<fold text=' { ' expand='false'> {
        </fold>System.out.println(verbose ? "verbose" : "quiet");<fold text=' }' expand='false'>
    }</fold>
}
