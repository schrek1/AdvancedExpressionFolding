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
        List<String> ids = orders.pending.stream().map(Order::id).toList();
        List<Customer> buyers = history.stream().map(Order::customer).collect(Collectors.toList());
        Set<String> names = customers.stream().map(Customer::getName).collect(Collectors.toSet());
        List<String> pendingIds = orders.getPending()
                .stream()
                .map(Order::id)
                .toList();
        int count = history.stream().map(Order::id).toList().size();
        Set<String> pendingIdSet = orders.pending.stream().map(Order::id).collect(toSet());
        Set<String> paidIdSet = history.stream()
                .filter(Order::paid)
                .map(Order::id)
                .collect(toSet());
        List<String> sortedIds = history.stream().map(Order::id).sorted().collect(Collectors.toList());

        List<String> upper = history.stream().map(order -> order.id().toUpperCase()).toList();
        List<String> paidIds = history.stream().filter(Order::paid).map(Order::id).toList();
        List<String> sorted = history.stream().map(Order::id).sorted().toList();
        String joined = history.stream().map(Order::id).collect(Collectors.joining(", "));
    }
}
