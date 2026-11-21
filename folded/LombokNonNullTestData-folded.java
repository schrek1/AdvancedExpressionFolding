package data;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class LombokNonNullTestData {

    private String customer;

    public LombokNonNullTestData(@NonNull String customer) {
        this.customer = customer;
    }

    public void ship(@NonNull String order) {
        System.out.println(order);
    }

    public void ship(@NonNull String order, @NonNull String address, int quantity) {
        System.out.println(order + address + quantity);
    }

    public void pack(@NonNull String order, @NonNull String box) {
    }

    public void notFolded(String order, String address, String note) {
        System.out.println("start");
        Objects.requireNonNull(order, "order");
        if (address == null) {
            System.out.println("missing address");
            throw new NullPointerException("address");
        }
        if (note == null) {
            throw new IllegalStateException("note");
        }
    }

    public void notFoldedChecks(String order, String address) {
        Objects.requireNonNull(customer, "customer");
        Objects.requireNonNull(order, () -> "order");
        Objects.requireNonNull(address);
    }

    public void onlyFirstCheckOfParameter(@NonNull String order) {
        Objects.requireNonNull(order, "order");
    }

    public void notFoldedElse(String order) {
        if (order == null) {
            throw new NullPointerException("order");
        } else {
            System.out.println(order);
        }
    }
}
