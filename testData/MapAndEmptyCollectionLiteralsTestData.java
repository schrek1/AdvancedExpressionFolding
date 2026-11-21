package data;

import java.util.*;

public class MapAndEmptyCollectionLiteralsTestData {
    public static void main(String[] args) <fold text='{...}' expand='true'>{
        Map<String, Integer> limits = <fold text='[' expand='false'>Map.of(</fold>"cpu"<fold text=':' expand='false'>,</fold> 4, "ram"<fold text=':' expand='false'>,</fold> 16<fold text=']' expand='false'>)</fold>;
        System.out.println(limits);
        Map<String, Integer> single = <fold text='[' expand='false'>Map.of(</fold>"cpu"<fold text=':' expand='false'>,</fold> 4<fold text=']' expand='false'>)</fold>;
        System.out.println(single);
        Map<String, Integer> emptyMap = <fold text='[:]' expand='false'>Map.of()</fold>;
        System.out.println(emptyMap);
        Map<String, Long> counts = <fold text='[:]' expand='false'>new HashMap<>()</fold>;
        System.out.println(counts);
        Map<String, Long> emptyCounts = <fold text='[:]' expand='false'>Collections.emptyMap()</fold>;
        System.out.println(emptyCounts);

        List<String> names = <fold text='[]' expand='false'>List.of()</fold>;
        System.out.println(names);
        List<String> tags = <fold text='[]' expand='false'>new ArrayList<>()</fold>;
        System.out.println(tags);
        List<String> noTags = <fold text='[]' expand='false'>Collections.emptyList()</fold>;
        System.out.println(noTags);
        Set<String> ids = <fold text='[]' expand='false'>Set.of()</fold>;
        System.out.println(ids);
        Set<String> seen = <fold text='[]' expand='false'>new HashSet<>()</fold>;
        System.out.println(seen);
        Set<String> noIds = <fold text='[]' expand='false'>Collections.emptySet()</fold>;
        System.out.println(noIds);

        // not folded: arguments, double-brace subclasses and other map factories
        Map<String, Long> copy = new HashMap<>(counts);
        System.out.println(copy);
        Map<String, Integer> filled = new HashMap<>() <fold text='{...}' expand='true'>{
            <fold text='{...}' expand='true'>{
                put("cpu", 4);
            }</fold>
        }</fold>;
        System.out.println(filled);
        Map<String, Integer> entries = Map.ofEntries(Map.entry("cpu", 4));
        System.out.println(entries);
        Map<String, Long> sorted = new TreeMap<>();
        System.out.println(sorted);
    }</fold>
}
