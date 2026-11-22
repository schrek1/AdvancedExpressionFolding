package data;

public class BuilderNamedArgumentsTestData {

    public void builds(Person source) {
        var alice = Person(
            name = "Alice",
            age = 31
        );
        Person bob = Person(name = "Bob");
        Person mixed = Person(name = "Carol",
            age = 40);
        Person mapped = Person(name = source.getName());
        Person titled = Person(name = "Dr. " + source.getName());
        Person commented = Person(
                // a comment keeps the chain on its lines
            name = "Dave"
        );
        Person managed = Person(
            name = "Eve",
            manager = Person(name = "Frank")
        );
    }

    public void notFolded(Person person) {
        Person.PersonBuilder unfinished = Person.builder().name("Alice");
        Person empty = Person.builder().build();
        Person greeted = Person.builder().greet().name("Alice").build();
        Person shouted = Person.builder().nameUpperCase("alice").build();
        Person copied = person.toBuilder().name("Alice").build();
        String text = Person.builder().name("Alice").toString();
    }

    public void setPrefixed() {
        var order = Order.builder()
                .setId(7)
                .setPayment(Payment.builder().setFixedInfo("card").build())
                .build();
        Order gift = Order.builder().setNote("gift").build();
    }

    public void setPrefixNotFolded() {
        Order mixed = Order.builder().setId(7).note("gift").build();
        Order tracked = Order.builder().setTracking("T-1").build();
    }

    static class Person {
        private final String name;
        private final int age;
        private final Person manager;

        Person(String name, int age, Person manager) {
            this.name = name;
            this.age = age;
            this.manager = manager;
        }

        public static PersonBuilder builder() {
            return new PersonBuilder();
        }

        public PersonBuilder toBuilder() {
            return new PersonBuilder().name(name).age(age).manager(manager);
        }

        public String getName() {
            return name;
        }

        static class PersonBuilder {
            private String name;
            private int age;
            private Person manager;

            public PersonBuilder name(String name) {
                this.name = name;
                return this;
            }

            public PersonBuilder age(int age) {
                this.age = age;
                return this;
            }

            public PersonBuilder manager(Person manager) {
                this.manager = manager;
                return this;
            }

            public PersonBuilder nameUpperCase(String name) {
                this.name = name.toUpperCase();
                return this;
            }

            public PersonBuilder greet() {
                return this;
            }

            public Person build() {
                return new Person(name, age, manager);
            }
        }
    }

    static class Order {
        private final int id;
        private final String note;
        private final Payment payment;

        Order(int id, String note, Payment payment) {
            this.id = id;
            this.note = note;
            this.payment = payment;
        }

        public static OrderBuilder builder() {
            return new OrderBuilder();
        }

        static class OrderBuilder {
            private int id;
            private String note;
            private Payment payment;

            public OrderBuilder setId(int id) {
                this.id = id;
                return this;
            }

            public OrderBuilder setNote(String note) {
                this.note = note;
                return this;
            }

            public OrderBuilder note(String note) {
                this.note = note;
                return this;
            }

            public OrderBuilder setPayment(Payment payment) {
                this.payment = payment;
                return this;
            }

            public OrderBuilder setTracking(String tracking) {
                return this;
            }

            public Order build() {
                return new Order(id, note, payment);
            }
        }
    }

    static class Payment {
        private final String fixedInfo;

        Payment(String fixedInfo) {
            this.fixedInfo = fixedInfo;
        }

        public static PaymentBuilder builder() {
            return new PaymentBuilder();
        }

        static class PaymentBuilder {
            private String fixedInfo;

            public PaymentBuilder setFixedInfo(String fixedInfo) {
                this.fixedInfo = fixedInfo;
                return this;
            }

            public Payment build() {
                return new Payment(fixedInfo);
            }
        }
    }
}
