package data;

public class BuilderNamedArgumentsTestData {

    public void builds(Person source) <fold text='{...}' expand='true'>{
        var alice = Person<fold text='(' expand='false'>.builder()</fold>
            <fold text='' expand='false'>    .</fold>name<fold text=' = ' expand='false'>(</fold>"Alice"<fold text=',' expand='false'>)</fold>
            <fold text='' expand='false'>    .</fold>age<fold text=' = ' expand='false'>(</fold>31<fold text='' expand='false'>)</fold>
        <fold text='' expand='false'>        .build(</fold>);
        Person bob = Person<fold text='(' expand='false'>.builder().</fold>name<fold text=' = ' expand='false'>(</fold>"Bob")<fold text='' expand='false'>.build()</fold>;
        Person mixed = Person<fold text='(' expand='false'>.builder()</fold><fold text='' expand='false'>.</fold>name<fold text=' = ' expand='false'>(</fold>"Carol"<fold text=',' expand='false'>)</fold>
            <fold text='' expand='false'>    .</fold>age<fold text=' = ' expand='false'>(</fold>40)<fold text='' expand='false'>.build()</fold>;
        Person mapped = Person<fold text='(' expand='false'>.builder()
                .</fold>name<fold text=' = ' expand='false'>(</fold>source.getName())<fold text='' expand='false'>
                .build()</fold>;
        Person titled = Person<fold text='(' expand='false'>.builder().</fold>name<fold text=' = ' expand='false'>(</fold>"Dr. " + source.getName())<fold text='' expand='false'>.build()</fold>;
        Person commented = Person<fold text='(' expand='false'>.builder()</fold>
                // a comment keeps the chain on its lines
            <fold text='' expand='false'>    .</fold>name<fold text=' = ' expand='false'>(</fold>"Dave"<fold text='' expand='false'>)</fold>
        <fold text='' expand='false'>        .build(</fold>);
        Person managed = Person<fold text='(' expand='false'>.builder()</fold>
            <fold text='' expand='false'>    .</fold>name<fold text=' = ' expand='false'>(</fold>"Eve"<fold text=',' expand='false'>)</fold>
            <fold text='' expand='false'>    .</fold>manager<fold text=' = ' expand='false'>(</fold>Person<fold text='(' expand='false'>.builder().</fold>name<fold text=' = ' expand='false'>(</fold>"Frank")<fold text='' expand='false'>.build()</fold><fold text='' expand='false'>)</fold>
        <fold text='' expand='false'>        .build(</fold>);
    }</fold>

    public void notFolded(Person person) <fold text='{...}' expand='true'>{
        Person.PersonBuilder unfinished = Person.builder().name("Alice");
        Person empty = Person.builder().build();
        Person greeted = Person.builder().greet().name("Alice").build();
        Person shouted = Person.builder().nameUpperCase("alice").build();
        Person copied = person.toBuilder().name("Alice").build();
        String text = Person.builder().name("Alice").toString();
    }</fold>

    static class Person <fold text='{...}' expand='true'>{
        private final String name;
        private final int age;
        private final Person manager;

        Person(String name, int age, Person manager) <fold text='{...}' expand='true'>{
            this.name = name;
            this.age = age;
            this.manager = manager;
        }</fold>

        public static PersonBuilder builder()<fold text=' { ' expand='false'> {
            </fold>return new PersonBuilder();<fold text=' }' expand='false'>
        }</fold>

        public PersonBuilder toBuilder()<fold text=' { ' expand='false'> {
            </fold>return new PersonBuilder().name(name).age(age).manager(manager);<fold text=' }' expand='false'>
        }</fold>

        public String getName()<fold text=' { ' expand='false'> {
            </fold>return name;<fold text=' }' expand='false'>
        }</fold>

        static class PersonBuilder <fold text='{...}' expand='true'>{
            private String name;
            private int age;
            private Person manager;

            public PersonBuilder name(String name) <fold text='{...}' expand='true'>{
                this.name = name;
                return this;
            }</fold>

            public PersonBuilder age(int age) <fold text='{...}' expand='true'>{
                this.age = age;
                return this;
            }</fold>

            public PersonBuilder manager(Person manager) <fold text='{...}' expand='true'>{
                this.manager = manager;
                return this;
            }</fold>

            public PersonBuilder nameUpperCase(String name) <fold text='{...}' expand='true'>{
                this.name = name.toUpperCase();
                return this;
            }</fold>

            public PersonBuilder greet()<fold text=' { ' expand='false'> {
                </fold>return this;<fold text=' }' expand='false'>
            }</fold>

            public Person build()<fold text=' { ' expand='false'> {
                </fold>return new Person(name, age, manager);<fold text=' }' expand='false'>
            }</fold>
        }</fold>
    }</fold>
}
