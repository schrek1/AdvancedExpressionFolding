package data;

public class ToBuilderCopyTestData {

    public void copies(Person person, Person source) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>var</fold> renamed = person.<fold text='copy(' expand='false'>toBuilder()</fold>
            <fold text='' expand='false'>    .</fold>name<fold text=' = ' expand='false'>(</fold>"Alice"<fold text=',' expand='false'>)</fold>
            <fold text='' expand='false'>    .</fold>age<fold text=' = ' expand='false'>(</fold>31<fold text='' expand='false'>)</fold>
        <fold text='' expand='false'>        .build(</fold>)<fold text='' expand='false'>;</fold>
        <fold text='val' expand='false'>Person</fold> older = person.<fold text='copy(' expand='false'>toBuilder().</fold>age<fold text=' = ' expand='false'>(</fold>person.<fold text='a' expand='false'>getA</fold>ge<fold text='' expand='false'>()</fold> + 1)<fold text='' expand='false'>.build()</fold>;
        <fold text='val' expand='false'>Person</fold> mixed = person.<fold text='copy(' expand='false'>toBuilder()</fold><fold text='' expand='false'>.</fold>name<fold text=' = ' expand='false'>(</fold>"Bob"<fold text=',' expand='false'>)</fold>
            <fold text='' expand='false'>    .</fold>age<fold text=' = ' expand='false'>(</fold>40<fold text='' expand='false'>).build();</fold>
        <fold text='val' expand='false'>Person</fold> mapped = person.<fold text='copy(' expand='false'>toBuilder()
                .</fold>name<fold text=' = ' expand='false'>(</fold>source<fold text='<<' expand='false'>.getName()</fold>)<fold text='' expand='false'>
                .build()</fold>;
        <fold text='val' expand='false'>Person</fold> titled = person.toBuilder().name("Dr. <fold text='${' expand='false'>" + </fold>person.<fold text='n' expand='false'>getN</fold>ame<fold text='}"' expand='false'>()</fold>).build();
        <fold text='val' expand='false'>Person</fold> commented = person.<fold text='copy(' expand='false'>toBuilder()</fold>
                // a comment keeps the chain on its lines
            <fold text='' expand='false'>    .</fold>name<fold text=' = ' expand='false'>(</fold>"Carol"<fold text='' expand='false'>)</fold>
        <fold text='' expand='false'>        .build(</fold>)<fold text='' expand='false'>;</fold>
        <fold text='val' expand='false'>Person</fold> twoArguments = person.<fold text='copy(' expand='false'>toBuilder()</fold>
            <fold text='' expand='false'>    .</fold>name<fold text=' = ' expand='false'>(</fold>"Grace"<fold text=',' expand='false'>)</fold>
            <fold text='' expand='false'>    .</fold>age<fold text=' = ' expand='false'>(</fold>29<fold text='' expand='false'>).build();</fold>
        <fold text='val' expand='false'>Person</fold> oneLine = person.<fold text='copy(' expand='false'>toBuilder()</fold><fold text='' expand='false'>.</fold>name<fold text=' = ' expand='false'>(</fold>"Ivan"<fold text=',' expand='false'>)</fold><fold text=' ' expand='false'>.</fold>age<fold text=' = ' expand='false'>(</fold>33)<fold text='' expand='false'>.build()</fold>;
    }</fold>

    public void notFolded(Person person) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>Person.PersonBuilder</fold> unfinished = person.toBuilder().name("Alice");
        <fold text='val' expand='false'>Person</fold> unchanged = person.toBuilder().build();
        <fold text='val' expand='false'>Person</fold> greeted = person.toBuilder().greet().name("Alice").build();
        <fold text='val' expand='false'>Person</fold> shouted = person.toBuilder().nameUpperCase("alice").build();
        <fold text='val' expand='false'>Person</fold> fresh = Person<fold text='(' expand='false'>.builder().</fold>name<fold text=' = ' expand='false'>(</fold>"Alice")<fold text='' expand='false'>.build()</fold>;
        <fold text='val' expand='false'>String</fold> text = person.toBuilder().name("Alice").toString();
    }</fold>

    public void setPrefixed(Order order) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>var</fold> shipped = order.<fold text='copy(' expand='false'>toBuilder()</fold>
            <fold text='s' expand='false'>    .setS</fold>tatus<fold text=' = ' expand='false'>(</fold>Status.SHIPPED<fold text=',' expand='false'>)</fold>
            <fold text='n' expand='false'>    .setN</fold>ote<fold text=' = ' expand='false'>(</fold>"left at the door"<fold text='' expand='false'>)</fold>
        <fold text='' expand='false'>        .build(</fold>)<fold text='' expand='false'>;</fold>
        <fold text='val' expand='false'>Order</fold> cancelled = order.<fold text='copy(' expand='false'>toBuilder()</fold><fold text='s' expand='false'>.setS</fold>tatus<fold text=' = ' expand='false'>(</fold>Status.CANCELLED<fold text=',' expand='false'>)</fold><fold text=' n' expand='false'>.setN</fold>ote<fold text=' = ' expand='false'>(</fold>"out of stock")<fold text='' expand='false'>.build()</fold>;
    }</fold>

    public void setPrefixNotFolded(Order order) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>Order</fold> mixed = order.toBuilder().setStatus(Status.SHIPPED).note("gift").build();
        <fold text='val' expand='false'>Order</fold> tracked = order.toBuilder().setTracking("T-1").build();
    }</fold>

    <fold text='@HasBuilder @LightValue s' expand='false'>s</fold>tatic class Person <fold text='{...}' expand='true'>{
        private final String name;
        private final int age;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>Person(String name, int age) <fold text='{...}' expand='true'>{
            this.name = <fold text='<<' expand='false'>name</fold>;
            this.age = <fold text='<<' expand='false'>age</fold>;
        }</fold></fold>

        public static PersonBuilder builder()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>new </fold>PersonBuilder()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        public PersonBuilder toBuilder()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>new </fold>PersonBuilder().name(name).age(age)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public String getName()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>name<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public int getAge()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>age<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>

        static class PersonBuilder <fold text='{...}' expand='true'>{
            private String name;
            private int age;

            public PersonBuilder name(String name) <fold text='{...}' expand='true'>{
                this.name = <fold text='<<' expand='false'>name</fold>;
                return this;
            }</fold>

            public PersonBuilder age(int age) <fold text='{...}' expand='true'>{
                this.age = <fold text='<<' expand='false'>age</fold>;
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
                </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>new </fold>Person(name, age)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
            </fold>}</fold>
        }</fold>
    }</fold>

    enum Status <fold text='{...}' expand='true'>{
        NEW, SHIPPED, CANCELLED
    }</fold>

    <fold text='@HasBuilder @RequiredArgsConstructor(default) s' expand='false'>s</fold>tatic class Order <fold text='{...}' expand='true'>{
        private final Status status;
        private final String note;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>Order(Status status, String note) <fold text='{...}' expand='true'>{
            this.status = <fold text='<<' expand='false'>status</fold>;
            this.note = <fold text='<<' expand='false'>note</fold>;
        }</fold></fold>

        public OrderBuilder toBuilder()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>new </fold>OrderBuilder().setStatus(status).setNote(note)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        static class OrderBuilder <fold text='{...}' expand='true'>{
            private Status status;
            private String note;

            public OrderBuilder setStatus(Status status) <fold text='{...}' expand='true'>{
                this.status = <fold text='<<' expand='false'>status</fold>;
                return this;
            }</fold>

            public OrderBuilder setNote(String note) <fold text='{...}' expand='true'>{
                this.note = <fold text='<<' expand='false'>note</fold>;
                return this;
            }</fold>

            public OrderBuilder note(String note) <fold text='{...}' expand='true'>{
                this.note = <fold text='<<' expand='false'>note</fold>;
                return this;
            }</fold>

            public OrderBuilder setTracking(String tracking)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
                </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>this<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
            </fold>}</fold>

            public Order build()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
                </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>new </fold>Order(status, note)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
            </fold>}</fold>
        }</fold>
    }</fold>
}
