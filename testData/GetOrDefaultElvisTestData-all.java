package data;

import <fold text='...' expand='false'>java.util.Map;
import java.util.function.Function;</fold>

public class GetOrDefaultElvisTestData {

    private Map<String, Integer> stock;
    private Map<String, Map<String, Integer>> stockByShop;

    public int count(Map<String, Integer> counts, String word) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>int</fold> count = counts<fold text='[' expand='false'>.getOrDefault(</fold>word<fold text='] ?:' expand='false'>,</fold> 0<fold text='' expand='false'>)</fold>;
        return count;
    }</fold>

    public String label(Map<Integer, String> labels, int code)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>labels<fold text='[' expand='false'>.getOrDefault(</fold>code<fold text='] ?:' expand='false'>,</fold> "unknown"<fold text='' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public void restock(String item, int amount) <fold text='{...}' expand='true'>{
        int current;
        current = stock<fold text='[' expand='false'>.getOrDefault(</fold>item<fold text='] ?:' expand='false'>,</fold> 0<fold text='' expand='false'>)</fold>;
        print(stock<fold text='[' expand='false'>.getOrDefault(</fold>item<fold text='] ?:' expand='false'>,</fold> 0<fold text='' expand='false'>)</fold>);
        <fold text='val' expand='false'>Function<String, Integer></fold> lookup = name -> stock<fold text='[' expand='false'>.getOrDefault(</fold>name<fold text='] ?:' expand='false'>,</fold> -1<fold text='' expand='false'>)</fold>;
        stock<fold text='[' expand='false'>.put(</fold>item<fold text='] = ' expand='false'>, </fold>current + amount + lookup.apply(item)<fold text='' expand='false'>)</fold>;
    }</fold>

    public int nested(String shop, String item) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>int</fold> inShop = stockByShop.getOrDefault(shop, <fold text='[:]' expand='false'>Map.of()</fold>)<fold text='[' expand='false'>.getOrDefault(</fold>item<fold text='] ?:' expand='false'>,</fold> 0<fold text='' expand='false'>)</fold>;
        return inShop;
    }</fold>

    public void notFolded(String item, Map<String, Boolean> flags) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>int</fold> incremented = stock.getOrDefault(item, 0) + 1;
        <fold text='val' expand='false'>int</fold> parenthesized = <fold text='' expand='false'>(</fold>stock.getOrDefault(item, 0)<fold text=' + ' expand='false'>) + </fold>1;
        <fold text='val' expand='false'>String</fold> trimmed = labelsOf().getOrDefault(item, "").trim();
        <fold text='val' expand='false'>boolean</fold> active = !flags.getOrDefault(item, false);
        <fold text='val' expand='false'>String</fold> chosen = flags.getOrDefault(item, false) ? "yes" : "no";
        <fold text='val' expand='false'>Integer</fold> single = stock<fold text='[' expand='false'>.get(</fold>item<fold text=']' expand='false'>)</fold>;
        print(incremented + parenthesized + trimmed + active + chosen + single);
    }</fold>

    private Map<String, String> labelsOf()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='[:]' expand='false'>Map.of()</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    private void print(Object value)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='false'>System.out.</fold>println(value)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>
}
