package data;

import java.util.HashMap;

public class ExpressionFuncEqualsTestData {

    private String field;

    public long findNinjaId() {
        return 1L;
    }

    private void printStatus() {
        new HashMap<String, String>().put("a", "b");
    }

    private String printStatusReturn() {
        return new HashMap<String, String>().put("a", "b");
    }

    public String columnName(String column) {
        return "column1";
    }

    public void assignField(String field) {
        this.field = field;
    }

    public String methodCall(String field) {
        return assignFieldAndReturn(field);
    }

    public String assignFieldAndReturn(String field) {
        return this.field = field;
    }

    public String value() {
        return name();
    }

    private String name() {
        return field;
    }
}
