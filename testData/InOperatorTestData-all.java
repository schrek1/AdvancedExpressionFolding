package data;

import <fold text='...' expand='false'>java.util.List;
import java.util.Map;
import java.util.Set;</fold>

@SuppressWarnings("ALL")
public class InOperatorTestData {
    private Set<String> admins;
    private int age;

    public void folded(String user, Set<String> banned, Map<Long, String> cache, Long id, List<String> tags, int age, int i, int size) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold><fold text='' expand='false'>admins.contains(</fold>user<fold text=' in admins' expand='false'>)</fold> && <fold text='' expand='false'>!banned.contains(</fold>user<fold text=' !in banned' expand='false'>)</fold>
                && age <fold text='in' expand='false'>>=</fold> 18<fold text='..' expand='false'> && age <= </fold>65 && i <fold text='in' expand='false'>>=</fold> 0<fold text='..<' expand='false'> && i < </fold>size<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println(user);
        }</fold>
        if <fold text='' expand='false'>(</fold><fold text='' expand='false'>cache.containsKey(</fold>id<fold text=' in cache' expand='false'>)</fold><fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println(cache<fold text='[' expand='false'>.get(</fold>id<fold text=']' expand='false'>)</fold>);
        }</fold>
        <fold text='val' expand='false'>boolean</fold> tagged = <fold text='' expand='false'>tags.contains(</fold>"new"<fold text=' in tags' expand='false'>)</fold>;
        <fold text='val' expand='false'>boolean</fold> admin = <fold text='' expand='false'>this.admins.contains(</fold>user<fold text=' in this.admins' expand='false'>)</fold>;
        <fold text='val' expand='false'>boolean</fold> adult = age <fold text='in' expand='false'>>=</fold> 18<fold text='..' expand='false'> && age <= </fold>65;
        <fold text='val' expand='false'>boolean</fold> inside = i <fold text='in' expand='false'>>=</fold> 0<fold text='..<' expand='false'> && i < </fold>size;
        <fold text='val' expand='false'>boolean</fold> last = i <fold text='in' expand='false'>>=</fold> 0<fold text='..' expand='false'> && i <= </fold>size - 1;
        <fold text='val' expand='false'>boolean</fold> tight = i<fold text=' in ' expand='false'>>=</fold>0<fold text='..<' expand='false'>&&i<</fold>size;
    }</fold>

    public void notFolded(String user, String name, List<String> tags, Map<Long, String> cache, int age, int height, int i) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>boolean</fold> mixed = age >= 18 && height <= 65;
        <fold text='val' expand='false'>boolean</fold> openStart = age > 18 && age <= 65;
        <fold text='val' expand='false'>boolean</fold> openBoth = 18 < age && age < 65;
        <fold text='val' expand='false'>boolean</fold> field = this.age >= 18 && this.age <= 65;
        <fold text='val' expand='false'>boolean</fold> callBound = i >= 0 && i < tags.size();
        <fold text='val' expand='false'>boolean</fold> callReceiver = loadAdmins().contains(user);
        <fold text='val' expand='false'>boolean</fold> callArgument = tags.contains(nextTag());
        <fold text='val' expand='false'>boolean</fold> text = name.contains("x");
        <fold text='val' expand='false'>boolean</fold> value = cache.containsValue(name);
    }</fold>

    private Set<String> loadAdmins()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>admins<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    private String nextTag()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>"new"<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>
}
