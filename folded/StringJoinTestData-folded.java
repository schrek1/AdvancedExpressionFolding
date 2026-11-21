package data;

import java.util.List;
import java.util.Set;

public class StringJoinTestData {

    private static final String SEPARATOR = " | ";

    private List<String> names;

    public List<String> getNames() {
        return names;
    }

    public void join(List<String> names, Set<String> tags, String[] parts, List<List<String>> rows, String[][] table) {
        String csv = names.joinToString(", ");
        String tagLine = tags.joinToString(SEPARATOR);
        String path = parts.joinToString("/");
        String fromGetter = getNames().joinToString(", ");
        String fromIndex = rows.get(0).joinToString(";");
        String fromArrayElement = table[0].joinToString("-");
        String nested = "Names: " + names.joinToString(", ");
        System.out.println(names.joinToString(", "));
    }

    public void staysUnfolded(List<String> names, String name, String other, boolean flag) {
        String single = String.join(", ", name);
        String varargs = String.join(", ", name, other);
        String noArgs = String.join(", ");
        String ternary = String.join(", ", flag ? names : List.of());
        String multiLine = String.join(
                flag
                        ? ", "
                        : ";",
                names);
    }
}
