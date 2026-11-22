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
        if <fold text='' expand='false'>(</fold>shape <fold text='is' expand='false'>instanceof</fold> Circle c<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return Math.PI * c.<fold text='radius' expand='false'>radius()</fold> * c.<fold text='radius' expand='false'>radius()</fold>;
        }</fold>
        <fold text='require' expand='false'>if </fold>(<fold text='' expand='false'>!</fold><fold text='' expand='false'>(</fold>shape <fold text='is' expand='false'>instanceof</fold> Square square<fold text='' expand='false'>)</fold>) <fold text='{...}' expand='true'>{<fold text=' ' expand='false'>
            throw new IllegalArgumentException(</fold>"Unknown shape"<fold text=' ' expand='false'>);
        </fold>}</fold>
        return square.<fold text='side' expand='false'>side()</fold> * square.<fold text='side' expand='false'>side()</fold>;
    }</fold>

    public boolean isRound(Object value)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>value <fold text='is' expand='false'>instanceof</fold> Circle<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public boolean isNotRound(Object value)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='true'>!(</fold>value <fold text='!is' expand='true'>instanceof</fold> Circle<fold text='' expand='true'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public long countRound(List<Shape> shapes) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>shapes<fold text='.' expand='false'>.stream().</fold>filter(shape -> shape <fold text='is' expand='false'>instanceof</fold> Circle).count()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    public String describe(Object value)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>value <fold text='is' expand='false'>instanceof</fold> String text && !text.<fold text='e' expand='false'>isE</fold>mpty<fold text='' expand='false'>()</fold> ? text : "-"<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public boolean doubleParentheses(Object value) <fold text='{...}' expand='true'>{
        // Double parentheses stay as written: only `instanceof` itself turns into `is`.<fold text=' ' expand='true'>
        </fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>!((value <fold text='is' expand='false'>instanceof</fold> Square))<fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>
}
