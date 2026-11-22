package data;

public class SetterNamedArgumentsTestData {

    public void folded(Person source) {
        var alice = new Person();
        alice.setName("Alice");
        alice.setAge(31);
        alice.setActive(true);

        Person bob = new Person();
        bob.setName("Bob");

        var copy = new Person();
        copy.setName(source.getName());
        copy.setAge(source.getAge() + 1);
        System.out.println(copy);

        var partial = new Person();
        partial.setName("Carol");
        partial.setAge(partial.getAge() + 1);

        var renamed = new Person();
        renamed.setName("Heidi");
        renamed.setName("Ivan");
    }

    public void notFolded(Person source) {
        var withArguments = new Person("Dave");
        withArguments.setAge(40);

        var commented = new Person();
        // a comment keeps the setters as they are
        commented.setName("Eve");

        var other = new Person();
        source.setName("Frank");

        var anonymous = new Person() {
        };
        anonymous.setName("Grace");

        var empty = new Person();
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
