package data;

import <fold text='...' expand='false'>java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;</fold>

public class LombokDelegateTestData {

    public static class Cart <fold text='{...}' expand='true'>{
        <fold text='@Delegate p' expand='false'>p</fold>rivate final List<String> items = <fold text='[]' expand='false'>new ArrayList<>()</fold>;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>public int size()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>items.size()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public boolean isEmpty()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>items.<fold text='empty' expand='false'>isEmpty()</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public void add(String item)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>items<fold text=' += ' expand='false'>.add(</fold>item<fold text='' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public boolean contains(Object item)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>this.items.contains(</fold>item<fold text=' in this.items' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>
    }</fold>

    public static class Shop <fold text='{...}' expand='true'>{
        <fold text='@Delegate p' expand='false'>p</fold>rivate final Map<String, Integer> stock = <fold text='[:]' expand='false'>new HashMap<>()</fold>;
        <fold text='@Getter @Delegate p' expand='false'>p</fold>rivate final List<String> orders = <fold text='[]' expand='false'>new ArrayList<>()</fold>;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>public Integer put(String product, Integer quantity)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>stock<fold text='[' expand='false'>.put(</fold>product<fold text='] = ' expand='false'>, </fold>quantity<fold text='' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public boolean containsKey(Object product)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>stock.containsKey(</fold>product<fold text=' in stock' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public String get(int index)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>orders<fold text='[' expand='false'>.get(</fold>index<fold text=']' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public List<String> getOrders()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>orders<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>
    }</fold>

    public static class NotForwarding <fold text='{...}' expand='true'>{
        private final List<String> items = <fold text='[]' expand='false'>new ArrayList<>()</fold>;
        private final NotForwarding other = null;

        public int count()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>items.size()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        public String get(int index)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>items<fold text='[' expand='false'>.get(</fold>index + 1<fold text=']' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        public void add(int index, String item)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>items.add(0, item)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        public void clear() <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println("clearing");
            items.clear();
        }</fold>

        public boolean isEmpty() <fold text='{...}' expand='true'>{
            // kept visible on purpose<fold text=' ' expand='true'>
            </fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>items.<fold text='empty' expand='false'>isEmpty()</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
        </fold>}</fold>

        public boolean contains(Object item)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>other.items.contains(</fold>item<fold text=' in other.items' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        private int indexOf(Object item)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>items.indexOf(item)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>
}
