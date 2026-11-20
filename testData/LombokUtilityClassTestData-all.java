package data;

import <fold text='...' expand='false'>java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;</fold>

public class LombokUtilityClassTestData {

    <fold text='@UtilityClass p' expand='false'>p</fold>ublic static <fold text='' expand='false'>final</fold><fold text='' expand='false'> </fold>class Money <fold text='{...}' expand='true'>{
        <fold text='const' expand='false'>public <fold text='' expand='false'>static</fold><fold text='' expand='false'> </fold>final </fold><fold text='' expand='false'>int</fold> SCALE = 2;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>private Money()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>throw new UnsupportedOperationException()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>

        public <fold text='' expand='false'>static</fold><fold text='' expand='false'> </fold>BigDecimal round(BigDecimal value)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>value.setScale(SCALE, RoundingMode.HALF_UP)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        <fold text='' expand='false'>static</fold><fold text='' expand='false'> </fold>BigDecimal sum(List<BigDecimal> values) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
            </fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>values<fold text='.' expand='false'>.stream().</fold>reduce(<fold text='0' expand='false'>BigDecimal.ZERO</fold>, BigDecimal::add)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
        </fold>}</fold>
    }</fold>

    <fold text='@UtilityClass s' expand='false'>s</fold>tatic <fold text='' expand='false'>final</fold><fold text='' expand='false'> </fold>class StockLimits <fold text='{...}' expand='true'>{
        <fold text='' expand='false'><fold text='default const' expand='false'>static</fold><fold text='' expand='false'> </fold>final </fold><fold text='' expand='false'>int</fold> MAX_ITEMS = 100;
        private <fold text='' expand='false'><fold text='const' expand='false'>static</fold><fold text='' expand='false'> </fold>final </fold><fold text='' expand='false'>int</fold> MIN_ITEMS = 1;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>private StockLimits() <fold text='{}' expand='true'>{
        }</fold></fold>

        public <fold text='' expand='false'>static</fold><fold text='' expand='false'> </fold>boolean isAllowed(int items)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>items >= MIN_ITEMS && items <= MAX_ITEMS<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        public <fold text='' expand='false'>static</fold><fold text='' expand='false'> </fold>class Defaults <fold text='{...}' expand='true'>{
            <fold text='const' expand='false'>public static final </fold><fold text='' expand='false'>int</fold> ITEMS = 10;
        }</fold>
    }</fold>

    // singleton: an INSTANCE of its own type, left to the `object` folding
    <fold text='@NoArgsConstructor(private) p' expand='false'>p</fold>ublic static <fold text='' expand='false'>final</fold> class OrderRegistry <fold text='{...}' expand='true'>{
        <fold text='const' expand='false'>public static final</fold> OrderRegistry INSTANCE =<fold text=' ::' expand='true'> </fold>new<fold text='' expand='true'> </fold><fold text='' expand='true'>OrderRegistry</fold><fold text='' expand='true'>()</fold>;
        private <fold text='const' expand='false'>static final</fold> List<String> orders = <fold text='[]' expand='false'>new ArrayList<>()</fold>;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>private OrderRegistry() <fold text='{}' expand='true'>{
        }</fold></fold>

        public static void register(String order)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>orders<fold text=' += ' expand='false'>.add(</fold>order<fold text='' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>

    // not a utility class: has an instance method
    public static <fold text='' expand='false'>final</fold> class Shop <fold text='{...}' expand='true'>{
        private Shop()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>throw new UnsupportedOperationException()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        public static Shop open()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>null<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        <fold text='' expand='true'>public</fold><fold text='' expand='true'> </fold><fold text='' expand='true'>String</fold><fold text='' expand='true'> </fold>name<fold text='' expand='true'>()</fold><fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>"shop"<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>

    // not a utility class: the constructor is not private
    public static <fold text='' expand='false'>final</fold> class Person <fold text='{...}' expand='true'>{
        public Person()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>throw new UnsupportedOperationException()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        public static String greet(String name)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>"Hello <fold text='$' expand='false'>" + </fold>name<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>

    // not a utility class: the constructor does more than block instantiation
    public static <fold text='' expand='false'>final</fold> class Order <fold text='{...}' expand='true'>{
        private static int created;

        private Order()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>created++<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        public static int created()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>created<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>
}
