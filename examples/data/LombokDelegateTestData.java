package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LombokDelegateTestData {

    public static class Cart {
        private final List<String> items = new ArrayList<>();

        public int size() {
            return items.size();
        }

        public boolean isEmpty() {
            return items.isEmpty();
        }

        public void add(String item) {
            items.add(item);
        }

        public boolean contains(Object item) {
            return this.items.contains(item);
        }
    }

    public static class Shop {
        private final Map<String, Integer> stock = new HashMap<>();
        private final List<String> orders = new ArrayList<>();

        public Integer put(String product, Integer quantity) {
            return stock.put(product, quantity);
        }

        public boolean containsKey(Object product) {
            return stock.containsKey(product);
        }

        public String get(int index) {
            return orders.get(index);
        }

        public List<String> getOrders() {
            return orders;
        }
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
