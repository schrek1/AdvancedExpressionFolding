package data;

@SuppressWarnings("ALL")
public class IfNullSafeData {
    public void enter(Data data) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>User</fold> user = null;
        <fold text='val' expand='false'>var</fold> threeChains = data<fold text='?' expand='false'> != null
                && data</fold>.<fold text='data1' expand='false'>getData1()</fold> != null
                && data<fold text='?' expand='false'> != null
                && data</fold>.<fold text='data1' expand='false'>getData1()</fold> != null
                && data != null
                && data<fold text='?' expand='false'> != null
                && data</fold>.<fold text='data1' expand='false'>getData1()</fold><fold text='?' expand='false'> != null
                && data.<fold text='data1' expand='false'>getData1()</fold></fold><fold text='.active == true' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold>;

        <fold text='val' expand='false'>var</fold> notChain = data != null && !data.<fold text='data1' expand='false'>getData1()</fold>.<fold text='active' expand='false'>isActive()</fold>;
        <fold text='val' expand='false'>var</fold> chain = data<fold text='?' expand='false'> != null && data</fold>.<fold text='data1' expand='false'>getData1()</fold><fold text='?' expand='false'> != null && data.<fold text='data1' expand='false'>getData1()</fold></fold>.<fold text='data4' expand='false'>getData4()</fold> != null;

        if <fold text='' expand='false'>(</fold>data<fold text='?' expand='false'> != null && data</fold>.<fold text='data1' expand='false'>getData1()</fold><fold text='?' expand='false'> != null &&
                data.<fold text='data1' expand='false'>getData1()</fold></fold>.<fold text='data2' expand='false'>getData2()</fold><fold text='?' expand='false'> != null && data.<fold text='data1' expand='false'>getData1()</fold>.
                <fold text='data2' expand='false'>getData2()</fold>
                </fold>.<fold text='data3' expand='false'>getData3()</fold> != null<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println("data?.data1?.data2?.data3 != null");
        }</fold>
        if <fold text='' expand='false'>(</fold>data<fold text='?' expand='false'> != null && data</fold>.<fold text='data1' expand='false'>getData1()</fold> != null<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println("data?.data1 != null");
        }</fold>
        <fold text='println(user?.profile?.name);' expand='false'>if (user != null
                && user.getProfile() != null
                && user.getProfile().getName() != null) <fold text='{...}' expand='true'>{
            System.out.println(user.getProfile().getName());
        }</fold></fold>
        if <fold text='' expand='false'>(</fold>user<fold text='?' expand='false'> != null
                && user</fold>.<fold text='profile' expand='false'>getProfile()</fold><fold text='?' expand='false'> != null
                && user.<fold text='profile' expand='false'>getProfile()</fold></fold>.<fold text='name' expand='false'>getName()</fold> != null<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println("Name: <fold text='${' expand='false'>" + </fold>user.<fold text='profile' expand='false'>getProfile()</fold>.<fold text='name}"' expand='false'>getName()</fold>);
        }</fold>
        if <fold text='' expand='false'>(</fold>user<fold text='?' expand='false'> != null
                && user</fold>.<fold text='profile' expand='false'>getProfile()</fold><fold text='?' expand='false'> != null
                && user.<fold text='profile' expand='false'>getProfile()</fold></fold>.<fold text='name' expand='false'>getName()</fold> != null<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println(user.<fold text='profile' expand='false'>getProfile()</fold>.<fold text='name' expand='false'>getName()</fold>.trim());
        }</fold>
        if <fold text='' expand='false'>(</fold>user.<fold text='profile' expand='false'>getProfile()</fold> != null
                && user != null
                && user.<fold text='profile' expand='false'>getProfile()</fold>.<fold text='name' expand='false'>getName()</fold> != null<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println(user.<fold text='profile' expand='false'>getProfile()</fold>.<fold text='name' expand='false'>getName()</fold>);
        }</fold>
        if <fold text='' expand='false'>(</fold>data<fold text='?' expand='false'> != null && data</fold><fold text='.active == true' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold><fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println("data?.active == true");
        }</fold>
        if <fold text='' expand='false'>(</fold>data<fold text='?' expand='false'> != null
                && data</fold>.<fold text='data1' expand='false'>getData1()</fold><fold text='?' expand='false'> != null
                && data.<fold text='data1' expand='false'>getData1()</fold></fold>.<fold text='data2' expand='false'>getData2()</fold><fold text='?' expand='false'> != null
                && data.<fold text='data1' expand='false'>getData1()</fold>.<fold text='data2' expand='false'>getData2()</fold></fold>.<fold text='data3' expand='false'>getData3()</fold><fold text='?' expand='false'> != null
                && data.<fold text='data1' expand='false'>getData1()</fold>.<fold text='data2' expand='false'>getData2()</fold>.<fold text='data3' expand='false'>getData3()</fold></fold>.<fold text='data4' expand='false'>getData4()</fold> != null
                && data != null
                && data<fold text='?' expand='false'> != null
                && data</fold>.<fold text='data1' expand='false'>getData1()</fold><fold text='?' expand='false'> != null
                && !data.<fold text='data1' expand='false'>getData1()</fold></fold><fold text='.active == false' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold>
        <fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println("2chainz");
        }</fold>
        <fold text='val' expand='false'>boolean</fold> has = data<fold text='?' expand='false'> != null
                && data</fold>.<fold text='data1' expand='false'>getData1()</fold><fold text='?' expand='false'> != null
                && data.<fold text='data1' expand='false'>getData1()</fold></fold>.<fold text='data2' expand='false'>getData2()</fold><fold text='?' expand='false'> != null
                && data.<fold text='data1' expand='false'>getData1()</fold>.<fold text='data2' expand='false'>getData2()</fold></fold>.<fold text='data3' expand='false'>getData3()</fold><fold text='?' expand='false'> != null
                && data.<fold text='data1' expand='false'>getData1()</fold>.<fold text='data2' expand='false'>getData2()</fold>.<fold text='data3' expand='false'>getData3()</fold></fold>.<fold text='data4' expand='false'>getData4()</fold> != null;
        var active = data<fold text='?' expand='false'> != null
                && data</fold>.<fold text='data1' expand='false'>getData1()</fold><fold text='?' expand='false'> != null
                && data.<fold text='data1' expand='false'>getData1()</fold></fold><fold text='.active == true' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold>;
        <fold text='val' expand='false'>var</fold> inactive = data<fold text='?' expand='false'> != null && !data</fold><fold text='.active == false' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold>;
        while <fold text='' expand='false'>(</fold>data<fold text='?' expand='false'> != null && data</fold>.<fold text='data2' expand='false'>getData2()</fold><fold text='?' expand='false'> != null && !data.<fold text='data2' expand='false'>getData2()</fold></fold><fold text='.active == false' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold><fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            active = !data.<fold text='data1' expand='false'>getData1()</fold>.<fold text='active' expand='false'>isActive()</fold>;
        }</fold>
    }</fold>

    public void equalsTrue(Data data, boolean flag) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>(data<fold text='?' expand='false'> != null && data</fold>.getData6()<fold text='?' expand='false'> != null &&
                data.getData6()</fold><fold text='.active == true' expand='false'>.isActive()</fold>)<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println("Conditions met!");
        }</fold>
    }</fold>

    public void equalsFalse(Data data, boolean flag) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>(data<fold text='?' expand='false'> != null && data</fold>.getData6()<fold text='?' expand='false'> != null &&
                !data.getData6()</fold><fold text='.active == false' expand='false'>.isActive()</fold>)<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println("Conditions met!");
        }</fold>
    }</fold>

    public void checkConditions(Data data, boolean flag) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>(flag
                || data<fold text='?' expand='false'> != null
                && data</fold>.<fold text='data1' expand='false'>getData1()</fold><fold text='?' expand='false'> != null
                && data.<fold text='data1' expand='false'>getData1()</fold></fold><fold text='.active == true' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold>)

                && (data<fold text='?' expand='false'> != null
                && data</fold>.<fold text='data2' expand='false'>getData2()</fold><fold text='?' expand='false'> != null
                && data.<fold text='data2' expand='false'>getData2()</fold></fold><fold text='.active == true' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold> ||

                data<fold text='?' expand='false'> != null
                        && data</fold>.<fold text='data3' expand='false'>getData3()</fold><fold text='?' expand='false'> != null
                        && data.<fold text='data3' expand='false'>getData3()</fold></fold><fold text='.active == true' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold>
                        && data.<fold text='data3' expand='false'>getData3()</fold>.<fold text='data4' expand='false'>getData4()</fold><fold text='?' expand='false'> != null
                        && data.<fold text='data3' expand='false'>getData3()</fold>.<fold text='data4' expand='false'>getData4()</fold></fold><fold text='.active == true' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold>) ||

                (data<fold text='?' expand='false'> != null
                        && data</fold>.getData5()<fold text='?' expand='false'> != null
                        && data.getData5()</fold><fold text='.active == true' expand='false'>.isActive()</fold>) &&
                        (flag && flag || flag &&

                                data<fold text='?' expand='false'> != null &&
                                data</fold>.<fold text='data6' expand='false'>getData6()</fold><fold text='?' expand='false'> != null &&
                                data.<fold text='data6' expand='false'>getData6()</fold></fold><fold text='.active == true' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold>)<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println("Conditions met!");
        }</fold>
    }</fold>

    public void notFullRoll(Data data) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>Data</fold> data2 = data;
        if <fold text='' expand='false'>(</fold>data.<fold text='data1' expand='false'>getData1()</fold>.<fold text='data2' expand='false'>getData2()</fold>.<fold text='data3' expand='false'>getData3()</fold><fold text='?' expand='false'> != null &&
                data.<fold text='data1' expand='false'>getData1()</fold>.<fold text='data2' expand='false'>getData2()</fold>.<fold text='data3' expand='false'>getData3()</fold></fold><fold text='.active == true' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold><fold text='' expand='false'>)</fold> {

        }

        if <fold text='' expand='false'>(</fold>data2.<fold text='data1' expand='false'>getData1()</fold>.<fold text='data2' expand='false'>getData2()</fold><fold text='?' expand='false'> != null &&
                data2.<fold text='data1' expand='false'>getData1()</fold>.<fold text='data2' expand='false'>getData2()</fold></fold><fold text='.active == true' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold><fold text='' expand='false'>)</fold> {

        }


        if <fold text='' expand='false'>(</fold>data2.<fold text='data1' expand='false'>getData1()</fold><fold text='?' expand='false'> != null &&
                data2.<fold text='data1' expand='false'>getData1()</fold></fold><fold text='.active == true' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold><fold text='' expand='false'>)</fold> {

        }
    }</fold>

    static class Data <fold text='{...}' expand='true'>{
        <fold text='' expand='true'>public</fold><fold text='' expand='true'> </fold><fold text='' expand='true'>Data</fold><fold text='' expand='true'> </fold>getData1<fold text='' expand='true'>()</fold><fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>null<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        <fold text='' expand='true'>public</fold><fold text='' expand='true'> </fold><fold text='' expand='true'>Data</fold><fold text='' expand='true'> </fold>getData2<fold text='' expand='true'>()</fold><fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>null<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        <fold text='' expand='true'>public</fold><fold text='' expand='true'> </fold><fold text='' expand='true'>Data</fold><fold text='' expand='true'> </fold>getData3<fold text='' expand='true'>()</fold><fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>null<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        <fold text='' expand='true'>public</fold><fold text='' expand='true'> </fold><fold text='' expand='true'>Data</fold><fold text='' expand='true'> </fold>getData4<fold text='' expand='true'>()</fold><fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>null<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        <fold text='' expand='true'>public</fold><fold text='' expand='true'> </fold><fold text='' expand='true'>Data</fold><fold text='' expand='true'> </fold>getData5<fold text='' expand='true'>()</fold><fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>null<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        <fold text='' expand='true'>public</fold><fold text='' expand='true'> </fold><fold text='' expand='true'>Data</fold><fold text='' expand='true'> </fold>getData6<fold text='' expand='true'>()</fold><fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>null<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        <fold text='' expand='true'>public</fold><fold text='' expand='true'> </fold><fold text='' expand='true'>boolean</fold><fold text='' expand='true'> </fold>isActive<fold text='' expand='true'>()</fold><fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>true<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>

    static class User <fold text='{...}' expand='true'>{
        <fold text='' expand='true'>public</fold><fold text='' expand='true'> </fold><fold text='' expand='true'>Profile</fold><fold text='' expand='true'> </fold>getProfile<fold text='' expand='true'>()</fold><fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>null<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>

    static class Profile <fold text='{...}' expand='true'>{
        <fold text='' expand='true'>public</fold><fold text='' expand='true'> </fold><fold text='' expand='true'>String</fold><fold text='' expand='true'> </fold>getName<fold text='' expand='true'>()</fold><fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>null<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>
}
