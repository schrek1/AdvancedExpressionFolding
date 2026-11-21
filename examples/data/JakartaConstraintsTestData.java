package data;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class JakartaConstraintsTestData {

    private static final int MAX_ITEMS = 10;

    private int stock;

    public void reserve(int quantity, String sku) {
        if (quantity < 1) throw new IllegalArgumentException("quantity");
        if (sku.isBlank()) throw new IllegalArgumentException("sku");
        stock -= quantity;
    }

    public void price(double amount, int discount, long points) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        if (discount < 0) throw new IllegalArgumentException("discount");
        if (discount > 100) throw new IllegalArgumentException("discount");
        if (points <= 9) throw new IllegalArgumentException("points");
        System.out.println(amount + discount + points);
    }

    public void order(List<String> items, String coupon, int quantity) {
        if (items.isEmpty()) throw new IllegalArgumentException("items");
        if (items.size() > 20) throw new IllegalArgumentException("items");
        Objects.requireNonNull(coupon, "coupon");
        if (coupon.length() > 12) throw new IllegalArgumentException("coupon");
        if (coupon.length() < 4) throw new IllegalArgumentException("coupon");
        if (50 <= quantity) throw new IllegalArgumentException("quantity");
        if (quantity < -5) throw new IllegalArgumentException("quantity");
        System.out.println(items + coupon + quantity);
    }

    public void notFoldedAfterStatement(int quantity) {
        System.out.println("start");
        if (quantity < 1) throw new IllegalArgumentException("quantity");
    }

    public void notFoldedOtherException(String sku) {
        if (sku.isBlank()) throw new IllegalStateException("sku");
    }

    public void notFoldedDecimalBound(double weight) {
        if (weight < 1.5) throw new IllegalArgumentException("weight");
    }

    public void notFoldedOptional(Optional<String> note) {
        if (note.isEmpty()) throw new IllegalArgumentException("note");
    }

    public void notFoldedConstantBound(List<String> items) {
        if (items.size() > MAX_ITEMS) throw new IllegalArgumentException("items");
    }

    public void notFoldedEquality(int quantity) {
        if (quantity == 0) throw new IllegalArgumentException("quantity");
    }

    public void notFoldedField(int quantity) {
        if (stock < 1) throw new IllegalArgumentException("stock");
    }

    public void onlyFirstMinOfParameter(int quantity) {
        if (quantity < 1) throw new IllegalArgumentException("quantity");
        if (quantity < 2) throw new IllegalArgumentException("quantity");
    }

    public void notFoldedBranches(int quantity) {
        if (quantity < 1) {
            System.out.println("too few");
            throw new IllegalArgumentException("quantity");
        }
        if (quantity > 99) {
            throw new IllegalArgumentException("quantity");
        } else {
            System.out.println(quantity);
        }
    }
}
