package data;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class JakartaConstraintsTestData {

    private static final int MAX_ITEMS = 10;

    private int stock;

    public void reserve(@Min(1) int quantity, @NotBlank String sku) {
        stock -= quantity;
    }

    public void price(@Positive double amount, @PositiveOrZero @Max(100) int discount, @Min(10) long points) {
        System.out.println(amount + discount + points);
    }

    public void order(@NotEmpty @Size(max = 20) List<String> items, @NonNull @Size(min = 4, max = 12) String coupon, @Max(49) @Min(-5) int quantity) {
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

    public void onlyFirstMinOfParameter(@Min(1) int quantity) {
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
