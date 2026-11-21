package data;

public class LombokBuilderTestData {

    static class Person {
        private final String name;
        private final int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        static Builder builder() {
            return new Builder();
        }

        static class Builder {
            private String name;
            private int age;

            Builder name(String name) {
                this.name = name;
                return this;
            }

            Builder age(int age) {
                this.age = age;
                return this;
            }

            Person build() {
                return new Person(name, age);
            }
        }
    }

    public static class Order {
        private String number;
        private long amount;

        Order(String number, long amount) {
            this.number = number;
            this.amount = amount;
        }

        public static OrderBuilder builder() {
            return new OrderBuilder();
        }

        public static class OrderBuilder {
            private String number;
            private long amount;

            OrderBuilder() {
            }

            public OrderBuilder number(String number) {
                this.number = number;
                return this;
            }

            public OrderBuilder amount(long amount) {
                this.amount = amount;
                return this;
            }

            public Order build() {
                return new Order(this.number, this.amount);
            }
        }
    }

    static class ValidatedStock {
        private final String sku;
        private final int quantity;

        ValidatedStock(String sku, int quantity) {
            this.sku = sku;
            this.quantity = quantity;
        }

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

    static class DefaultedStock {
        private final String sku;
        private final int quantity;

        DefaultedStock(String sku, int quantity) {
            this.sku = sku;
            this.quantity = quantity;
        }

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

    static class Shop {
        private final String name;
        private final String city;

        Shop(String name, String city) {
            this.name = name;
            this.city = city;
        }

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

    static class SwappedShop {
        private final String name;
        private final String city;

        SwappedShop(String city, String name) {
            this.name = name;
            this.city = city;
        }

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
