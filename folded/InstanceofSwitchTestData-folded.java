package data;

public class InstanceofSwitchTestData {

    sealed interface Shape permits Circle, Square, Triangle {
    }

    record Circle(double r) implements Shape {
    }

    record Square(double side) implements Shape {
    }

    record Triangle(double base, double height) implements Shape {
    }

    double area(Shape shape) {
        double area = 0;
        switch (shape) {
        case Circle c -> area = Math.PI * c.r() * c.r();
        case Square s -> area = s.side() * s.side();
        }
        return area;
    }

    String describe(Object value) {
        switch (value) {
        case Integer number -> return "number " + number;
        case String text -> return "text " + text;
        case Shape _ -> return "shape";
        default -> return "unknown";
        }
    }

    double recordPatterns(Shape shape) {
        switch (shape) {
        case Circle(double r) -> return r * r;
        case Triangle(double base, double height) -> return base * height / 2;
        default -> return 0;
        }
    }

    String singleCheck(Object value) {
        if (value instanceof String text) {
            return text;
        }
        return "";
    }

    String singleCheckWithElse(Object value) {
        if (value instanceof String text) {
            return text;
        } else {
            return "";
        }
    }

    String differentVariables(Object first, Object second) {
        if (first instanceof String text) {
            return text;
        } else if (second instanceof Integer number) {
            return "number " + number;
        }
        return "";
    }

    String guardedCondition(Object value) {
        if (value instanceof String text && !text.isEmpty()) {
            return text;
        } else if (value instanceof Integer number) {
            return "number " + number;
        }
        return "";
    }

    String otherConditionInChain(Object value) {
        if (value instanceof String text) {
            return text;
        } else if (value == null) {
            return "null";
        } else if (value instanceof Integer number) {
            return "number " + number;
        }
        return "";
    }

    String multiStatementBranch(Object value) {
        if (value instanceof String text) {
            String trimmed = text.trim();
            return trimmed;
        } else if (value instanceof Integer number) {
            return "number " + number;
        }
        return "";
    }

    String methodCallSubject() {
        if (load() instanceof String text) {
            return text;
        } else if (load() instanceof Integer number) {
            return "number " + number;
        }
        return "";
    }

    double commentedDefault(Object value) {
        switch (value) {
        case String text -> return Double.parseDouble(text);
        case Long number -> return number.doubleValue();
        default ->
            // should not happen; log and fail
            // callers validate the value first
            throw new IllegalArgumentException("unexpected " + value);
        }
    }

    String commentedCases(Object value) {
        switch (value) {
        case String text -> // already trimmed by the caller
            return text;
        case Integer number -> 
            // counts start at zero
            return "number " + number;
        case Shape _ -> return "shape";
        }
        return "";
    }

    String commentInCondition(Object value) {
        if (value /* raw input */ instanceof String text) {
            return text;
        } else if (value instanceof Integer number) {
            return "number " + number;
        }
        return "";
    }

    String commentAfterStatement(Object value) {
        if (value instanceof String text) {
            return text;
            // trailing note
        } else if (value instanceof Integer number) {
            return "number " + number;
        }
        return "";
    }

    String withoutBraces(Object value) {
        if (value instanceof String text)
            return text;
        else if (value instanceof Integer number)
            return "number " + number;
        return "";
    }

    private Object load() {
        return "value";
    }
}
