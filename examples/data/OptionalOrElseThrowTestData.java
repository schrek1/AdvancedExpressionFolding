package data;

import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings("ALL")
public class OptionalOrElseThrowTestData {

    public Shape byCode(String code) {
        return Shape.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Unsupported code " + code));
    }

    public Shape byId(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Missing shape " + id));
    }

    public Shape byKey(String key) {
        return lookup(key).orElseThrow(NotFoundException::new);
    }

    public Shape plainMessage(String key) {
        return lookup(key).orElseThrow(() -> new IllegalStateException("No shape"));
    }

    public Shape withCause(String key, Exception cause) {
        return lookup(key).orElseThrow(() -> new IllegalStateException("No shape " + key, cause));
    }

    public Shape stateWithoutMessage(String key) {
        return lookup(key).orElseThrow(() -> new IllegalStateException());
    }

    public Shape stateConstructorReference(String key) {
        return lookup(key).orElseThrow(IllegalStateException::new);
    }

    public Shape noArgument(String key) {
        return lookup(key).orElseThrow(() -> new NotFoundException());
    }

    public Shape twoArguments(String key) {
        return lookup(key).orElseThrow(() -> new NotFoundException("shape", key));
    }

    public Shape stillAssertion(String key) {
        return lookup(key).orElseThrow();
    }

    public Shape blockLambda(String key) {
        return lookup(key).orElseThrow(() -> {
            return new NotFoundException();
        });
    }

    public Shape supplierVariable(String key, Supplier<NotFoundException> notFound) {
        return lookup(key).orElseThrow(notFound);
    }

    public Shape factoryMethod(String key) {
        return lookup(key).orElseThrow(NotFoundException::forKey);
    }

    private Optional<Shape> lookup(String key) {
        return Optional.empty();
    }

    private final Repository repository = new Repository();

    static class Repository {
        Optional<Shape> findById(long id) {
            return Optional.empty();
        }
    }

    static class Shape {
        static Optional<Shape> findByCode(String code) {
            return Optional.empty();
        }
    }

    static class NotFoundException extends RuntimeException {
        NotFoundException() {
        }

        NotFoundException(String kind, String key) {
            super(kind + " " + key);
        }

        static NotFoundException forKey() {
            return new NotFoundException();
        }
    }
}
