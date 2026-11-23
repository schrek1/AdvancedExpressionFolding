package data;

public class ToBuilderCopyTestData {

    public void copies(Person person, Person source) {
        var renamed = person.toBuilder()
                .name("Alice")
                .age(31)
                .build();
        Person older = person.toBuilder().age(person.getAge() + 1).build();
        Person mixed = person.toBuilder().name("Bob")
                .age(40).build();
        Person mapped = person.toBuilder()
                .name(source.getName())
                .build();
        Person titled = person.toBuilder().name("Dr. " + person.getName()).build();
        Person commented = person.toBuilder()
                // a comment keeps the chain on its lines
                .name("Carol")
                .build();
        Person twoArguments = person.toBuilder()
                .name("Grace")
                .age(29).build();
        Person oneLine = person.toBuilder().name("Ivan").age(33).build();
    }

    public void notFolded(Person person) {
        Person.PersonBuilder unfinished = person.toBuilder().name("Alice");
        Person unchanged = person.toBuilder().build();
        Person greeted = person.toBuilder().greet().name("Alice").build();
        Person shouted = person.toBuilder().nameUpperCase("alice").build();
        Person fresh = Person.builder().name("Alice").build();
        String text = person.toBuilder().name("Alice").toString();
    }

    public void setPrefixed(Order order) {
        var shipped = order.toBuilder()
                .setStatus(Status.SHIPPED)
                .setNote("left at the door")
                .build();
        Order cancelled = order.toBuilder().setStatus(Status.CANCELLED).setNote("out of stock").build();
    }

    public void setPrefixNotFolded(Order order) {
        Order mixed = order.toBuilder().setStatus(Status.SHIPPED).note("gift").build();
        Order tracked = order.toBuilder().setTracking("T-1").build();
    }

    static class Person {
        private final String name;
        private final int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public static PersonBuilder builder() {
            return new PersonBuilder();
        }

        public PersonBuilder toBuilder() {
            return new PersonBuilder().name(name).age(age);
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        static class PersonBuilder {
            private String name;
            private int age;

            public PersonBuilder name(String name) {
                this.name = name;
                return this;
            }

            public PersonBuilder age(int age) {
                this.age = age;
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
                return new Person(name, age);
            }
        }
    }

    enum Status {
        NEW, SHIPPED, CANCELLED
    }

    static class Order {
        private final Status status;
        private final String note;

        Order(Status status, String note) {
            this.status = status;
            this.note = note;
        }

        public OrderBuilder toBuilder() {
            return new OrderBuilder().setStatus(status).setNote(note);
        }

        static class OrderBuilder {
            private Status status;
            private String note;

            public OrderBuilder setStatus(Status status) {
                this.status = status;
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

            public OrderBuilder setTracking(String tracking) {
                return this;
            }

            public Order build() {
                return new Order(status, note);
            }
        }
    }
}
