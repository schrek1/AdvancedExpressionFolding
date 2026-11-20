package data;

import java.math.BigDecimal;

@SuppressWarnings("ALL")
public class ObjectDestructuringTestData {

    public void recordAccessors(Order order) {
        val customer, total, currency) = order;
        blackhole(customer, total, currency);
    }

    public void getters(Shop shop) {
        val name, open) = shop;
        blackhole(name, open);
    }

    public void reassignedLocal(Order order) {
        var customer, currency) = order;
        customer = "anonymous";
        blackhole(customer, currency);
    }

    public void castBeforeAccessors(Object o) {
        if (o instanceof Order) {
            val order = (Order) o;
            val customer, total, currency) = order;
            blackhole(customer, total, currency);
        }
    }

    public void singleLocal(Order order) {
        val customer = order.customer;
        blackhole(customer);
    }

    public void nameDoesNotMatchAccessor(Order order) {
        val buyer = order.customer;
        val currency = order.currency;
        blackhole(buyer, currency);
    }

    public void differentObjects(Order order, Order other) {
        val customer = order.customer;
        val currency = other.currency;
        blackhole(customer, currency);
    }

    public void qualifierIsACall(Order order) {
        val customer = order.copy.customer;
        val currency = order.copy.currency;
        blackhole(customer, currency);
    }

    public void notARecordAccessor(Shop shop) {
        val name = shop.name();
        val city = shop.city();
        blackhole(name, city);
    }

    public void methodWithArgument(Shop shop) {
        val name = shop.name;
        val label = shop.getLabel("en");
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
