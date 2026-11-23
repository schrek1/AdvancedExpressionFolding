package data;

public class SetterNamedArgumentsTestData {

    public void folded(Person source) {
        val alice = Person(
        name = "Alice",
        age = 31,
        active = true

        val bob = Person(name = "Bob");

        val copy = Person(
        name = source.name,
        age = source.age + 1
        System.out.println(copy);

        val partial = Person(name = "Carol");
        partial.age = partial.age + 1;

        val renamed = Person(name = "Heidi");
        renamed.name = "Ivan";
    }

    public void notFolded(Person source) {
        val withArguments = new Person("Dave");
        withArguments.age = 40;

        val commented = new Person();
        // a comment keeps the setters as they are
        commented.name = "Eve";

        val other = new Person();
        source.name = "Frank";

        val anonymous = new Person() {
        };
        anonymous.name = "Grace";

        val empty = new Person();
        System.out.println(empty);
    }

    static class Person {
        private String name;
        private int age;
        private boolean active;

        Person() {
        }

        Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }
    }
}
