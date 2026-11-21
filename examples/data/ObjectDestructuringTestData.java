package data;

import java.math.BigDecimal;

@SuppressWarnings("ALL")
public class ObjectDestructuringTestData {

    public void recordAccessors(Order order) {
        String customer = order.customer();
        BigDecimal total = order.total();
        String currency = order.currency();
        blackhole(customer, total, currency);
    }

    public void getters(Shop shop) {
        String name = shop.getName();
        boolean open = shop.isOpen();
        blackhole(name, open);
    }

    public void reassignedLocal(Order order) {
        String customer = order.customer();
        String currency = order.currency();
        customer = "anonymous";
        blackhole(customer, currency);
    }

    public void castBeforeAccessors(Object o) {
        if (o instanceof Order) {
            Order order = (Order) o;
            String customer = order.customer();
            BigDecimal total = order.total();
            String currency = order.currency();
            blackhole(customer, total, currency);
        }
    }

    public void singleLocal(Order order) {
        String customer = order.customer();
        blackhole(customer);
    }

    public void nameDoesNotMatchAccessor(Order order) {
        String buyer = order.customer();
        String currency = order.currency();
        blackhole(buyer, currency);
    }

    public void differentObjects(Order order, Order other) {
        String customer = order.customer();
        String currency = other.currency();
        blackhole(customer, currency);
    }

    public void qualifierIsACall(Order order) {
        String customer = order.copy().customer();
        String currency = order.copy().currency();
        blackhole(customer, currency);
    }

    public void notARecordAccessor(Shop shop) {
        String name = shop.name();
        String city = shop.city();
        blackhole(name, city);
    }

    public void methodWithArgument(Shop shop) {
        String name = shop.getName();
        String label = shop.getLabel("en");
        blackhole(name, label);
    }

    public record Order(String customer, BigDecimal total, String currency) {
        public Order copy() {
            return this;
        }
    }

    public static class Shop {
        public String getName() {
            return null;
        }

        public boolean isOpen() {
            return true;
        }

        public String name() {
            return null;
        }

        public String city() {
            return null;
        }

        public String getLabel(String language) {
            return null;
        }
    }

    void blackhole(Object... values) {
    }
}
