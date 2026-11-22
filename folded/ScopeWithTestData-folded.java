package data;

import java.util.List;

public class ScopeWithTestData {

    private Order current;

    public void folded(Order order, List<String> lines) {
        validate();
        reserveStock();
        notifyCustomer("Thanks for your order")

        add("first");
        add("second");
        add("third");
        add("fourth")

        validate();
        reserveStock();
        notifyCustomer("Updated")
    }

    public void notFolded(Order order, Order other) {
        order.validate();
        order.reserveStock();

        order.validate();
        System.out.println("between");
        order.reserveStock();
        order.notifyCustomer("Thanks");

        order.validate();
        other.reserveStock();
        order.notifyCustomer("Thanks");

        order.validate();
        // stock first
        order.reserveStock();
        order.notifyCustomer("Thanks");

        order.validate();
        order.reserveStock();
        order.notifyCustomer(
                "Thanks");

        order.items().clear();
        order.items().clear();
        order.items().clear();
    }

    static class Order {
        private List<String> items;

        public void validate() {
        }

        public void reserveStock() {
        }

        public void notifyCustomer(String message) {
        }

        public List<String> items() {
            return items;
        }
    }
}
