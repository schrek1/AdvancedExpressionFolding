package data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("ALL")
public class InOperatorTestData {
    private Set<String> admins;
    private int age;

    public void folded(String user, Set<String> banned, Map<Long, String> cache, Long id, List<String> tags, int age, int i, int size) {
        if (user in admins && user !in banned
                && age in 18..65 && i in 0..<size) {
            System.out.println(user);
        }
        if (id in cache) {
            System.out.println(cache.get(id));
        }
        boolean tagged = "new" in tags;
        boolean admin = user in this.admins;
        boolean adult = age in 18..65;
        boolean inside = i in 0..<size;
        boolean last = i in 0..size - 1;
        boolean tight = i in 0..<size;
    }

    public void notFolded(String user, String name, List<String> tags, Map<Long, String> cache, int age, int height, int i) {
        boolean mixed = age >= 18 && height <= 65;
        boolean openStart = age > 18 && age <= 65;
        boolean openBoth = 18 < age && age < 65;
        boolean field = this.age >= 18 && this.age <= 65;
        boolean callBound = i >= 0 && i < tags.size();
        boolean callReceiver = loadAdmins().contains(user);
        boolean callArgument = tags.contains(nextTag());
        boolean text = name.contains("x");
        boolean value = cache.containsValue(name);
    }

    private Set<String> loadAdmins() {
        return admins;
    }

    private String nextTag() {
        return "new";
    }
}
