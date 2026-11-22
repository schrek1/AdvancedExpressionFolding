package data;

import java.util.List;

public class ScopeWithTestData {

    private Order current;

    public void folded(Order order, List<String> lines) <fold text='{...}' expand='true'>{
       <fold text='with(' expand='false'> </fold>order<fold text=') { ' expand='false'>.</fold>validate();<fold text=' ' expand='false'>
        order.</fold>reserveStock();<fold text=' ' expand='false'>
        order.</fold>notifyCustomer("Thanks for your order")<fold text=' }' expand='false'>;</fold>

       <fold text='with(' expand='false'> </fold>lines<fold text=') { ' expand='false'>.</fold>add("first");<fold text=' ' expand='false'>
        lines.</fold>add("second");<fold text=' ' expand='false'>
        lines.</fold>add("third");<fold text=' ' expand='false'>
        lines.</fold>add("fourth")<fold text=' }' expand='false'>;</fold>

       <fold text='with(' expand='false'> </fold>current<fold text=') { ' expand='false'>.</fold>validate();<fold text=' ' expand='false'>
        current.</fold>reserveStock();<fold text=' ' expand='false'>
        current.</fold>notifyCustomer("Updated")<fold text=' }' expand='false'>;</fold>
    }</fold>

    public void notFolded(Order order, Order other) <fold text='{...}' expand='true'>{
        order.validate();
        order.reserveStock();

        order.validate();
        System.out.println("between");
        order.reserveStock();
        order.notifyCustomer("Thanks");

        order.validate();
        other.reserveStock();
        order.notifyCustomer("Thanks");

        order.validate();
        // stock first
        order.reserveStock();
        order.notifyCustomer("Thanks");

        order.validate();
        order.reserveStock();
        order.notifyCustomer(
                "Thanks");

        order.items().clear();
        order.items().clear();
        order.items().clear();
    }</fold>

    static class Order <fold text='{...}' expand='true'>{
        private List<String> items;

        public void validate() <fold text='{}' expand='true'>{
        }</fold>

        public void reserveStock() <fold text='{}' expand='true'>{
        }</fold>

        public void notifyCustomer(String message) <fold text='{}' expand='true'>{
        }</fold>

        public List<String> items()<fold text=' { ' expand='false'> {
            </fold>return items;<fold text=' }' expand='false'>
        }</fold>
    }</fold>
}
