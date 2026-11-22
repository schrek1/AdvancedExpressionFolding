package data;

import <fold text='...' expand='false'>java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;</fold>

public class StreamSpreadChainTestData {

    record Order(String id, Customer customer, boolean paid) <fold text='{...}' expand='true'>{
    }</fold>

    static class Customer <fold text='{...}' expand='true'>{
        private String name;

        public String getName()<fold text=' { ' expand='false'> {
            </fold>return name;<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    static class Orders <fold text='{...}' expand='true'>{
        List<Order> pending;

        public List<Order> getPending()<fold text=' { ' expand='false'> {
            </fold>return pending;<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    public void spread(Orders orders, List<Order> history, Set<Customer> customers) <fold text='{...}' expand='true'>{
        List<String> ids = orders.pending<fold text='*.' expand='false'>.stream().map(Order::</fold>id<fold text='' expand='false'>).toList()</fold>;
        List<Customer> buyers = history<fold text='*.' expand='false'>.stream().map(Order::</fold>customer<fold text='' expand='false'>).collect(Collectors.toList())</fold>;
        Set<String> names = customers<fold text='*.name' expand='false'>.stream().map(Customer::getName</fold><fold text='.' expand='false'>).collect(Collectors.</fold>toSet()<fold text='' expand='false'>)</fold>;
        List<String> pendingIds = orders.<fold text='pending' expand='false'>getPending()</fold><fold text='*.' expand='false'>
                .stream()
                .map(Order::</fold>id<fold text='' expand='false'>)
                .toList()</fold>;
        int count = history<fold text='*.' expand='false'>.stream().map(Order::</fold>id<fold text='' expand='false'>).toList()</fold>.size();
        Set<String> pendingIdSet = orders.pending<fold text='*.' expand='false'>.stream().map(Order::</fold>id<fold text='.' expand='false'>).collect(</fold>toSet()<fold text='' expand='false'>)</fold>;
        Set<String> paidIdSet = history.stream()
                .filter(Order::paid)
                .map(Order::id)
                <fold text='.' expand='false'>.collect(</fold>toSet()<fold text='' expand='false'>)</fold>;
        List<String> sortedIds = history.stream().map(Order::id).sorted()<fold text='.' expand='false'>.collect(Collectors.</fold>toList()<fold text='' expand='false'>)</fold>;

        List<String> upper = history.stream().map(order -> order.<fold text='id' expand='false'>id()</fold>.toUpperCase()).toList();
        List<String> paidIds = history.stream().filter(Order::paid).map(Order::id).toList();
        List<String> sorted = history.stream().map(Order::id).sorted().toList();
        String joined = history.stream().map(Order::id).collect(Collectors.joining(", "));
    }</fold>
}
