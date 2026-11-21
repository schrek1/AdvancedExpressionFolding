package data;

import <fold text='...' expand='false'>java.util.List;
import java.util.Map;
import java.util.Set;</fold>

@SuppressWarnings("ALL")
public class InOperatorTestData {
    private Set<String> admins;
    private int age;

    public void folded(String user, Set<String> banned, Map<Long, String> cache, Long id, List<String> tags, int age, int i, int size) <fold text='{...}' expand='true'>{
        if (<fold text='' expand='false'>admins.contains(</fold>user<fold text=' in admins' expand='false'>)</fold> && <fold text='' expand='false'>!banned.contains(</fold>user<fold text=' !in banned' expand='false'>)</fold>
                && age <fold text='in' expand='false'>>=</fold> 18<fold text='..' expand='false'> && age <= </fold>65 && i <fold text='in' expand='false'>>=</fold> 0<fold text='..<' expand='false'> && i < </fold>size) <fold text='{...}' expand='true'>{
            System.out.println(user);
        }</fold>
        if (<fold text='' expand='false'>cache.containsKey(</fold>id<fold text=' in cache' expand='false'>)</fold>) <fold text='{...}' expand='true'>{
            System.out.println(cache.get(id));
        }</fold>
        boolean tagged = <fold text='' expand='false'>tags.contains(</fold>"new"<fold text=' in tags' expand='false'>)</fold>;
        boolean admin = <fold text='' expand='false'>this.admins.contains(</fold>user<fold text=' in this.admins' expand='false'>)</fold>;
        boolean adult = age <fold text='in' expand='false'>>=</fold> 18<fold text='..' expand='false'> && age <= </fold>65;
        boolean inside = i <fold text='in' expand='false'>>=</fold> 0<fold text='..<' expand='false'> && i < </fold>size;
        boolean last = i <fold text='in' expand='false'>>=</fold> 0<fold text='..' expand='false'> && i <= </fold>size - 1;
        boolean tight = i<fold text=' in ' expand='false'>>=</fold>0<fold text='..<' expand='false'>&&i<</fold>size;
    }</fold>

    public void notFolded(String user, String name, List<String> tags, Map<Long, String> cache, int age, int height, int i) <fold text='{...}' expand='true'>{
        boolean mixed = age >= 18 && height <= 65;
        boolean openStart = age > 18 && age <= 65;
        boolean openBoth = 18 < age && age < 65;
        boolean field = this.age >= 18 && this.age <= 65;
        boolean callBound = i >= 0 && i < tags.size();
        boolean callReceiver = loadAdmins().contains(user);
        boolean callArgument = tags.contains(nextTag());
        boolean text = name.contains("x");
        boolean value = cache.containsValue(name);
    }</fold>

    private Set<String> loadAdmins()<fold text=' { ' expand='false'> {
        </fold>return admins;<fold text=' }' expand='false'>
    }</fold>

    private String nextTag()<fold text=' { ' expand='false'> {
        </fold>return "new";<fold text=' }' expand='false'>
    }</fold>
}
