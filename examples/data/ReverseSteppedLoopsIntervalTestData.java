package data;

public class ReverseSteppedLoopsIntervalTestData {
    public static void main(String[] args) {
        int n = args.length;
        int step = 3;
        for (int i = n - 1; i >= 0; i--) {
            System.out.println(args[i]);
        }
        // Kotlin syntax leaves it unfolded: `downTo` always includes its end
        for (int i = n; i > 0; --i) {
            System.out.println(i);
        }
        for (int i = 0; i < n; i += 2) {
            System.out.println(i);
        }
        for (long i = 0; i <= n; i += step) {
            System.out.println(i);
        }
        for (int i = 100; i >= 0; i -= 10) {
            System.out.println(i);
        }
        // not folded: the condition goes the other way than the step
        for (int i = 0; i < n; i--) {
            System.out.println(i);
        }
        // not folded: multiplication is not a step
        for (int i = 1; i < n; i *= 2) {
            System.out.println(i);
        }
        // not folded: the step is not a single literal or variable
        for (int i = 0; i < n; i += step + 1) {
            System.out.println(i);
        }
        // not folded: the condition checks another variable
        for (int i = n; step > 0; i--) {
            System.out.println(i);
        }
    }
}
