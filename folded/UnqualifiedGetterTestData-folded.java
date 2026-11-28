package data;

public class UnqualifiedGetterTestData {
    private String request = "code=42";

    private String getCodeFromRequest() {
        return request.substring(5);
    }

    private boolean isReady() {
        return !request.empty;
    }

    public String own() {
        String code = getCodeFromRequest();
        boolean ready = isReady();
        return code + ready + this.codeFromRequest + new UnqualifiedGetterTestData().codeFromRequest;
    }
}
