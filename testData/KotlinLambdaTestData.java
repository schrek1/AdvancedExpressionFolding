package data;

import <fold text='...' expand='false'>java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;</fold>

@SuppressWarnings("ALL")
public class KotlinLambdaTestData {
    enum Direction <fold text='{...}' expand='true'>{ IN, OUT }</fold>

    static class Item <fold text='{...}' expand='true'>{
        private final Direction direction;
        private final int price;
        private final List<Item> parts;

        Item(Direction direction, int price, List<Item> parts) <fold text='{...}' expand='true'>{
            this.direction = direction;
            this.price = price;
            this.parts = parts;
        }</fold>

        public Direction getDirection()<fold text=' { ' expand='false'> {
            </fold>return direction;<fold text=' }' expand='false'>
        }</fold>

        public int getPrice()<fold text=' { ' expand='false'> {
            </fold>return price;<fold text=' }' expand='false'>
        }</fold>

        public List<Item> getParts()<fold text=' { ' expand='false'> {
            </fold>return parts;<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    boolean anyOutgoing(List<Item> items)<fold text=' { ' expand='false'> {
        </fold>return items.stream().anyMatch<fold text=' {' expand='false'>(d -></fold> <fold text='it' expand='false'>d</fold>.<fold text='direction' expand='false'>getDirection()</fold> == Direction.OUT<fold text=' }' expand='false'>)</fold>;<fold text=' }' expand='false'>
    }</fold>

    int load()<fold text=' { ' expand='false'> {
        </fold>return 42;<fold text=' }' expand='false'>
    }</fold>

    Supplier<Integer> lazyLoad()<fold text=' { ' expand='false'> {
        </fold>return <fold text='{' expand='false'>() -></fold> load()<fold text=' };' expand='false'>;</fold><fold text=' }' expand='false'>
    }</fold>

    BiFunction<Integer, Integer, Integer> adder()<fold text=' { ' expand='false'> {
        </fold>return <fold text='{ ' expand='false'>(</fold>a, b<fold text='' expand='false'>)</fold> -> a + b<fold text=' };' expand='false'>;</fold><fold text=' }' expand='false'>
    }</fold>

    List<Integer> prices(List<Item> items)<fold text=' { ' expand='false'> {
        </fold>return items.stream().map<fold text=' {' expand='false'>((Item d) -></fold> <fold text='it' expand='false'>d</fold>.<fold text='price' expand='false'>getPrice()</fold><fold text=' }' expand='false'>)</fold>.toList();<fold text=' }' expand='false'>
    }</fold>

    void printAll(List<Item> items) <fold text='{...}' expand='true'>{
        items.forEach<fold text='' expand='false'>(item -></fold> <fold text='{...}' expand='true'>{
            int price = <fold text='it' expand='false'>item</fold>.<fold text='price' expand='false'>getPrice()</fold>;
            System.out.println(price);
        }</fold><fold text='' expand='false'>)</fold>;
    }</fold>

    void printEntries(Map<String, Integer> stock) <fold text='{...}' expand='true'>{
        stock.forEach<fold text=' { ' expand='false'>((</fold>name, count<fold text='' expand='false'>)</fold> -> <fold text='' expand='false'><fold text='{...}' expand='true'>{</fold>
            System.out.println(name + count);
        }</fold><fold text='' expand='false'>)</fold>;
    }</fold>

    Item firstOrCreate(Optional<Item> found, int price) <fold text='{...}' expand='true'>{
        return found.orElseGet<fold text=' {' expand='false'>(() -></fold> new Item(Direction.IN, price, List.of())<fold text=' }' expand='false'>)</fold>;
    }</fold>

    <T> T withDefault(T fallback, Supplier<T> supplier)<fold text=' { ' expand='false'> {
        </fold>return supplier.get() != null ? supplier.get() : fallback;<fold text=' }' expand='false'>
    }</fold>

    <T> T withSupplierFirst(Supplier<T> supplier, T fallback) <fold text='{...}' expand='true'>{
        return supplier.get() != null ? supplier.get() : fallback;
    }</fold>

    void arguments(Item item) <fold text='{...}' expand='true'>{
        withDefault(0<fold text=')' expand='false'>,</fold> <fold text='{' expand='false'>() -></fold> item.<fold text='price' expand='false'>getPrice()</fold><fold text=' }' expand='false'>)</fold>;
        withSupplierFirst(<fold text='{' expand='false'>() -></fold> item.<fold text='price' expand='false'>getPrice()</fold><fold text=' },' expand='false'>,</fold> 0);
        withDefault(0, // keep the comment
                <fold text='{' expand='false'>() -></fold> item.<fold text='price' expand='false'>getPrice()</fold><fold text=' })' expand='false'>)</fold>;
    }</fold>

    static class Funding <fold text='{...}' expand='true'>{
        public String getAccountId()<fold text=' { ' expand='false'> {
            </fold>return "acme-1";<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    static class AccountService <fold text='{...}' expand='true'>{
        Item requireAccount(String accountId)<fold text=' { ' expand='false'> {
            </fold>return null;<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    void multiLineArgument(AccountService accountService, Funding funding, Item other) <fold text='{...}' expand='true'>{
        withSupplierFirst(
                <fold text='{' expand='false'>() -></fold> accountService.requireAccount(
                        funding.<fold text='accountId' expand='false'>getAccountId()</fold>)<fold text=' },' expand='false'>,</fold>
                other);
    }</fold>

    void declarations() <fold text='{...}' expand='true'>{
        Function<Item, Integer> price = <fold text='{' expand='false'>item -></fold> <fold text='it' expand='false'>item</fold>.<fold text='price' expand='false'>getPrice()</fold><fold text=' };' expand='false'>;</fold>
        Predicate<Item> outgoing = <fold text='{' expand='false'>d -></fold> <fold text='it' expand='false'>d</fold>.<fold text='direction' expand='false'>getDirection()</fold> == Direction.OUT<fold text=' };' expand='false'>;</fold>
        Comparator<Item> byPrice = <fold text='{' expand='false'>(Item</fold> a, <fold text='' expand='false'>Item </fold>b<fold text='' expand='false'>)</fold> -> Integer.compare(a.<fold text='price' expand='false'>getPrice()</fold>, b.<fold text='price' expand='false'>getPrice()</fold>)<fold text=' };' expand='false'>;</fold>
        Callable<Integer> call = <fold text='' expand='false'>() -> </fold><fold text='{...}' expand='true'>{
            return load();
        }</fold>;
    }</fold>

    long nested(List<Item> items) <fold text='{...}' expand='true'>{
        return items.stream()
                .filter<fold text=' {' expand='false'>(order -></fold> <fold text='it' expand='false'>order</fold>.<fold text='parts' expand='false'>getParts()</fold>.stream().anyMatch<fold text=' { ' expand='false'>(</fold>part -> part.<fold text='price' expand='false'>getPrice()</fold> > <fold text='it' expand='false'>order</fold>.<fold text='price' expand='false'>getPrice()</fold><fold text=' }' expand='false'>)</fold><fold text=' }' expand='false'>)</fold>
                .count();
    }</fold>

    void alreadyNamedIt(List<Item> items)<fold text=' { ' expand='false'> {
        </fold>items.forEach<fold text=' {' expand='false'>(it -></fold> System.out.println(it.<fold text='price' expand='false'>getPrice()</fold>)<fold text=' }' expand='false'>)</fold>;<fold text=' }' expand='false'>
    }</fold>

    void outerIt(List<Item> items, Item it)<fold text=' { ' expand='false'> {
        </fold>items.forEach<fold text=' { ' expand='false'>(</fold>d -> System.out.println(d.<fold text='price' expand='false'>getPrice()</fold> + it.<fold text='price' expand='false'>getPrice()</fold>)<fold text=' }' expand='false'>)</fold>;<fold text=' }' expand='false'>
    }</fold>

    Function<Item, Integer> methodReference()<fold text=' { ' expand='false'> {
        </fold>return Item::getPrice;<fold text=' }' expand='false'>
    }</fold>
}
