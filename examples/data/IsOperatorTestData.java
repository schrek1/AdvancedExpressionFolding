package data;

import java.util.List;

@SuppressWarnings("ALL")
public class IsOperatorTestData {

    interface Shape {
    }

    record Circle(double radius) implements Shape {
    }

    record Square(double side) implements Shape {
    }

    public double area(Shape shape) {
        if (shape instanceof Circle c) {
            return Math.PI * c.radius() * c.radius();
        }
        if (!(shape instanceof Square square)) {
            throw new IllegalArgumentException("Unknown shape");
        }
        return square.side() * square.side();
    }

    public boolean isRound(Object value) {
        return value instanceof Circle;
    }

    public boolean isNotRound(Object value) {
        return !(value instanceof Circle);
    }

    public long countRound(List<Shape> shapes) {
        return shapes.stream().filter(shape -> shape instanceof Circle).count();
    }

    public String describe(Object value) {
        return value instanceof String text && !text.isEmpty() ? text : "-";
    }

    public boolean doubleParentheses(Object value) {
        // Double parentheses stay as written: only `instanceof` itself turns into `is`.
        return !((value instanceof Square));
    }
}
