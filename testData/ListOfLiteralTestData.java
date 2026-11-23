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

        Set<String> ids = <fold text='setOf' expand='false'>Set.of</fold>("a", "b");
        System.out.println(ids);
        String[] names = {"x", "y"};
        List<String> fromArray = <fold text='[*' expand='false'>List.of(</fold>names<fold text=']' expand='false'>)</fold>;
        System.out.println(fromArray);
        Set<String> idsFromArray = <fold text='setOf(*' expand='false'>Set.of(</fold>names);
        System.out.println(idsFromArray);
        List<String> copy = <fold text='[*' expand='false'>new ArrayList<>(List.of(</fold>names<fold text=']' expand='false'>))</fold>;
        System.out.println(copy);
        System.out.println(<fold text='[*' expand='false'>List.of(</fold><fold text='arrayOf(' expand='false'>new String[]{</fold>"p", "q"<fold text=')' expand='false'>}</fold><fold text=']' expand='false'>)</fold>);

        System.out.println(Arrays.toString(<fold text='arrayOf(' expand='false'>new String[]{</fold>"x", "y"<fold text=')' expand='false'>}</fold>));
        System.out.println(Arrays.toString(<fold text='intArrayOf(' expand='false'>new int[]{</fold>1, 2<fold text=')' expand='false'>}</fold>));
        System.out.println(Arrays.toString(<fold text='arrayOf()' expand='false'>new String[]{}</fold>));
        System.out.println(Arrays.deepToString(new int[][]{{1}, {2}}));
    }</fold>

    static String[] codes()<fold text=' { ' expand='false'> {
        </fold>return <fold text='arrayOf(' expand='false'>new String[]{</fold>"red", "blue"<fold text=')' expand='false'>}</fold>;<fold text=' }' expand='false'>
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
