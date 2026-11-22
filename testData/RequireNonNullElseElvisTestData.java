package data;

import <fold text='...' expand='false'>java.util.Objects;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNullElse;
import static java.util.Objects.requireNonNullElseGet;</fold>

public class RequireNonNullElseElvisTestData {

    private String nickname;
    private Theme theme;

    public String displayName(String name) <fold text='{...}' expand='true'>{
        String shown = <fold text='' expand='false'>Objects.requireNonNullElse(</fold>name<fold text=' ?:' expand='false'>,</fold> "anonymous"<fold text='' expand='false'>)</fold>;
        return shown;
    }</fold>

    public Theme themeOf(Order order)<fold text=' { ' expand='false'> {
        </fold>return <fold text='' expand='false'>Objects.requireNonNullElseGet(</fold>order.getTheme()<fold text=' ?:' expand='false'>,</fold> Theme<fold text='(' expand='false'>::new</fold>);<fold text=' }' expand='false'>
    }</fold>

    public String guestName(String name, int id)<fold text=' { ' expand='false'> {
        </fold>return <fold text='' expand='false'>Objects.requireNonNullElseGet(</fold>name<fold text=' ?:' expand='false'>,</fold> <fold text='' expand='false'>() -> </fold>"guest-" + id<fold text='' expand='false'>)</fold>;<fold text=' }' expand='false'>
    }</fold>

    public List<String> tagsOf(Patch patch)<fold text=' { ' expand='false'> {
        </fold>return <fold text='' expand='false'>Objects.requireNonNullElse(</fold>patch.getTags().get()<fold text=' ?:' expand='false'>,</fold> List.of()<fold text='' expand='false'>)</fold>;<fold text=' }' expand='false'>
    }</fold>

    public void methodReferences(String name) <fold text='{...}' expand='true'>{
        String own = <fold text='' expand='false'>Objects.requireNonNullElseGet(</fold>name<fold text=' ?:' expand='false'>,</fold> <fold text='' expand='false'>this::</fold>defaultName<fold text='()' expand='false'>)</fold>;
        Theme fallback = <fold text='' expand='false'>Objects.requireNonNullElseGet(</fold>theme<fold text=' ?:' expand='false'>,</fold> Theme<fold text='.' expand='false'>::</fold>standard<fold text='()' expand='false'>)</fold>;
        String trimmed = <fold text='' expand='false'>Objects.requireNonNullElseGet(</fold>name<fold text=' ?:' expand='false'>,</fold> nickname<fold text='.' expand='false'>::</fold>trim<fold text='()' expand='false'>)</fold>;
        print(own + fallback + trimmed);
    }</fold>

    public void staticImports(String name) <fold text='{...}' expand='true'>{
        String shown = <fold text='' expand='false'>requireNonNullElse(</fold>name<fold text=' ?:' expand='false'>,</fold> "anonymous"<fold text='' expand='false'>)</fold>;
        Theme current = <fold text='' expand='false'>requireNonNullElseGet(</fold>theme<fold text=' ?:' expand='false'>,</fold> Theme<fold text='(' expand='false'>::new</fold>);
        print(shown + current);
    }</fold>

    public void notFolded(String name, Supplier<String> supplier) <fold text='{...}' expand='true'>{
        String fromVariable = Objects.requireNonNullElseGet(name, supplier);
        String fromBlock = Objects.requireNonNullElseGet(name, () -> <fold text='{...}' expand='true'>{
            return "block";
        }</fold>);
        int length = Objects.requireNonNullElse(name, "").length();
        print(fromVariable + fromBlock + length);
    }</fold>

    private String defaultName()<fold text=' { ' expand='false'> {
        </fold>return "nobody";<fold text=' }' expand='false'>
    }</fold>

    private void print(Object value)<fold text=' { ' expand='false'> {
        </fold>System.out.println(value);<fold text=' }' expand='false'>
    }</fold>

    static class Theme <fold text='{...}' expand='true'>{
        static Theme standard()<fold text=' { ' expand='false'> {
            </fold>return new Theme();<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    static class Order <fold text='{...}' expand='true'>{
        private Theme theme;

        Theme getTheme()<fold text=' { ' expand='false'> {
            </fold>return theme;<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    static class Patch <fold text='{...}' expand='true'>{
        private Maybe<List<String>> tags;

        Maybe<List<String>> getTags()<fold text=' { ' expand='false'> {
            </fold>return tags;<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    static class Maybe<T> <fold text='{...}' expand='true'>{
        private T value;

        T get()<fold text=' { ' expand='false'> {
            </fold>return value;<fold text=' }' expand='false'>
        }</fold>
    }</fold>
}
