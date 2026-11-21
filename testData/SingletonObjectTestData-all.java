package data;

import <fold text='...' expand='false'>java.util.ArrayList;
import java.util.List;</fold>

public class SingletonObjectTestData {

    static <fold text='object' expand='true'>class</fold> Registry <fold text='{...}' expand='true'>{<fold text='' expand='true'>
        </fold><fold text='' expand='true'>private <fold text='const' expand='false'>static final</fold> Registry INSTANCE =<fold text=' ::' expand='true'> </fold>new<fold text='' expand='true'> </fold><fold text='' expand='true'>Registry</fold><fold text='' expand='true'>()</fold>;</fold><fold text='' expand='true'>

        </fold><fold text='' expand='true'>private Registry() {}</fold><fold text='' expand='true'>

        </fold><fold text='' expand='true'>static Registry get()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>INSTANCE<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>
    }</fold>

    public static <fold text='' expand='false'>final</fold> <fold text='object' expand='true'>class</fold> Inventory <fold text='{...}' expand='true'>{<fold text='' expand='true'>
        </fold><fold text='' expand='true'>private <fold text='const' expand='false'>static final</fold> Inventory INSTANCE =<fold text=' ::' expand='true'> </fold>new<fold text='' expand='true'> </fold><fold text='' expand='true'>Inventory</fold><fold text='' expand='true'>()</fold>;</fold>

        private final List<String> items = <fold text='[]' expand='false'>new ArrayList<>()</fold>;<fold text='' expand='true'>

        </fold><fold text='' expand='true'>private Inventory() <fold text='{}' expand='true'>{
        }</fold></fold><fold text='' expand='true'>

        </fold><fold text='' expand='true'>public static Inventory getInstance()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>INSTANCE<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>

        public void add(String item)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>items<fold text=' += ' expand='false'>.add(</fold>item<fold text='' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>

    // Not folded: lazy initialization
    <fold text='@NoArgsConstructor(private) s' expand='false'>s</fold>tatic class LazyCatalog <fold text='{...}' expand='true'>{
        private static LazyCatalog instance;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>private LazyCatalog() {}</fold>

        static LazyCatalog getInstance() <fold text='{...}' expand='true'>{
            if <fold text='' expand='false'>(</fold>instance == null<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
                instance = new LazyCatalog();
            }</fold>
            return instance;
        }</fold>
    }</fold>

    // Not folded: no getter
    <fold text='@NoArgsConstructor(private) s' expand='false'>s</fold>tatic class PublicInstance <fold text='{...}' expand='true'>{
        <fold text='const' expand='false'>public static final</fold> PublicInstance INSTANCE =<fold text=' ::' expand='true'> </fold>new<fold text='' expand='true'> </fold><fold text='' expand='true'>PublicInstance</fold><fold text='' expand='true'>()</fold>;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>private PublicInstance() {}</fold>
    }</fold>

    // Not folded: the constructor does work
    static class Clock <fold text='{...}' expand='true'>{
        private <fold text='const' expand='false'>static final</fold> Clock INSTANCE =<fold text=' ::' expand='true'> </fold>new<fold text='' expand='true'> </fold><fold text='' expand='true'>Clock</fold><fold text='' expand='true'>()</fold>;

        private final long start;

        private Clock()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>start = System.nanoTime()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        static Clock get()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>INSTANCE<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>

    // Not folded: the constructor is public
    <fold text='@NoArgsConstructor s' expand='false'>s</fold>tatic class Config <fold text='{...}' expand='true'>{
        private <fold text='const' expand='false'>static final</fold> Config DEFAULT =<fold text=' ::' expand='true'> </fold>new<fold text='' expand='true'> </fold><fold text='' expand='true'>Config</fold><fold text='' expand='true'>()</fold>;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>public Config() {}</fold>

        static Config defaults()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>DEFAULT<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>

    // Not folded: utility class without an instance
    static <fold text='' expand='false'>final</fold> class Prices <fold text='{...}' expand='true'>{
        private Prices()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>throw new UnsupportedOperationException()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        static int twice(int value)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>value * 2<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>
}
