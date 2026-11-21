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
        String csv = String.join(", ", names);
        String tagLine = String.join(SEPARATOR, tags);
        String path = String.join("/", parts);
        String fromGetter = String.join(", ", getNames());
        String fromIndex = String.join(";", rows.get(0));
        String fromArrayElement = String.join("-", table[0]);
        String nested = "Names: " + String.join(", ", names);
        System.out.println(String.join(", ", names));
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
