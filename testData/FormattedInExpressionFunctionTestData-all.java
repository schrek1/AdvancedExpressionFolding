package data;

public class FormattedInExpressionFunctionTestData {
    <fold text='@Getter s' expand='false'>s</fold>tatic class Device <fold text='{...}' expand='true'>{
        private String name;
        private String platform;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>String getName()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>name<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>String getPlatform()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>platform<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>
    }</fold>

    String describe(Device device)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>"device <fold text='${device.name}' expand='false'>%s</fold> (<fold text='${device.platform}' expand='false'>%s</fold>)"<fold text='' expand='false'>.formatted(device.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold>, device.<fold text='p' expand='false'>getP</fold>latform<fold text='' expand='false'>()</fold>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    String describeName(Device device)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>"device <fold text='${device.name}' expand='false'>%s</fold>"<fold text='' expand='false'>.formatted(device.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    String describeInBlock(Device device) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>String</fold> text = "device <fold text='${device.name}' expand='false'>%s</fold>"<fold text='' expand='false'>.formatted(device.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold>)</fold>;
        return text;
    }</fold>

    String describeNullable(Device device) <fold text='{...}' expand='true'>{
        return<fold text=' if (' expand='false'> </fold>device.<fold text='p' expand='false'>getP</fold>latform<fold text='' expand='false'>()</fold> == null<fold text=')' expand='false'> ?</fold> "device <fold text='${device.name}' expand='false'>%s</fold>"<fold text='' expand='false'>.formatted(device.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold>)</fold> <fold text='else' expand='false'>:</fold> "device <fold text='${device.name}' expand='false'>%s</fold> (<fold text='${device.platform}' expand='false'>%s</fold>)"<fold text='' expand='false'>.formatted(device.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold>, device.<fold text='p' expand='false'>getP</fold>latform<fold text='' expand='false'>()</fold>)</fold><fold text='' expand='false'>;</fold>
    }</fold>
}
