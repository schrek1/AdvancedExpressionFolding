package data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class StreamSpreadChainTestData {

    record Order(String id, Customer customer, boolean paid) {
    }

    static class Customer {
        private String name;

        public String getName() {
            return name;
        }
    }

    static class Orders {
        List<Order> pending;

        public List<Order> getPending() {
            return pending;
        }
    }

    public void spread(Orders orders, List<Order> history, Set<Customer> customers) {
        List<String> ids = orders.pending*.id;
        List<Customer> buyers = history*.customer;
        Set<String> names = customers*.name.toSet();
        List<String> pendingIds = orders.pending*.id;
        int count = history*.id.size();
        Set<String> pendingIdSet = orders.pending*.id.toSet();
        Set<String> paidIdSet = history.stream()
                .filter(Order::paid)
                .map(Order::id)
                .toSet();
        List<String> sortedIds = history.stream().map(Order::id).sorted().toList();

        List<String> upper = history.stream().map(order -> order.id.toUpperCase()).toList();
        List<String> paidIds = history.stream().filter(Order::paid).map(Order::id).toList();
        List<String> sorted = history.stream().map(Order::id).sorted().toList();
        String joined = history.stream().map(Order::id).collect(Collectors.joining(", "));
    }
}
