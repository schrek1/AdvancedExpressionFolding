package data;

public class FormattedInExpressionFunctionTestData {
    static class Device <fold text='{...}' expand='true'>{
        private String name;
        private String platform;

        String getName()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>name<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        String getPlatform()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>platform<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>

    String describe(Device device)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>"device <fold text='${' expand='false'>%s (%s)".formatted(</fold>device.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold><fold text='} (${' expand='false'>, </fold>device.<fold text='p' expand='false'>getP</fold>latform<fold text='' expand='false'>()</fold><fold text='})"' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    String describeName(Device device)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>"device <fold text='${' expand='false'>%s".formatted(</fold>device.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold><fold text='}"' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    String describeInBlock(Device device) <fold text='{...}' expand='true'>{
        String text = "device <fold text='${' expand='false'>%s".formatted(</fold>device.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold><fold text='}"' expand='false'>)</fold>;
        return text;
    }</fold>

    String describeNullable(Device device) <fold text='{...}' expand='true'>{
        return<fold text=' if (' expand='false'> </fold>device.<fold text='p' expand='false'>getP</fold>latform<fold text='' expand='false'>()</fold> == null<fold text=')' expand='false'> ?</fold> "device <fold text='${' expand='false'>%s".formatted(</fold>device.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold><fold text='}"' expand='false'>)</fold> <fold text='else' expand='false'>:</fold> "device <fold text='${' expand='false'>%s (%s)".formatted(</fold>device.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold><fold text='} (${' expand='false'>, </fold>device.<fold text='p' expand='false'>getP</fold>latform<fold text='' expand='false'>()</fold><fold text='})"' expand='false'>)</fold><fold text='' expand='false'>;</fold>
    }</fold>
}
