package data;

import java.util.*;

public class MapAndEmptyCollectionLiteralsTestData {
    public static void main(String[] args) {
        Map<String, Integer> limits = Map.of("cpu", 4, "ram", 16);
        System.out.println(limits);
        Map<String, Integer> single = Map.of("cpu", 4);
        System.out.println(single);
        Map<String, Integer> emptyMap = Map.of();
        System.out.println(emptyMap);
        Map<String, Long> counts = new HashMap<>();
        System.out.println(counts);
        Map<String, Long> emptyCounts = Collections.emptyMap();
        System.out.println(emptyCounts);

        List<String> names = List.of();
        System.out.println(names);
        List<String> tags = new ArrayList<>();
        System.out.println(tags);
        List<String> noTags = Collections.emptyList();
        System.out.println(noTags);
        Set<String> ids = Set.of();
        System.out.println(ids);
        Set<String> seen = new HashSet<>();
        System.out.println(seen);
        Set<String> noIds = Collections.emptySet();
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
