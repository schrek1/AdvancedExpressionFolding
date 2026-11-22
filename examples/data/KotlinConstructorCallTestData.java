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

    private final Map<String, Order> cache = new HashMap<>();
    private final Registry registry = new Registry();

    Order plainCreation(long id, double total) {
        var order = new Order(id, total);
        return order;
    }

    Page<String> genericCreation(List<String> items, int number) {
        return new Page<>(items, number);
    }

    void throwCreation(String code) {
        throw new IllegalArgumentException("Unsupported code " + code);
    }

    Order nestedCreation(Order base) {
        return new Order(base.id(), new Order(1, 2.5).total());
    }

    void standardTypes() {
        List<String> names = new ArrayList<>();
        StringBuilder text = new StringBuilder();
        BigDecimal price = new BigDecimal("1.50");
        Thread worker = new Thread(() -> System.out.println("done"));
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
