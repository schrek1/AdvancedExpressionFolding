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
    private final Registry registry = <fold text='' expand='false'>new </fold>Registry();

    Order plainCreation(long id, double total) <fold text='{...}' expand='true'>{
        var order = <fold text='' expand='false'>new </fold>Order(id, total);
        return order;
    }</fold>

    Page<String> genericCreation(List<String> items, int number)<fold text=' { ' expand='false'> {
        </fold>return <fold text='' expand='false'>new </fold>Page<>(items, number);<fold text=' }' expand='false'>
    }</fold>

    void throwCreation(String code)<fold text=' { ' expand='false'> {
        </fold>throw <fold text='' expand='false'>new </fold>IllegalArgumentException("Unsupported code " + code);<fold text=' }' expand='false'>
    }</fold>

    Order nestedCreation(Order base)<fold text=' { ' expand='false'> {
        </fold>return <fold text='' expand='false'>new </fold>Order(base.id(), <fold text='' expand='false'>new </fold>Order(1, 2.5).total());<fold text=' }' expand='false'>
    }</fold>

    void standardTypes() <fold text='{...}' expand='true'>{
        List<String> names = <fold text='' expand='false'>new </fold>ArrayList<>();
        StringBuilder text = <fold text='""' expand='false'>new StringBuilder()</fold>;
        BigDecimal price = <fold text='' expand='false'>new BigDecimal(</fold><fold text='1.50' expand='false'>"1.50"</fold><fold text='' expand='false'>)</fold>;
        Thread worker = <fold text='' expand='false'>new </fold>Thread(() -> System.out.println("done"));
    }</fold>

    void notFolded(KotlinConstructorCallTestData outer) <fold text='{...}' expand='true'>{
        Runnable task = new Runnable() <fold text='{...}' expand='true'>{
            @Override
            public void run() <fold text='{}' expand='true'>{
            }</fold>
        }</fold>;
        int[] counts = new int[3];
        String[] labels = new String[]{"a", "b"};
        Line line = outer.new Line();
    }</fold>
}
