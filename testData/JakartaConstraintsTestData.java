package data;

import <fold text='...' expand='false'>java.util.List;
import java.util.Objects;
import java.util.Optional;</fold>

public class JakartaConstraintsTestData {

    private static final int MAX_ITEMS = 10;

    private int stock;

    public void reserve<fold text='(@Min(1) ' expand='true'>(</fold>int quantity,<fold text=' @NotBlank ' expand='true'> </fold>String sku) <fold text='{...}' expand='true'>{<fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (quantity < 1) throw new IllegalArgumentException("quantity");</fold><fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (sku.isBlank()) throw new IllegalArgumentException("sku");</fold>
        stock -= quantity;
    }</fold>

    public void price<fold text='(@Positive ' expand='true'>(</fold>double amount,<fold text=' @PositiveOrZero @Max(100) ' expand='true'> </fold>int discount,<fold text=' @Min(10) ' expand='true'> </fold>long points) <fold text='{...}' expand='true'>{<fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (amount <= 0) <fold text='{...}' expand='true'>{
            throw new IllegalArgumentException("amount must be positive");
        }</fold></fold><fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (discount < 0) throw new IllegalArgumentException("discount");</fold><fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (discount > 100) throw new IllegalArgumentException("discount");</fold><fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (points <= 9) throw new IllegalArgumentException("points");</fold>
        System.out.println(amount + discount + points);
    }</fold>

    public void order<fold text='(@NotEmpty @Size(max = 20) ' expand='true'>(</fold>List<String> items,<fold text=' @NonNull @Size(min = 4, max = 12) ' expand='true'> </fold>String coupon,<fold text=' @Max(49) @Min(-5) ' expand='true'> </fold>int quantity) <fold text='{...}' expand='true'>{<fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (items.isEmpty()) throw new IllegalArgumentException("items");</fold><fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (items.size() > 20) throw new IllegalArgumentException("items");</fold><fold text='' expand='true'>
        </fold><fold text='' expand='true'>Objects.requireNonNull(coupon, "coupon");</fold><fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (coupon.length() > 12) throw new IllegalArgumentException("coupon");</fold><fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (coupon.length() < 4) throw new IllegalArgumentException("coupon");</fold><fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (50 <= quantity) throw new IllegalArgumentException("quantity");</fold><fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (quantity < -5) throw new IllegalArgumentException("quantity");</fold>
        System.out.println(items + coupon + quantity);
    }</fold>

    public void notFoldedAfterStatement(int quantity) <fold text='{...}' expand='true'>{
        System.out.println("start");
        if (quantity < 1) throw new IllegalArgumentException("quantity");
    }</fold>

    public void notFoldedOtherException(String sku)<fold text=' { ' expand='false'> {
        </fold>if (sku.isBlank()) throw new IllegalStateException("sku");<fold text=' }' expand='false'>
    }</fold>

    public void notFoldedDecimalBound(double weight)<fold text=' { ' expand='false'> {
        </fold>if (weight < 1.5) throw new IllegalArgumentException("weight");<fold text=' }' expand='false'>
    }</fold>

    public void notFoldedOptional(Optional<String> note) <fold text='{...}' expand='true'>{
        if (note.isEmpty()) throw new IllegalArgumentException("note");
    }</fold>

    public void notFoldedConstantBound(List<String> items) <fold text='{...}' expand='true'>{
        if (items.size() > MAX_ITEMS) throw new IllegalArgumentException("items");
    }</fold>

    public void notFoldedEquality(int quantity)<fold text=' { ' expand='false'> {
        </fold>if (quantity == 0) throw new IllegalArgumentException("quantity");<fold text=' }' expand='false'>
    }</fold>

    public void notFoldedField(int quantity)<fold text=' { ' expand='false'> {
        </fold>if (stock < 1) throw new IllegalArgumentException("stock");<fold text=' }' expand='false'>
    }</fold>

    public void onlyFirstMinOfParameter<fold text='(@Min(1) ' expand='true'>(</fold>int quantity) <fold text='{...}' expand='true'>{<fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (quantity < 1) throw new IllegalArgumentException("quantity");</fold>
        if (quantity < 2) throw new IllegalArgumentException("quantity");
    }</fold>

    public void notFoldedBranches(int quantity) <fold text='{...}' expand='true'>{
        if (quantity < 1) <fold text='{...}' expand='true'>{
            System.out.println("too few");
            throw new IllegalArgumentException("quantity");
        }</fold>
        if (quantity > 99) <fold text='{...}' expand='true'>{
            throw new IllegalArgumentException("quantity");
        }</fold> else <fold text='{...}' expand='true'>{
            System.out.println(quantity);
        }</fold>
    }</fold>
}
