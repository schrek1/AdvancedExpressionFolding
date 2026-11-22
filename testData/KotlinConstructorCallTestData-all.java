package data;

import <fold text='...' expand='false'>java.math.BigDecimal;
import java.util.*;</fold>

public class KotlinConstructorCallTestData {

    record Order(long id, double total) <fold text='{...}' expand='true'>{
    }</fold>

    static class Page<T> <fold text='{...}' expand='true'>{
        Page(List<T> items, int number) <fold text='{}' expand='true'>{
        }</fold>
    }</fold>

    static class Registry <fold text='{...}' expand='true'>{
    }</fold>

    class Line <fold text='{...}' expand='true'>{
    }</fold>

    private final Map<String, Order> cache = <fold text='' expand='false'>new </fold>HashMap<>();
    private final Registry registry =<fold text=' ::' expand='true'> </fold>new<fold text='' expand='true'> </fold><fold text='' expand='true'>Registry</fold><fold text='' expand='true'>()</fold>;

    Order plainCreation(long id, double total) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>var</fold> order = <fold text='' expand='false'>new </fold>Order(id, total);
        return order;
    }</fold>

    Page<String> genericCreation(List<String> items, int number)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>new </fold>Page<>(items, number)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    void throwCreation(String code)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold>throw <fold text='' expand='false'>new </fold>IllegalArgumentException("Unsupported code <fold text='$' expand='false'>" + </fold>code<fold text='")' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    Order nestedCreation(Order base)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>new </fold>Order(base.<fold text='id' expand='false'>id()</fold>, <fold text='' expand='false'>new </fold>Order(1, 2.5).<fold text='total' expand='false'>total()</fold>)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    void standardTypes() <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>List<String></fold> names = <fold text='[]' expand='false'>new ArrayList<>()</fold>;
        <fold text='val' expand='false'>StringBuilder</fold> text = <fold text='""' expand='false'>new StringBuilder()</fold>;
        <fold text='val' expand='false'>BigDecimal</fold> price = <fold text='' expand='false'>new BigDecimal(</fold><fold text='1.50' expand='false'>"1.50"</fold><fold text='' expand='false'>)</fold>;
        <fold text='val' expand='false'>Thread</fold> worker = <fold text='' expand='false'>new </fold>Thread(() -> <fold text='' expand='false'>System.out.</fold>println(<fold text='"done"' expand='false'>"done"</fold>));
    }</fold>

    void notFolded(KotlinConstructorCallTestData outer) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>Runnable</fold> task = new Runnable() <fold text='{...}' expand='true'>{
            <fold text='' expand='true'>@Override</fold><fold text='' expand='true'>
            </fold>public void run() <fold text='{}' expand='true'>{
            }</fold>
        }</fold>;
        <fold text='val' expand='false'>int[]</fold> counts = new int[3];
        <fold text='val' expand='false'>String[]</fold> labels = <fold text='[' expand='false'>new String[]{</fold>"a", "b"<fold text=']' expand='false'>}</fold>;
        <fold text='val' expand='false'>Line</fold> line = outer.new Line();
    }</fold>
}
