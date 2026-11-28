package data;

import java.time.LocalDateTime;

@SuppressWarnings("ALL")
public class ConditionalAsIfElseTestData {

    // A1 - checked value inside a formatted template, `== null` orientation
    String labelNullFirst(Device device) <fold text='{...}' expand='true'>{
        return<fold text=' if (' expand='false'> </fold>device.<fold text='p' expand='false'>getP</fold>latform<fold text='' expand='false'>()</fold> == null<fold text=')' expand='false'> ?</fold> "device '<fold text='${device.name}' expand='false'>%s</fold>'"<fold text='' expand='false'>.formatted(device.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold>)</fold> <fold text='else' expand='false'>:</fold> "device '<fold text='${device.name}' expand='false'>%s</fold>' (<fold text='${device.platform}' expand='false'>%s</fold>)"<fold text='' expand='false'>.formatted(device.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold>, device.<fold text='p' expand='false'>getP</fold>latform<fold text='' expand='false'>()</fold>)</fold><fold text='' expand='false'>;</fold>
    }</fold>

    // A2 - checked value inside a formatted template, `!= null` orientation
    String labelNotNullFirst(Device device) <fold text='{...}' expand='true'>{
        return<fold text=' if (' expand='false'> </fold>device.<fold text='p' expand='false'>getP</fold>latform<fold text='' expand='false'>()</fold> != null<fold text=')' expand='false'> ?</fold> "device '<fold text='${device.name}' expand='false'>%s</fold>' (<fold text='${device.platform}' expand='false'>%s</fold>)"<fold text='' expand='false'>.formatted(device.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold>, device.<fold text='p' expand='false'>getP</fold>latform<fold text='' expand='false'>()</fold>)</fold> <fold text='else' expand='false'>:</fold> "device '<fold text='${device.name}' expand='false'>%s</fold>'"<fold text='' expand='false'>.formatted(device.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold>)</fold><fold text='' expand='false'>;</fold>
    }</fold>

    // B1 - checked value inside a concatenation, `== null` orientation
    String changedSuffixNullFirst(LocalDateTime changedAt) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='' expand='true'>return</fold><fold text='if (' expand='true'> </fold>changedAt == null<fold text=')' expand='false'> ?</fold> "" <fold text='else' expand='false'>:</fold> ", changed at <fold text='$' expand='false'>" + </fold>changedAt<fold text='"' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    // B2 - checked value inside a concatenation, `!= null` orientation
    String changedSuffixNotNullFirst(LocalDateTime changedAt) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='' expand='true'>return</fold><fold text='if (' expand='true'> </fold>changedAt != null<fold text=')' expand='false'> ?</fold> ", changed at <fold text='$' expand='false'>" + </fold>changedAt<fold text='" ' expand='false'> </fold><fold text='else' expand='false'>:</fold> ""<fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    // B3 - checked value as an argument of another call
    String changedText(LocalDateTime changedAt)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>changedAt != null ? format(changedAt) : ""<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    // C1 - the value itself
    String nameOrUnknown(String name)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>name != null ? </fold>name<fold text=' ?: ' expand='false'> : </fold>"unknown"<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    // C2 - the value itself, inverted
    String nameOrUnknownInverted(String name)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>name == null ? "unknown" : </fold>name<fold text=' ?: "unknown"' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    // C3 - a getter as the checked value
    String platformOrDefault(Device device)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>device.getPlatform() != null ? </fold>device.<fold text='p' expand='false'>getP</fold>latform<fold text='' expand='false'>()</fold><fold text=' ?: ' expand='false'> : </fold>"generic"<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    // D1 - a call on the value
    String deviceName(Device device)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>device != null ? </fold>device<fold text='?.' expand='false'>.</fold><fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold><fold text=' ?: ' expand='false'> : </fold>"unknown"<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    // D2 - a call on the value, inverted
    String deviceNameInverted(Device device)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>device == null ? "unknown" : </fold>device<fold text='?.' expand='false'>.</fold><fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold><fold text=' ?: "unknown"' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    // D3 - a call chain on the value
    String ownerEmail(Device device)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>device != null ? device.<fold text='o' expand='false'>getO</fold>wner<fold text='' expand='false'>()</fold>.<fold text='e' expand='false'>getE</fold>mail<fold text='' expand='false'>()</fold> : ""<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    // E1 - `: null` tail
    String deviceNameOrNull(Device device)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>device != null ? </fold>device<fold text='?.' expand='false'>.</fold><fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold><fold text='' expand='false'> : null</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    // E2 - `: null` tail, inverted
    String deviceNameOrNullInverted(Device device)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>device == null ? null : </fold>device<fold text='?.' expand='false'>.</fold><fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    // F1 - nested ternary, honest Elvis chain
    String firstKnown(String primary, String fallback) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>primary != null ? </fold>primary<fold text=' ?: ' expand='false'> : </fold><fold text='' expand='false'>fallback != null ? </fold>fallback<fold text=' ?: ' expand='false'> : </fold>"none"<fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    // F2 - nested ternary, checked values inside larger expressions
    String describe(Device device) <fold text='{...}' expand='true'>{
        return<fold text=' if (' expand='false'> </fold>device == null<fold text=')' expand='false'> ?</fold> "none" <fold text='else' expand='false'>:</fold><fold text=' if (' expand='false'> </fold>device.<fold text='p' expand='false'>getP</fold>latform<fold text='' expand='false'>()</fold> == null<fold text=')' expand='false'> ?</fold> device.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold> <fold text='else' expand='false'>:</fold><fold text=' "${' expand='false'> </fold>device.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold><fold text='}' expand='false'> + "</fold> on <fold text='${' expand='false'>" + </fold>device.<fold text='p' expand='false'>getP</fold>latform<fold text='}"' expand='false'>()</fold><fold text='' expand='false'>;</fold>
    }</fold>

    // G1 - multi-line, checked value inside a formatted template
    String labelMultiLine(Device device) <fold text='{...}' expand='true'>{
        return<fold text=' if (' expand='false'> </fold>device.<fold text='p' expand='false'>getP</fold>latform<fold text='' expand='false'>()</fold> == null
                <fold text='' expand='false'>? </fold>"device '<fold text='${device.name}' expand='false'>%s</fold>'"<fold text='' expand='false'>.formatted(device.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold>)</fold>
                <fold text='else' expand='false'>:</fold> "device '<fold text='${device.name}' expand='false'>%s</fold>' (<fold text='${device.platform}' expand='false'>%s</fold>)"<fold text='' expand='false'>.formatted(device.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold>, device.<fold text='p' expand='false'>getP</fold>latform<fold text='' expand='false'>()</fold>)</fold><fold text='' expand='false'>;</fold>
    }</fold>

    // G2 - multi-line, honest Elvis
    String deviceNameMultiLine(Device device) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>device != null
                ? </fold>device<fold text='?.' expand='false'>.</fold><fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold><fold text=' ?: ' expand='false'>
                : </fold>"unknown"<fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    // H1 - short ternary without a string branch stays a ternary
    int pageSize(int requested)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>requested > 0 ? requested : 20<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    // H2 - long ternary without a string branch
    long retryDelayMillis(int attempt, long baseDelayMillis, long maxDelayMillis) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='' expand='true'>return</fold><fold text='if (' expand='true'> </fold>attempt > 5<fold text=')' expand='false'> ?</fold> Math.min(maxDelayMillis, baseDelayMillis * attempt * attempt) <fold text='else' expand='false'>:</fold> baseDelayMillis * attempt<fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    // H3 - string branch in an assignment
    String status(boolean active, String name) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>String</fold> label =<fold text=' if (' expand='false'> </fold>active<fold text=')' expand='false'> ?</fold> "active: <fold text='$' expand='false'>" + </fold>name<fold text='" ' expand='false'> </fold><fold text='else' expand='false'>:</fold> "inactive"<fold text='' expand='false'>;</fold>
        return label;
    }</fold>

    // H4 - string branch as a call argument stays a ternary
    void printStatus(boolean active, String name)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='false'>System.out.</fold>println(active ? "active: <fold text='$' expand='false'>" + </fold>name<fold text='" ' expand='false'> </fold>: "inactive")<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    private static String format(LocalDateTime time)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>time.toString()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    <fold text='@Getter s' expand='false'>s</fold>tatic class Device <fold text='{...}' expand='true'>{
        private String name;
        private String platform;
        private Owner owner;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>public String getName()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>name<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public String getPlatform()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>platform<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public Owner getOwner()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>owner<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>
    }</fold>

    <fold text='@Getter s' expand='false'>s</fold>tatic class Owner <fold text='{...}' expand='true'>{
        private String email;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>public String getEmail()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>email<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>
    }</fold>
}
