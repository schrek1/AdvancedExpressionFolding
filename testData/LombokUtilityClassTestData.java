package data;

import <fold text='...' expand='false'>java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;</fold>

public class LombokUtilityClassTestData {

    <fold text='@UtilityClass p' expand='false'>p</fold>ublic static <fold text='' expand='false'>final</fold><fold text='' expand='false'> </fold>class Money <fold text='{...}' expand='true'>{
        public <fold text='' expand='false'>static</fold><fold text='' expand='false'> </fold>final int SCALE = 2;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>private Money()<fold text=' { ' expand='false'> {
            </fold>throw new UnsupportedOperationException();<fold text=' }' expand='false'>
        }</fold></fold>

        public <fold text='' expand='false'>static</fold><fold text='' expand='false'> </fold>BigDecimal round(BigDecimal value)<fold text=' { ' expand='false'> {
            </fold>return value.setScale(SCALE, RoundingMode.HALF_UP);<fold text=' }' expand='false'>
        }</fold>

        <fold text='' expand='false'>static</fold><fold text='' expand='false'> </fold>BigDecimal sum(List<BigDecimal> values) <fold text='{...}' expand='true'>{
            return values.stream().reduce(<fold text='0' expand='false'>BigDecimal.ZERO</fold>, BigDecimal::add);
        }</fold>
    }</fold>

    <fold text='@UtilityClass s' expand='false'>s</fold>tatic <fold text='' expand='false'>final</fold><fold text='' expand='false'> </fold>class StockLimits <fold text='{...}' expand='true'>{
        <fold text='' expand='false'>static</fold><fold text='' expand='false'> </fold>final int MAX_ITEMS = 100;
        private <fold text='' expand='false'>static</fold><fold text='' expand='false'> </fold>final int MIN_ITEMS = 1;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>private StockLimits() <fold text='{}' expand='true'>{
        }</fold></fold>

        public <fold text='' expand='false'>static</fold><fold text='' expand='false'> </fold>boolean isAllowed(int items)<fold text=' { ' expand='false'> {
            </fold>return items >= MIN_ITEMS && items <= MAX_ITEMS;<fold text=' }' expand='false'>
        }</fold>

        public <fold text='' expand='false'>static</fold><fold text='' expand='false'> </fold>class Defaults <fold text='{...}' expand='true'>{
            public static final int ITEMS = 10;
        }</fold>
    }</fold>

    // singleton: an INSTANCE of its own type, left to the `object` folding
    <fold text='@NoArgsConstructor(private) p' expand='false'>p</fold>ublic static final class OrderRegistry <fold text='{...}' expand='true'>{
        public static final OrderRegistry INSTANCE = new OrderRegistry();
        private static final List<String> orders = new ArrayList<>();<fold text='' expand='false'>

        </fold><fold text='' expand='false'>private OrderRegistry() <fold text='{}' expand='true'>{
        }</fold></fold>

        public static void register(String order)<fold text=' { ' expand='false'> {
            </fold>orders.add(order);<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    // not a utility class: has an instance method
    public static final class Shop <fold text='{...}' expand='true'>{
        private Shop()<fold text=' { ' expand='false'> {
            </fold>throw new UnsupportedOperationException();<fold text=' }' expand='false'>
        }</fold>

        public static Shop open()<fold text=' { ' expand='false'> {
            </fold>return null;<fold text=' }' expand='false'>
        }</fold>

        public String name()<fold text=' { ' expand='false'> {
            </fold>return "shop";<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    // not a utility class: the constructor is not private
    public static final class Person <fold text='{...}' expand='true'>{
        public Person()<fold text=' { ' expand='false'> {
            </fold>throw new UnsupportedOperationException();<fold text=' }' expand='false'>
        }</fold>

        public static String greet(String name)<fold text=' { ' expand='false'> {
            </fold>return "Hello " + name;<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    // not a utility class: the constructor does more than block instantiation
    public static final class Order <fold text='{...}' expand='true'>{
        private static int created;

        private Order()<fold text=' { ' expand='false'> {
            </fold>created++;<fold text=' }' expand='false'>
        }</fold>

        public static int created()<fold text=' { ' expand='false'> {
            </fold>return created;<fold text=' }' expand='false'>
        }</fold>
    }</fold>
}
