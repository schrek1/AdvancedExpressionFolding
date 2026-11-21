package data;

import <fold text='...' expand='false'>java.util.List;
import java.util.Set;</fold>

public class StringJoinTestData {

    private static final String SEPARATOR = " | ";

    private List<String> names;

    public List<String> getNames()<fold text=' { ' expand='false'> {
        </fold>return names;<fold text=' }' expand='false'>
    }</fold>

    public void join(List<String> names, Set<String> tags, String[] parts, List<List<String>> rows, String[][] table) <fold text='{...}' expand='true'>{
        String csv = <fold text='' expand='false'>String.join(", ", </fold>names<fold text='.joinToString(", ")' expand='false'>)</fold>;
        String tagLine = <fold text='' expand='false'>String.join(SEPARATOR, </fold>tags<fold text='.joinToString(SEPARATOR)' expand='false'>)</fold>;
        String path = <fold text='' expand='false'>String.join("/", </fold>parts<fold text='.joinToString("/")' expand='false'>)</fold>;
        String fromGetter = <fold text='' expand='false'>String.join(", ", </fold>getNames()<fold text='.joinToString(", ")' expand='false'>)</fold>;
        String fromIndex = <fold text='' expand='false'>String.join(";", </fold>rows.get(0)<fold text='.joinToString(";")' expand='false'>)</fold>;
        String fromArrayElement = <fold text='' expand='false'>String.join("-", </fold>table[0]<fold text='.joinToString("-")' expand='false'>)</fold>;
        String nested = "Names: " + <fold text='' expand='false'>String.join(", ", </fold>names<fold text='.joinToString(", ")' expand='false'>)</fold>;
        System.out.println(<fold text='' expand='false'>String.join(", ", </fold>names<fold text='.joinToString(", ")' expand='false'>)</fold>);
    }</fold>

    public void staysUnfolded(List<String> names, String name, String other, boolean flag) <fold text='{...}' expand='true'>{
        String single = String.join(", ", name);
        String varargs = String.join(", ", name, other);
        String noArgs = String.join(", ");
        String ternary = String.join(", ", flag ? names : List.of());
        String multiLine = String.join(
                flag
                        ? ", "
                        : ";",
                names);
    }</fold>
}
