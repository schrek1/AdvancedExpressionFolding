package data;

import java.util.*;

public class MapAndEmptyCollectionLiteralsTestData {
    public static void main(String[] args) {
        Map<String, Integer> limits = ["cpu": 4, "ram": 16];
        System.out.println(limits);
        Map<String, Integer> single = ["cpu": 4];
        System.out.println(single);
        Map<String, Integer> emptyMap = [:];
        System.out.println(emptyMap);
        Map<String, Long> counts = [:];
        System.out.println(counts);
        Map<String, Long> emptyCounts = [:];
        System.out.println(emptyCounts);

        List<String> names = [];
        System.out.println(names);
        List<String> tags = [];
        System.out.println(tags);
        List<String> noTags = [];
        System.out.println(noTags);
        Set<String> ids = [];
        System.out.println(ids);
        Set<String> seen = [];
        System.out.println(seen);
        Set<String> noIds = [];
        System.out.println(noIds);

        // not folded: arguments, double-brace subclasses and other map factories
        Map<String, Long> copy = new HashMap<>(counts);
        System.out.println(copy);
        Map<String, Integer> filled = new HashMap<>() {
            {
                put("cpu", 4);
            }
        };
        System.out.println(filled);
        Map<String, Integer> entries = Map.ofEntries(Map.entry("cpu", 4));
        System.out.println(entries);
        Map<String, Long> sorted = new TreeMap<>();
        System.out.println(sorted);
    }
}
