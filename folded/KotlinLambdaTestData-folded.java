package data;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@SuppressWarnings("ALL")
public class KotlinLambdaTestData {
    enum Direction { IN, OUT }

    static class Item {
        private final Direction direction;
        private final int price;
        private final List<Item> parts;

        Item(Direction direction, int price, List<Item> parts) {
            this.direction = direction;
            this.price = price;
            this.parts = parts;
        }

        public Direction getDirection() {
            return direction;
        }

        public int getPrice() {
            return price;
        }

        public List<Item> getParts() {
            return parts;
        }
    }

    boolean anyOutgoing(List<Item> items) {
        return items.stream().anyMatch { it.direction == Direction.OUT };
    }

    int load() {
        return 42;
    }

    Supplier<Integer> lazyLoad() {
        return { load() };
    }

    BiFunction<Integer, Integer, Integer> adder() {
        return { a, b -> a + b };
    }

    List<Integer> prices(List<Item> items) {
        return items.stream().map { it.price }.toList();
    }

    void printAll(List<Item> items) {
        items.forEach {
            int price = it.price;
            System.out.println(price);
        };
    }

    void printEntries(Map<String, Integer> stock) {
        stock.forEach { name, count -> 
            System.out.println(name + count);
        };
    }

    Item firstOrCreate(Optional<Item> found, int price) {
        return found.orElseGet { new Item(Direction.IN, price, List.of()) };
    }

    <T> T withDefault(T fallback, Supplier<T> supplier) {
        return supplier.get() != null ? supplier.get() : fallback;
    }

    <T> T withSupplierFirst(Supplier<T> supplier, T fallback) {
        return supplier.get() != null ? supplier.get() : fallback;
    }

    void arguments(Item item) {
        withDefault(0) { item.price };
        withSupplierFirst({ item.price }, 0);
        withDefault(0, // keep the comment
                { item.price });
    }

    static class Funding {
        public String getAccountId() {
            return "acme-1";
        }
    }

    static class AccountService {
        Item requireAccount(String accountId) {
            return null;
        }
    }

    void multiLineArgument(AccountService accountService, Funding funding, Item other) {
        withSupplierFirst(
                { accountService.requireAccount(
                        funding.accountId) },
                other);
    }

    void declarations() {
        Function<Item, Integer> price = { it.price };
        Predicate<Item> outgoing = { it.direction == Direction.OUT };
        Comparator<Item> byPrice = { a, b -> Integer.compare(a.price, b.price) };
        Callable<Integer> call = {
            return load();
        };
    }

    long nested(List<Item> items) {
        return items.stream()
                .filter { it.parts.stream().anyMatch { part -> part.price > it.price } }
                .count();
    }

    void alreadyNamedIt(List<Item> items) {
        items.forEach { System.out.println(it.price) };
    }

    void outerIt(List<Item> items, Item it) {
        items.forEach { d -> System.out.println(d.price + it.price) };
    }

    Function<Item, Integer> methodReference() {
        return Item::getPrice;
    }
}
