package data;

import <fold text='...' expand='false'>java.util.ArrayList;
import java.util.List;</fold>

public class SingletonObjectTestData {

    static <fold text='object' expand='true'>class</fold> Registry <fold text='{...}' expand='true'>{<fold text='' expand='true'>
        </fold><fold text='' expand='true'>private static final Registry INSTANCE = new Registry();</fold><fold text='' expand='true'>

        </fold><fold text='' expand='true'>private Registry() {}</fold><fold text='' expand='true'>

        </fold><fold text='' expand='true'>static Registry get()<fold text=' { ' expand='false'> {
            </fold>return INSTANCE;<fold text=' }' expand='false'>
        }</fold></fold>
    }</fold>

    public static final <fold text='object' expand='true'>class</fold> Inventory <fold text='{...}' expand='true'>{<fold text='' expand='true'>
        </fold><fold text='' expand='true'>private static final Inventory INSTANCE = new Inventory();</fold>

        private final List<String> items = new ArrayList<>();<fold text='' expand='true'>

        </fold><fold text='' expand='true'>private Inventory() <fold text='{}' expand='true'>{
        }</fold></fold><fold text='' expand='true'>

        </fold><fold text='' expand='true'>public static Inventory getInstance()<fold text=' { ' expand='false'> {
            </fold>return INSTANCE;<fold text=' }' expand='false'>
        }</fold></fold>

        public void add(String item)<fold text=' { ' expand='false'> {
            </fold>items.add(item);<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    // Not folded: lazy initialization
    static class LazyCatalog <fold text='{...}' expand='true'>{
        private static LazyCatalog instance;

        private LazyCatalog() {}

        static LazyCatalog getInstance() <fold text='{...}' expand='true'>{
            if (instance == null) <fold text='{...}' expand='true'>{
                instance = new LazyCatalog();
            }</fold>
            return instance;
        }</fold>
    }</fold>

    // Not folded: no getter
    static class PublicInstance <fold text='{...}' expand='true'>{
        public static final PublicInstance INSTANCE = new PublicInstance();

        private PublicInstance() {}
    }</fold>

    // Not folded: the constructor does work
    static class Clock <fold text='{...}' expand='true'>{
        private static final Clock INSTANCE = new Clock();

        private final long start;

        private Clock()<fold text=' { ' expand='false'> {
            </fold>start = System.nanoTime();<fold text=' }' expand='false'>
        }</fold>

        static Clock get()<fold text=' { ' expand='false'> {
            </fold>return INSTANCE;<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    // Not folded: the constructor is public
    static class Config <fold text='{...}' expand='true'>{
        private static final Config DEFAULT = new Config();

        public Config() {}

        static Config defaults()<fold text=' { ' expand='false'> {
            </fold>return DEFAULT;<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    // Not folded: utility class without an instance
    static final class Prices <fold text='{...}' expand='true'>{
        private Prices()<fold text=' { ' expand='false'> {
            </fold>throw new UnsupportedOperationException();<fold text=' }' expand='false'>
        }</fold>

        static int twice(int value)<fold text=' { ' expand='false'> {
            </fold>return value * 2;<fold text=' }' expand='false'>
        }</fold>
    }</fold>
}
