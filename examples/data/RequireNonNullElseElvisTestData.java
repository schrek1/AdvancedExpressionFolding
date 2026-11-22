package data;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNullElse;
import static java.util.Objects.requireNonNullElseGet;

public class RequireNonNullElseElvisTestData {

    private String nickname;
    private Theme theme;

    public String displayName(String name) {
        String shown = Objects.requireNonNullElse(name, "anonymous");
        return shown;
    }

    public Theme themeOf(Order order) {
        return Objects.requireNonNullElseGet(order.getTheme(), Theme::new);
    }

    public String guestName(String name, int id) {
        return Objects.requireNonNullElseGet(name, () -> "guest-" + id);
    }

    public List<String> tagsOf(Patch patch) {
        return Objects.requireNonNullElse(patch.getTags().get(), List.of());
    }

    public void methodReferences(String name) {
        String own = Objects.requireNonNullElseGet(name, this::defaultName);
        Theme fallback = Objects.requireNonNullElseGet(theme, Theme::standard);
        String trimmed = Objects.requireNonNullElseGet(name, nickname::trim);
        print(own + fallback + trimmed);
    }

    public void staticImports(String name) {
        String shown = requireNonNullElse(name, "anonymous");
        Theme current = requireNonNullElseGet(theme, Theme::new);
        print(shown + current);
    }

    public void notFolded(String name, Supplier<String> supplier) {
        String fromVariable = Objects.requireNonNullElseGet(name, supplier);
        String fromBlock = Objects.requireNonNullElseGet(name, () -> {
            return "block";
        });
        int length = Objects.requireNonNullElse(name, "").length();
        print(fromVariable + fromBlock + length);
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
