package data;

@SuppressWarnings("unused")
public class TodoTestData {

    public void export() {
        TODO("Not implemented");
    }

    public void exportWithoutReason() {
        TODO();
    }

    public String render(String format) {
        if (format.isEmpty()) {
            return "";
        }
        TODO("Format not supported: " + format);
    }

    public void qualified() {
        TODO("Qualified name");
    }

    public void withCause(Exception cause) {
        throw new UnsupportedOperationException(cause);
    }

    public void withReasonAndCause(Exception cause) {
        throw new UnsupportedOperationException("Not implemented", cause);
    }

    public void otherException() {
        throw new IllegalStateException("Not implemented");
    }

    public void subclass() {
        throw new ReadOnlyException("Read only");
    }

    public void anonymousSubclass() {
        throw new UnsupportedOperationException("Anonymous") {
        };
    }

    public void rethrow(UnsupportedOperationException existing) {
        throw existing;
    }

    public void guard(int size) {
        if (size > 10) {
            TODO("Too large");
        }
    }

    static class ReadOnlyException extends UnsupportedOperationException {
        ReadOnlyException(String message) {
            super(message);
        }
    }
}
