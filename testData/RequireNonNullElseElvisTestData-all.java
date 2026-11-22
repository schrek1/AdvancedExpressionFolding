package data;

import <fold text='...' expand='false'>java.util.Objects;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNullElse;
import static java.util.Objects.requireNonNullElseGet;</fold>

public class RequireNonNullElseElvisTestData {

    private String nickname;
    private Theme theme;

    public String displayName(String name) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>String</fold> shown = <fold text='' expand='false'>Objects.requireNonNullElse(</fold>name<fold text=' ?:' expand='false'>,</fold> "anonymous"<fold text='' expand='false'>)</fold>;
        return shown;
    }</fold>

    public Theme themeOf(Order order)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>Objects.requireNonNullElseGet(</fold>order.<fold text='theme' expand='false'>getTheme()</fold><fold text=' ?:' expand='false'>,</fold> Theme<fold text='(' expand='false'>::new</fold>)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public String guestName(String name, int id)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>Objects.requireNonNullElseGet(</fold>name<fold text=' ?:' expand='false'>,</fold> <fold text='' expand='false'>() -> </fold>"guest-<fold text='$' expand='false'>" + </fold>id<fold text='"' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public String labelOf(String name, int id)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>Objects.requireNonNullElse</fold><fold text='"$' expand='false'>(</fold>id<fold text='' expand='false'> + "</fold>-label"<fold text=' ?:' expand='false'>,</fold> name<fold text='' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public List<String> tagsOf(Patch patch)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>Objects.requireNonNullElse(</fold>patch.<fold text='tags' expand='false'>getTags()</fold>.<fold text='!!' expand='false'>get()</fold><fold text=' ?:' expand='false'>,</fold> List.of()<fold text='' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public void methodReferences(String name) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>String</fold> own = <fold text='' expand='false'>Objects.requireNonNullElseGet(</fold>name<fold text=' ?:' expand='false'>,</fold> <fold text='' expand='false'>this::</fold>defaultName<fold text='()' expand='false'>)</fold>;
        <fold text='val' expand='false'>Theme</fold> fallback = <fold text='' expand='false'>Objects.requireNonNullElseGet(</fold>theme<fold text=' ?:' expand='false'>,</fold> Theme<fold text='.' expand='false'>::</fold>standard<fold text='()' expand='false'>)</fold>;
        <fold text='val' expand='false'>String</fold> trimmed = <fold text='' expand='false'>Objects.requireNonNullElseGet(</fold>name<fold text=' ?:' expand='false'>,</fold> nickname<fold text='.' expand='false'>::</fold>trim<fold text='()' expand='false'>)</fold>;
        print(own + fallback + trimmed);
    }</fold>

    public void staticImports(String name) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>String</fold> shown = <fold text='' expand='false'>requireNonNullElse(</fold>name<fold text=' ?:' expand='false'>,</fold> "anonymous"<fold text='' expand='false'>)</fold>;
        <fold text='val' expand='false'>Theme</fold> current = <fold text='' expand='false'>requireNonNullElseGet(</fold>theme<fold text=' ?:' expand='false'>,</fold> Theme<fold text='(' expand='false'>::new</fold>);
        print(shown + current);
    }</fold>

    public void notFolded(String name, Supplier<String> supplier) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>String</fold> fromVariable = Objects.requireNonNullElseGet(name, supplier);
        <fold text='val' expand='false'>String</fold> fromBlock = Objects.requireNonNullElseGet(name, () -> <fold text='{...}' expand='true'>{
            return "block";
        }</fold>);
        <fold text='val' expand='false'>int</fold> length = Objects.requireNonNullElse(name, "").length();
        print(fromVariable + fromBlock + length);
    }</fold>

    private String defaultName()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>"nobody"<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    private void print(Object value)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='false'>System.out.</fold>println(value)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    static class Theme <fold text='{...}' expand='true'>{
        static Theme standard()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>new Theme()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>

    <fold text='@Getter s' expand='false'>s</fold>tatic class Order <fold text='{...}' expand='true'>{
        private Theme theme;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>Theme getTheme()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>theme<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>
    }</fold>

    <fold text='@Getter s' expand='false'>s</fold>tatic class Patch <fold text='{...}' expand='true'>{
        private Maybe<List<String>> tags;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>Maybe<List<String>> getTags()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>tags<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>
    }</fold>

    static class Maybe<T> <fold text='{...}' expand='true'>{
        private T value;

        T get()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>value<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>
}
