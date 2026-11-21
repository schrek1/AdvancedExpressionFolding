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
        <fold text='switch' expand='false'>if</fold> (shape<fold text='' expand='false'> instanceof Circle c</fold>) <fold text='{...}' expand='true'>{
           <fold text=' case Circle c -> ' expand='false'> </fold>area = Math.PI * c.<fold text='r' expand='false'>r()</fold> * c.<fold text='r' expand='false'>r()</fold>;
        <fold text='' expand='false'>}</fold></fold><fold text='    case ' expand='false'> else if (shape instanceof </fold>Square s<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>area = s.<fold text='side' expand='false'>side()</fold> * s.<fold text='side' expand='false'>side()</fold>;
        }</fold>
        return area;
    }</fold>

    String describe(Object value) <fold text='{...}' expand='true'>{
        <fold text='switch' expand='false'>if</fold> (value<fold text='' expand='false'> instanceof Integer number</fold>) <fold text='{...}' expand='true'>{
           <fold text=' case Integer number -> ' expand='false'> </fold>return "number <fold text='$' expand='false'>" + </fold>number<fold text='";' expand='false'>;</fold>
        <fold text='' expand='false'>}</fold></fold><fold text='    case ' expand='false'> else if (value instanceof </fold>String text<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return "text <fold text='$' expand='false'>" + </fold>text<fold text='";' expand='false'>;</fold>
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
        if <fold text='' expand='false'>(</fold>value instanceof String text<fold text='' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return text;<fold text='' expand='false'>
        }</fold></fold>
        return "";
    }</fold>

    String singleCheckWithElse(Object value) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold>if <fold text='' expand='false'>(</fold>value instanceof String text<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return text;
        }</fold> else <fold text='{...}' expand='true'>{
            return "";
        }</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    String differentVariables(Object first, Object second) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>first instanceof String text<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return text;
        }</fold> else if <fold text='' expand='false'>(</fold>second instanceof Integer number<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return "number <fold text='$' expand='false'>" + </fold>number<fold text='";' expand='false'>;</fold>
        }</fold>
        return "";
    }</fold>

    String guardedCondition(Object value) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>value instanceof String text && !text.<fold text='empty' expand='false'>isEmpty()</fold><fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return text;
        }</fold> else if <fold text='' expand='false'>(</fold>value instanceof Integer number<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return "number <fold text='$' expand='false'>" + </fold>number<fold text='";' expand='false'>;</fold>
        }</fold>
        return "";
    }</fold>

    String otherConditionInChain(Object value) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>value instanceof String text<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return text;
        }</fold> else if <fold text='' expand='false'>(</fold>value == null<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return "null";
        }</fold> else if <fold text='' expand='false'>(</fold>value instanceof Integer number<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return "number <fold text='$' expand='false'>" + </fold>number<fold text='";' expand='false'>;</fold>
        }</fold>
        return "";
    }</fold>

    String multiStatementBranch(Object value) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>value instanceof String text<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='val' expand='false'>String</fold> trimmed = text.trim();
            return trimmed;
        }</fold> else if <fold text='' expand='false'>(</fold>value instanceof Integer number<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return "number <fold text='$' expand='false'>" + </fold>number<fold text='";' expand='false'>;</fold>
        }</fold>
        return "";
    }</fold>

    String methodCallSubject() <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>load() instanceof String text<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return text;
        }</fold> else if <fold text='' expand='false'>(</fold>load() instanceof Integer number<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return "number <fold text='$' expand='false'>" + </fold>number<fold text='";' expand='false'>;</fold>
        }</fold>
        return "";
    }</fold>

    String commentInChain(Object value) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>value instanceof String text<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            // keeps the chain unfolded
            return text;
        }</fold> else if <fold text='' expand='false'>(</fold>value instanceof Integer number<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            return "number <fold text='$' expand='false'>" + </fold>number<fold text='";' expand='false'>;</fold>
        }</fold>
        return "";
    }</fold>

    String withoutBraces(Object value) <fold text='{...}' expand='true'>{
        if (value instanceof String text)
            return text;
        else if (value instanceof Integer number)
            return "number <fold text='$' expand='false'>" + </fold>number<fold text='";' expand='false'>;</fold>
        return "";
    }</fold>

    private Object load()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>"value"<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>
}
