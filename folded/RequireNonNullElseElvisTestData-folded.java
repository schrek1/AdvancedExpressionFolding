package data;

import java.util.Objects;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNullElse;
import static java.util.Objects.requireNonNullElseGet;

public class RequireNonNullElseElvisTestData {

    private String nickname;
    private Theme theme;

    public String displayName(String name) {
        String shown = name ?: "anonymous";
        return shown;
    }

    public Theme themeOf(Order order) {
        return order.getTheme() ?: Theme();
    }

    public String guestName(String name, int id) {
        return name ?: "guest-" + id;
    }

    public String labelOf(String name, int id) {
        return id + "-label" ?: name;
    }

    public List<String> tagsOf(Patch patch) {
        return patch.getTags().get() ?: List.of();
    }

    public void methodReferences(String name) {
        String own = name ?: defaultName();
        Theme fallback = theme ?: Theme.standard();
        String trimmed = name ?: nickname.trim();
        print(own + fallback + trimmed);
    }

    public void staticImports(String name) {
        String shown = name ?: "anonymous";
        Theme current = theme ?: Theme();
        print(shown + current);
    }

    public void receivers(String name) {
        int length = (name ?: "").length();
        String fromBlock = name ?: "block";
        print(length + fromBlock);
    }

    public void notFolded(String name, Supplier<String> supplier) {
        String fromVariable = Objects.requireNonNullElseGet(name, supplier);
        String fromLongBlock = Objects.requireNonNullElseGet(name, () -> {
            String generated = "block";
            return generated;
        });
        int length = Objects.requireNonNullElse(name, "").trim().length();
        print(fromVariable + fromLongBlock + length);
    }

    private String defaultName() {
        return "nobody";
    }

    private void print(Object value) {
        System.out.println(value);
    }

    static class Theme {
        static Theme standard() {
            return new Theme();
        }
    }

    static class Order {
        private Theme theme;

        Theme getTheme() {
            return theme;
        }
    }

    static class Patch {
        private Maybe<List<String>> tags;

        Maybe<List<String>> getTags() {
            return tags;
        }
    }

    static class Maybe<T> {
        private T value;

        T get() {
            return value;
        }
    }
}
