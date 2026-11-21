package data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("ALL")
public class InOperatorTestData {
    private Set<String> admins;
    private int age;

    public void folded(String user, Set<String> banned, Map<Long, String> cache, Long id, List<String> tags, int age, int i, int size) {
        if (admins.contains(user) && !banned.contains(user)
                && age >= 18 && age <= 65 && i >= 0 && i < size) {
            System.out.println(user);
        }
        if (cache.containsKey(id)) {
            System.out.println(cache.get(id));
        }
        boolean tagged = tags.contains("new");
        boolean admin = this.admins.contains(user);
        boolean adult = age >= 18 && age <= 65;
        boolean inside = i >= 0 && i < size;
        boolean last = i >= 0 && i <= size - 1;
        boolean tight = i>=0&&i<size;
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
