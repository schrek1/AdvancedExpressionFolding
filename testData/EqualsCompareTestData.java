package data;

import <fold text='...' expand='false'>java.util.List;
import java.util.Set;</fold>

public class EqualsCompareTestData implements Comparable<EqualsCompareTestData> {
    public static void main(String[] args) <fold text='{...}' expand='true'>{
        EqualsCompareTestData a = new EqualsCompareTestData();
        EqualsCompareTestData b = new EqualsCompareTestData();
        System.out.println(a<fold text=' ≡ ' expand='false'>.equals(</fold>b<fold text='' expand='false'>)</fold>);
        System.out.println(<fold text='' expand='false'>!</fold>a<fold text=' ≢ ' expand='false'>.equals(</fold>b<fold text='' expand='false'>)</fold>);
        System.out.println(a<fold text=' ≡ ' expand='false'>.compareTo(</fold>b<fold text='' expand='false'>) == 0</fold>);
        System.out.println(a<fold text=' ≢ ' expand='false'>.compareTo(</fold>b<fold text='' expand='false'>) != 0</fold>);

        System.out.println(a<fold text=' > ' expand='false'>.compareTo(</fold>b<fold text='' expand='false'>) > 0</fold>);
        System.out.println(a<fold text=' > ' expand='false'>.compareTo(</fold>b<fold text='' expand='false'>) == 1</fold>);
        System.out.println(a<fold text=' ≥ ' expand='false'>.compareTo(</fold>b<fold text='' expand='false'>) > -1</fold>);
        System.out.println(a<fold text=' ≥ ' expand='false'>.compareTo(</fold>b<fold text='' expand='false'>) >= 0</fold>); // Should be a >= b

        System.out.println(a<fold text=' < ' expand='false'>.compareTo(</fold>b<fold text='' expand='false'>) < 0</fold>);
        System.out.println(a<fold text=' < ' expand='false'>.compareTo(</fold>b<fold text='' expand='false'>) == -1</fold>);
        System.out.println(a<fold text=' ≤ ' expand='false'>.compareTo(</fold>b<fold text='' expand='false'>) < 1</fold>);
        System.out.println(a<fold text=' ≤ ' expand='false'>.compareTo(</fold>b<fold text='' expand='false'>) <= 0</fold>); // Should be a <= b
    }</fold>

    @Override
    public int compareTo(EqualsCompareTestData o)<fold text=' { ' expand='false'> {
        </fold>return 0;<fold text=' }' expand='false'>
    }</fold>

    static void overriddenEquals(Order current, Order patched, Point p, Point q, Money m, Money n, Shape s, Shape t) <fold text='{...}' expand='true'>{
        System.out.println(current.getTags()<fold text=' ≡ ' expand='false'>.equals(</fold>patched.getTags()<fold text='' expand='false'>)</fold>);
        System.out.println(<fold text='' expand='false'>!</fold>current.getLabels()<fold text=' ≢ ' expand='false'>.equals(</fold>patched.getLabels()<fold text='' expand='false'>)</fold>);
        System.out.println(p<fold text=' ≡ ' expand='false'>.equals(</fold>q<fold text='' expand='false'>)</fold>);
        System.out.println(<fold text='' expand='false'>!</fold>p<fold text=' ≢ ' expand='false'>.equals(</fold>q<fold text='' expand='false'>)</fold>);
        System.out.println(m<fold text=' ≡ ' expand='false'>.equals(</fold>n<fold text='' expand='false'>)</fold>);
        System.out.println(<fold text='' expand='false'>!</fold>m<fold text=' ≢ ' expand='false'>.equals(</fold>n<fold text='' expand='false'>)</fold>);
        System.out.println(s.equals(t)); // equals(Shape) does not override Object.equals: stays unfolded
    }</fold>

    record Point(int x, int y) <fold text='{...}' expand='true'>{
    }</fold>

    static class Order <fold text='{...}' expand='true'>{
        private List<String> tags;
        private Set<String> labels;

        public List<String> getTags()<fold text=' { ' expand='false'> {
            </fold>return tags;<fold text=' }' expand='false'>
        }</fold>

        public Set<String> getLabels()<fold text=' { ' expand='false'> {
            </fold>return labels;<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    static class Money <fold text='{...}' expand='true'>{
        @Override
        public boolean equals(Object other)<fold text=' { ' expand='false'> {
            </fold>return other instanceof Money;<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    static class Shape <fold text='{...}' expand='true'>{
        public boolean equals(Shape other)<fold text=' { ' expand='false'> {
            </fold>return other != null;<fold text=' }' expand='false'>
        }</fold>
    }</fold>
}
