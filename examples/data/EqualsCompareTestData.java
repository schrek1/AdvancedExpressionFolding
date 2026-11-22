package data;

import java.util.List;
import java.util.Set;

public class EqualsCompareTestData implements Comparable<EqualsCompareTestData> {
    public static void main(String[] args) {
        EqualsCompareTestData a = new EqualsCompareTestData();
        EqualsCompareTestData b = new EqualsCompareTestData();
        System.out.println(a.equals(b));
        System.out.println(!a.equals(b));
        System.out.println(a.compareTo(b) == 0);
        System.out.println(a.compareTo(b) != 0);

        System.out.println(a.compareTo(b) > 0);
        System.out.println(a.compareTo(b) == 1);
        System.out.println(a.compareTo(b) > -1);
        System.out.println(a.compareTo(b) >= 0); // Should be a >= b

        System.out.println(a.compareTo(b) < 0);
        System.out.println(a.compareTo(b) == -1);
        System.out.println(a.compareTo(b) < 1);
        System.out.println(a.compareTo(b) <= 0); // Should be a <= b
    }

    @Override
    public int compareTo(EqualsCompareTestData o) {
        return 0;
    }

    static void overriddenEquals(Order current, Order patched, Point p, Point q, Money m, Money n, Shape s, Shape t) {
        System.out.println(current.getTags().equals(patched.getTags()));
        System.out.println(!current.getLabels().equals(patched.getLabels()));
        System.out.println(p.equals(q));
        System.out.println(!p.equals(q));
        System.out.println(m.equals(n));
        System.out.println(!m.equals(n));
        System.out.println(s.equals(t)); // equals(Shape) does not override Object.equals: stays unfolded
    }

    record Point(int x, int y) {
    }

    static class Order {
        private List<String> tags;
        private Set<String> labels;

        public List<String> getTags() {
            return tags;
        }

        public Set<String> getLabels() {
            return labels;
        }
    }

    static class Money {
        @Override
        public boolean equals(Object other) {
            return other instanceof Money;
        }
    }

    static class Shape {
        public boolean equals(Shape other) {
            return other != null;
        }
    }
}
