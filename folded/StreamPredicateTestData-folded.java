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
        boolean anyAdmin = users.any(User::isAdmin);
        boolean allPaid = orders.all(o -> o.paid);
        boolean noneEmpty = names.none(String::isEmpty);
        boolean anyShipped = orders.any(Order::wasShipped);

        boolean anyLongName = users.any(user -> user.name.length() > 10);
        boolean allNamed = users.all(it -> it.name != null);
        boolean noneBlank = users*.name()
                .none(String::isBlank);
        boolean anyKnownAdmin = users.filterNotNull()
                .any(User::isAdmin);

        boolean anyActive = users.any(isActive);
        boolean anySelf = users.any(StreamPredicateTestData::isSelf);
        boolean anyBlock = users.any(user -> {
            return user.admin;
        });
        boolean anyNested = users.any(user -> orders.any(it -> it.owner == user));
        boolean anyOwner = users.any(user -> orders.any(order -> order.owner == user));

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
