package data;

import <fold text='...' expand='false'>java.util.Objects;

import static java.util.Objects.requireNonNull;</fold>

public class LombokNonNullTestData {

    private String customer;

    public LombokNonNullTestData<fold text='(@NonNull ' expand='true'>(</fold>String customer) <fold text='{...}' expand='true'>{<fold text='' expand='true'>
        </fold><fold text='' expand='true'>Objects.requireNonNull(customer);</fold>
        this.customer = <fold text='<<' expand='false'>customer</fold>;
    }</fold>

    public void ship<fold text='(@NonNull ' expand='true'>(</fold>String order) <fold text='{...}' expand='true'>{<fold text='' expand='true'>
        </fold><fold text='' expand='true'>Objects.requireNonNull(order, "order");</fold>
        <fold text='' expand='false'>System.out.</fold>println(order);
    }</fold>

    public void ship<fold text='(@NonNull ' expand='true'>(</fold>String order,<fold text=' @NonNull ' expand='true'> </fold>String address, int quantity) <fold text='{...}' expand='true'>{<fold text='' expand='true'>
        </fold><fold text='' expand='true'>requireNonNull(order);</fold><fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (address == null) <fold text='{...}' expand='true'>{
            throw new NullPointerException("address is marked non-null but is null");
        }</fold></fold>
        <fold text='' expand='false'>System.out.</fold>println(order + address + quantity);
    }</fold>

    public void pack<fold text='(@NonNull ' expand='true'>(</fold>String order,<fold text=' @NonNull ' expand='true'> </fold>String box) <fold text='{...}' expand='true'>{<fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (null == order) throw new IllegalArgumentException("order");</fold><fold text='' expand='true'>
        </fold><fold text='' expand='true'>Objects.requireNonNull(box, "box");</fold>
    }</fold>

    public void notFolded(String order, String address, String note) <fold text='{...}' expand='true'>{
        <fold text='' expand='false'>System.out.</fold>println("start");
        <fold text='requireNotNull' expand='false'>Objects.requireNonNull</fold>(order<fold text=') { ' expand='false'>, </fold>"order"<fold text=' }' expand='false'>)</fold>;
        if <fold text='' expand='false'>(</fold>address == null<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println("missing address");
            throw new NullPointerException("address");
        }</fold>
        <fold text='check' expand='false'>if </fold>(note <fold text='!=' expand='false'>==</fold> null) <fold text='{...}' expand='true'>{<fold text=' ' expand='false'>
            throw new IllegalStateException(</fold>"note"<fold text=' ' expand='false'>);
        </fold>}</fold>
    }</fold>

    public void notFoldedChecks(String order, String address) <fold text='{...}' expand='true'>{
        <fold text='requireNotNull' expand='false'>Objects.requireNonNull</fold>(customer<fold text=') { ' expand='false'>, </fold>"customer"<fold text=' }' expand='false'>)</fold>;
        Objects.requireNonNull(order<fold text=')' expand='false'>,</fold> <fold text='{' expand='false'>() -></fold> "order"<fold text=' }' expand='false'>)</fold>;
        <fold text='' expand='false'>Objects.requireNonNull(</fold>address<fold text='!!' expand='false'>)</fold>;
    }</fold>

    public void onlyFirstCheckOfParameter<fold text='(@NonNull ' expand='true'>(</fold>String order) <fold text='{...}' expand='true'>{<fold text='' expand='true'>
        </fold><fold text='' expand='true'>Objects.requireNonNull(order);</fold>
        <fold text='requireNotNull' expand='false'>Objects.requireNonNull</fold>(order<fold text=') { ' expand='false'>, </fold>"order"<fold text=' }' expand='false'>)</fold>;
    }</fold>

    public void notFoldedElse(String order) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>order == null<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            throw new NullPointerException("order");
        }</fold> else <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println(order);
        }</fold>
    }</fold>
}
