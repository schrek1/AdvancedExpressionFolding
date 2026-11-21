package data;

public class SwitchArrowTestData {

    enum Status <fold text='{...}' expand='true'>{ NEW, PAID, SHIPPED, DONE, CANCELLED }</fold>

    void breakCases(Status status) <fold text='{...}' expand='true'>{
        switch <fold text='' expand='false'>(</fold>status<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            case NEW<fold text=' -> ' expand='false'>:
                </fold>start();<fold text='' expand='false'>
                break;</fold>
            case DONE<fold text=' -> ' expand='false'>:
                </fold>archive();<fold text='' expand='false'>
                break;</fold>
        }</fold>
    }</fold>

    void groupedLabelsAndDefault(Status status) <fold text='{...}' expand='true'>{
        switch <fold text='' expand='false'>(</fold>status<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            case NEW<fold text=', ' expand='false'>:
            case </fold>PAID<fold text=' -> ' expand='false'>:
                </fold>start();<fold text='' expand='false'>
                break;</fold>
            case SHIPPED, DONE<fold text=' -> ' expand='false'>:
                </fold>archive();<fold text='' expand='false'>
                break;</fold>
            default<fold text=' -> ' expand='false'>:
                </fold>log("unexpected <fold text='$' expand='false'>" + </fold>status<fold text='")' expand='false'>)</fold>;
        }</fold>
    }</fold>

    String returnCases(Status status) <fold text='{...}' expand='true'>{
        switch <fold text='' expand='false'>(</fold>status<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            case NEW<fold text=' -> ' expand='false'>:
                </fold>return "new";
            case CANCELLED<fold text=' -> ' expand='false'>:
                </fold>throw new IllegalStateException("cancelled");
            default<fold text=' -> ' expand='false'>:
                </fold>return "other";
        }</fold>
    }</fold>

    void assignmentCases(int code) <fold text='{...}' expand='true'>{
        <fold text='var' expand='false'>String</fold> label = "";
        switch <fold text='' expand='false'>(</fold>code<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            case 1<fold text=' -> ' expand='false'>:
                </fold>label = "one";<fold text='' expand='false'>
                break;</fold>
            case 2<fold text=' -> ' expand='false'>:
                </fold>label = "two";<fold text='' expand='false'>
                break;</fold>
        }</fold>
        log(label);
    }</fold>

    void fallThrough(Status status) <fold text='{...}' expand='true'>{
        switch <fold text='' expand='false'>(</fold>status<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            case NEW:
                start();
            case DONE:
                archive();
                break;
        }</fold>
    }</fold>

    void multipleStatements(Status status) <fold text='{...}' expand='true'>{
        switch <fold text='' expand='false'>(</fold>status<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            case NEW:
                start();
                log("started");
                break;
            case DONE:
                archive();
                break;
        }</fold>
    }</fold>

    void withComment(Status status) <fold text='{...}' expand='true'>{
        switch <fold text='' expand='false'>(</fold>status<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            case NEW:
                // keeps the switch as it is
                start();
                break;
            case DONE:
                archive();
                break;
        }</fold>
    }</fold>

    void emptyCase(Status status) <fold text='{...}' expand='true'>{
        switch <fold text='' expand='false'>(</fold>status<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            case NEW:
                break;
            case DONE:
                archive();
                break;
        }</fold>
    }</fold>

    void arrowSwitch(Status status) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold>switch <fold text='' expand='false'>(</fold>status<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            case NEW -> start();
            case DONE -> archive();
        }</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    private void start() <fold text='{}' expand='true'>{
    }</fold>

    private void archive() <fold text='{}' expand='true'>{
    }</fold>

    private void log(String message) <fold text='{}' expand='true'>{
    }</fold>
}
