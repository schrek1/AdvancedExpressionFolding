package data;

public class ScopeLetTestData {

    private String defaultEmail;

    public void folded(Contact contact, String email, String phone) {
        if (email != null) {
            contact.setEmail(email);
        }
        if (phone != null) contact.setPhone(phone);
        String nickname = contact.getNickname();
        if (nickname != null) {
            System.out.println("Hi " + nickname + ", welcome back");
        }
        if (email != null) {
            contact.setBackupEmail(email.trim());
        }
        if (defaultEmail != null) {
            contact.setEmail(defaultEmail);
        }
    }

    public void notFolded(Contact contact, String email, String phone) {
        if (email != null) {
            contact.setEmail(email);
        } else {
            contact.setEmail("unknown");
        }
        if (email != null) {
            contact.setEmail(email);
            contact.setPhone(phone);
        }
        if (email != null) {
            email = email.trim();
        }
        if (email != null) {
            contact.clear();
        }
        if (contact != null) {
            contact.setEmail(email);
        }
        if (email == null) {
            contact.setEmail(phone);
        }
        if (email != null) {
            // keep the old phone
            contact.setEmail(email);
        }
    }

    public byte[] ternary(String body) {
        byte[] payload = body != null ? toBytes(body) : null;
        String trimmed = body != null ? body.trim() : null;
        byte[] fallback = body != null ? toBytes(body) : new byte[0];
        return payload;
    }

    private static byte[] toBytes(String text) {
        return text.getBytes();
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
