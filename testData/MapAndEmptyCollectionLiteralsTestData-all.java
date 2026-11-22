package data;

import java.util.*;

public class MapAndEmptyCollectionLiteralsTestData {
    public static void main(String[] args) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>Map<String, Integer></fold> limits = <fold text='[' expand='false'>Map.of(</fold>"cpu"<fold text=':' expand='false'>,</fold> 4, "ram"<fold text=':' expand='false'>,</fold> 16<fold text=']' expand='false'>)</fold>;
        <fold text='' expand='false'>System.out.</fold>println(limits);
        <fold text='val' expand='false'>Map<String, Integer></fold> single = <fold text='[' expand='false'>Map.of(</fold>"cpu"<fold text=':' expand='false'>,</fold> 4<fold text=']' expand='false'>)</fold>;
        <fold text='' expand='false'>System.out.</fold>println(single);
        <fold text='val' expand='false'>Map<String, Integer></fold> emptyMap = <fold text='[:]' expand='false'>Map.of()</fold>;
        <fold text='' expand='false'>System.out.</fold>println(emptyMap);
        <fold text='val' expand='false'>Map<String, Long></fold> counts = <fold text='[:]' expand='false'>new HashMap<>()</fold>;
        <fold text='' expand='false'>System.out.</fold>println(counts);
        <fold text='val' expand='false'>Map<String, Long></fold> emptyCounts = <fold text='[:]' expand='false'>Collections.emptyMap()</fold>;
        <fold text='' expand='false'>System.out.</fold>println(emptyCounts);

        <fold text='val' expand='false'>List<String></fold> names = <fold text='[]' expand='false'>List.of()</fold>;
        <fold text='' expand='false'>System.out.</fold>println(names);
        <fold text='val' expand='false'>List<String></fold> tags = <fold text='[]' expand='false'>new ArrayList<>()</fold>;
        <fold text='' expand='false'>System.out.</fold>println(tags);
        <fold text='val' expand='false'>List<String></fold> noTags = <fold text='[]' expand='false'>Collections.emptyList()</fold>;
        <fold text='' expand='false'>System.out.</fold>println(noTags);
        <fold text='val' expand='false'>Set<String></fold> ids = <fold text='[]' expand='false'>Set.of()</fold>;
        <fold text='' expand='false'>System.out.</fold>println(ids);
        <fold text='val' expand='false'>Set<String></fold> seen = <fold text='[]' expand='false'>new HashSet<>()</fold>;
        <fold text='' expand='false'>System.out.</fold>println(seen);
        <fold text='val' expand='false'>Set<String></fold> noIds = <fold text='[]' expand='false'>Collections.emptySet()</fold>;
        <fold text='' expand='false'>System.out.</fold>println(noIds);

        // not folded: arguments, double-brace subclasses and other map factories
        <fold text='val' expand='false'>Map<String, Long></fold> copy = <fold text='' expand='false'>new </fold>HashMap<>(counts);
        <fold text='' expand='false'>System.out.</fold>println(copy);
        <fold text='val' expand='false'>Map<String, Integer></fold> filled = new HashMap<>() <fold text='{...}' expand='true'>{
            <fold text='{...}' expand='true'>{
                put("cpu", 4);
            }</fold>
        }</fold>;
        <fold text='' expand='false'>System.out.</fold>println(filled);
        <fold text='val' expand='false'>Map<String, Integer></fold> entries = Map.ofEntries(Map.entry("cpu", 4));
        <fold text='' expand='false'>System.out.</fold>println(entries);
        <fold text='val' expand='false'>Map<String, Long></fold> sorted = <fold text='' expand='false'>new </fold>TreeMap<>();
        <fold text='' expand='false'>System.out.</fold>println(sorted);
    }</fold>
}
