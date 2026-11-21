package data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class LombokUtilityClassTestData {

    @UtilityClass public static class Money {
        public final int SCALE = 2;

        public BigDecimal round(BigDecimal value) {
            return value.setScale(SCALE, RoundingMode.HALF_UP);
        }

        BigDecimal sum(List<BigDecimal> values) {
            return values.stream().reduce(0, BigDecimal::add);
        }
    }

    @UtilityClass static class StockLimits {
        final int MAX_ITEMS = 100;
        private final int MIN_ITEMS = 1;

        public boolean isAllowed(int items) {
            return items >= MIN_ITEMS && items <= MAX_ITEMS;
        }

        public class Defaults {
            public static final int ITEMS = 10;
        }
    }

    // singleton: an INSTANCE of its own type, left to the `object` folding
    @NoArgsConstructor(private) public static final class OrderRegistry {
        public static final OrderRegistry INSTANCE = new OrderRegistry();
        private static final List<String> orders = new ArrayList<>();

        public static void register(String order) {
            orders.add(order);
        }
    }

    // not a utility class: has an instance method
    public static final class Shop {
        private Shop() {
            throw new UnsupportedOperationException();
        }

        public static Shop open() {
            return null;
        }

        public String name() {
            return "shop";
        }
    }

    // not a utility class: the constructor is not private
    public static final class Person {
        public Person() {
            throw new UnsupportedOperationException();
        }

        public static String greet(String name) {
            return "Hello " + name;
        }
    }

    // not a utility class: the constructor does more than block instantiation
    public static final class Order {
        private static int created;

        private Order() {
            created++;
        }

        public static int created() {
            return created;
        }
    }
}
