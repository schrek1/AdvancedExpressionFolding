package data;

public class BuilderNamedArgumentsTestData {

    public void builds(Person source) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>var</fold> alice = Person<fold text='(' expand='false'>.builder()</fold>
            <fold text='' expand='false'>    .</fold>name<fold text=' = ' expand='false'>(</fold>"Alice"<fold text=',' expand='false'>)</fold>
            <fold text='' expand='false'>    .</fold>age<fold text=' = ' expand='false'>(</fold>31<fold text='' expand='false'>)</fold>
        <fold text='' expand='false'>        .build(</fold>);
        <fold text='val' expand='false'>Person</fold> bob = Person<fold text='(' expand='false'>.builder().</fold>name<fold text=' = ' expand='false'>(</fold>"Bob")<fold text='' expand='false'>.build()</fold>;
        <fold text='val' expand='false'>Person</fold> mixed = Person<fold text='(' expand='false'>.builder()</fold><fold text='' expand='false'>.</fold>name<fold text=' = ' expand='false'>(</fold>"Carol"<fold text=',' expand='false'>)</fold>
            <fold text='' expand='false'>    .</fold>age<fold text=' = ' expand='false'>(</fold>40)<fold text='' expand='false'>.build()</fold>;
        <fold text='val' expand='false'>Person</fold> mapped = Person<fold text='(' expand='false'>.builder()
                .</fold>name<fold text=' = ' expand='false'>(</fold>source<fold text='<<' expand='false'>.getName()</fold>)<fold text='' expand='false'>
                .build()</fold>;
        <fold text='val' expand='false'>Person</fold> titled = Person.builder().name("Dr. <fold text='${' expand='false'>" + </fold>source.<fold text='name}"' expand='false'>getName()</fold>).build();
        <fold text='val' expand='false'>Person</fold> commented = Person<fold text='(' expand='false'>.builder()</fold>
                // a comment keeps the chain on its lines
            <fold text='' expand='false'>    .</fold>name<fold text=' = ' expand='false'>(</fold>"Dave"<fold text='' expand='false'>)</fold>
        <fold text='' expand='false'>        .build(</fold>);
        <fold text='val' expand='false'>Person</fold> managed = Person<fold text='(' expand='false'>.builder()</fold>
            <fold text='' expand='false'>    .</fold>name<fold text=' = ' expand='false'>(</fold>"Eve"<fold text=',' expand='false'>)</fold>
            <fold text='' expand='false'>    .</fold>manager<fold text=' = ' expand='false'>(</fold>Person<fold text='(' expand='false'>.builder().</fold>name<fold text=' = ' expand='false'>(</fold>"Frank")<fold text='' expand='false'>.build()</fold><fold text='' expand='false'>)</fold>
        <fold text='' expand='false'>        .build(</fold>);
    }</fold>

    public void notFolded(Person person) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>Person.PersonBuilder</fold> unfinished = Person.builder().name("Alice");
        <fold text='val' expand='false'>Person</fold> empty = Person.builder().build();
        <fold text='val' expand='false'>Person</fold> greeted = Person.builder().greet().name("Alice").build();
        <fold text='val' expand='false'>Person</fold> shouted = Person.builder().nameUpperCase("alice").build();
        <fold text='val' expand='false'>Person</fold> copied = person.<fold text='copy(' expand='false'>toBuilder().</fold>name<fold text=' = ' expand='false'>(</fold>"Alice")<fold text='' expand='false'>.build()</fold>;
        <fold text='val' expand='false'>String</fold> text = Person.builder().name("Alice").toString();
    }</fold>

    <fold text='@HasBuilder @RequiredArgsConstructor(default) s' expand='false'>s</fold>tatic class Person <fold text='{...}' expand='true'>{
        <fold text='@Getter p' expand='false'>p</fold>rivate final String name;
        private final int age;
        private final Person manager;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>Person(String name, int age, Person manager) <fold text='{...}' expand='true'>{
            this.name = <fold text='<<' expand='false'>name</fold>;
            this.age = <fold text='<<' expand='false'>age</fold>;
            this.manager = <fold text='<<' expand='false'>manager</fold>;
        }</fold></fold>

        public static PersonBuilder builder()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>new PersonBuilder()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        public PersonBuilder toBuilder()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>new PersonBuilder().name(name).age(age).manager(manager)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public String getName()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>name<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>

        static class PersonBuilder <fold text='{...}' expand='true'>{
            private String name;
            private int age;
            private Person manager;

            public PersonBuilder name(String name) <fold text='{...}' expand='true'>{
                this.name = <fold text='<<' expand='false'>name</fold>;
                return this;
            }</fold>

            public PersonBuilder age(int age) <fold text='{...}' expand='true'>{
                this.age = <fold text='<<' expand='false'>age</fold>;
                return this;
            }</fold>

            public PersonBuilder manager(Person manager) <fold text='{...}' expand='true'>{
                this.manager = <fold text='<<' expand='false'>manager</fold>;
                return this;
            }</fold>

            public PersonBuilder nameUpperCase(String name) <fold text='{...}' expand='true'>{
                this.name = name.toUpperCase();
                return this;
            }</fold>

            public PersonBuilder greet()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
                </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>this<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
            </fold>}</fold>

            public Person build()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
                </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>new Person(name, age, manager)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
            </fold>}</fold>
        }</fold>
    }</fold>
}
