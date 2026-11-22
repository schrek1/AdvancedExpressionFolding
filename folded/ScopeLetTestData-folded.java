package data;

public class ScopeLetTestData {

    private String defaultEmail;

    public void folded(Contact contact, String email, String phone) {
        email?.let { contact.email = it }
        phone?.let { contact.phone = it }
        String nickname = contact.nickname;
        nickname?.let { System.out.println("Hi " + it + ", welcome back") }
        email?.let { contact.backupEmail = it.trim() }
        defaultEmail?.let { contact.email = it }
    }

    public void notFolded(Contact contact, String email, String phone) {
        if (email != null) {
            contact.email = email;
        } else {
            contact.email = "unknown";
        }
        if (email != null) {
            contact.email = email;
            contact.phone = phone;
        }
        if (email != null) {
            email = email.trim();
        }
        if (email != null) {
            contact.clear();
        }
        if (contact != null) {
            contact.email = email;
        }
        if (email == null) {
            contact.email = phone;
        }
        if (email != null) {
            // keep the old phone
            contact.email = email;
        }
    }

    public byte[] ternary(String body) {
        byte[] payload = body?.let { toBytes(it) };
        String trimmed = body != null ? body.trim() : null;
        byte[] fallback = body != null ? toBytes(body) : new byte[0];
        return payload;
    }

    private static byte[] toBytes(String text) {
        return text.bytes;
    }

    static class Contact {
        private String email;
        private String backupEmail;
        private String phone;
        private String nickname;

        public void setEmail(String email) {
            this.email = email;
        }

        public void setBackupEmail(String backupEmail) {
            this.backupEmail = backupEmail;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickname() {
            return nickname;
        }

        public void clear() {
            email = null;
            phone = null;
        }
    }
}
