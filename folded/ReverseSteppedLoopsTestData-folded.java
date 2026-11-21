package data;

public class ReverseSteppedLoopsTestData {
    public static void main(String[] args) {
        int n = args.length;
        int step = 3;
        for (i in n - 1 downTo 0) {
            System.out.println(args[i]);
        }
        // Kotlin syntax leaves it unfolded: `downTo` always includes its end
        for (int i = n; i > 0; --i) {
            System.out.println(i);
        }
        for (i in 0..<n step 2) {
            System.out.println(i);
        }
        for (i in 0..n step step) {
            System.out.println(i);
        }
        for (i in 100 downTo 0 step 10) {
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
