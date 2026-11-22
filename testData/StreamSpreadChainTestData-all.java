package data;

import <fold text='...' expand='false'>java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;</fold>

public class StreamSpreadChainTestData {

    record Order(String id, Customer customer, boolean paid) <fold text='{...}' expand='true'>{
    }</fold>

    <fold text='@Getter s' expand='false'>s</fold>tatic class Customer <fold text='{...}' expand='true'>{
        private String name;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>public String getName()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>name<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>
    }</fold>

    <fold text='@Getter s' expand='false'>s</fold>tatic class Orders <fold text='{...}' expand='true'>{
        List<Order> pending;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>public List<Order> getPending()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>pending<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>
    }</fold>

    public void spread(Orders orders, List<Order> history, Set<Customer> customers) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>List<String></fold> ids = orders.pending<fold text='*.' expand='false'>.stream().map(Order::</fold>id<fold text='' expand='false'>).toList()</fold>;
        <fold text='val' expand='false'>List<Customer></fold> buyers = history<fold text='*.' expand='false'>.stream().map(Order::</fold>customer<fold text='' expand='false'>).collect(Collectors.toList())</fold>;
        <fold text='val' expand='false'>Set<String></fold> names = customers<fold text='*.name' expand='false'>.stream().map(Customer::getName</fold><fold text='.' expand='false'>).collect(Collectors.</fold>toSet()<fold text='' expand='false'>)</fold>;
        <fold text='val' expand='false'>List<String></fold> pendingIds = orders.<fold text='pending' expand='false'>getPending()</fold><fold text='*.' expand='false'>
                .stream()
                .map(Order::</fold>id<fold text='' expand='false'>)
                .toList()</fold>;
        <fold text='val' expand='false'>int</fold> count = history<fold text='*.' expand='false'>.stream().map(Order::</fold>id<fold text='' expand='false'>).toList()</fold>.size();
        <fold text='val' expand='false'>Set<String></fold> pendingIdSet = orders.pending<fold text='*.' expand='false'>.stream().map(Order::</fold>id<fold text='.' expand='false'>).collect(</fold>toSet()<fold text='' expand='false'>)</fold>;
        <fold text='val' expand='false'>Set<String></fold> paidIdSet = history<fold text='' expand='false'>.stream()</fold>
                .filter(Order::paid)<fold text='*.' expand='false'>
                .map(</fold><fold text='id()' expand='false'>Order::id</fold><fold text='' expand='false'>)</fold>
                <fold text='.' expand='false'>.collect(</fold>toSet()<fold text='' expand='false'>)</fold>;
        <fold text='val' expand='false'>List<String></fold> sortedIds = history.stream()<fold text='*.' expand='false'>.map(</fold><fold text='id()' expand='false'>Order::id</fold><fold text='' expand='false'>)</fold>.sorted()<fold text='.' expand='false'>.collect(Collectors.</fold>toList()<fold text='' expand='false'>)</fold>;

        <fold text='val' expand='false'>List<String></fold> upper = history<fold text='.' expand='false'>.stream().</fold>map(order -> order.<fold text='id' expand='false'>id()</fold>.toUpperCase()).toList();
        <fold text='val' expand='false'>List<String></fold> paidIds = history<fold text='.' expand='false'>.stream().</fold>filter(Order::paid)<fold text='*.' expand='false'>.map(</fold><fold text='id()' expand='false'>Order::id</fold><fold text='' expand='false'>)</fold>.toList();
        <fold text='val' expand='false'>List<String></fold> sorted = history.stream()<fold text='*.' expand='false'>.map(</fold><fold text='id()' expand='false'>Order::id</fold><fold text='' expand='false'>)</fold>.sorted().toList();
        <fold text='val' expand='false'>String</fold> joined = history.stream()<fold text='*.' expand='false'>.map(</fold><fold text='id()' expand='false'>Order::id</fold><fold text='' expand='false'>)</fold>.collect(Collectors.joining(", "));
    }</fold>
}
