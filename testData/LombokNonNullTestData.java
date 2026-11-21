package data;

import <fold text='...' expand='false'>java.util.Objects;

import static java.util.Objects.requireNonNull;</fold>

public class LombokNonNullTestData {

    private String customer;

    public LombokNonNullTestData<fold text='(@NonNull ' expand='true'>(</fold>String customer) <fold text='{...}' expand='true'>{<fold text='' expand='true'>
        </fold><fold text='' expand='true'>Objects.requireNonNull(customer);</fold>
        this.customer = customer;
    }</fold>

    public void ship<fold text='(@NonNull ' expand='true'>(</fold>String order) <fold text='{...}' expand='true'>{<fold text='' expand='true'>
        </fold><fold text='' expand='true'>Objects.requireNonNull(order, "order");</fold>
        System.out.println(order);
    }</fold>

    public void ship<fold text='(@NonNull ' expand='true'>(</fold>String order,<fold text=' @NonNull ' expand='true'> </fold>String address, int quantity) <fold text='{...}' expand='true'>{<fold text='' expand='true'>
        </fold><fold text='' expand='true'>requireNonNull(order);</fold><fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (address == null) <fold text='{...}' expand='true'>{
            throw new NullPointerException("address is marked non-null but is null");
        }</fold></fold>
        System.out.println(order + address + quantity);
    }</fold>

    public void pack<fold text='(@NonNull ' expand='true'>(</fold>String order,<fold text=' @NonNull ' expand='true'> </fold>String box) <fold text='{...}' expand='true'>{<fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (null == order) throw new IllegalArgumentException("order");</fold><fold text='' expand='true'>
        </fold><fold text='' expand='true'>Objects.requireNonNull(box, "box");</fold>
    }</fold>

    public void notFolded(String order, String address, String note) <fold text='{...}' expand='true'>{
        System.out.println("start");
        Objects.requireNonNull(order, "order");
        if (address == null) <fold text='{...}' expand='true'>{
            System.out.println("missing address");
            throw new NullPointerException("address");
        }</fold>
        if (note == null) <fold text='{...}' expand='true'>{
            throw new IllegalStateException("note");
        }</fold>
    }</fold>

    public void notFoldedChecks(String order, String address) <fold text='{...}' expand='true'>{
        Objects.requireNonNull(customer, "customer");
        Objects.requireNonNull(order, () -> "order");
        Objects.requireNonNull(address);
    }</fold>

    public void onlyFirstCheckOfParameter<fold text='(@NonNull ' expand='true'>(</fold>String order) <fold text='{...}' expand='true'>{<fold text='' expand='true'>
        </fold><fold text='' expand='true'>Objects.requireNonNull(order);</fold>
        Objects.requireNonNull(order, "order");
    }</fold>

    public void notFoldedElse(String order) <fold text='{...}' expand='true'>{
        if (order == null) <fold text='{...}' expand='true'>{
            throw new NullPointerException("order");
        }</fold> else <fold text='{...}' expand='true'>{
            System.out.println(order);
        }</fold>
    }</fold>
}
