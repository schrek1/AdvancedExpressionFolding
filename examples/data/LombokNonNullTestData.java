package data;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class LombokNonNullTestData {

    private String customer;

    public LombokNonNullTestData(String customer) {
        Objects.requireNonNull(customer);
        this.customer = customer;
    }

    public void ship(String order) {
        Objects.requireNonNull(order, "order");
        System.out.println(order);
    }

    public void ship(String order, String address, int quantity) {
        requireNonNull(order);
        if (address == null) {
            throw new NullPointerException("address is marked non-null but is null");
        }
        System.out.println(order + address + quantity);
    }

    public void pack(String order, String box) {
        if (null == order) throw new IllegalArgumentException("order");
        Objects.requireNonNull(box, "box");
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

    public void onlyFirstCheckOfParameter(String order) {
        Objects.requireNonNull(order);
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
