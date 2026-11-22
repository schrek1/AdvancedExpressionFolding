package data;

import <fold text='...' expand='false'>java.util.List;
import java.util.Set;</fold>

public class EqualsCompareTestData implements Comparabl<fold text='e(nothing overridden)' expand='true'>e</fold><EqualsCompareTestData> {
    public static void main(String[] args) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>EqualsCompareTestData</fold> a = new EqualsCompareTestData();
        <fold text='val' expand='false'>EqualsCompareTestData</fold> b = new EqualsCompareTestData();
        <fold text='' expand='false'>System.out.</fold>println(a<fold text=' ≡ ' expand='false'>.equals(</fold>b<fold text='' expand='false'>)</fold>);
        <fold text='' expand='false'>System.out.</fold>println(<fold text='' expand='false'>!</fold>a<fold text=' ≢ ' expand='false'>.equals(</fold>b<fold text='' expand='false'>)</fold>);
        <fold text='' expand='false'>System.out.</fold>println(a<fold text=' ≡ ' expand='false'>.compareTo(</fold>b<fold text='' expand='false'>) == 0</fold>);
        <fold text='' expand='false'>System.out.</fold>println(a<fold text=' ≢ ' expand='false'>.compareTo(</fold>b<fold text='' expand='false'>) != 0</fold>);

        <fold text='' expand='false'>System.out.</fold>println(a<fold text=' > ' expand='false'>.compareTo(</fold>b<fold text='' expand='false'>) > 0</fold>);
        <fold text='' expand='false'>System.out.</fold>println(a<fold text=' > ' expand='false'>.compareTo(</fold>b<fold text='' expand='false'>) == 1</fold>);
        <fold text='' expand='false'>System.out.</fold>println(a<fold text=' ≥ ' expand='false'>.compareTo(</fold>b<fold text='' expand='false'>) > -1</fold>);
        <fold text='' expand='false'>System.out.</fold>println(a<fold text=' ≥ ' expand='false'>.compareTo(</fold>b<fold text='' expand='false'>) >= 0</fold>); // Should be a >= b

        <fold text='' expand='false'>System.out.</fold>println(a<fold text=' < ' expand='false'>.compareTo(</fold>b<fold text='' expand='false'>) < 0</fold>);
        <fold text='' expand='false'>System.out.</fold>println(a<fold text=' < ' expand='false'>.compareTo(</fold>b<fold text='' expand='false'>) == -1</fold>);
        <fold text='' expand='false'>System.out.</fold>println(a<fold text=' ≤ ' expand='false'>.compareTo(</fold>b<fold text='' expand='false'>) < 1</fold>);
        <fold text='' expand='false'>System.out.</fold>println(a<fold text=' ≤ ' expand='false'>.compareTo(</fold>b<fold text='' expand='false'>) <= 0</fold>); // Should be a <= b
    }</fold>

    <fold text='' expand='true'>@Override</fold><fold text='' expand='true'>
    </fold><fold text='' expand='true'>public</fold><fold text='' expand='true'> </fold><fold text='' expand='true'>int</fold><fold text='' expand='true'> </fold>compareTo(EqualsCompareTestData o)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>0<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    static void overriddenEquals(Order current, Order patched, Point p, Point q, Money m, Money n, Shape s, Shape t) <fold text='{...}' expand='true'>{
        <fold text='' expand='false'>System.out.</fold>println(current.<fold text='tags' expand='false'>getTags()</fold><fold text=' ≡ ' expand='false'>.equals(</fold>patched.<fold text='tags' expand='false'>getTags()</fold><fold text='' expand='false'>)</fold>);
        <fold text='' expand='false'>System.out.</fold>println(<fold text='' expand='false'>!</fold>current.<fold text='labels' expand='false'>getLabels()</fold><fold text=' ≢ ' expand='false'>.equals(</fold>patched.<fold text='labels' expand='false'>getLabels()</fold><fold text='' expand='false'>)</fold>);
        <fold text='' expand='false'>System.out.</fold>println(p<fold text=' ≡ ' expand='false'>.equals(</fold>q<fold text='' expand='false'>)</fold>);
        <fold text='' expand='false'>System.out.</fold>println(<fold text='' expand='false'>!</fold>p<fold text=' ≢ ' expand='false'>.equals(</fold>q<fold text='' expand='false'>)</fold>);
        <fold text='' expand='false'>System.out.</fold>println(m<fold text=' ≡ ' expand='false'>.equals(</fold>n<fold text='' expand='false'>)</fold>);
        <fold text='' expand='false'>System.out.</fold>println(<fold text='' expand='false'>!</fold>m<fold text=' ≢ ' expand='false'>.equals(</fold>n<fold text='' expand='false'>)</fold>);
        <fold text='' expand='false'>System.out.</fold>println(s.equals(t)); // equals(Shape) does not override Object.equals: stays unfolded
    }</fold>

    record Point(int x, int y) <fold text='{...}' expand='true'>{
    }</fold>

    <fold text='@Getter s' expand='false'>s</fold>tatic class Order <fold text='{...}' expand='true'>{
        private List<String> tags;
        private Set<String> labels;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>public List<String> getTags()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>tags<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public Set<String> getLabels()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>labels<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>
    }</fold>

    static class Money <fold text='{...}' expand='true'>{
        <fold text='' expand='true'>@Override</fold><fold text='' expand='true'>
        </fold>public boolean equals(Object other)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>other instanceof Money<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>

    static class Shape <fold text='{...}' expand='true'>{
        public boolean equals(Shape other)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>other != null<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>
}
