package data;

import java.util.*;

public class ListOfLiteralTestData {
    public static void main(String[] args) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>List<String></fold> tags = <fold text='[' expand='false'>List.of(</fold>"new", "sale"<fold text=']' expand='false'>)</fold>;
        <fold text='' expand='false'>System.out.</fold>println(tags);
        <fold text='val' expand='false'>List<Integer></fold> single = <fold text='[' expand='false'>List.of(</fold>42<fold text=']' expand='false'>)</fold>;
        <fold text='' expand='false'>System.out.</fold>println(single);
        <fold text='val' expand='false'>List<List<String>></fold> prices = <fold text='[' expand='false'>List.of(</fold><fold text='[' expand='false'>List.of(</fold>"12", "14.5"<fold text=']' expand='false'>)</fold>, <fold text='[' expand='false'>List.of(</fold>"9.99"<fold text=']' expand='false'>)</fold><fold text=']' expand='false'>)</fold>;
        <fold text='' expand='false'>System.out.</fold>println(prices);
        <fold text='val' expand='false'>Map<String, List<String>></fold> sizes = <fold text='[' expand='false'>Map.of(</fold>"shirt"<fold text=':' expand='false'>,</fold> <fold text='[' expand='false'>List.of(</fold>"S", "M"<fold text=']' expand='false'>)</fold><fold text=']' expand='false'>)</fold>;
        <fold text='' expand='false'>System.out.</fold>println(sizes);

        <fold text='val' expand='false'>List<String></fold> colors = <fold text='[' expand='false'>List.of(</fold>
                "red",
                "green",
                "blue"<fold text='' expand='false'>);</fold>
        <fold text='' expand='false'>System.out.</fold>println(colors);
        <fold text='val' expand='false'>List<String></fold> shapes = <fold text='[' expand='false'>List.of(</fold>
                "circle",
                "square"
        <fold text=']' expand='false'>)</fold>;
        <fold text='' expand='false'>System.out.</fold>println(shapes);
        <fold text='val' expand='false'>List<List<String>></fold> grid = <fold text='[' expand='false'>List.of(</fold>
                <fold text='[' expand='false'>List.of(</fold>"a1", "a2"<fold text=']' expand='false'>)</fold>,
                <fold text='[' expand='false'>List.of(</fold>
                        "b1",
                        "b2"<fold text='' expand='false'>),</fold>
                <fold text='[' expand='false'>List.of(</fold>"c1"<fold text=']' expand='false'>)</fold><fold text='' expand='false'>);</fold>
        <fold text='' expand='false'>System.out.</fold>println(grid);
        <fold text='' expand='false'>System.out.</fold>println(<fold text='[' expand='false'>List.of(</fold>
                "first",
                "second"<fold text=']' expand='false'>)</fold>.size());

        <fold text='' expand='false'>System.out.</fold>println(checkout("best", "sold out"));

        <fold text='val' expand='false'>Set<String></fold> ids = <fold text='setOf' expand='false'>Set.of</fold>("a", "b");
        <fold text='' expand='false'>System.out.</fold>println(ids);
        <fold text='val' expand='false'>String[]</fold> names = {"x", "y"};
        <fold text='val' expand='false'>List<String></fold> fromArray = <fold text='[*' expand='false'>List.of(</fold>names<fold text=']' expand='false'>)</fold>;
        <fold text='' expand='false'>System.out.</fold>println(fromArray);
        <fold text='val' expand='false'>Set<String></fold> idsFromArray = <fold text='setOf(*' expand='false'>Set.of(</fold>names);
        <fold text='' expand='false'>System.out.</fold>println(idsFromArray);
        <fold text='val' expand='false'>List<String></fold> copy = <fold text='[*' expand='false'>new ArrayList<>(List.of(</fold>names<fold text=']' expand='false'>))</fold>;
        <fold text='' expand='false'>System.out.</fold>println(copy);
        <fold text='' expand='false'>System.out.</fold>println(<fold text='[*' expand='false'>List.of(</fold><fold text='arrayOf(' expand='false'>new String[]{</fold>"p", "q"<fold text=')' expand='false'>}</fold><fold text=']' expand='false'>)</fold>);

        <fold text='' expand='false'>System.out.</fold>println(Arrays.toString(<fold text='arrayOf(' expand='false'>new String[]{</fold>"x", "y"<fold text=')' expand='false'>}</fold>));
        <fold text='' expand='false'>System.out.</fold>println(Arrays.toString(<fold text='intArrayOf(' expand='false'>new int[]{</fold>1, 2<fold text=')' expand='false'>}</fold>));
        <fold text='' expand='false'>System.out.</fold>println(Arrays.toString(<fold text='arrayOf()' expand='false'>new String[]{}</fold>));
        <fold text='' expand='false'>System.out.</fold>println(Arrays.deepToString(new int[][]{{1}, {2}}));
    }</fold>

    static String[] codes()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='arrayOf(' expand='false'>new String[]{</fold>"red", "blue"<fold text=')' expand='false'>}</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    static PriceBook priceBook(String bestAsk) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>var</fold> book = <fold text='' expand='false'>new </fold>PriceBook<fold text='(' expand='false'>();</fold>
        <fold text='a' expand='false'>book.setA</fold>sks<fold text=' = ' expand='false'>(</fold><fold text='[' expand='false'>List.of(</fold>bestAsk<fold text=']' expand='false'>)</fold><fold text=',' expand='false'>);</fold>
        <fold text='b' expand='false'>book.setB</fold>ids<fold text=' = ' expand='false'>(</fold><fold text='[]' expand='false'>List.of()</fold><fold text='' expand='false'>);</fold>
        return book;
    }</fold>

    static CheckoutService checkout(String priceSource, String fallback) <fold text='{...}' expand='true'>{
        return <fold text='' expand='false'>new </fold>CheckoutService(
                "orders",
                "settings",
                <fold text='[' expand='false'>List.of(</fold>priceSource<fold text=']' expand='false'>)</fold>,
                fallback,
                42
        );
    }</fold>

    <fold text='@Setter s' expand='false'>s</fold>tatic class PriceBook <fold text='{...}' expand='true'>{
        private List<String> asks;
        private List<String> bids;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>public void setAsks(List<String> asks)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>this.asks = <fold text='<<' expand='false'>asks</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public void setBids(List<String> bids)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>this.bids = <fold text='<<' expand='false'>bids</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>
    }</fold>

    record CheckoutService(String repository, String settings, List<String> priceSources, String fallback, int timeout) <fold text='{...}' expand='true'>{
    }</fold>
}
