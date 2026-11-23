package data;

public class BuilderNamedArgumentsTestData {

    public void builds(Person source) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>var</fold> alice = Person<fold text='(' expand='false'>.builder()</fold>
            <fold text='' expand='false'>    .</fold>name<fold text=' = ' expand='false'>(</fold>"Alice"<fold text=',' expand='false'>)</fold>
            <fold text='' expand='false'>    .</fold>age<fold text=' = ' expand='false'>(</fold>31<fold text='' expand='false'>)</fold>
        <fold text='' expand='false'>        .build(</fold>)<fold text='' expand='false'>;</fold>
        <fold text='val' expand='false'>Person</fold> bob = Person<fold text='(' expand='false'>.builder().</fold>name<fold text=' = ' expand='false'>(</fold>"Bob")<fold text='' expand='false'>.build()</fold>;
        <fold text='val' expand='false'>Person</fold> mixed = Person<fold text='(' expand='false'>.builder()</fold><fold text='' expand='false'>.</fold>name<fold text=' = ' expand='false'>(</fold>"Carol"<fold text=',' expand='false'>)</fold>
            <fold text='' expand='false'>    .</fold>age<fold text=' = ' expand='false'>(</fold>40<fold text='' expand='false'>).build();</fold>
        <fold text='val' expand='false'>Person</fold> mapped = Person<fold text='(' expand='false'>.builder()
                .</fold>name<fold text=' = ' expand='false'>(</fold>source<fold text='<<' expand='false'>.getName()</fold>)<fold text='' expand='false'>
                .build()</fold>;
        <fold text='val' expand='false'>Person</fold> titled = Person.builder().name("Dr. <fold text='${' expand='false'>" + </fold>source.<fold text='n' expand='false'>getN</fold>ame<fold text='}"' expand='false'>()</fold>).build();
        <fold text='val' expand='false'>Person</fold> commented = Person<fold text='(' expand='false'>.builder()</fold>
                // a comment keeps the chain on its lines
            <fold text='' expand='false'>    .</fold>name<fold text=' = ' expand='false'>(</fold>"Dave"<fold text='' expand='false'>)</fold>
        <fold text='' expand='false'>        .build(</fold>)<fold text='' expand='false'>;</fold>
        <fold text='val' expand='false'>Person</fold> managed = Person<fold text='(' expand='false'>.builder()</fold>
            <fold text='' expand='false'>    .</fold>name<fold text=' = ' expand='false'>(</fold>"Eve"<fold text=',' expand='false'>)</fold>
            <fold text='' expand='false'>    .</fold>manager<fold text=' = ' expand='false'>(</fold>Person<fold text='(' expand='false'>.builder().</fold>name<fold text=' = ' expand='false'>(</fold>"Frank")<fold text='' expand='false'>.build()</fold><fold text='' expand='false'>)</fold>
        <fold text='' expand='false'>        .build(</fold>)<fold text='' expand='false'>;</fold>
        <fold text='val' expand='false'>Person</fold> twoArguments = Person<fold text='(' expand='false'>.builder()</fold>
            <fold text='' expand='false'>    .</fold>name<fold text=' = ' expand='false'>(</fold>"Grace"<fold text=',' expand='false'>)</fold>
            <fold text='' expand='false'>    .</fold>age<fold text=' = ' expand='false'>(</fold>29<fold text='' expand='false'>).build();</fold>
        <fold text='val' expand='false'>Person</fold> threeArguments = Person<fold text='(' expand='false'>.builder()</fold>
            <fold text='' expand='false'>    .</fold>name<fold text=' = ' expand='false'>(</fold>"Heidi"<fold text=',' expand='false'>)</fold>
            <fold text='' expand='false'>    .</fold>age<fold text=' = ' expand='false'>(</fold>52<fold text=',' expand='false'>)</fold>
            <fold text='' expand='false'>    .</fold>manager<fold text=' = ' expand='false'>(</fold>source<fold text='' expand='false'>).build();</fold>
        <fold text='val' expand='false'>Person</fold> oneLine = Person<fold text='(' expand='false'>.builder()</fold><fold text='' expand='false'>.</fold>name<fold text=' = ' expand='false'>(</fold>"Ivan"<fold text=',' expand='false'>)</fold><fold text=' ' expand='false'>.</fold>age<fold text=' = ' expand='false'>(</fold>33)<fold text='' expand='false'>.build()</fold>;
    }</fold>

    public void notFolded(Person person) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>Person.PersonBuilder</fold> unfinished = Person.builder().name("Alice");
        <fold text='val' expand='false'>Person</fold> empty = Person.builder().build();
        <fold text='val' expand='false'>Person</fold> greeted = Person.builder().greet().name("Alice").build();
        <fold text='val' expand='false'>Person</fold> shouted = Person.builder().nameUpperCase("alice").build();
        <fold text='val' expand='false'>Person</fold> copied = person.<fold text='copy(' expand='false'>toBuilder().</fold>name<fold text=' = ' expand='false'>(</fold>"Alice")<fold text='' expand='false'>.build()</fold>;
        <fold text='val' expand='false'>String</fold> text = Person.builder().name("Alice").toString();
    }</fold>

    public void setPrefixed() <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>var</fold> order = Order<fold text='(' expand='false'>.builder()</fold>
            <fold text='i' expand='false'>    .setI</fold>d<fold text=' = ' expand='false'>(</fold>7<fold text=',' expand='false'>)</fold>
            <fold text='p' expand='false'>    .setP</fold>ayment<fold text=' = ' expand='false'>(</fold>Payment<fold text='(f' expand='false'>.builder().setF</fold>ixedInfo<fold text=' = ' expand='false'>(</fold>"card")<fold text='' expand='false'>.build()</fold><fold text='' expand='false'>)</fold>
        <fold text='' expand='false'>        .build(</fold>)<fold text='' expand='false'>;</fold>
        <fold text='val' expand='false'>Order</fold> gift = Order<fold text='(n' expand='false'>.builder().setN</fold>ote<fold text=' = ' expand='false'>(</fold>"gift")<fold text='' expand='false'>.build()</fold>;
    }</fold>

    public void nestedClassBuilder(Wallet wallet, Asset asset, Chain chain) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>Payment.PaymentDetails</fold> details = Payment.PaymentDetails<fold text='(' expand='false'>.builder()</fold>
            <fold text='t' expand='false'>    .setT</fold>ype<fold text=' = ' expand='false'>(</fold>PaymentType.CARD<fold text=',' expand='false'>)</fold>
            <fold text='a' expand='false'>    .setA</fold>ccountId<fold text=' = ' expand='false'>(</fold>wallet.<fold text='v' expand='false'>getV</fold>alue<fold text='' expand='false'>()</fold><fold text=',' expand='false'>)</fold>
            <fold text='c' expand='false'>    .setC</fold>urrencyCode<fold text=' = ' expand='false'>(</fold>asset<fold text='<<' expand='false'>.getCurrencyCode()</fold><fold text=',' expand='false'>)</fold>
            <fold text='c' expand='false'>    .setC</fold>hain<fold text=' = ' expand='false'>(</fold><fold text='' expand='false'>chain != null ? </fold>chain<fold text='?.' expand='false'>.</fold>value()<fold text='' expand='false'> : null</fold><fold text='' expand='false'>)</fold>
        <fold text='' expand='false'>        .build(</fold>)<fold text='' expand='false'>;</fold>
    }</fold>

    public void setPrefixNotFolded() <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>Order</fold> mixed = Order.builder().setId(7).note("gift").build();
        <fold text='val' expand='false'>Order</fold> tracked = Order.builder().setTracking("T-1").build();
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
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>new </fold>PersonBuilder()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        public PersonBuilder toBuilder()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>new </fold>PersonBuilder().name(name).age(age).manager(manager)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
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
                </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>new </fold>Person(name, age, manager)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
            </fold>}</fold>
        }</fold>
    }</fold>

    <fold text='@HasBuilder @RequiredArgsConstructor(default) s' expand='false'>s</fold>tatic class Order <fold text='{...}' expand='true'>{
        private final int id;
        private final String note;
        private final Payment payment;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>Order(int id, String note, Payment payment) <fold text='{...}' expand='true'>{
            this.id = <fold text='<<' expand='false'>id</fold>;
            this.note = <fold text='<<' expand='false'>note</fold>;
            this.payment = <fold text='<<' expand='false'>payment</fold>;
        }</fold></fold>

        public static OrderBuilder builder()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>new </fold>OrderBuilder()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        static class OrderBuilder <fold text='{...}' expand='true'>{
            private int id;
            private String note;
            private Payment payment;

            public OrderBuilder setId(int id) <fold text='{...}' expand='true'>{
                this.id = <fold text='<<' expand='false'>id</fold>;
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

            public OrderBuilder setPayment(Payment payment) <fold text='{...}' expand='true'>{
                this.payment = <fold text='<<' expand='false'>payment</fold>;
                return this;
            }</fold>

            public OrderBuilder setTracking(String tracking)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
                </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>this<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
            </fold>}</fold>

            public Order build()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
                </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>new </fold>Order(id, note, payment)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
            </fold>}</fold>
        }</fold>
    }</fold>

    <fold text='@HasBuilder @RequiredArgsConstructor(default) s' expand='false'>s</fold>tatic class Payment <fold text='{...}' expand='true'>{
        private final String fixedInfo;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>Payment(String fixedInfo)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>this.fixedInfo = <fold text='<<' expand='false'>fixedInfo</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>

        public static PaymentBuilder builder()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>new </fold>PaymentBuilder()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        static class PaymentBuilder <fold text='{...}' expand='true'>{
            private String fixedInfo;

            public PaymentBuilder setFixedInfo(String fixedInfo) <fold text='{...}' expand='true'>{
                this.fixedInfo = <fold text='<<' expand='false'>fixedInfo</fold>;
                return this;
            }</fold>

            public Payment build()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
                </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>new </fold>Payment(fixedInfo)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
            </fold>}</fold>
        }</fold>

        <fold text='@HasBuilder s' expand='false'>s</fold>tatic class PaymentDetails <fold text='{...}' expand='true'>{
            private PaymentType type;
            private String accountId;
            private String currencyCode;
            private String chain;

            public static PaymentDetailsBuilder builder()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
                </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>new </fold>PaymentDetailsBuilder()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
            </fold>}</fold>

            static class PaymentDetailsBuilder <fold text='{...}' expand='true'>{
                private final PaymentDetails details =<fold text=' ::' expand='true'> </fold>new<fold text='' expand='true'> </fold><fold text='' expand='true'>PaymentDetails</fold><fold text='' expand='true'>()</fold>;

                public PaymentDetailsBuilder setType(PaymentType type) <fold text='{...}' expand='true'>{
                    details.type = <fold text='<<' expand='false'>type</fold>;
                    return this;
                }</fold>

                public PaymentDetailsBuilder setAccountId(String accountId) <fold text='{...}' expand='true'>{
                    details.accountId = <fold text='<<' expand='false'>accountId</fold>;
                    return this;
                }</fold>

                public PaymentDetailsBuilder setCurrencyCode(String currencyCode) <fold text='{...}' expand='true'>{
                    details.currencyCode = <fold text='<<' expand='false'>currencyCode</fold>;
                    return this;
                }</fold>

                public PaymentDetailsBuilder setChain(String chain) <fold text='{...}' expand='true'>{
                    details.chain = <fold text='<<' expand='false'>chain</fold>;
                    return this;
                }</fold>

                public PaymentDetails build()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
                    </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>details<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
                </fold>}</fold>
            }</fold>
        }</fold>
    }</fold>

    enum PaymentType <fold text='{...}' expand='true'>{
        CARD, TRANSFER
    }</fold>

    static class Wallet <fold text='{...}' expand='true'>{
        <fold text='' expand='true'>public</fold><fold text='' expand='true'> </fold><fold text='' expand='true'>String</fold><fold text='' expand='true'> </fold>getValue<fold text='' expand='true'>()</fold><fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>"w-1"<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>

    static class Asset <fold text='{...}' expand='true'>{
        <fold text='' expand='true'>public</fold><fold text='' expand='true'> </fold><fold text='' expand='true'>String</fold><fold text='' expand='true'> </fold>getCurrencyCode<fold text='' expand='true'>()</fold><fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>"EUR"<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>

    static class Chain <fold text='{...}' expand='true'>{
        <fold text='' expand='true'>public</fold><fold text='' expand='true'> </fold><fold text='' expand='true'>String</fold><fold text='' expand='true'> </fold>value<fold text='' expand='true'>()</fold><fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>"main"<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>
}
