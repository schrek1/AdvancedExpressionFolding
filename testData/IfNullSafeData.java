package data;

@SuppressWarnings("ALL")
public class IfNullSafeData {
    public void enter(Data data) <fold text='{...}' expand='true'>{
        User user = null;
        var threeChains = data<fold text='?' expand='false'> != null
                && data</fold>.<fold text='data1' expand='false'>getData1()</fold> != null
                && data<fold text='?' expand='false'> != null
                && data</fold>.<fold text='data1' expand='false'>getData1()</fold> != null
                && data != null
                && data<fold text='?' expand='false'> != null
                && data</fold>.<fold text='data1' expand='false'>getData1()</fold><fold text='?' expand='false'> != null
                && data.<fold text='data1' expand='false'>getData1()</fold></fold><fold text='.active == true' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold>;

        var notChain = data != null && !data.<fold text='data1' expand='false'>getData1()</fold>.<fold text='active' expand='false'>isActive()</fold>;
        var chain = data<fold text='?' expand='false'> != null && data</fold>.<fold text='data1' expand='false'>getData1()</fold><fold text='?' expand='false'> != null && data.<fold text='data1' expand='false'>getData1()</fold></fold>.<fold text='data4' expand='false'>getData4()</fold> != null;

        if (data<fold text='?' expand='false'> != null && data</fold>.<fold text='data1' expand='false'>getData1()</fold><fold text='?' expand='false'> != null &&
                data.<fold text='data1' expand='false'>getData1()</fold></fold>.<fold text='data2' expand='false'>getData2()</fold><fold text='?' expand='false'> != null && data.<fold text='data1' expand='false'>getData1()</fold>.
                <fold text='data2' expand='false'>getData2()</fold>
                </fold>.<fold text='data3' expand='false'>getData3()</fold> != null) <fold text='{...}' expand='true'>{
            System.out.println("data?.data1?.data2?.data3 != null");
        }</fold>
        if (data<fold text='?' expand='false'> != null && data</fold>.<fold text='data1' expand='false'>getData1()</fold> != null) <fold text='{...}' expand='true'>{
            System.out.println("data?.data1 != null");
        }</fold>
        <fold text='System.out.println(user?.profile?.name);' expand='false'>if (user != null
                && user.getProfile() != null
                && user.getProfile().getName() != null) <fold text='{...}' expand='true'>{
            System.out.println(user.getProfile().getName());
        }</fold></fold>
        if (user<fold text='?' expand='false'> != null
                && user</fold>.<fold text='profile' expand='false'>getProfile()</fold><fold text='?' expand='false'> != null
                && user.<fold text='profile' expand='false'>getProfile()</fold></fold>.<fold text='name' expand='false'>getName()</fold> != null) <fold text='{...}' expand='true'>{
            System.out.println("Name: " + user.<fold text='profile' expand='false'>getProfile()</fold>.<fold text='name' expand='false'>getName()</fold>);
        }</fold>
        if (user<fold text='?' expand='false'> != null
                && user</fold>.<fold text='profile' expand='false'>getProfile()</fold><fold text='?' expand='false'> != null
                && user.<fold text='profile' expand='false'>getProfile()</fold></fold>.<fold text='name' expand='false'>getName()</fold> != null) <fold text='{...}' expand='true'>{
            System.out.println(user.<fold text='profile' expand='false'>getProfile()</fold>.<fold text='name' expand='false'>getName()</fold>.trim());
        }</fold>
        if (user.<fold text='profile' expand='false'>getProfile()</fold> != null
                && user != null
                && user.<fold text='profile' expand='false'>getProfile()</fold>.<fold text='name' expand='false'>getName()</fold> != null) <fold text='{...}' expand='true'>{
            System.out.println(user.<fold text='profile' expand='false'>getProfile()</fold>.<fold text='name' expand='false'>getName()</fold>);
        }</fold>
        if (data<fold text='?' expand='false'> != null && data</fold><fold text='.active == true' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold>) <fold text='{...}' expand='true'>{
            System.out.println("data?.active == true");
        }</fold>
        if (data<fold text='?' expand='false'> != null
                && data</fold>.<fold text='data1' expand='false'>getData1()</fold><fold text='?' expand='false'> != null
                && data.<fold text='data1' expand='false'>getData1()</fold></fold>.<fold text='data2' expand='false'>getData2()</fold><fold text='?' expand='false'> != null
                && data.<fold text='data1' expand='false'>getData1()</fold>.<fold text='data2' expand='false'>getData2()</fold></fold>.<fold text='data3' expand='false'>getData3()</fold><fold text='?' expand='false'> != null
                && data.<fold text='data1' expand='false'>getData1()</fold>.<fold text='data2' expand='false'>getData2()</fold>.<fold text='data3' expand='false'>getData3()</fold></fold>.<fold text='data4' expand='false'>getData4()</fold> != null
                && data != null
                && data<fold text='?' expand='false'> != null
                && data</fold>.<fold text='data1' expand='false'>getData1()</fold><fold text='?' expand='false'> != null
                && !data.<fold text='data1' expand='false'>getData1()</fold></fold><fold text='.active == false' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold>
        ) <fold text='{...}' expand='true'>{
            System.out.println("2chainz");
        }</fold>
        boolean has = data<fold text='?' expand='false'> != null
                && data</fold>.<fold text='data1' expand='false'>getData1()</fold><fold text='?' expand='false'> != null
                && data.<fold text='data1' expand='false'>getData1()</fold></fold>.<fold text='data2' expand='false'>getData2()</fold><fold text='?' expand='false'> != null
                && data.<fold text='data1' expand='false'>getData1()</fold>.<fold text='data2' expand='false'>getData2()</fold></fold>.<fold text='data3' expand='false'>getData3()</fold><fold text='?' expand='false'> != null
                && data.<fold text='data1' expand='false'>getData1()</fold>.<fold text='data2' expand='false'>getData2()</fold>.<fold text='data3' expand='false'>getData3()</fold></fold>.<fold text='data4' expand='false'>getData4()</fold> != null;
        var active = data<fold text='?' expand='false'> != null
                && data</fold>.<fold text='data1' expand='false'>getData1()</fold><fold text='?' expand='false'> != null
                && data.<fold text='data1' expand='false'>getData1()</fold></fold><fold text='.active == true' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold>;
        var inactive = data<fold text='?' expand='false'> != null && !data</fold><fold text='.active == false' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold>;
        while (data<fold text='?' expand='false'> != null && data</fold>.<fold text='data2' expand='false'>getData2()</fold><fold text='?' expand='false'> != null && !data.<fold text='data2' expand='false'>getData2()</fold></fold><fold text='.active == false' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold>) <fold text='{...}' expand='true'>{
            active = !data.<fold text='data1' expand='false'>getData1()</fold>.<fold text='active' expand='false'>isActive()</fold>;
        }</fold>
    }</fold>

    public void equalsTrue(Data data, boolean flag) <fold text='{...}' expand='true'>{
        if ((data<fold text='?' expand='false'> != null && data</fold>.<fold text='data6' expand='false'>getData6()</fold><fold text='?' expand='false'> != null &&
                data.<fold text='data6' expand='false'>getData6()</fold></fold><fold text='.active == true' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold>)) <fold text='{...}' expand='true'>{
            System.out.println("Conditions met!");
        }</fold>
    }</fold>

    public void equalsFalse(Data data, boolean flag) <fold text='{...}' expand='true'>{
        if ((data<fold text='?' expand='false'> != null && data</fold>.<fold text='data6' expand='false'>getData6()</fold><fold text='?' expand='false'> != null &&
                !data.<fold text='data6' expand='false'>getData6()</fold></fold><fold text='.active == false' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold>)) <fold text='{...}' expand='true'>{
            System.out.println("Conditions met!");
        }</fold>
    }</fold>

    public void checkConditions(Data data, boolean flag) <fold text='{...}' expand='true'>{
        if ((flag
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
                        && data</fold>.<fold text='data5' expand='false'>getData5()</fold><fold text='?' expand='false'> != null
                        && data.<fold text='data5' expand='false'>getData5()</fold></fold><fold text='.active == true' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold>) &&
                        (flag && flag || flag &&

                                data<fold text='?' expand='false'> != null &&
                                data</fold>.<fold text='data6' expand='false'>getData6()</fold><fold text='?' expand='false'> != null &&
                                data.<fold text='data6' expand='false'>getData6()</fold></fold><fold text='.active == true' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold>)) <fold text='{...}' expand='true'>{
            System.out.println("Conditions met!");
        }</fold>
    }</fold>

    public void notFullRoll(Data data) <fold text='{...}' expand='true'>{
        Data data2 = data;
        if (data.<fold text='data1' expand='false'>getData1()</fold>.<fold text='data2' expand='false'>getData2()</fold>.<fold text='data3' expand='false'>getData3()</fold><fold text='?' expand='false'> != null &&
                data.<fold text='data1' expand='false'>getData1()</fold>.<fold text='data2' expand='false'>getData2()</fold>.<fold text='data3' expand='false'>getData3()</fold></fold><fold text='.active == true' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold>) {

        }

        if (data2.<fold text='data1' expand='false'>getData1()</fold>.<fold text='data2' expand='false'>getData2()</fold><fold text='?' expand='false'> != null &&
                data2.<fold text='data1' expand='false'>getData1()</fold>.<fold text='data2' expand='false'>getData2()</fold></fold><fold text='.active == true' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold>) {

        }


        if (data2.<fold text='data1' expand='false'>getData1()</fold><fold text='?' expand='false'> != null &&
                data2.<fold text='data1' expand='false'>getData1()</fold></fold><fold text='.active == true' expand='false'>.<fold text='active' expand='false'>isActive()</fold></fold>) {

        }
    }</fold>

    static class Data <fold text='{...}' expand='true'>{
        public Data getData1()<fold text=' { ' expand='false'> {
            </fold>return null;<fold text=' }' expand='false'>
        }</fold>

        public Data getData2()<fold text=' { ' expand='false'> {
            </fold>return null;<fold text=' }' expand='false'>
        }</fold>

        public Data getData3()<fold text=' { ' expand='false'> {
            </fold>return null;<fold text=' }' expand='false'>
        }</fold>

        public Data getData4()<fold text=' { ' expand='false'> {
            </fold>return null;<fold text=' }' expand='false'>
        }</fold>

        public Data getData5()<fold text=' { ' expand='false'> {
            </fold>return null;<fold text=' }' expand='false'>
        }</fold>

        public Data getData6()<fold text=' { ' expand='false'> {
            </fold>return null;<fold text=' }' expand='false'>
        }</fold>

        public boolean isActive()<fold text=' { ' expand='false'> {
            </fold>return true;<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    static class User <fold text='{...}' expand='true'>{
        public Profile getProfile()<fold text=' { ' expand='false'> {
            </fold>return null;<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    static class Profile <fold text='{...}' expand='true'>{
        public String getName()<fold text=' { ' expand='false'> {
            </fold>return null;<fold text=' }' expand='false'>
        }</fold>
    }</fold>
}
