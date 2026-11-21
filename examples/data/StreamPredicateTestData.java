package data;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

@SuppressWarnings("ALL")
public class StreamPredicateTestData {
    public static void main(List<User> users, Set<Order> orders, String[] names, Stream<User> stream, Predicate<User> isActive) {
        boolean anyAdmin = users.stream().anyMatch(User::isAdmin);
        boolean allPaid = orders.stream().allMatch(o -> o.isPaid());
        boolean noneEmpty = Arrays.stream(names).noneMatch(String::isEmpty);
        boolean anyShipped = orders.stream().anyMatch(Order::wasShipped);

        boolean anyLongName = users.stream().anyMatch(user -> user.getName().length() > 10);
        boolean allNamed = users.stream().allMatch(it -> it.getName() != null);
        boolean noneBlank = users.stream()
                .map(User::getName)
                .noneMatch(String::isBlank);
        boolean anyKnownAdmin = users.stream()
                .filter(Objects::nonNull)
                .anyMatch(User::isAdmin);

        boolean anyActive = users.stream().anyMatch(isActive);
        boolean anySelf = users.stream().anyMatch(StreamPredicateTestData::isSelf);
        boolean anyBlock = users.stream().anyMatch(user -> {
            return user.isAdmin();
        });
        boolean anyNested = users.stream().anyMatch(user -> orders.stream().anyMatch(it -> it.getOwner() == user));
        boolean anyOwner = users.stream().anyMatch(user -> orders.stream().anyMatch(order -> order.getOwner() == user));

        boolean notACollection = stream.anyMatch(User::isAdmin);
        boolean streamOf = Stream.of("a", "b").anyMatch(String::isEmpty);
    }

    static boolean isSelf(User user) {
        return false;
    }

    static class User {
        boolean admin;
        String name;

        public boolean isAdmin() {
            return admin;
        }

        public String getName() {
            return name;
        }
    }

    static class Order {
        boolean paid;
        User owner;

        public boolean isPaid() {
            return paid;
        }

        public User getOwner() {
            return owner;
        }

        public boolean wasShipped() {
            return owner != null;
        }
    }
}
