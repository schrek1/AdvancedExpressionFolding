package data;

public class GuardReturnWhenTestData {

    enum Code <fold text='{...}' expand='true'>{ NOT_FOUND, CONFLICT, RATE_LIMITED }</fold>

    interface Status <fold text='{...}' expand='true'>{
        boolean matches(Code code);
    }</fold>

    record Response(Status status, String body) <fold text='{...}' expand='true'>{
    }</fold>

    record Result(int httpStatus, String body) <fold text='{...}' expand='true'>{
        static Result ok(Response response)<fold text=' { ' expand='false'> {
            </fold>return new Result(200, response.body());<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    Result handle(Response response) <fold text='{...}' expand='true'>{
        Status status = response.status();
        <fold text='' expand='false'>if (</fold>status.matches(Code.NOT_FOUND)<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return notFound(response)<fold text='' expand='false'>;
        }</fold></fold>
        <fold text='' expand='false'>if (</fold>status.matches(Code.CONFLICT)<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return conflict(response)<fold text='' expand='false'>;
        }</fold></fold>
        <fold text='' expand='false'>if (</fold>status.matches(Code.RATE_LIMITED)<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return retryLater(response)<fold text='' expand='false'>;
        }</fold></fold>
        return Result.ok(response)<fold text='' expand='false'>;</fold>
    }</fold>

    String classify(int size) <fold text='{...}' expand='true'>{
        <fold text='' expand='false'>if (</fold>size == 0<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return "empty"<fold text='' expand='false'>;
        }</fold></fold>
        <fold text='' expand='false'>if (</fold>size < 10<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return "small"<fold text='' expand='false'>;
        }</fold></fold>
        return "large"<fold text='' expand='false'>;</fold>
    }</fold>

    void dispatch(String command) <fold text='{...}' expand='true'>{
        <fold text='' expand='false'>if (</fold>command.isEmpty()<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return<fold text='' expand='false'>;
        }</fold></fold>
        <fold text='' expand='false'>if (</fold>command.startsWith("#")<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return<fold text='' expand='false'>;
        }</fold></fold>
        return<fold text='' expand='false'>;</fold>
    }</fold>

    String onlyTheTail(String name, int size) <fold text='{...}' expand='true'>{
        if (name == null) <fold text='{...}' expand='true'>{
            name = "unknown";
            return name;
        }</fold>
        <fold text='' expand='false'>if (</fold>size == 0<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return name<fold text='' expand='false'>;
        }</fold></fold>
        <fold text='' expand='false'>if (</fold>size == 1<fold text=' ->' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return name + "!"<fold text='' expand='false'>;
        }</fold></fold>
        return name + size<fold text='' expand='false'>;</fold>
    }</fold>

    String singleGuard(String name) <fold text='{...}' expand='true'>{
        if (name == null) <fold text='{...}' expand='true'>{
            return "unknown";
        }</fold>
        return name;
    }</fold>

    String commentInBranch(int size) <fold text='{...}' expand='true'>{
        if (size == 0) <fold text='{...}' expand='true'>{
            // nothing to show
            return "empty";
        }</fold>
        if (size < 10) <fold text='{...}' expand='true'>{
            return "small";
        }</fold>
        return "large";
    }</fold>

    String commentBetween(int size) <fold text='{...}' expand='true'>{
        if (size == 0) <fold text='{...}' expand='true'>{
            return "empty";
        }</fold>
        // the common case
        if (size < 10) <fold text='{...}' expand='true'>{
            return "small";
        }</fold>
        return "large";
    }</fold>

    String elseBranch(int size) <fold text='{...}' expand='true'>{
        if (size == 0) <fold text='{...}' expand='true'>{
            return "empty";
        }</fold> else if (size < 10) <fold text='{...}' expand='true'>{
            return "small";
        }</fold>
        return "large";
    }</fold>

    String noFinalReturn(int size) <fold text='{...}' expand='true'>{
        if (size == 0) <fold text='{...}' expand='true'>{
            return "empty";
        }</fold>
        if (size < 10) <fold text='{...}' expand='true'>{
            return "small";
        }</fold>
        String label = "large";
        return label;
    }</fold>

    private Result notFound(Response response)<fold text=' { ' expand='false'> {
        </fold>return new Result(404, response.body());<fold text=' }' expand='false'>
    }</fold>

    private Result conflict(Response response)<fold text=' { ' expand='false'> {
        </fold>return new Result(409, response.body());<fold text=' }' expand='false'>
    }</fold>

    private Result retryLater(Response response)<fold text=' { ' expand='false'> {
        </fold>return new Result(429, response.body());<fold text=' }' expand='false'>
    }</fold>
}
