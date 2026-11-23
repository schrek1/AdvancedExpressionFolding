package data;

import java.util.*;

public class ListOfLiteralTestData {
    public static void main(String[] args) {
        List<String> tags = ["new", "sale"];
        System.out.println(tags);
        List<Integer> single = [42];
        System.out.println(single);
        List<List<String>> prices = [["12", "14.5"], ["9.99"]];
        System.out.println(prices);
        Map<String, List<String>> sizes = ["shirt": ["S", "M"]];
        System.out.println(sizes);

        List<String> colors = [
                "red",
                "green",
                "blue"
        System.out.println(colors);
        List<String> shapes = [
                "circle",
                "square"
        ];
        System.out.println(shapes);
        List<List<String>> grid = [
                ["a1", "a2"],
                [
                        "b1",
                        "b2"
                ["c1"]
        System.out.println(grid);
        System.out.println([
                "first",
                "second"].size());

        System.out.println(checkout("best", "sold out"));

        Set<String> ids = setOf("a", "b");
        System.out.println(ids);
        String[] names = {"x", "y"};
        List<String> fromArray = [*names];
        System.out.println(fromArray);
        Set<String> idsFromArray = setOf(*names);
        System.out.println(idsFromArray);
        List<String> copy = [*names];
        System.out.println(copy);
        System.out.println([*arrayOf("p", "q")]);

        System.out.println(Arrays.toString(arrayOf("x", "y")));
        System.out.println(Arrays.toString(intArrayOf(1, 2)));
        System.out.println(Arrays.toString(arrayOf()));
        System.out.println(Arrays.deepToString(new int[][]{{1}, {2}}));
    }

    static String[] codes() {
        return arrayOf("red", "blue");
    }

    static PriceBook priceBook(String bestAsk) {
        var book = new PriceBook();
        book.setAsks([bestAsk]);
        book.setBids([]);
        return book;
    }

    static CheckoutService checkout(String priceSource, String fallback) {
        return new CheckoutService(
                "orders",
                "settings",
                [priceSource],
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
