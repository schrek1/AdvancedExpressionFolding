package data;

import java.util.Map;
import java.util.function.Function;

public class GetOrDefaultElvisTestData {

    private Map<String, Integer> stock;
    private Map<String, Map<String, Integer>> stockByShop;

    public int count(Map<String, Integer> counts, String word) {
        int count = counts.getOrDefault(word, 0);
        return count;
    }

    public String label(Map<Integer, String> labels, int code) {
        return labels.getOrDefault(code, "unknown");
    }

    public void restock(String item, int amount) {
        int current;
        current = stock.getOrDefault(item, 0);
        print(stock.getOrDefault(item, 0));
        Function<String, Integer> lookup = name -> stock.getOrDefault(name, -1);
        stock.put(item, current + amount + lookup.apply(item));
    }

    public int nested(String shop, String item) {
        int inShop = stockByShop.getOrDefault(shop, Map.of()).getOrDefault(item, 0);
        return inShop;
    }

    public void notFolded(String item, Map<String, Boolean> flags) {
        int incremented = stock.getOrDefault(item, 0) + 1;
        int parenthesized = (stock.getOrDefault(item, 0)) + 1;
        String trimmed = labelsOf().getOrDefault(item, "").trim();
        boolean active = !flags.getOrDefault(item, false);
        String chosen = flags.getOrDefault(item, false) ? "yes" : "no";
        Integer single = stock.get(item);
        print(incremented + parenthesized + trimmed + active + chosen + single);
    }

    private Map<String, String> labelsOf() {
        return Map.of();
    }

    private void print(Object value) {
        System.out.println(value);
    }
}
