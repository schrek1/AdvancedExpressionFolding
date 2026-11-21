package data;

import <fold text='...' expand='false'>java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;</fold>

public class LombokDelegateTestData {

    public static class Cart <fold text='{...}' expand='true'>{
        <fold text='@Delegate p' expand='false'>p</fold>rivate final List<String> items = new ArrayList<>();<fold text='' expand='false'>

        </fold><fold text='' expand='false'>public int size()<fold text=' { ' expand='false'> {
            </fold>return items.size();<fold text=' }' expand='false'>
        }</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public boolean isEmpty()<fold text=' { ' expand='false'> {
            </fold>return items.isEmpty();<fold text=' }' expand='false'>
        }</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public void add(String item)<fold text=' { ' expand='false'> {
            </fold>items.add(item);<fold text=' }' expand='false'>
        }</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public boolean contains(Object item)<fold text=' { ' expand='false'> {
            </fold>return this.items.contains(item);<fold text=' }' expand='false'>
        }</fold></fold>
    }</fold>

    public static class Shop <fold text='{...}' expand='true'>{
        <fold text='@Delegate p' expand='false'>p</fold>rivate final Map<String, Integer> stock = new HashMap<>();
        <fold text='@Getter @Delegate p' expand='false'>p</fold>rivate final List<String> orders = new ArrayList<>();<fold text='' expand='false'>

        </fold><fold text='' expand='false'>public Integer put(String product, Integer quantity)<fold text=' { ' expand='false'> {
            </fold>return stock.put(product, quantity);<fold text=' }' expand='false'>
        }</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public boolean containsKey(Object product)<fold text=' { ' expand='false'> {
            </fold>return stock.containsKey(product);<fold text=' }' expand='false'>
        }</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public String get(int index)<fold text=' { ' expand='false'> {
            </fold>return orders.get(index);<fold text=' }' expand='false'>
        }</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public List<String> getOrders()<fold text=' { ' expand='false'> {
            </fold>return orders;<fold text=' }' expand='false'>
        }</fold></fold>
    }</fold>

    public static class NotForwarding <fold text='{...}' expand='true'>{
        private final List<String> items = new ArrayList<>();
        private final NotForwarding other = null;

        public int count()<fold text=' { ' expand='false'> {
            </fold>return items.size();<fold text=' }' expand='false'>
        }</fold>

        public String get(int index)<fold text=' { ' expand='false'> {
            </fold>return items.get(index + 1);<fold text=' }' expand='false'>
        }</fold>

        public void add(int index, String item)<fold text=' { ' expand='false'> {
            </fold>items.add(0, item);<fold text=' }' expand='false'>
        }</fold>

        public void clear() <fold text='{...}' expand='true'>{
            System.out.println("clearing");
            items.clear();
        }</fold>

        public boolean isEmpty() <fold text='{...}' expand='true'>{
            // kept visible on purpose
            return items.isEmpty();
        }</fold>

        public boolean contains(Object item)<fold text=' { ' expand='false'> {
            </fold>return other.items.contains(item);<fold text=' }' expand='false'>
        }</fold>

        private int indexOf(Object item)<fold text=' { ' expand='false'> {
            </fold>return items.indexOf(item);<fold text=' }' expand='false'>
        }</fold>
    }</fold>
}
