package data;

import <fold text='...' expand='false'>java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;</fold>

public class GuardIfOneLinerTestData {

    String nullGuard(String name) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>name == null<fold text='' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return null;<fold text='' expand='false'>
        }</fold></fold>
        return name.trim();
    }</fold>

    boolean literalGuards(String name, int size) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>size == 0<fold text='' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return false;<fold text='' expand='false'>
        }</fold></fold>
        if <fold text='' expand='false'>(</fold>size > 10<fold text='' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return true;<fold text='' expand='false'>
        }</fold></fold>
        return name.<fold text='empty' expand='false'>isEmpty()</fold>;
    }</fold>

    int numberGuard(List<String> items) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>items.<fold text='empty' expand='false'>isEmpty()</fold><fold text='' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return -1;<fold text='' expand='false'>
        }</fold></fold>
        return items.size();
    }</fold>

    String stringGuard(String name) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>name.<fold text='blank' expand='false'>isBlank()</fold><fold text='' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return "unknown";<fold text='' expand='false'>
        }</fold></fold>
        return name;
    }</fold>

    String variableGuard(String name, String fallback) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>name == null<fold text='' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return fallback;<fold text='' expand='false'>
        }</fold></fold>
        return name;
    }</fold>

    List<String> emptyListGuard(List<String> items) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>items == null<fold text='' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return <fold text='[]' expand='false'>List.of()</fold>;<fold text='' expand='false'>
        }</fold></fold>
        return items;
    }</fold>

    Map<String, Integer> emptyMapGuard(Map<String, Integer> counts) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>counts == null<fold text='' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return <fold text='[:]' expand='false'>Collections.emptyMap()</fold>;<fold text='' expand='false'>
        }</fold></fold>
        return counts;
    }</fold>

    Optional<String> optionalGuard(String name) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>name == null<fold text='' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return Optional.empty();<fold text='' expand='false'>
        }</fold></fold>
        return <fold text='' expand='false'>Optional.of(</fold>name<fold text='!!' expand='false'>)</fold>;
    }</fold>

    void voidGuard(String name) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>name == null<fold text='' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return;<fold text='' expand='false'>
        }</fold></fold>
        <fold text='' expand='false'>System.out.</fold>println(name);
    }</fold>

    String braceOnSameLine(String name) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>name == null<fold text='' expand='false'>)</fold> <fold text='' expand='false'>{</fold>return null;<fold text='' expand='false'>}</fold>
        return name;
    }</fold>

    List<String> callIsNotSimple(List<String> items) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>items.<fold text='empty' expand='false'>isEmpty()</fold><fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return defaultItems("shop", 3);
        }</fold>
        return items;
    }</fold>

    List<String> moreThanOneStatement(List<String> items) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>items.<fold text='empty' expand='false'>isEmpty()</fold><fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println("empty");
            return <fold text='[]' expand='false'>List.of()</fold>;
        }</fold>
        return items;
    }</fold>

    String withElse(String name) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold>if <fold text='' expand='false'>(</fold>name == null<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return null;
        }</fold> else <fold text='{...}' expand='true'>{
            return name.trim();
        }</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    String elseIfChain(int code) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>code == 0<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return "zero";
        }</fold> else if <fold text='' expand='false'>(</fold>code == 1<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return "one";
        }</fold>
        return "many";
    }</fold>

    String withComment(String name) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>name == null<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            // nothing to trim
            return null;
        }</fold>
        return name.trim();
    }</fold>

    String qualifiedValue(String name) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>name == null<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return this.fallbackName;
        }</fold>
        return name;
    }</fold>

    void throwGuard<fold text='(@NonNull ' expand='true'>(</fold>String name) <fold text='{...}' expand='true'>{<fold text='' expand='true'>
        </fold><fold text='' expand='true'>if (name == null) <fold text='{...}' expand='true'>{
            throw new IllegalArgumentException("name");
        }</fold></fold>
        <fold text='' expand='false'>System.out.</fold>println(name);
    }</fold>

    String quickReturnShape(String key) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>String</fold> value = lookup(key)<fold text=' ?: return null' expand='true'>;
        if (value == null) <fold text='{...}' expand='true'>{
            return null;
        }</fold></fold>
        return value.trim();
    }</fold>

    private String fallbackName = "shop";

    private List<String> defaultItems(String prefix, int count)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>List.of(prefix + count)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    private String lookup(String key)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>key<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>
}
