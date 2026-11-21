package data;

public class ReverseSteppedLoopsTestData {
    public static void main(String[] args) <fold text='{...}' expand='true'>{
        int n = args.length;
        int step = 3;
        for (<fold text='' expand='false'>int </fold>i<fold text=' in ' expand='false'> = </fold>n - 1<fold text=' downTo ' expand='false'>; i >= </fold>0<fold text='' expand='false'>; i--</fold>) <fold text='{...}' expand='true'>{
            System.out.println(args[i]);
        }</fold>
        // Kotlin syntax leaves it unfolded: `downTo` always includes its end
        for (int i = n; i > 0; --i) <fold text='{...}' expand='true'>{
            System.out.println(i);
        }</fold>
        for (<fold text='' expand='false'>int </fold>i<fold text=' in ' expand='false'> = </fold>0<fold text='..<' expand='false'>; i < </fold>n<fold text=' step ' expand='false'>; i += </fold>2) <fold text='{...}' expand='true'>{
            System.out.println(i);
        }</fold>
        for (<fold text='' expand='false'>long </fold>i<fold text=' in ' expand='false'> = </fold>0<fold text='..' expand='false'>; i <= </fold>n<fold text=' step ' expand='false'>; i += </fold>step) <fold text='{...}' expand='true'>{
            System.out.println(i);
        }</fold>
        for (<fold text='' expand='false'>int </fold>i<fold text=' in ' expand='false'> = </fold>100<fold text=' downTo ' expand='false'>; i >= </fold>0<fold text=' step ' expand='false'>; i -= </fold>10) <fold text='{...}' expand='true'>{
            System.out.println(i);
        }</fold>
        // not folded: the condition goes the other way than the step
        for (int i = 0; i < n; i--) <fold text='{...}' expand='true'>{
            System.out.println(i);
        }</fold>
        // not folded: multiplication is not a step
        for (int i = 1; i < n; i *= 2) <fold text='{...}' expand='true'>{
            System.out.println(i);
        }</fold>
        // not folded: the step is not a single literal or variable
        for (int i = 0; i < n; i += step + 1) <fold text='{...}' expand='true'>{
            System.out.println(i);
        }</fold>
        // not folded: the condition checks another variable
        for (int i = n; step > 0; i--) <fold text='{...}' expand='true'>{
            System.out.println(i);
        }</fold>
    }</fold>
}
