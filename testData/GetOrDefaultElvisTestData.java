package data;

import <fold text='...' expand='false'>java.util.Map;
import java.util.function.Function;</fold>

public class GetOrDefaultElvisTestData {

    private Map<String, Integer> stock;
    private Map<String, Map<String, Integer>> stockByShop;

    public int count(Map<String, Integer> counts, String word) <fold text='{...}' expand='true'>{
        int count = counts<fold text='[' expand='false'>.getOrDefault(</fold>word<fold text='] ?:' expand='false'>,</fold> 0<fold text='' expand='false'>)</fold>;
        return count;
    }</fold>

    public String label(Map<Integer, String> labels, int code)<fold text=' { ' expand='false'> {
        </fold>return labels<fold text='[' expand='false'>.getOrDefault(</fold>code<fold text='] ?:' expand='false'>,</fold> "unknown"<fold text='' expand='false'>)</fold>;<fold text=' }' expand='false'>
    }</fold>

    public void restock(String item, int amount) <fold text='{...}' expand='true'>{
        int current;
        current = stock<fold text='[' expand='false'>.getOrDefault(</fold>item<fold text='] ?:' expand='false'>,</fold> 0<fold text='' expand='false'>)</fold>;
        print(stock<fold text='[' expand='false'>.getOrDefault(</fold>item<fold text='] ?:' expand='false'>,</fold> 0<fold text='' expand='false'>)</fold>);
        Function<String, Integer> lookup = name -> stock<fold text='[' expand='false'>.getOrDefault(</fold>name<fold text='] ?:' expand='false'>,</fold> -1<fold text='' expand='false'>)</fold>;
        stock.put(item, current + amount + lookup.apply(item));
    }</fold>

    public int nested(String shop, String item) <fold text='{...}' expand='true'>{
        int inShop = stockByShop.getOrDefault(shop, Map.of())<fold text='[' expand='false'>.getOrDefault(</fold>item<fold text='] ?:' expand='false'>,</fold> 0<fold text='' expand='false'>)</fold>;
        return inShop;
    }</fold>

    public void notFolded(String item, Map<String, Boolean> flags) <fold text='{...}' expand='true'>{
        int incremented = stock.getOrDefault(item, 0) + 1;
        int parenthesized = (stock.getOrDefault(item, 0)) + 1;
        String trimmed = labelsOf().getOrDefault(item, "").trim();
        boolean active = !flags.getOrDefault(item, false);
        String chosen = flags.getOrDefault(item, false) ? "yes" : "no";
        Integer single = stock.get(item);
        print(incremented + parenthesized + trimmed + active + chosen + single);
    }</fold>

    private Map<String, String> labelsOf()<fold text=' { ' expand='false'> {
        </fold>return Map.of();<fold text=' }' expand='false'>
    }</fold>

    private void print(Object value)<fold text=' { ' expand='false'> {
        </fold>System.out.println(value);<fold text=' }' expand='false'>
    }</fold>
}
