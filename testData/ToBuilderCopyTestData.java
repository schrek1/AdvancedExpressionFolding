package data;

public class ToBuilderCopyTestData {

    public void copies(Person person, Person source) <fold text='{...}' expand='true'>{
        var renamed = person.<fold text='copy(' expand='false'>toBuilder()</fold>
            <fold text='' expand='false'>    .</fold>name<fold text=' = ' expand='false'>(</fold>"Alice"<fold text=',' expand='false'>)</fold>
            <fold text='' expand='false'>    .</fold>age<fold text=' = ' expand='false'>(</fold>31<fold text='' expand='false'>)</fold>
        <fold text='' expand='false'>        .build(</fold>);
        Person older = person.<fold text='copy(' expand='false'>toBuilder().</fold>age<fold text=' = ' expand='false'>(</fold>person.getAge() + 1)<fold text='' expand='false'>.build()</fold>;
        Person mixed = person.<fold text='copy(' expand='false'>toBuilder()</fold><fold text='' expand='false'>.</fold>name<fold text=' = ' expand='false'>(</fold>"Bob"<fold text=',' expand='false'>)</fold>
            <fold text='' expand='false'>    .</fold>age<fold text=' = ' expand='false'>(</fold>40)<fold text='' expand='false'>.build()</fold>;
        Person mapped = person.<fold text='copy(' expand='false'>toBuilder()
                .</fold>name<fold text=' = ' expand='false'>(</fold>source.getName())<fold text='' expand='false'>
                .build()</fold>;
        Person titled = person.<fold text='copy(' expand='false'>toBuilder().</fold>name<fold text=' = ' expand='false'>(</fold>"Dr. " + person.getName())<fold text='' expand='false'>.build()</fold>;
        Person commented = person.<fold text='copy(' expand='false'>toBuilder()</fold>
                // a comment keeps the chain on its lines
            <fold text='' expand='false'>    .</fold>name<fold text=' = ' expand='false'>(</fold>"Carol"<fold text='' expand='false'>)</fold>
        <fold text='' expand='false'>        .build(</fold>);
    }</fold>

    public void notFolded(Person person) <fold text='{...}' expand='true'>{
        Person.PersonBuilder unfinished = person.toBuilder().name("Alice");
        Person unchanged = person.toBuilder().build();
        Person greeted = person.toBuilder().greet().name("Alice").build();
        Person shouted = person.toBuilder().nameUpperCase("alice").build();
        Person fresh = Person.builder().name("Alice").build();
        String text = person.toBuilder().name("Alice").toString();
    }</fold>

    public void setPrefixed(Order order) <fold text='{...}' expand='true'>{
        var shipped = order.<fold text='copy(' expand='false'>toBuilder()</fold>
            <fold text='s' expand='false'>    .setS</fold>tatus<fold text=' = ' expand='false'>(</fold>Status.SHIPPED<fold text=',' expand='false'>)</fold>
            <fold text='n' expand='false'>    .setN</fold>ote<fold text=' = ' expand='false'>(</fold>"left at the door"<fold text='' expand='false'>)</fold>
        <fold text='' expand='false'>        .build(</fold>);
        Order cancelled = order.<fold text='copy(' expand='false'>toBuilder()</fold><fold text='s' expand='false'>.setS</fold>tatus<fold text=' = ' expand='false'>(</fold>Status.CANCELLED<fold text=',' expand='false'>)</fold><fold text=' n' expand='false'>.setN</fold>ote<fold text=' = ' expand='false'>(</fold>"out of stock")<fold text='' expand='false'>.build()</fold>;
    }</fold>

    public void setPrefixNotFolded(Order order) <fold text='{...}' expand='true'>{
        Order mixed = order.toBuilder().setStatus(Status.SHIPPED).note("gift").build();
        Order tracked = order.toBuilder().setTracking("T-1").build();
    }</fold>

    static class Person <fold text='{...}' expand='true'>{
        private final String name;
        private final int age;

        Person(String name, int age) <fold text='{...}' expand='true'>{
            this.name = name;
            this.age = age;
        }</fold>

        public static PersonBuilder builder()<fold text=' { ' expand='false'> {
            </fold>return new PersonBuilder();<fold text=' }' expand='false'>
        }</fold>

        public PersonBuilder toBuilder()<fold text=' { ' expand='false'> {
            </fold>return new PersonBuilder().name(name).age(age);<fold text=' }' expand='false'>
        }</fold>

        public String getName()<fold text=' { ' expand='false'> {
            </fold>return name;<fold text=' }' expand='false'>
        }</fold>

        public int getAge()<fold text=' { ' expand='false'> {
            </fold>return age;<fold text=' }' expand='false'>
        }</fold>

        static class PersonBuilder <fold text='{...}' expand='true'>{
            private String name;
            private int age;

            public PersonBuilder name(String name) <fold text='{...}' expand='true'>{
                this.name = name;
                return this;
            }</fold>

            public PersonBuilder age(int age) <fold text='{...}' expand='true'>{
                this.age = age;
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
                </fold>return new Person(name, age);<fold text=' }' expand='false'>
            }</fold>
        }</fold>
    }</fold>

    enum Status <fold text='{...}' expand='true'>{
        NEW, SHIPPED, CANCELLED
    }</fold>

    static class Order <fold text='{...}' expand='true'>{
        private final Status status;
        private final String note;

        Order(Status status, String note) <fold text='{...}' expand='true'>{
            this.status = status;
            this.note = note;
        }</fold>

        public OrderBuilder toBuilder()<fold text=' { ' expand='false'> {
            </fold>return new OrderBuilder().setStatus(status).setNote(note);<fold text=' }' expand='false'>
        }</fold>

        static class OrderBuilder <fold text='{...}' expand='true'>{
            private Status status;
            private String note;

            public OrderBuilder setStatus(Status status) <fold text='{...}' expand='true'>{
                this.status = status;
                return this;
            }</fold>

            public OrderBuilder setNote(String note) <fold text='{...}' expand='true'>{
                this.note = note;
                return this;
            }</fold>

            public OrderBuilder note(String note) <fold text='{...}' expand='true'>{
                this.note = note;
                return this;
            }</fold>

            public OrderBuilder setTracking(String tracking)<fold text=' { ' expand='false'> {
                </fold>return this;<fold text=' }' expand='false'>
            }</fold>

            public Order build()<fold text=' { ' expand='false'> {
                </fold>return new Order(status, note);<fold text=' }' expand='false'>
            }</fold>
        }</fold>
    }</fold>
}
