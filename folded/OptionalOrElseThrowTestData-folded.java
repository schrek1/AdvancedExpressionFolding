package data;

import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings("ALL")
public class OptionalOrElseThrowTestData {

    public Shape byCode(String code) {
        return Shape.findByCode(code)
                ?: throw IllegalArgumentException("Unsupported code $code");
    }

    public Shape byId(long id) {
        return repository.findById(id)
                ?: error("Missing shape $id");
    }

    public Shape byKey(String key) {
        return lookup(key) ?: throw NotFoundException();
    }

    public Shape plainMessage(String key) {
        return lookup(key) ?: error("No shape");
    }

    public Shape withCause(String key, Exception cause) {
        return lookup(key) ?: throw IllegalStateException("No shape $key", cause);
    }

    public Shape stateWithoutMessage(String key) {
        return lookup(key) ?: throw IllegalStateException();
    }

    public Shape stateConstructorReference(String key) {
        return lookup(key) ?: throw IllegalStateException();
    }

    public Shape noArgument(String key) {
        return lookup(key) ?: throw NotFoundException();
    }

    public Shape twoArguments(String key) {
        return lookup(key) ?: throw NotFoundException("shape", key);
    }

    public Shape stillAssertion(String key) {
        return lookup(key)!!;
    }

    public Shape singleReturnBlock(String key) {
        return lookup(key) ?: throw NotFoundException();
    }

    public Shape singleReturnBlockState(String key) {
        return lookup(key)
                ?: error("No shape $key");
    }

    public Shape multiStatementBlock(String key) {
        return lookup(key).orElseThrow(() -> {
            System.out.println("missing $key");
            return new NotFoundException();
        });
    }

    public Shape commentedBlock(String key) {
        return lookup(key).orElseThrow(() -> {
            // lookups by key are case-sensitive
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
            super("$kind $key");
        }

        static NotFoundException forKey() {
            return new NotFoundException();
        }
    }
}
