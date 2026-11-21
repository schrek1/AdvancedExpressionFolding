package data;

public class SwitchArrowTestData {

    enum Status { NEW, PAID, SHIPPED, DONE, CANCELLED }

    void breakCases(Status status) {
        switch (status) {
            case NEW -> start();
            case DONE -> archive();
        }
    }

    void groupedLabelsAndDefault(Status status) {
        switch (status) {
            case NEW, PAID -> start();
            case SHIPPED, DONE -> archive();
            default -> log("unexpected " + status);
        }
    }

    String returnCases(Status status) {
        switch (status) {
            case NEW -> return "new";
            case CANCELLED -> throw new IllegalStateException("cancelled");
            default -> return "other";
        }
    }

    void assignmentCases(int code) {
        String label = "";
        switch (code) {
            case 1 -> label = "one";
            case 2 -> label = "two";
        }
        log(label);
    }

    void fallThrough(Status status) {
        switch (status) {
            case NEW:
                start();
            case DONE:
                archive();
                break;
        }
    }

    void multipleStatements(Status status) {
        switch (status) {
            case NEW:
                start();
                log("started");
                break;
            case DONE:
                archive();
                break;
        }
    }

    void withComment(Status status) {
        switch (status) {
            case NEW:
                // keeps the switch as it is
                start();
                break;
            case DONE:
                archive();
                break;
        }
    }

    void emptyCase(Status status) {
        switch (status) {
            case NEW:
                break;
            case DONE:
                archive();
                break;
        }
    }

    void arrowSwitch(Status status) {
        switch (status) {
            case NEW -> start();
            case DONE -> archive();
        }
    }

    private void start() {
    }

    private void archive() {
    }

    private void log(String message) {
    }
}
