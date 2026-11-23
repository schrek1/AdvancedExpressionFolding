package data;

@SuppressWarnings("ALL")
public class ConditionalAsIfElseNeverTestData {

    // a string branch stays a ternary
    String status(boolean active, String name)<fold text=' { ' expand='false'> {
        </fold>return active ? "active: " + name : "inactive";<fold text=' }' expand='false'>
    }</fold>

    // a nested ternary stays a ternary
    String size(int count)<fold text=' { ' expand='false'> {
        </fold>return count == 0 ? "none" : count == 1 ? "one" : "many";<fold text=' }' expand='false'>
    }</fold>

    // an honest Elvis stays Elvis
    String nameOrUnknown(String name)<fold text=' { ' expand='false'> {
        </fold>return <fold text='' expand='false'>name != null ? </fold>name<fold text=' ?: ' expand='false'> : </fold>"unknown";<fold text=' }' expand='false'>
    }</fold>
}
