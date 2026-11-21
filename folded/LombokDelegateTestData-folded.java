package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LombokDelegateTestData {

    public static class Cart {
        @Delegate private final List<String> items = new ArrayList<>();
    }

    public static class Shop {
        @Delegate private final Map<String, Integer> stock = new HashMap<>();
        @Getter @Delegate private final List<String> orders = new ArrayList<>();
    }

    public static class NotForwarding {
        private final List<String> items = new ArrayList<>();
        private final NotForwarding other = null;

        public int count() {
            return items.size();
        }

        public String get(int index) {
            return items.get(index + 1);
        }

        public void add(int index, String item) {
            items.add(0, item);
        }

        public void clear() {
            System.out.println("clearing");
            items.clear();
        }

        public boolean isEmpty() {
            // kept visible on purpose
            return items.isEmpty();
        }

        public boolean contains(Object item) {
            return other.items.contains(item);
        }

        private int indexOf(Object item) {
            return items.indexOf(item);
        }
    }
}
