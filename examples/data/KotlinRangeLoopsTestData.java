package data;

import java.util.List;

public class KotlinRangeLoopsTestData {
    public void printOrders(List<String> orders, String[] items, int pageSize) {
        for (int i = 0; i < orders.size(); i++) {
            System.out.println(i + ": " + orders.get(i));
        }
        for (int page = 1; page <= pageSize; page++) {
            System.out.println(page);
        }
        for (int i = 1; i < items.length; i++) {
            System.out.println(items[i - 1] + " -> " + items[i]);
        }
        for (long id = 100L; id < 200L; id++) {
            System.out.println(id);
        }
        // not folded: the loop goes down
        for (int i = pageSize; i > 0; i--) {
            System.out.println(i);
        }
    }
}
