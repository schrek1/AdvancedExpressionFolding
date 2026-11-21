package data;

import java.math.BigDecimal;

@SuppressWarnings("ALL")
public class ObjectDestructuringTestData {

    public void recordAccessors(Order order) <fold text='{...}' expand='true'>{
        <fold text='val (' expand='true'><fold text='val' expand='false'>String</fold> </fold>customer<fold text=', ' expand='true'> = order.<fold text='customer' expand='false'>customer()</fold>;</fold><fold text='' expand='true'>
        <fold text='val' expand='false'>BigDecimal</fold> </fold>total<fold text=', ' expand='true'> = order.<fold text='total' expand='false'>total()</fold>;</fold><fold text='' expand='true'>
        <fold text='val' expand='false'>String</fold> </fold>currency<fold text=') ' expand='true'> </fold>= order<fold text='' expand='true'>.<fold text='currency' expand='false'>currency()</fold></fold>;
        blackhole(customer, total, currency);
    }</fold>

    public void getters(Shop shop) <fold text='{...}' expand='true'>{
        <fold text='val (' expand='true'><fold text='val' expand='false'>String</fold> </fold>name<fold text=', ' expand='true'> = shop.<fold text='name' expand='false'>getName()</fold>;</fold><fold text='' expand='true'>
        <fold text='val' expand='false'>boolean</fold> </fold>open<fold text=') ' expand='true'> </fold>= shop<fold text='' expand='true'>.<fold text='open' expand='false'>isOpen()</fold></fold>;
        blackhole(name, open);
    }</fold>

    public void reassignedLocal(Order order) <fold text='{...}' expand='true'>{
        <fold text='var (' expand='true'><fold text='var' expand='false'>String</fold> </fold>customer<fold text=', ' expand='true'> = order.<fold text='customer' expand='false'>customer()</fold>;</fold><fold text='' expand='true'>
        <fold text='val' expand='false'>String</fold> </fold>currency<fold text=') ' expand='true'> </fold>= order<fold text='' expand='true'>.<fold text='currency' expand='false'>currency()</fold></fold>;
        customer = "anonymous";
        blackhole(customer, currency);
    }</fold>

    public void castBeforeAccessors(Object o) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>o instanceof Order<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='val' expand='false'>Order</fold> order = <fold text='' expand='false'>(Order) </fold>o;
            <fold text='val' expand='false'>String</fold> customer = order.<fold text='customer' expand='false'>customer()</fold>;
            <fold text='val' expand='false'>BigDecimal</fold> total = order.<fold text='total' expand='false'>total()</fold>;
            <fold text='val' expand='false'>String</fold> currency = order.<fold text='currency' expand='false'>currency()</fold>;
            blackhole(customer, total, currency);
        }</fold>
    }</fold>

    public void singleLocal(Order order) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>String</fold> customer = order.<fold text='customer' expand='false'>customer()</fold>;
        blackhole(customer);
    }</fold>

    public void nameDoesNotMatchAccessor(Order order) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>String</fold> buyer = order.<fold text='customer' expand='false'>customer()</fold>;
        <fold text='val' expand='false'>String</fold> currency = order.<fold text='currency' expand='false'>currency()</fold>;
        blackhole(buyer, currency);
    }</fold>

    public void differentObjects(Order order, Order other) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>String</fold> customer = order.<fold text='customer' expand='false'>customer()</fold>;
        <fold text='val' expand='false'>String</fold> currency = other.<fold text='currency' expand='false'>currency()</fold>;
        blackhole(customer, currency);
    }</fold>

    public void qualifierIsACall(Order order) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>String</fold> customer = order.<fold text='copy' expand='false'>copy()</fold>.<fold text='customer' expand='false'>customer()</fold>;
        <fold text='val' expand='false'>String</fold> currency = order.<fold text='copy' expand='false'>copy()</fold>.<fold text='currency' expand='false'>currency()</fold>;
        blackhole(customer, currency);
    }</fold>

    public void notARecordAccessor(Shop shop) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>String</fold> name = shop.name();
        <fold text='val' expand='false'>String</fold> city = shop.city();
        blackhole(name, city);
    }</fold>

    public void methodWithArgument(Shop shop) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>String</fold> name = shop.<fold text='name' expand='false'>getName()</fold>;
        <fold text='val' expand='false'>String</fold> label = shop.getLabel("en");
        blackhole(name, label);
    }</fold>

    public record Order(String customer, BigDecimal total, String currency) <fold text='{...}' expand='true'>{
        public Order copy()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>this<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>

    public static class Shop <fold text='{...}' expand='true'>{
        <fold text='' expand='true'>public</fold><fold text='' expand='true'> </fold><fold text='' expand='true'>String</fold><fold text='' expand='true'> </fold>getName<fold text='' expand='true'>()</fold><fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>null<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        <fold text='' expand='true'>public</fold><fold text='' expand='true'> </fold><fold text='' expand='true'>boolean</fold><fold text='' expand='true'> </fold>isOpen<fold text='' expand='true'>()</fold><fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>true<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        <fold text='' expand='true'>public</fold><fold text='' expand='true'> </fold><fold text='' expand='true'>String</fold><fold text='' expand='true'> </fold>name<fold text='' expand='true'>()</fold><fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>null<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        <fold text='' expand='true'>public</fold><fold text='' expand='true'> </fold><fold text='' expand='true'>String</fold><fold text='' expand='true'> </fold>city<fold text='' expand='true'>()</fold><fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>null<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        <fold text='' expand='true'>public</fold><fold text='' expand='true'> </fold><fold text='' expand='true'>String</fold><fold text='' expand='true'> </fold>getLabel(String language)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>null<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>

    void blackhole(Object... values) <fold text='{}' expand='true'>{
    }</fold>
}
