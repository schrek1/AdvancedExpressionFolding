package data;

@SuppressWarnings("ALL")
public class ConditionalAsIfElseAlwaysTestData {

    // a short ternary becomes if-else too
    int pageSize(int requested) {
        return requested > 0 ? requested : 20;
    }

    // an honest Elvis stays Elvis
    String nameOrUnknown(String name) {
        return name != null ? name : "unknown";
    }

    // a ternary right after `(` has no space for ` if (`, so it stays a ternary
    void printMode(boolean verbose) {
        System.out.println(verbose ? "verbose" : "quiet");
    }
}
