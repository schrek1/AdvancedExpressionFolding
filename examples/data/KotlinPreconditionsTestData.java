package data;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class KotlinPreconditionsTestData {

    private final String name;
    private boolean connected;

    public KotlinPreconditionsTestData(String name) {
        this.name = Objects.requireNonNull(name, "name");
    }

    public void ship(String order, String customer, int quantity) {
        if (order == null) {
            throw new IllegalArgumentException("order");
        }
        Objects.requireNonNull(customer, "customer");
        requireNonNull(name);
        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity must be positive: " + quantity);
        if (!(quantity < 100)) {
            throw new IllegalArgumentException();
        }
    }

    public void send(String message) {
        if (!connected) {
            throw new IllegalStateException("Not connected");
        }
        if (message != null) {
            throw new IllegalStateException("Already sent " + message);
        }
    }

    public void restock(String sku, int stock, long reserved, Integer limit, char grade, double price, Object item) {
        if (stock < 0) {
            throw new IllegalArgumentException("stock");
        }
        if (reserved > stock) {
            throw new IllegalStateException("over-reserved");
        }
        if (grade >= 'C') {
            throw new IllegalArgumentException("grade");
        }
        if (limit != null && limit >= 10) {
            throw new IllegalArgumentException("limit");
        }
        if (sku == null || sku.isEmpty()) {
            throw new IllegalArgumentException("sku");
        }
        if (sku.isBlank() || !(stock < 5 || reserved == 0)) {
            throw new IllegalStateException("restock");
        }
        if (!(!connected)) {
            throw new IllegalStateException("connected");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("price");
        }
        if (sku == null || price < 1.0) {
            throw new IllegalArgumentException("cheap");
        }
        if (sku == null || stock < 0 && reserved > 0) {
            throw new IllegalArgumentException("mixed");
        }
        if (item instanceof String) {
            throw new IllegalArgumentException("item");
        }
    }

    public String describe(int state) {
        switch (state) {
            case 0:
                return "new";
            case 1:
                return "done";
            default:
                throw new IllegalStateException("Unknown state " + state);
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
            throw new IllegalStateException("connected");
        } else {
            connected = true;
        }
        Objects.requireNonNull(order, () -> "order");
    }
}
