package data;

import java.util.*;

public class ListOfLiteralTestData {
    public static void main(String[] args) <fold text='{...}' expand='true'>{
        List<String> tags = <fold text='[' expand='false'>List.of(</fold>"new", "sale"<fold text=']' expand='false'>)</fold>;
        System.out.println(tags);
        List<Integer> single = <fold text='[' expand='false'>List.of(</fold>42<fold text=']' expand='false'>)</fold>;
        System.out.println(single);
        List<List<String>> prices = <fold text='[' expand='false'>List.of(</fold><fold text='[' expand='false'>List.of(</fold>"12", "14.5"<fold text=']' expand='false'>)</fold>, <fold text='[' expand='false'>List.of(</fold>"9.99"<fold text=']' expand='false'>)</fold><fold text=']' expand='false'>)</fold>;
        System.out.println(prices);
        Map<String, List<String>> sizes = <fold text='[' expand='false'>Map.of(</fold>"shirt"<fold text=':' expand='false'>,</fold> <fold text='[' expand='false'>List.of(</fold>"S", "M"<fold text=']' expand='false'>)</fold><fold text=']' expand='false'>)</fold>;
        System.out.println(sizes);

        List<String> colors = <fold text='[' expand='false'>List.of(</fold>
                "red",
                "green",
                "blue"<fold text='' expand='false'>);</fold>
        System.out.println(colors);
        List<String> shapes = <fold text='[' expand='false'>List.of(</fold>
                "circle",
                "square"
        <fold text=']' expand='false'>)</fold>;
        System.out.println(shapes);
        List<List<String>> grid = <fold text='[' expand='false'>List.of(</fold>
                <fold text='[' expand='false'>List.of(</fold>"a1", "a2"<fold text=']' expand='false'>)</fold>,
                <fold text='[' expand='false'>List.of(</fold>
                        "b1",
                        "b2"<fold text='' expand='false'>),</fold>
                <fold text='[' expand='false'>List.of(</fold>"c1"<fold text=']' expand='false'>)</fold><fold text='' expand='false'>);</fold>
        System.out.println(grid);
        System.out.println(<fold text='[' expand='false'>List.of(</fold>
                "first",
                "second"<fold text=']' expand='false'>)</fold>.size());

        System.out.println(checkout("best", "sold out"));

        // not folded: a set is not a list, and an array passed as the varargs
        Set<String> ids = Set.of("a", "b");
        System.out.println(ids);
        String[] names = {"x", "y"};
        List<String> fromArray = List.of(names);
        System.out.println(fromArray);
    }</fold>

    static PriceBook priceBook(String bestAsk) <fold text='{...}' expand='true'>{
        var book = new PriceBook();
        book.setAsks(<fold text='[' expand='false'>List.of(</fold>bestAsk<fold text=']' expand='false'>)</fold>);
        book.setBids(<fold text='[]' expand='false'>List.of()</fold>);
        return book;
    }</fold>

    static CheckoutService checkout(String priceSource, String fallback) <fold text='{...}' expand='true'>{
        return new CheckoutService(
                "orders",
                "settings",
                <fold text='[' expand='false'>List.of(</fold>priceSource<fold text=']' expand='false'>)</fold>,
                fallback,
                42
        );
    }</fold>

    static class PriceBook <fold text='{...}' expand='true'>{
        private List<String> asks;
        private List<String> bids;

        public void setAsks(List<String> asks)<fold text=' { ' expand='false'> {
            </fold>this.asks = asks;<fold text=' }' expand='false'>
        }</fold>

        public void setBids(List<String> bids)<fold text=' { ' expand='false'> {
            </fold>this.bids = bids;<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    record CheckoutService(String repository, String settings, List<String> priceSources, String fallback, int timeout) <fold text='{...}' expand='true'>{
    }</fold>
}
