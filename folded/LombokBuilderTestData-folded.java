package data;

public class LombokBuilderTestData {

    @Builder @RequiredArgsConstructor(default) static class Person {
        private final String name;
        private final int age;
    }

    @Builder @AllArgsConstructor(default) public static class Order {
        private String number;
        private long amount;
    }

    @HasBuilder(Builder) @RequiredArgsConstructor(default) static class ValidatedStock {
        private final String sku;
        private final int quantity;

        static Builder builder() {
            return new Builder();
        }

        static class Builder {
            private String sku;
            private int quantity;

            Builder sku(String sku) {
                this.sku = sku;
                return this;
            }

            Builder quantity(int quantity) {
                this.quantity = quantity;
                return this;
            }

            ValidatedStock build() {
                if (quantity < 0) {
                    throw new IllegalStateException("negative quantity");
                }
                return new ValidatedStock(sku, quantity);
            }
        }
    }

    @HasBuilder(Builder) @RequiredArgsConstructor(default) static class DefaultedStock {
        private final String sku;
        private final int quantity;

        static Builder builder() {
            return new Builder();
        }

        static class Builder {
            private String sku;
            private int quantity = 1;

            Builder sku(String sku) {
                this.sku = sku;
                return this;
            }

            Builder quantity(int quantity) {
                this.quantity = quantity;
                return this;
            }

            DefaultedStock build() {
                return new DefaultedStock(sku, quantity);
            }
        }
    }

    @HasBuilder(Builder) @RequiredArgsConstructor(default) static class Shop {
        private final String name;
        private final String city;

        static Builder builder() {
            return new Builder();
        }

        static class Builder {
            private String name;
            private String city;

            Builder name(String name) {
                this.name = name;
                return this;
            }

            Builder city(String city) {
                this.city = city;
                return this;
            }

            Builder inPrague() {
                this.city = "Prague";
                return this;
            }

            Shop build() {
                return new Shop(name, city);
            }
        }
    }

    @HasBuilder(Builder) @RequiredArgsConstructor(default) static class SwappedShop {
        private final String name;
        private final String city;

        static Builder builder() {
            return new Builder();
        }

        static class Builder {
            private String name;
            private String city;

            Builder name(String name) {
                this.name = name;
                return this;
            }

            Builder city(String city) {
                this.city = city;
                return this;
            }

            SwappedShop build() {
                return new SwappedShop(city, name);
            }
        }
    }
}
