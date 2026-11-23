package data;

public class InstanceofSwitchTestData {

    sealed interface Shape permits Circle, Square, Triangle <fold text='{...}' expand='true'>{
    }</fold>

    record Circle(double r) implements Shape <fold text='{...}' expand='true'>{
    }</fold>

    record Square(double side) implements Shape <fold text='{...}' expand='true'>{
    }</fold>

    record Triangle(double base, double height) implements Shape <fold text='{...}' expand='true'>{
    }</fold>

    double area(Shape shape) <fold text='{...}' expand='true'>{
        <fold text='var' expand='false'>double</fold> area = 0;
        <fold text='case' expand='false'>if (shape instanceof</fold> Circle c<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>area = Math.PI * c.<fold text='r' expand='false'>r()</fold> * c.<fold text='r' expand='false'>r()</fold>;
        <fold text='' expand='false'>}</fold></fold><fold text='case' expand='false'> else if (shape instanceof</fold> Square s<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>area = s.<fold text='side' expand='false'>side()</fold> * s.<fold text='side' expand='false'>side()</fold>;
        }</fold>
        return area;
    }</fold>

    String describe(Object value) <fold text='{...}' expand='true'>{
        <fold text='case' expand='false'>if (value instanceof</fold> Integer number<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return "number <fold text='$' expand='false'>" + </fold>number<fold text='";' expand='false'>;</fold>
        <fold text='' expand='false'>}</fold></fold><fold text='case' expand='false'> else if (value instanceof</fold> String text<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return "text <fold text='$' expand='false'>" + </fold>text<fold text='";' expand='false'>;</fold>
        <fold text='' expand='false'>}</fold></fold><fold text='case' expand='false'> else if (value instanceof</fold> Shape<fold text=' _ ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return "shape";
        <fold text='' expand='false'>}</fold></fold><fold text='' expand='false'> </fold><fold text='default' expand='false'>else</fold> <fold text='-> ' expand='false'><fold text='{...}' expand='true'>{
            </fold>return "unknown";
        }</fold>
    }</fold>

    double recordPatterns(Shape shape) <fold text='{...}' expand='true'>{
        <fold text='case' expand='false'>if (shape instanceof</fold> Circle(double r)<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return r * r;
        <fold text='' expand='false'>}</fold></fold><fold text='case' expand='false'> else if (shape instanceof</fold> Triangle(double base, double height)<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return base * height / 2;
        <fold text='' expand='false'>}</fold></fold><fold text='' expand='false'> </fold><fold text='default' expand='false'>else</fold> <fold text='-> ' expand='false'><fold text='{...}' expand='true'>{
            </fold>return 0;
        }</fold>
    }</fold>

    String singleCheck(Object value) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>value <fold text='is' expand='false'>instanceof</fold> String text<fold text='' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return text;<fold text='' expand='false'>
        }</fold></fold>
        return "";
    }</fold>

    String singleCheckWithElse(Object value) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold>if <fold text='' expand='false'>(</fold>value <fold text='is' expand='false'>instanceof</fold> String text<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return text;
        }</fold> else <fold text='{...}' expand='true'>{
            return "";
        }</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    String differentVariables(Object first, Object second) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>first <fold text='is' expand='false'>instanceof</fold> String text<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return text;
        }</fold> else if <fold text='' expand='false'>(</fold>second <fold text='is' expand='false'>instanceof</fold> Integer number<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return "number <fold text='$' expand='false'>" + </fold>number<fold text='";' expand='false'>;</fold>
        }</fold>
        return "";
    }</fold>

    String guardedCondition(Object value) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>value <fold text='is' expand='false'>instanceof</fold> String text && !text.<fold text='e' expand='false'>isE</fold>mpty<fold text='' expand='false'>()</fold><fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return text;
        }</fold> else if <fold text='' expand='false'>(</fold>value <fold text='is' expand='false'>instanceof</fold> Integer number<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return "number <fold text='$' expand='false'>" + </fold>number<fold text='";' expand='false'>;</fold>
        }</fold>
        return "";
    }</fold>

    String otherConditionInChain(Object value) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>value <fold text='is' expand='false'>instanceof</fold> String text<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return text;
        }</fold> else if <fold text='' expand='false'>(</fold>value == null<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return "null";
        }</fold> else if <fold text='' expand='false'>(</fold>value <fold text='is' expand='false'>instanceof</fold> Integer number<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return "number <fold text='$' expand='false'>" + </fold>number<fold text='";' expand='false'>;</fold>
        }</fold>
        return "";
    }</fold>

    String multiStatementBranch(Object value) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>value <fold text='is' expand='false'>instanceof</fold> String text<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='val' expand='false'>String</fold> trimmed = text.trim();
            return trimmed;
        }</fold> else if <fold text='' expand='false'>(</fold>value <fold text='is' expand='false'>instanceof</fold> Integer number<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return "number <fold text='$' expand='false'>" + </fold>number<fold text='";' expand='false'>;</fold>
        }</fold>
        return "";
    }</fold>

    String methodCallSubject() <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>load() <fold text='is' expand='false'>instanceof</fold> String text<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return text;
        }</fold> else if <fold text='' expand='false'>(</fold>load() <fold text='is' expand='false'>instanceof</fold> Integer number<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return "number <fold text='$' expand='false'>" + </fold>number<fold text='";' expand='false'>;</fold>
        }</fold>
        return "";
    }</fold>

    double commentedDefault(Object value) <fold text='{...}' expand='true'>{
        <fold text='case' expand='false'>if (value instanceof</fold> String text<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return Double.parseDouble(text);
        <fold text='' expand='false'>}</fold></fold><fold text='case' expand='false'> else if (value instanceof</fold> Long number<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return number.doubleValue();
        <fold text='' expand='false'>}</fold></fold><fold text='' expand='false'> </fold><fold text='default' expand='false'>else</fold> <fold text='->' expand='false'><fold text='{...}' expand='true'>{</fold>
            <fold text='//...' expand='true'>// should not happen; log and fail
            // callers validate the value first</fold>
            throw <fold text='' expand='false'>new </fold>IllegalArgumentException("unexpected <fold text='$' expand='false'>" + </fold>value<fold text='")' expand='false'>)</fold>;
        }</fold>
    }</fold>

    String commentedCases(Object value) <fold text='{...}' expand='true'>{
        <fold text='case' expand='false'>if (value instanceof</fold> String text<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{</fold>
            // already trimmed by the caller
            return text;
        <fold text='' expand='false'>}</fold></fold><fold text='case' expand='false'> else if (value instanceof</fold> Integer number<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{</fold>
            // counts start at zero
            return "number <fold text='$' expand='false'>" + </fold>number<fold text='";' expand='false'>;</fold>
        <fold text='' expand='false'>}</fold></fold><fold text='case' expand='false'> else if (value instanceof</fold> Shape<fold text=' _ ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return "shape";
        }</fold>
        return "";
    }</fold>

    double toAmount(Object value) <fold text='{...}' expand='true'>{
        <fold text='case' expand='false'>if (value instanceof</fold> String text<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return Double.parseDouble(text);
        <fold text='' expand='false'>}</fold></fold><fold text='case' expand='false'> else if (value instanceof</fold> Integer number<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return number.doubleValue();
        <fold text='' expand='false'>}</fold></fold><fold text='case' expand='false'> else if (value instanceof</fold> Long number<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return number.doubleValue();
        <fold text='' expand='false'>}</fold></fold><fold text='' expand='false'> </fold><fold text='default' expand='false'>else</fold> <fold text='->' expand='false'><fold text='{...}' expand='true'>{</fold>
            // only the types above are expected
            throw <fold text='' expand='false'>new </fold>IllegalArgumentException("unexpected type: <fold text='$' expand='false'>" + </fold>value<fold text='")' expand='false'>)</fold>;
        }</fold>
    }</fold>

    String fourCases(Object value) <fold text='{...}' expand='true'>{
        <fold text='case' expand='false'>if (value instanceof</fold> String text<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return text;
        <fold text='' expand='false'>}</fold></fold><fold text='case' expand='false'> else if (value instanceof</fold> Integer number<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return "int <fold text='$' expand='false'>" + </fold>number<fold text='";' expand='false'>;</fold>
        <fold text='' expand='false'>}</fold></fold><fold text='case' expand='false'> else if (value instanceof</fold> Long number<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return "long <fold text='$' expand='false'>" + </fold>number<fold text='";' expand='false'>;</fold>
        <fold text='' expand='false'>}</fold></fold><fold text='case' expand='false'> else if (value instanceof</fold> Shape<fold text=' _ ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return "shape";
        }</fold>
        return "";
    }</fold>

    String commentedLastCase(Object value) <fold text='{...}' expand='true'>{
        <fold text='case' expand='false'>if (value instanceof</fold> String text<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return text;
        <fold text='' expand='false'>}</fold></fold><fold text='case' expand='false'> else if (value instanceof</fold> Integer number<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{</fold>
            // counts start at zero
            return "number <fold text='$' expand='false'>" + </fold>number<fold text='";' expand='false'>;</fold>
        }</fold>
        return "";
    }</fold>

    String commentInCondition(Object value) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>value <fold text='/*...*/' expand='true'>/* raw input */</fold> <fold text='is' expand='false'>instanceof</fold> String text<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return text;
        }</fold> else if <fold text='' expand='false'>(</fold>value <fold text='is' expand='false'>instanceof</fold> Integer number<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return "number <fold text='$' expand='false'>" + </fold>number<fold text='";' expand='false'>;</fold>
        }</fold>
        return "";
    }</fold>

    String commentAfterStatement(Object value) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>value <fold text='is' expand='false'>instanceof</fold> String text<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return text;
            // trailing note
        }</fold> else if <fold text='' expand='false'>(</fold>value <fold text='is' expand='false'>instanceof</fold> Integer number<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return "number <fold text='$' expand='false'>" + </fold>number<fold text='";' expand='false'>;</fold>
        }</fold>
        return "";
    }</fold>

    String withoutBraces(Object value) <fold text='{...}' expand='true'>{
        if (value <fold text='is' expand='false'>instanceof</fold> String text)
            return text;
        else if (value <fold text='is' expand='false'>instanceof</fold> Integer number)
            return "number <fold text='$' expand='false'>" + </fold>number<fold text='";' expand='false'>;</fold>
        return "";
    }</fold>

    private Object load()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>"value"<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>
}
