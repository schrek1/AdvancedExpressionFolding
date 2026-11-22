package data;

@SuppressWarnings("ALL")
public class IfNullSafeData {
    public void enter(Data data) <fold text='{...}' expand='true'>{
        User user = null;
        var threeChains = <fold text='data?.data1 != null' expand='false'>data != null
                && data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold> != null</fold>
                && <fold text='data?.data1 != null' expand='false'>data != null
                && data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold> != null</fold>
                && data != null
                && <fold text='data?.data1?.active == true' expand='false'>data != null
                && data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold> != null
                && data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold>.<fold text='a' expand='false'>isA</fold>ctive<fold text='' expand='false'>()</fold></fold>;

        var notChain = data != null && !data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold>.<fold text='a' expand='false'>isA</fold>ctive<fold text='' expand='false'>()</fold>;
        var chain = <fold text='data?.data1?.data4 != null' expand='false'>data != null && data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold> != null && data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold>.<fold text='d' expand='false'>getD</fold>ata4<fold text='' expand='false'>()</fold> != null</fold>;

        if (<fold text='data?.data1?.data2?.data3 != null' expand='false'>data != null && data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold> != null &&
                data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold>.<fold text='d' expand='false'>getD</fold>ata2<fold text='' expand='false'>()</fold> != null && data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold>.
                <fold text='d' expand='false'>getD</fold>ata2<fold text='' expand='false'>()</fold>
                .<fold text='d' expand='false'>getD</fold>ata3<fold text='' expand='false'>()</fold> != null</fold>) <fold text='{...}' expand='true'>{
            System.out.println("data?.data1?.data2?.data3 != null");
        }</fold>
        if (<fold text='data?.data1 != null' expand='false'>data != null && data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold> != null</fold>) <fold text='{...}' expand='true'>{
            System.out.println("data?.data1 != null");
        }</fold>
        <fold text='System.out.println(user?.profile?.name);' expand='false'>if (user != null
                && user.getProfile() != null
                && user.getProfile().getName() != null) <fold text='{...}' expand='true'>{
            System.out.println(user.getProfile().getName());
        }</fold></fold>
        if (<fold text='user?.profile?.name != null' expand='false'>user != null
                && user.<fold text='p' expand='false'>getP</fold>rofile<fold text='' expand='false'>()</fold> != null
                && user.<fold text='p' expand='false'>getP</fold>rofile<fold text='' expand='false'>()</fold>.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold> != null</fold>) <fold text='{...}' expand='true'>{
            System.out.println("Name: " + user.<fold text='p' expand='false'>getP</fold>rofile<fold text='' expand='false'>()</fold>.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold>);
        }</fold>
        if (<fold text='user?.profile?.name != null' expand='false'>user != null
                && user.<fold text='p' expand='false'>getP</fold>rofile<fold text='' expand='false'>()</fold> != null
                && user.<fold text='p' expand='false'>getP</fold>rofile<fold text='' expand='false'>()</fold>.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold> != null</fold>) <fold text='{...}' expand='true'>{
            System.out.println(user.<fold text='p' expand='false'>getP</fold>rofile<fold text='' expand='false'>()</fold>.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold>.trim());
        }</fold>
        if (user.<fold text='p' expand='false'>getP</fold>rofile<fold text='' expand='false'>()</fold> != null
                && user != null
                && user.<fold text='p' expand='false'>getP</fold>rofile<fold text='' expand='false'>()</fold>.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold> != null) <fold text='{...}' expand='true'>{
            System.out.println(user.<fold text='p' expand='false'>getP</fold>rofile<fold text='' expand='false'>()</fold>.<fold text='n' expand='false'>getN</fold>ame<fold text='' expand='false'>()</fold>);
        }</fold>
        if (<fold text='data?.active == true' expand='false'>data != null && data.<fold text='a' expand='false'>isA</fold>ctive<fold text='' expand='false'>()</fold></fold>) <fold text='{...}' expand='true'>{
            System.out.println("data?.active == true");
        }</fold>
        if (<fold text='data?.data1?.data2?.data3?.data4 != null' expand='false'>data != null
                && data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold> != null
                && data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold>.<fold text='d' expand='false'>getD</fold>ata2<fold text='' expand='false'>()</fold> != null
                && data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold>.<fold text='d' expand='false'>getD</fold>ata2<fold text='' expand='false'>()</fold>.<fold text='d' expand='false'>getD</fold>ata3<fold text='' expand='false'>()</fold> != null
                && data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold>.<fold text='d' expand='false'>getD</fold>ata2<fold text='' expand='false'>()</fold>.<fold text='d' expand='false'>getD</fold>ata3<fold text='' expand='false'>()</fold>.<fold text='d' expand='false'>getD</fold>ata4<fold text='' expand='false'>()</fold> != null</fold>
                && data != null
                && <fold text='data?.data1?.active == false' expand='false'>data != null
                && data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold> != null
                && !data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold>.<fold text='a' expand='false'>isA</fold>ctive<fold text='' expand='false'>()</fold></fold>
        ) <fold text='{...}' expand='true'>{
            System.out.println("2chainz");
        }</fold>
        boolean has = <fold text='data?.data1?.data2?.data3?.data4 != null' expand='false'>data != null
                && data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold> != null
                && data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold>.<fold text='d' expand='false'>getD</fold>ata2<fold text='' expand='false'>()</fold> != null
                && data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold>.<fold text='d' expand='false'>getD</fold>ata2<fold text='' expand='false'>()</fold>.<fold text='d' expand='false'>getD</fold>ata3<fold text='' expand='false'>()</fold> != null
                && data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold>.<fold text='d' expand='false'>getD</fold>ata2<fold text='' expand='false'>()</fold>.<fold text='d' expand='false'>getD</fold>ata3<fold text='' expand='false'>()</fold>.<fold text='d' expand='false'>getD</fold>ata4<fold text='' expand='false'>()</fold> != null</fold>;
        var active = <fold text='data?.data1?.active == true' expand='false'>data != null
                && data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold> != null
                && data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold>.<fold text='a' expand='false'>isA</fold>ctive<fold text='' expand='false'>()</fold></fold>;
        var inactive = <fold text='data?.active == false' expand='false'>data != null && !data.<fold text='a' expand='false'>isA</fold>ctive<fold text='' expand='false'>()</fold></fold>;
        while (<fold text='data?.data2?.active == false' expand='false'>data != null && data.<fold text='d' expand='false'>getD</fold>ata2<fold text='' expand='false'>()</fold> != null && !data.<fold text='d' expand='false'>getD</fold>ata2<fold text='' expand='false'>()</fold>.<fold text='a' expand='false'>isA</fold>ctive<fold text='' expand='false'>()</fold></fold>) <fold text='{...}' expand='true'>{
            active = !data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold>.<fold text='a' expand='false'>isA</fold>ctive<fold text='' expand='false'>()</fold>;
        }</fold>
    }</fold>

    public void equalsTrue(Data data, boolean flag) <fold text='{...}' expand='true'>{
        if ((<fold text='data?.data6?.active == true' expand='false'>data != null && data.<fold text='d' expand='false'>getD</fold>ata6<fold text='' expand='false'>()</fold> != null &&
                data.<fold text='d' expand='false'>getD</fold>ata6<fold text='' expand='false'>()</fold>.<fold text='a' expand='false'>isA</fold>ctive<fold text='' expand='false'>()</fold></fold>)) <fold text='{...}' expand='true'>{
            System.out.println("Conditions met!");
        }</fold>
    }</fold>

    public void equalsFalse(Data data, boolean flag) <fold text='{...}' expand='true'>{
        if ((<fold text='data?.data6?.active == false' expand='false'>data != null && data.<fold text='d' expand='false'>getD</fold>ata6<fold text='' expand='false'>()</fold> != null &&
                !data.<fold text='d' expand='false'>getD</fold>ata6<fold text='' expand='false'>()</fold>.<fold text='a' expand='false'>isA</fold>ctive<fold text='' expand='false'>()</fold></fold>)) <fold text='{...}' expand='true'>{
            System.out.println("Conditions met!");
        }</fold>
    }</fold>

    public void checkConditions(Data data, boolean flag) <fold text='{...}' expand='true'>{
        if ((flag
                || <fold text='data?.data1?.active == true' expand='false'>data != null
                && data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold> != null
                && data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold>.<fold text='a' expand='false'>isA</fold>ctive<fold text='' expand='false'>()</fold></fold>)

                && (<fold text='data?.data2?.active == true' expand='false'>data != null
                && data.<fold text='d' expand='false'>getD</fold>ata2<fold text='' expand='false'>()</fold> != null
                && data.<fold text='d' expand='false'>getD</fold>ata2<fold text='' expand='false'>()</fold>.<fold text='a' expand='false'>isA</fold>ctive<fold text='' expand='false'>()</fold></fold> ||

                <fold text='data?.data3?.active == true' expand='false'>data != null
                        && data.<fold text='d' expand='false'>getD</fold>ata3<fold text='' expand='false'>()</fold> != null
                        && data.<fold text='d' expand='false'>getD</fold>ata3<fold text='' expand='false'>()</fold>.<fold text='a' expand='false'>isA</fold>ctive<fold text='' expand='false'>()</fold></fold>
                        && <fold text='data.data3.data4?.active == true' expand='false'>data.<fold text='d' expand='false'>getD</fold>ata3<fold text='' expand='false'>()</fold>.<fold text='d' expand='false'>getD</fold>ata4<fold text='' expand='false'>()</fold> != null
                        && data.<fold text='d' expand='false'>getD</fold>ata3<fold text='' expand='false'>()</fold>.<fold text='d' expand='false'>getD</fold>ata4<fold text='' expand='false'>()</fold>.<fold text='a' expand='false'>isA</fold>ctive<fold text='' expand='false'>()</fold></fold>) ||

                (<fold text='data?.data5?.active == true' expand='false'>data != null
                        && data.<fold text='d' expand='false'>getD</fold>ata5<fold text='' expand='false'>()</fold> != null
                        && data.<fold text='d' expand='false'>getD</fold>ata5<fold text='' expand='false'>()</fold>.<fold text='a' expand='false'>isA</fold>ctive<fold text='' expand='false'>()</fold></fold>) &&
                        (flag && flag || flag &&

                                <fold text='data?.data6?.active == true' expand='false'>data != null &&
                                data.<fold text='d' expand='false'>getD</fold>ata6<fold text='' expand='false'>()</fold> != null &&
                                data.<fold text='d' expand='false'>getD</fold>ata6<fold text='' expand='false'>()</fold>.<fold text='a' expand='false'>isA</fold>ctive<fold text='' expand='false'>()</fold></fold>)) <fold text='{...}' expand='true'>{
            System.out.println("Conditions met!");
        }</fold>
    }</fold>

    public void notFullRoll(Data data) <fold text='{...}' expand='true'>{
        Data data2 = data;
        if (<fold text='data.data1.data2.data3?.active == true' expand='false'>data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold>.<fold text='d' expand='false'>getD</fold>ata2<fold text='' expand='false'>()</fold>.<fold text='d' expand='false'>getD</fold>ata3<fold text='' expand='false'>()</fold> != null &&
                data.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold>.<fold text='d' expand='false'>getD</fold>ata2<fold text='' expand='false'>()</fold>.<fold text='d' expand='false'>getD</fold>ata3<fold text='' expand='false'>()</fold>.<fold text='a' expand='false'>isA</fold>ctive<fold text='' expand='false'>()</fold></fold>) {

        }

        if (<fold text='data2.data1.data2?.active == true' expand='false'>data2.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold>.<fold text='d' expand='false'>getD</fold>ata2<fold text='' expand='false'>()</fold> != null &&
                data2.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold>.<fold text='d' expand='false'>getD</fold>ata2<fold text='' expand='false'>()</fold>.<fold text='a' expand='false'>isA</fold>ctive<fold text='' expand='false'>()</fold></fold>) {

        }


        if (<fold text='data2.data1?.active == true' expand='false'>data2.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold> != null &&
                data2.<fold text='d' expand='false'>getD</fold>ata1<fold text='' expand='false'>()</fold>.<fold text='a' expand='false'>isA</fold>ctive<fold text='' expand='false'>()</fold></fold>) {

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
