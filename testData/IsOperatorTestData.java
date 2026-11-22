package data;

import java.util.List;

@SuppressWarnings("ALL")
public class IsOperatorTestData {

    interface Shape <fold text='{...}' expand='true'>{
    }</fold>

    record Circle(double radius) implements Shape <fold text='{...}' expand='true'>{
    }</fold>

    record Square(double side) implements Shape <fold text='{...}' expand='true'>{
    }</fold>

    public double area(Shape shape) <fold text='{...}' expand='true'>{
        if (shape <fold text='is' expand='false'>instanceof</fold> Circle c) <fold text='{...}' expand='true'>{
            return Math.PI * c.radius() * c.radius();
        }</fold>
        if (<fold text='' expand='true'>!(</fold>shape <fold text='!is' expand='true'>instanceof</fold> Square square<fold text='' expand='true'>)</fold>) <fold text='{...}' expand='true'>{
            throw new IllegalArgumentException("Unknown shape");
        }</fold>
        return square.side() * square.side();
    }</fold>

    public boolean isRound(Object value)<fold text=' { ' expand='false'> {
        </fold>return value <fold text='is' expand='false'>instanceof</fold> Circle;<fold text=' }' expand='false'>
    }</fold>

    public boolean isNotRound(Object value)<fold text=' { ' expand='false'> {
        </fold>return <fold text='' expand='true'>!(</fold>value <fold text='!is' expand='true'>instanceof</fold> Circle<fold text='' expand='true'>)</fold>;<fold text=' }' expand='false'>
    }</fold>

    public long countRound(List<Shape> shapes) <fold text='{...}' expand='true'>{
        return shapes.stream().filter(shape -> shape <fold text='is' expand='false'>instanceof</fold> Circle).count();
    }</fold>

    public String describe(Object value)<fold text=' { ' expand='false'> {
        </fold>return value <fold text='is' expand='false'>instanceof</fold> String text && !text.isEmpty() ? text : "-";<fold text=' }' expand='false'>
    }</fold>

    public boolean doubleParentheses(Object value) <fold text='{...}' expand='true'>{
        // Double parentheses stay as written: only `instanceof` itself turns into `is`.
        return !((value <fold text='is' expand='false'>instanceof</fold> Square));
    }</fold>
}
