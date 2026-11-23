package data;

@SuppressWarnings("ALL")
public class ConditionalAsIfElseAlwaysTestData {

    // a short ternary becomes if-else too
    int pageSize(int requested) {
        return if (requested > 0) requested else 20
    }

    // an honest Elvis stays Elvis
    String nameOrUnknown(String name) {
        return name ?: "unknown";
    }

    // a ternary right after `(` has no space for ` if (`, so it stays a ternary
    void printMode(boolean verbose) {
        System.out.println(verbose ? "verbose" : "quiet");
    }
}
