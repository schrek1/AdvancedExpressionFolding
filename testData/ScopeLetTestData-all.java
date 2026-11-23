package data;

public class ScopeLetTestData {

    private String defaultEmail;

    public void folded(Contact contact, String email, String phone) <fold text='{...}' expand='true'>{
        <fold text='' expand='false'>if (</fold>email<fold text='?.let ' expand='false'> != null) </fold><fold text='{ ' expand='false'><fold text='{...}' expand='true'>{
            </fold>contact.<fold text='e' expand='false'>setE</fold>mail<fold text=' = ' expand='false'>(</fold><fold text='it' expand='false'>email</fold><fold text='' expand='false'>)</fold><fold text=' }' expand='false'>;
        }</fold></fold>
        <fold text='' expand='false'>if (</fold>phone<fold text='?.let { ' expand='false'> != null) </fold>contact.<fold text='p' expand='false'>setP</fold>hone<fold text=' = ' expand='false'>(</fold><fold text='it' expand='false'>phone</fold><fold text='' expand='false'>)</fold><fold text=' }' expand='false'>;</fold>
        <fold text='val' expand='false'>String</fold> nickname = contact.<fold text='n' expand='false'>getN</fold>ickname<fold text='' expand='false'>()</fold>;
        <fold text='' expand='false'>if (</fold>nickname<fold text='?.let ' expand='false'> != null) </fold><fold text='{ ' expand='false'><fold text='{...}' expand='true'>{
            </fold><fold text='' expand='false'>System.out.</fold>println("Hi <fold text='$' expand='false'>" + </fold><fold text='it' expand='false'>nickname</fold><fold text='' expand='false'> + "</fold>, welcome back")<fold text=' }' expand='false'>;
        }</fold></fold>
        <fold text='' expand='false'>if (</fold>email<fold text='?.let ' expand='false'> != null) </fold><fold text='{ ' expand='false'><fold text='{...}' expand='true'>{
            </fold>contact.<fold text='b' expand='false'>setB</fold>ackupEmail<fold text=' = ' expand='false'>(</fold><fold text='it' expand='false'>email</fold>.trim()<fold text='' expand='false'>)</fold><fold text=' }' expand='false'>;
        }</fold></fold>
        <fold text='' expand='false'>if (</fold>defaultEmail<fold text='?.let ' expand='false'> != null) </fold><fold text='{ ' expand='false'><fold text='{...}' expand='true'>{
            </fold>contact.<fold text='e' expand='false'>setE</fold>mail<fold text=' = ' expand='false'>(</fold><fold text='it' expand='false'>defaultEmail</fold><fold text='' expand='false'>)</fold><fold text=' }' expand='false'>;
        }</fold></fold>
    }</fold>

    public void notFolded(Contact contact, String email, String phone) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>email != null<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            contact.<fold text='e' expand='false'>setE</fold>mail<fold text=' = ' expand='false'>(</fold>email<fold text='' expand='false'>)</fold>;
        }</fold> else <fold text='{...}' expand='true'>{
            contact.<fold text='e' expand='false'>setE</fold>mail<fold text=' = ' expand='false'>(</fold>"unknown"<fold text='' expand='false'>)</fold>;
        }</fold>
        if (email != null) <fold text='{...}' expand='true'>{
            contact.<fold text='e' expand='false'>setE</fold>mail<fold text=' = ' expand='false'>(</fold>email<fold text='' expand='false'>)</fold>;
            contact.<fold text='p' expand='false'>setP</fold>hone<fold text=' = ' expand='false'>(</fold>phone<fold text='' expand='false'>)</fold>;
        }</fold>
        if <fold text='' expand='false'>(</fold>email != null<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            email = email.trim();
        }</fold>
        if <fold text='' expand='false'>(</fold>email != null<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            contact.clear();
        }</fold>
        if (contact != null) <fold text='{...}' expand='true'>{
            contact<fold text='?.' expand='false'>.</fold><fold text='e' expand='false'>setE</fold>mail<fold text=' = ' expand='false'>(</fold>email<fold text='' expand='false'>)</fold>;<fold text='' expand='false'>
        }</fold></fold>
        if <fold text='' expand='false'>(</fold>email == null<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            contact.<fold text='e' expand='false'>setE</fold>mail<fold text=' = ' expand='false'>(</fold>phone<fold text='' expand='false'>)</fold>;
        }</fold>
        if <fold text='' expand='false'>(</fold>email != null<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            // keep the old phone
            contact.<fold text='e' expand='false'>setE</fold>mail<fold text=' = ' expand='false'>(</fold>email<fold text='' expand='false'>)</fold>;
        }</fold>
    }</fold>

    public byte[] ternary(String body) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>byte[]</fold> payload = body<fold text='?.let { ' expand='false'> != null ? </fold>toBytes(<fold text='it' expand='false'>body</fold>)<fold text=' }' expand='false'> : null</fold>;
        <fold text='val' expand='false'>String</fold> trimmed = <fold text='' expand='false'>body != null ? </fold>body<fold text='?.' expand='false'>.</fold>trim()<fold text='' expand='false'> : null</fold>;
        <fold text='val' expand='false'>byte[]</fold> fallback = body != null ? toBytes(body) : new byte[0];
        return payload;
    }</fold>

    private static byte[] toBytes(String text)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>text.<fold text='b' expand='false'>getB</fold>ytes<fold text='' expand='false'>()</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    static class Contact <fold text='{...}' expand='true'>{
        <fold text='@Setter p' expand='false'>p</fold>rivate String email;
        <fold text='@Setter p' expand='false'>p</fold>rivate String backupEmail;
        <fold text='@Setter p' expand='false'>p</fold>rivate String phone;
        <fold text='@Getter p' expand='false'>p</fold>rivate String nickname;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>public void setEmail(String email)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>this.email = <fold text='<<' expand='false'>email</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public void setBackupEmail(String backupEmail)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>this.backupEmail = <fold text='<<' expand='false'>backupEmail</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public void setPhone(String phone)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>this.phone = <fold text='<<' expand='false'>phone</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public String getNickname()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>nickname<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>

        public void clear() <fold text='{...}' expand='true'>{
            email = null;
            phone = null;
        }</fold>
    }</fold>
}
