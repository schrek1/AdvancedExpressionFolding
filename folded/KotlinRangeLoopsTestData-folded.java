package data;

import java.util.List;

public class KotlinRangeLoopsTestData {
    public void printOrders(List<String> orders, String[] items, int pageSize) {
        for (i in 0..<orders.size()) {
            System.out.println(i + ": " + orders.get(i));
        }
        for (page in 1..pageSize) {
            System.out.println(page);
        }
        for (i in 1..<items.length) {
            System.out.println(items[i - 1] + " -> " + items[i]);
        }
        for (id in 100L..<200L) {
            System.out.println(id);
        }
        // not folded: the loop goes down
        for (int i = pageSize; i > 0; i--) {
            System.out.println(i);
        }
    }
}
