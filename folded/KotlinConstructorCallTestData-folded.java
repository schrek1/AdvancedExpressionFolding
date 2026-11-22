package data;

import java.math.BigDecimal;
import java.util.*;

public class KotlinConstructorCallTestData {

    record Order(long id, double total) {
    }

    static class Page<T> {
        Page(List<T> items, int number) {
        }
    }

    static class Registry {
    }

    class Line {
    }

    private final Map<String, Order> cache = HashMap<>();
    private final Registry registry = Registry();

    Order plainCreation(long id, double total) {
        var order = Order(id, total);
        return order;
    }

    Page<String> genericCreation(List<String> items, int number) {
        return Page<>(items, number);
    }

    void throwCreation(String code) {
        throw IllegalArgumentException("Unsupported code " + code);
    }

    Order nestedCreation(Order base) {
        return Order(base.id(), Order(1, 2.5).total());
    }

    void standardTypes() {
        List<String> names = ArrayList<>();
        StringBuilder text = "";
        BigDecimal price = 1.50;
        Thread worker = Thread(() -> System.out.println("done"));
    }

    void notFolded(KotlinConstructorCallTestData outer) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
            }
        };
        int[] counts = new int[3];
        String[] labels = new String[]{"a", "b"};
        Line line = outer.new Line();
    }
}
