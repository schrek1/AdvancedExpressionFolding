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

    <fold text='@LightValue s' expand='false'>s</fold>tatic class Item <fold text='{...}' expand='true'>{
        private final Direction direction;
        private final int price;
        private final List<Item> parts;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>Item(Direction direction, int price, List<Item> parts) <fold text='{...}' expand='true'>{
            this.direction = <fold text='<<' expand='false'>direction</fold>;
            this.price = <fold text='<<' expand='false'>price</fold>;
            this.parts = <fold text='<<' expand='false'>parts</fold>;
        }</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public Direction getDirection()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>direction<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public int getPrice()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>price<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public List<Item> getParts()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>parts<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>
    }</fold>

    boolean anyOutgoing(List<Item> items)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>items<fold text='' expand='false'>.stream()</fold>.<fold text='any' expand='false'>anyMatch</fold><fold text=' {' expand='false'>(d -></fold> <fold text='it' expand='false'>d</fold>.<fold text='direction' expand='false'>getDirection()</fold> == Direction.OUT<fold text=' }' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    int load()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>42<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    Supplier<Integer> lazyLoad()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='{' expand='false'>() -></fold> load()<fold text=' }' expand='false'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    BiFunction<Integer, Integer, Integer> adder()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='{ ' expand='false'>(</fold>a, b<fold text='' expand='false'>)</fold> -> a + b<fold text=' }' expand='false'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    List<Integer> prices(List<Item> items)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>items<fold text='.' expand='false'>.stream().</fold>map<fold text=' {' expand='false'>((Item d) -></fold> <fold text='it' expand='false'>d</fold>.<fold text='price' expand='false'>getPrice()</fold><fold text=' }' expand='false'>)</fold>.toList()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    void printAll(List<Item> items) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold>items.forEach<fold text='' expand='false'>(item -></fold> <fold text='{...}' expand='true'>{
            <fold text='val' expand='false'>int</fold> price = <fold text='it' expand='false'>item</fold>.<fold text='price' expand='false'>getPrice()</fold>;
            <fold text='' expand='false'>System.out.</fold>println(price);
        }</fold><fold text='' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    void printEntries(Map<String, Integer> stock) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold>stock.forEach<fold text=' { ' expand='false'>((</fold>name, count<fold text='' expand='false'>)</fold> -> <fold text='' expand='false'><fold text='{...}' expand='true'>{</fold>
            <fold text='' expand='false'>System.out.</fold>println(name + count);
        }</fold><fold text='' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    Item firstOrCreate(Optional<Item> found, int price) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>found<fold text=' ?: ' expand='false'>.orElseGet(</fold>() -> new Item(Direction.IN, price, <fold text='[]' expand='false'>List.of()</fold>)<fold text='' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    <T> T withDefault(T fallback, Supplier<T> supplier)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>supplier.get() != null ? </fold>supplier.<fold text='!!' expand='false'>get()</fold><fold text=' ?: ' expand='false'> : </fold>fallback<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    <T> T withSupplierFirst(Supplier<T> supplier, T fallback) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>supplier.get() != null ? </fold>supplier.<fold text='!!' expand='false'>get()</fold><fold text=' ?: ' expand='false'> : </fold>fallback<fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    void arguments(Item item) <fold text='{...}' expand='true'>{
        withDefault(0<fold text=')' expand='false'>,</fold> <fold text='{' expand='false'>() -></fold> item.<fold text='price' expand='false'>getPrice()</fold><fold text=' }' expand='false'>)</fold>;
        withSupplierFirst(<fold text='{' expand='false'>() -></fold> item.<fold text='price' expand='false'>getPrice()</fold><fold text=' },' expand='false'>,</fold> 0);
        withDefault(0, // keep the comment
                <fold text='{' expand='false'>() -></fold> item.<fold text='price' expand='false'>getPrice()</fold><fold text=' })' expand='false'>)</fold>;
    }</fold>

    static class Funding <fold text='{...}' expand='true'>{
        <fold text='' expand='true'>public</fold><fold text='' expand='true'> </fold><fold text='' expand='true'>String</fold><fold text='' expand='true'> </fold>getAccountId<fold text='' expand='true'>()</fold><fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>"acme-1"<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>

    static class AccountService <fold text='{...}' expand='true'>{
        Item requireAccount(String accountId)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>null<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>

    void multiLineArgument(AccountService accountService, Funding funding, Item other) <fold text='{...}' expand='true'>{
        withSupplierFirst(
                <fold text='{' expand='false'>() -></fold> accountService.requireAccount(
                        funding.<fold text='accountId' expand='false'>getAccountId()</fold>)<fold text=' },' expand='false'>,</fold>
                other);
    }</fold>

    void declarations() <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>Function<Item, Integer></fold> price = <fold text='{' expand='false'>item -></fold> <fold text='it' expand='false'>item</fold>.<fold text='price' expand='false'>getPrice()</fold><fold text=' };' expand='false'>;</fold>
        <fold text='val' expand='false'>Predicate<Item></fold> outgoing = <fold text='{' expand='false'>d -></fold> <fold text='it' expand='false'>d</fold>.<fold text='direction' expand='false'>getDirection()</fold> == Direction.OUT<fold text=' };' expand='false'>;</fold>
        <fold text='val' expand='false'>Comparator<Item></fold> byPrice = <fold text='{' expand='false'>(Item</fold> a, <fold text='' expand='false'>Item </fold>b<fold text='' expand='false'>)</fold> -> Integer.compare(a.<fold text='price' expand='false'>getPrice()</fold>, b.<fold text='price' expand='false'>getPrice()</fold>)<fold text=' };' expand='false'>;</fold>
        <fold text='val' expand='false'>Callable<Integer></fold> call = <fold text='' expand='false'>() -> </fold><fold text='{...}' expand='true'>{
            return load();
        }</fold>;
    }</fold>

    long nested(List<Item> items) <fold text='{...}' expand='true'>{
        return items<fold text='' expand='false'>.stream()</fold>
                .filter<fold text=' {' expand='false'>(order -></fold> <fold text='it' expand='false'>order</fold>.<fold text='parts' expand='false'>getParts()</fold><fold text='' expand='false'>.stream()</fold>.<fold text='any' expand='false'>anyMatch</fold><fold text=' { ' expand='false'>(</fold>part -> part.<fold text='price' expand='false'>getPrice()</fold> > <fold text='it' expand='false'>order</fold>.<fold text='price' expand='false'>getPrice()</fold><fold text=' }' expand='false'>)</fold><fold text=' }' expand='false'>)</fold>
                .count();
    }</fold>

    void alreadyNamedIt(List<Item> items)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold>items.forEach<fold text=' {' expand='false'>(it -></fold> <fold text='' expand='false'>System.out.</fold>println(it.<fold text='price' expand='false'>getPrice()</fold>)<fold text=' }' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    void outerIt(List<Item> items, Item it)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold>items.forEach<fold text=' { ' expand='false'>(</fold>d -> <fold text='' expand='false'>System.out.</fold>println(d.<fold text='price' expand='false'>getPrice()</fold> + it.<fold text='price' expand='false'>getPrice()</fold>)<fold text=' }' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    Function<Item, Integer> methodReference()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>Item::getPrice<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>
}
