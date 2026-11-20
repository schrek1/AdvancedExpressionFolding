package data;

import java.util.ArrayList;
import java.util.List;

public class SingletonObjectTestData {

    static object Registry {
    }

    public static final object Inventory {

        private final List<String> items = new ArrayList<>();

        public void add(String item) {
            items.add(item);
        }
    }

    // Not folded: lazy initialization
    static class LazyCatalog {
        private static LazyCatalog instance;

        private LazyCatalog() {}

        static LazyCatalog getInstance() {
            if (instance == null) {
                instance = new LazyCatalog();
            }
            return instance;
        }
    }

    // Not folded: no getter
    static class PublicInstance {
        public static final PublicInstance INSTANCE = new PublicInstance();

        private PublicInstance() {}
    }

    // Not folded: the constructor does work
    static class Clock {
        private static final Clock INSTANCE = new Clock();

        private final long start;

        private Clock() {
            start = System.nanoTime();
        }

        static Clock get() {
            return INSTANCE;
        }
    }

    // Not folded: the constructor is public
    static class Config {
        private static final Config DEFAULT = new Config();

        public Config() {}

        static Config defaults() {
            return DEFAULT;
        }
    }

    // Not folded: utility class without an instance
    static final class Prices {
        private Prices() {
            throw new UnsupportedOperationException();
        }

        static int twice(int value) {
            return value * 2;
        }
    }
}
