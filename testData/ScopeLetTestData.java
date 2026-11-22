package data;

public class ScopeLetTestData {

    private String defaultEmail;

    public void folded(Contact contact, String email, String phone) <fold text='{...}' expand='true'>{
        <fold text='' expand='false'>if (</fold>email<fold text='?.let ' expand='false'> != null) </fold><fold text='{ ' expand='false'><fold text='{...}' expand='true'>{
            </fold>contact.<fold text='e' expand='false'>setE</fold>mail<fold text=' = ' expand='false'>(</fold><fold text='it' expand='false'>email</fold><fold text='' expand='false'>)</fold><fold text=' }' expand='false'>;
        }</fold></fold>
        <fold text='' expand='false'>if (</fold>phone<fold text='?.let { ' expand='false'> != null) </fold>contact.<fold text='p' expand='false'>setP</fold>hone<fold text=' = ' expand='false'>(</fold><fold text='it' expand='false'>phone</fold><fold text='' expand='false'>)</fold><fold text=' }' expand='false'>;</fold>
        String nickname = contact.<fold text='n' expand='false'>getN</fold>ickname<fold text='' expand='false'>()</fold>;
        <fold text='' expand='false'>if (</fold>nickname<fold text='?.let ' expand='false'> != null) </fold><fold text='{ ' expand='false'><fold text='{...}' expand='true'>{
            </fold>System.out.println("Hi " + <fold text='it' expand='false'>nickname</fold> + ", welcome back")<fold text=' }' expand='false'>;
        }</fold></fold>
        <fold text='' expand='false'>if (</fold>email<fold text='?.let ' expand='false'> != null) </fold><fold text='{ ' expand='false'><fold text='{...}' expand='true'>{
            </fold>contact.<fold text='b' expand='false'>setB</fold>ackupEmail<fold text=' = ' expand='false'>(</fold><fold text='it' expand='false'>email</fold>.trim()<fold text='' expand='false'>)</fold><fold text=' }' expand='false'>;
        }</fold></fold>
        <fold text='' expand='false'>if (</fold>defaultEmail<fold text='?.let ' expand='false'> != null) </fold><fold text='{ ' expand='false'><fold text='{...}' expand='true'>{
            </fold>contact.<fold text='e' expand='false'>setE</fold>mail<fold text=' = ' expand='false'>(</fold><fold text='it' expand='false'>defaultEmail</fold><fold text='' expand='false'>)</fold><fold text=' }' expand='false'>;
        }</fold></fold>
    }</fold>

    public void notFolded(Contact contact, String email, String phone) <fold text='{...}' expand='true'>{
        if (email != null) <fold text='{...}' expand='true'>{
            contact.<fold text='e' expand='false'>setE</fold>mail<fold text=' = ' expand='false'>(</fold>email<fold text='' expand='false'>)</fold>;
        }</fold> else <fold text='{...}' expand='true'>{
            contact.<fold text='e' expand='false'>setE</fold>mail<fold text=' = ' expand='false'>(</fold>"unknown"<fold text='' expand='false'>)</fold>;
        }</fold>
        if (email != null) <fold text='{...}' expand='true'>{
            contact.<fold text='e' expand='false'>setE</fold>mail<fold text=' = ' expand='false'>(</fold>email<fold text='' expand='false'>)</fold>;
            contact.<fold text='p' expand='false'>setP</fold>hone<fold text=' = ' expand='false'>(</fold>phone<fold text='' expand='false'>)</fold>;
        }</fold>
        if (email != null) <fold text='{...}' expand='true'>{
            email = email.trim();
        }</fold>
        if (email != null) <fold text='{...}' expand='true'>{
            contact.clear();
        }</fold>
        if (contact != null) <fold text='{...}' expand='true'>{
            contact.<fold text='e' expand='false'>setE</fold>mail<fold text=' = ' expand='false'>(</fold>email<fold text='' expand='false'>)</fold>;
        }</fold>
        if (email == null) <fold text='{...}' expand='true'>{
            contact.<fold text='e' expand='false'>setE</fold>mail<fold text=' = ' expand='false'>(</fold>phone<fold text='' expand='false'>)</fold>;
        }</fold>
        if (email != null) <fold text='{...}' expand='true'>{
            // keep the old phone
            contact.<fold text='e' expand='false'>setE</fold>mail<fold text=' = ' expand='false'>(</fold>email<fold text='' expand='false'>)</fold>;
        }</fold>
    }</fold>

    public byte[] ternary(String body) <fold text='{...}' expand='true'>{
        byte[] payload = body<fold text='?.let { ' expand='false'> != null ? </fold>toBytes(<fold text='it' expand='false'>body</fold>)<fold text=' }' expand='false'> : null</fold>;
        String trimmed = body != null ? body.trim() : null;
        byte[] fallback = body != null ? toBytes(body) : new byte[0];
        return payload;
    }</fold>

    private static byte[] toBytes(String text)<fold text=' { ' expand='false'> {
        </fold>return text.<fold text='b' expand='false'>getB</fold>ytes<fold text='' expand='false'>()</fold>;<fold text=' }' expand='false'>
    }</fold>

    static class Contact <fold text='{...}' expand='true'>{
        private String email;
        private String backupEmail;
        private String phone;
        private String nickname;

        public void setEmail(String email)<fold text=' { ' expand='false'> {
            </fold>this.email = email;<fold text=' }' expand='false'>
        }</fold>

        public void setBackupEmail(String backupEmail)<fold text=' { ' expand='false'> {
            </fold>this.backupEmail = backupEmail;<fold text=' }' expand='false'>
        }</fold>

        public void setPhone(String phone)<fold text=' { ' expand='false'> {
            </fold>this.phone = phone;<fold text=' }' expand='false'>
        }</fold>

        public String getNickname()<fold text=' { ' expand='false'> {
            </fold>return nickname;<fold text=' }' expand='false'>
        }</fold>

        public void clear() <fold text='{...}' expand='true'>{
            email = null;
            phone = null;
        }</fold>
    }</fold>
}
