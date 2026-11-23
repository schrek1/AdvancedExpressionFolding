package data;

@SuppressWarnings("ALL")
public class ConditionalAsIfElseNeverTestData {

    // a string branch stays a ternary
    String status(boolean active, String name) {
        return active ? "active: " + name : "inactive";
    }

    // a nested ternary stays a ternary
    String size(int count) {
        return count == 0 ? "none" : count == 1 ? "one" : "many";
    }

    // an honest Elvis stays Elvis
    String nameOrUnknown(String name) {
        return name ?: "unknown";
    }
}
