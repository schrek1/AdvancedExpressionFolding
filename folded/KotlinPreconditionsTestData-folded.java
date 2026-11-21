package data;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class KotlinPreconditionsTestData {

    private final String name;
    private boolean connected;

    public KotlinPreconditionsTestData(String name) {
        this.name = requireNotNull(name) { "name" };
    }

    public void ship(String order, String customer, int quantity) {
        require(order != null) { "order" }
        requireNotNull(customer) { "customer" };
        requireNotNull(name);
        require(quantity > 0) { "Quantity must be positive: " + quantity }
        require(quantity < 100)
    }

    public void send(String message) {
        check(connected) { "Not connected" }
        check(message == null) { "Already sent " + message }
    }

    public void restock(String sku, int stock, long reserved, Integer limit, char grade, double price, Object item) {
        require(stock >= 0) { "stock" }
        check(reserved <= stock) { "over-reserved" }
        require(grade < 'C') { "grade" }
        require(limit == null || limit < 10) { "limit" }
        require(sku != null && !sku.isEmpty()) { "sku" }
        check(!sku.isBlank() && (stock < 5 || reserved == 0)) { "restock" }
        check(!connected) { "connected" }
        require(!(price <= 0)) { "price" }
        require(!(sku == null || price < 1.0)) { "cheap" }
        require(!(sku == null || stock < 0 && reserved > 0)) { "mixed" }
        require(!(item instanceof String)) { "item" }
    }

    public String describe(int state) {
        switch (state) {
            case 0:
                return "new";
            case 1:
                return "done";
            default:
                error("Unknown state " + state);
        }
    }

    public void notFolded(String order, Exception cause) {
        if (order == null) {
            System.out.println("missing order");
            throw new IllegalArgumentException("order");
        }
        if (order.isEmpty()) {
            throw new UnsupportedOperationException("order");
        }
        if (order.isBlank()) {
            throw new IllegalArgumentException("order", cause);
        }
        if (connected) {
            error("connected");
        } else {
            connected = true;
        }
        Objects.requireNonNull(order, () -> "order");
    }
}
