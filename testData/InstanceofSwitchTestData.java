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
        double area = 0;
        <fold text='switch' expand='false'>if</fold> (shape<fold text='' expand='false'> instanceof Circle c</fold>) <fold text='{...}' expand='true'>{
           <fold text=' case Circle c -> ' expand='false'> </fold>area = Math.PI * c.r() * c.r();
        <fold text='' expand='false'>}</fold></fold><fold text='    case ' expand='false'> else if (shape instanceof </fold>Square s<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>area = s.side() * s.side();
        }</fold>
        return area;
    }</fold>

    String describe(Object value) <fold text='{...}' expand='true'>{
        <fold text='switch' expand='false'>if</fold> (value<fold text='' expand='false'> instanceof Integer number</fold>) <fold text='{...}' expand='true'>{
           <fold text=' case Integer number -> ' expand='false'> </fold>return "number " + number;
        <fold text='' expand='false'>}</fold></fold><fold text='    case ' expand='false'> else if (value instanceof </fold>String text<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return "text " + text;
        <fold text='' expand='false'>}</fold></fold><fold text='    case ' expand='false'> else if (value instanceof </fold>Shape<fold text=' _ ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return "shape";
        <fold text='' expand='false'>}</fold></fold><fold text='    ' expand='false'> </fold><fold text='default' expand='false'>else</fold> <fold text='-> ' expand='false'><fold text='{...}' expand='true'>{
            </fold>return "unknown";
        }</fold>
    }</fold>

    double recordPatterns(Shape shape) <fold text='{...}' expand='true'>{
        <fold text='switch' expand='false'>if</fold> (shape<fold text='' expand='false'> instanceof Circle(double r)</fold>) <fold text='{...}' expand='true'>{
           <fold text=' case Circle(double r) -> ' expand='false'> </fold>return r * r;
        <fold text='' expand='false'>}</fold></fold><fold text='    case ' expand='false'> else if (shape instanceof </fold>Triangle(double base, double height)<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return base * height / 2;
        <fold text='' expand='false'>}</fold></fold><fold text='    ' expand='false'> </fold><fold text='default' expand='false'>else</fold> <fold text='-> ' expand='false'><fold text='{...}' expand='true'>{
            </fold>return 0;
        }</fold>
    }</fold>

    String singleCheck(Object value) <fold text='{...}' expand='true'>{
        if (value instanceof String text) <fold text='{...}' expand='true'>{
            return text;
        }</fold>
        return "";
    }</fold>

    String singleCheckWithElse(Object value) <fold text='{...}' expand='true'>{
        if (value instanceof String text) <fold text='{...}' expand='true'>{
            return text;
        }</fold> else <fold text='{...}' expand='true'>{
            return "";
        }</fold>
    }</fold>

    String differentVariables(Object first, Object second) <fold text='{...}' expand='true'>{
        if (first instanceof String text) <fold text='{...}' expand='true'>{
            return text;
        }</fold> else if (second instanceof Integer number) <fold text='{...}' expand='true'>{
            return "number " + number;
        }</fold>
        return "";
    }</fold>

    String guardedCondition(Object value) <fold text='{...}' expand='true'>{
        if (value instanceof String text && !text.isEmpty()) <fold text='{...}' expand='true'>{
            return text;
        }</fold> else if (value instanceof Integer number) <fold text='{...}' expand='true'>{
            return "number " + number;
        }</fold>
        return "";
    }</fold>

    String otherConditionInChain(Object value) <fold text='{...}' expand='true'>{
        if (value instanceof String text) <fold text='{...}' expand='true'>{
            return text;
        }</fold> else if (value == null) <fold text='{...}' expand='true'>{
            return "null";
        }</fold> else if (value instanceof Integer number) <fold text='{...}' expand='true'>{
            return "number " + number;
        }</fold>
        return "";
    }</fold>

    String multiStatementBranch(Object value) <fold text='{...}' expand='true'>{
        if (value instanceof String text) <fold text='{...}' expand='true'>{
            String trimmed = text.trim();
            return trimmed;
        }</fold> else if (value instanceof Integer number) <fold text='{...}' expand='true'>{
            return "number " + number;
        }</fold>
        return "";
    }</fold>

    String methodCallSubject() <fold text='{...}' expand='true'>{
        if (load() instanceof String text) <fold text='{...}' expand='true'>{
            return text;
        }</fold> else if (load() instanceof Integer number) <fold text='{...}' expand='true'>{
            return "number " + number;
        }</fold>
        return "";
    }</fold>

    double commentedDefault(Object value) <fold text='{...}' expand='true'>{
        <fold text='switch' expand='false'>if</fold> (value<fold text='' expand='false'> instanceof String text</fold>) <fold text='{...}' expand='true'>{
           <fold text=' case String text -> ' expand='false'> </fold>return Double.parseDouble(text);
        <fold text='' expand='false'>}</fold></fold><fold text='    case ' expand='false'> else if (value instanceof </fold>Long number<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return number.doubleValue();
        <fold text='' expand='false'>}</fold></fold><fold text='    ' expand='false'> </fold><fold text='default' expand='false'>else</fold> <fold text='->' expand='false'><fold text='{...}' expand='true'>{</fold>
            <fold text='//...' expand='true'>// should not happen; log and fail
            // callers validate the value first</fold>
            throw new IllegalArgumentException("unexpected " + value);
        }</fold>
    }</fold>

    String commentedCases(Object value) <fold text='{...}' expand='true'>{
        <fold text='switch' expand='false'>if</fold> (value<fold text='' expand='false'> instanceof String text</fold>) <fold text='{...}' expand='true'>{
           <fold text=' case String text -> ' expand='false'> </fold>// already trimmed by the caller
            return text;
        <fold text='' expand='false'>}</fold></fold><fold text='    case ' expand='false'> else if (value instanceof </fold>Integer number<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{</fold>
            // counts start at zero
            return "number " + number;
        <fold text='' expand='false'>}</fold></fold><fold text='    case ' expand='false'> else if (value instanceof </fold>Shape<fold text=' _ ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return "shape";
        }</fold>
        return "";
    }</fold>

    String commentInCondition(Object value) <fold text='{...}' expand='true'>{
        if (value <fold text='/*...*/' expand='true'>/* raw input */</fold> instanceof String text) <fold text='{...}' expand='true'>{
            return text;
        }</fold> else if (value instanceof Integer number) <fold text='{...}' expand='true'>{
            return "number " + number;
        }</fold>
        return "";
    }</fold>

    String commentAfterStatement(Object value) <fold text='{...}' expand='true'>{
        if (value instanceof String text) <fold text='{...}' expand='true'>{
            return text;
            // trailing note
        }</fold> else if (value instanceof Integer number) <fold text='{...}' expand='true'>{
            return "number " + number;
        }</fold>
        return "";
    }</fold>

    String withoutBraces(Object value) <fold text='{...}' expand='true'>{
        if (value instanceof String text)
            return text;
        else if (value instanceof Integer number)
            return "number " + number;
        return "";
    }</fold>

    private Object load()<fold text=' { ' expand='false'> {
        </fold>return "value";<fold text=' }' expand='false'>
    }</fold>
}
