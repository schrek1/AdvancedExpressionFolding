package data;

import <fold text='...' expand='false'>java.util.List;
import java.util.Set;</fold>

<fold text='@Getter p' expand='false'>p</fold>ublic class StringJoinTestData {

    private <fold text='const' expand='false'>static final </fold><fold text='' expand='false'>String</fold> SEPARATOR = " | ";

    private List<String> names;<fold text='' expand='false'>

    </fold><fold text='' expand='false'>public List<String> getNames()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>names<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold></fold>

    public void join(List<String> names, Set<String> tags, String[] parts, List<List<String>> rows, String[][] table) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>String</fold> csv = <fold text='' expand='false'>String.join(", ", </fold>names<fold text='.joinToString(", ")' expand='false'>)</fold>;
        <fold text='val' expand='false'>String</fold> tagLine = <fold text='' expand='false'>String.join(SEPARATOR, </fold>tags<fold text='.joinToString(SEPARATOR)' expand='false'>)</fold>;
        <fold text='val' expand='false'>String</fold> path = <fold text='' expand='false'>String.join("/", </fold>parts<fold text='.joinToString("/")' expand='false'>)</fold>;
        <fold text='val' expand='false'>String</fold> fromGetter = <fold text='' expand='false'>String.join(", ", </fold><fold text='names' expand='false'>getNames()</fold><fold text='.joinToString(", ")' expand='false'>)</fold>;
        <fold text='val' expand='false'>String</fold> fromIndex = <fold text='' expand='false'>String.join(";", </fold>rows<fold text='.getFirst' expand='false'>.get</fold>(<fold text='' expand='false'>0</fold>)<fold text='.joinToString(";")' expand='false'>)</fold>;
        <fold text='val' expand='false'>String</fold> fromArrayElement = <fold text='' expand='false'>String.join("-", </fold>table[0]<fold text='.joinToString("-")' expand='false'>)</fold>;
        <fold text='val' expand='false'>String</fold> nested = "Names: <fold text='${' expand='false'>" + </fold><fold text='' expand='false'>String.join(", ", </fold>names<fold text='.joinToString(", ")}"' expand='false'>)</fold>;
        <fold text='' expand='false'>System.out.</fold>println(<fold text='' expand='false'>String.join(", ", </fold>names<fold text='.joinToString(", ")' expand='false'>)</fold>);
    }</fold>

    public void staysUnfolded(List<String> names, String name, String other, boolean flag) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>String</fold> single = String.join(", ", name);
        <fold text='val' expand='false'>String</fold> varargs = String.join(", ", name, other);
        <fold text='val' expand='false'>String</fold> noArgs = String.join(", ");
        <fold text='val' expand='false'>String</fold> ternary = String.join(", ", flag ? names : List.of());
        <fold text='val' expand='false'>String</fold> multiLine = String.join(
                flag
                        ? ", "
                        : ";",
                names);
    }</fold>
}
