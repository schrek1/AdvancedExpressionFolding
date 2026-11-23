package data;

import java.util.*;

public class ListOfLiteralTestData {
    public static void main(String[] args) {
        List<String> tags = List.of("new", "sale");
        System.out.println(tags);
        List<Integer> single = List.of(42);
        System.out.println(single);
        List<List<String>> prices = List.of(List.of("12", "14.5"), List.of("9.99"));
        System.out.println(prices);
        Map<String, List<String>> sizes = Map.of("shirt", List.of("S", "M"));
        System.out.println(sizes);

        List<String> colors = List.of(
                "red",
                "green",
                "blue");
        System.out.println(colors);
        List<String> shapes = List.of(
                "circle",
                "square"
        );
        System.out.println(shapes);
        List<List<String>> grid = List.of(
                List.of("a1", "a2"),
                List.of(
                        "b1",
                        "b2"),
                List.of("c1"));
        System.out.println(grid);
        System.out.println(List.of(
                "first",
                "second").size());

        System.out.println(checkout("best", "sold out"));

        // not folded: a set is not a list, and an array passed as the varargs
        Set<String> ids = Set.of("a", "b");
        System.out.println(ids);
        String[] names = {"x", "y"};
        List<String> fromArray = List.of(names);
        System.out.println(fromArray);
    }

    static PriceBook priceBook(String bestAsk) {
        var book = new PriceBook();
        book.setAsks(List.of(bestAsk));
        book.setBids(List.of());
        return book;
    }

    static CheckoutService checkout(String priceSource, String fallback) {
        return new CheckoutService(
                "orders",
                "settings",
                List.of(priceSource),
                fallback,
                42
        );
    }

    static class PriceBook {
        private List<String> asks;
        private List<String> bids;

        public void setAsks(List<String> asks) {
            this.asks = asks;
        }

        public void setBids(List<String> bids) {
            this.bids = bids;
        }
    }

    record CheckoutService(String repository, String settings, List<String> priceSources, String fallback, int timeout) {
    }
}
