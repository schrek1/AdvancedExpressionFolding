package data;

import <fold text='...' expand='false'>java.util.List;
import java.util.Objects;
import java.util.Optional;</fold>

public class JakartaConstraintsTestData {

    private <fold text='const' expand='false'>static final </fold><fold text='' expand='false'>int</fold> MAX_ITEMS = 10;

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
        <fold text='' expand='false'>System.out.</fold>println(amount + discount + points);
    }</fold>

    public void order<fold text='(@NotEmpty @Size(max = 20) ' expand='true'>(</fold>List<String> items,<fold text=' @NonNull @Size(min = 4, max = 12) ' expand='true'> </fold>String coupon,<fold text=' @Max(49) @Min(-5) ' expand='true'> </fold>int quantity) <fold text='{...}' expand='true'>{<fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (items.isEmpty()) throw new IllegalArgumentException("items");</fold><fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (items.size() > 20) throw new IllegalArgumentException("items");</fold><fold text='' expand='true'>
        </fold><fold text='' expand='true'>Objects.requireNonNull(coupon, "coupon");</fold><fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (coupon.length() > 12) throw new IllegalArgumentException("coupon");</fold><fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (coupon.length() < 4) throw new IllegalArgumentException("coupon");</fold><fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (50 <= quantity) throw new IllegalArgumentException("quantity");</fold><fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (quantity < -5) throw new IllegalArgumentException("quantity");</fold>
        <fold text='' expand='false'>System.out.</fold>println(items + coupon + quantity);
    }</fold>

    public void notFoldedAfterStatement(int quantity) <fold text='{...}' expand='true'>{
        <fold text='' expand='false'>System.out.</fold>println("start");
        <fold text='require' expand='false'>if </fold>(quantity <fold text='>=' expand='false'><</fold> 1)<fold text=' { ' expand='false'> throw new IllegalArgumentException(</fold>"quantity"<fold text=' }' expand='false'>);</fold>
    }</fold>

    public void notFoldedOtherException(String sku)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='check(!' expand='false'>if (</fold>sku.<fold text='b' expand='false'>isB</fold>lank<fold text='' expand='false'>()</fold>)<fold text=' { ' expand='false'> throw new IllegalStateException(</fold>"sku"<fold text=' }' expand='false'>);</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public void notFoldedDecimalBound(double weight)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='require(!' expand='false'>if </fold>(weight < 1.5)<fold text=') { ' expand='false'> throw new IllegalArgumentException(</fold>"weight"<fold text=' }' expand='false'>);</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public void notFoldedOptional(Optional<String> note) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='require(!' expand='false'>if (</fold>note.<fold text='e' expand='false'>isE</fold>mpty<fold text='' expand='false'>()</fold>)<fold text=' { ' expand='false'> throw new IllegalArgumentException(</fold>"note"<fold text=' }' expand='false'>);</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    public void notFoldedConstantBound(List<String> items) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='require' expand='false'>if </fold>(items.size() <fold text='<=' expand='false'>></fold> MAX_ITEMS)<fold text=' { ' expand='false'> throw new IllegalArgumentException(</fold>"items"<fold text=' }' expand='false'>);</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    public void notFoldedEquality(int quantity)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='require' expand='false'>if </fold>(quantity <fold text='!=' expand='false'>==</fold> 0)<fold text=' { ' expand='false'> throw new IllegalArgumentException(</fold>"quantity"<fold text=' }' expand='false'>);</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public void notFoldedField(int quantity)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='require' expand='false'>if </fold>(stock <fold text='>=' expand='false'><</fold> 1)<fold text=' { ' expand='false'> throw new IllegalArgumentException(</fold>"stock"<fold text=' }' expand='false'>);</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public void onlyFirstMinOfParameter<fold text='(@Min(1) ' expand='true'>(</fold>int quantity) <fold text='{...}' expand='true'>{<fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (quantity < 1) throw new IllegalArgumentException("quantity");</fold>
        <fold text='require' expand='false'>if </fold>(quantity <fold text='>=' expand='false'><</fold> 2)<fold text=' { ' expand='false'> throw new IllegalArgumentException(</fold>"quantity"<fold text=' }' expand='false'>);</fold>
    }</fold>

    public void notFoldedBranches(int quantity) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>quantity < 1<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println("too few");
            throw <fold text='' expand='false'>new </fold>IllegalArgumentException("quantity");
        }</fold>
        if <fold text='' expand='false'>(</fold>quantity > 99<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            throw <fold text='' expand='false'>new </fold>IllegalArgumentException("quantity");
        }</fold> else <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println(quantity);
        }</fold>
    }</fold>
}
