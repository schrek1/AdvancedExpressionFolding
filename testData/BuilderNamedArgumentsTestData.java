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

    public void setPrefixed() <fold text='{...}' expand='true'>{
        var order = Order<fold text='(' expand='false'>.builder()</fold>
            <fold text='i' expand='false'>    .setI</fold>d<fold text=' = ' expand='false'>(</fold>7<fold text=',' expand='false'>)</fold>
            <fold text='p' expand='false'>    .setP</fold>ayment<fold text=' = ' expand='false'>(</fold>Payment<fold text='(f' expand='false'>.builder().setF</fold>ixedInfo<fold text=' = ' expand='false'>(</fold>"card")<fold text='' expand='false'>.build()</fold><fold text='' expand='false'>)</fold>
        <fold text='' expand='false'>        .build(</fold>);
        Order gift = Order<fold text='(n' expand='false'>.builder().setN</fold>ote<fold text=' = ' expand='false'>(</fold>"gift")<fold text='' expand='false'>.build()</fold>;
    }</fold>

    public void nestedClassBuilder(Wallet wallet, Asset asset, Chain chain) <fold text='{...}' expand='true'>{
        Payment.PaymentDetails details = Payment.PaymentDetails<fold text='(' expand='false'>.builder()</fold>
            <fold text='t' expand='false'>    .setT</fold>ype<fold text=' = ' expand='false'>(</fold>PaymentType.CARD<fold text=',' expand='false'>)</fold>
            <fold text='a' expand='false'>    .setA</fold>ccountId<fold text=' = ' expand='false'>(</fold>wallet.getValue()<fold text=',' expand='false'>)</fold>
            <fold text='c' expand='false'>    .setC</fold>urrencyCode<fold text=' = ' expand='false'>(</fold>asset.getCurrencyCode()<fold text=',' expand='false'>)</fold>
            <fold text='c' expand='false'>    .setC</fold>hain<fold text=' = ' expand='false'>(</fold>chain != null ? chain.value() : null<fold text='' expand='false'>)</fold>
        <fold text='' expand='false'>        .build(</fold>);
    }</fold>

    public void setPrefixNotFolded() <fold text='{...}' expand='true'>{
        Order mixed = Order.builder().setId(7).note("gift").build();
        Order tracked = Order.builder().setTracking("T-1").build();
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

    static class Order <fold text='{...}' expand='true'>{
        private final int id;
        private final String note;
        private final Payment payment;

        Order(int id, String note, Payment payment) <fold text='{...}' expand='true'>{
            this.id = id;
            this.note = note;
            this.payment = payment;
        }</fold>

        public static OrderBuilder builder()<fold text=' { ' expand='false'> {
            </fold>return new OrderBuilder();<fold text=' }' expand='false'>
        }</fold>

        static class OrderBuilder <fold text='{...}' expand='true'>{
            private int id;
            private String note;
            private Payment payment;

            public OrderBuilder setId(int id) <fold text='{...}' expand='true'>{
                this.id = id;
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

            public OrderBuilder setPayment(Payment payment) <fold text='{...}' expand='true'>{
                this.payment = payment;
                return this;
            }</fold>

            public OrderBuilder setTracking(String tracking)<fold text=' { ' expand='false'> {
                </fold>return this;<fold text=' }' expand='false'>
            }</fold>

            public Order build()<fold text=' { ' expand='false'> {
                </fold>return new Order(id, note, payment);<fold text=' }' expand='false'>
            }</fold>
        }</fold>
    }</fold>

    static class Payment <fold text='{...}' expand='true'>{
        private final String fixedInfo;

        Payment(String fixedInfo)<fold text=' { ' expand='false'> {
            </fold>this.fixedInfo = fixedInfo;<fold text=' }' expand='false'>
        }</fold>

        public static PaymentBuilder builder()<fold text=' { ' expand='false'> {
            </fold>return new PaymentBuilder();<fold text=' }' expand='false'>
        }</fold>

        static class PaymentBuilder <fold text='{...}' expand='true'>{
            private String fixedInfo;

            public PaymentBuilder setFixedInfo(String fixedInfo) <fold text='{...}' expand='true'>{
                this.fixedInfo = fixedInfo;
                return this;
            }</fold>

            public Payment build()<fold text=' { ' expand='false'> {
                </fold>return new Payment(fixedInfo);<fold text=' }' expand='false'>
            }</fold>
        }</fold>

        static class PaymentDetails <fold text='{...}' expand='true'>{
            private PaymentType type;
            private String accountId;
            private String currencyCode;
            private String chain;

            public static PaymentDetailsBuilder builder()<fold text=' { ' expand='false'> {
                </fold>return new PaymentDetailsBuilder();<fold text=' }' expand='false'>
            }</fold>

            static class PaymentDetailsBuilder <fold text='{...}' expand='true'>{
                private final PaymentDetails details = new PaymentDetails();

                public PaymentDetailsBuilder setType(PaymentType type) <fold text='{...}' expand='true'>{
                    details.type = type;
                    return this;
                }</fold>

                public PaymentDetailsBuilder setAccountId(String accountId) <fold text='{...}' expand='true'>{
                    details.accountId = accountId;
                    return this;
                }</fold>

                public PaymentDetailsBuilder setCurrencyCode(String currencyCode) <fold text='{...}' expand='true'>{
                    details.currencyCode = currencyCode;
                    return this;
                }</fold>

                public PaymentDetailsBuilder setChain(String chain) <fold text='{...}' expand='true'>{
                    details.chain = chain;
                    return this;
                }</fold>

                public PaymentDetails build()<fold text=' { ' expand='false'> {
                    </fold>return details;<fold text=' }' expand='false'>
                }</fold>
            }</fold>
        }</fold>
    }</fold>

    enum PaymentType <fold text='{...}' expand='true'>{
        CARD, TRANSFER
    }</fold>

    static class Wallet <fold text='{...}' expand='true'>{
        public String getValue()<fold text=' { ' expand='false'> {
            </fold>return "w-1";<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    static class Asset <fold text='{...}' expand='true'>{
        public String getCurrencyCode()<fold text=' { ' expand='false'> {
            </fold>return "EUR";<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    static class Chain <fold text='{...}' expand='true'>{
        public String value()<fold text=' { ' expand='false'> {
            </fold>return "main";<fold text=' }' expand='false'>
        }</fold>
    }</fold>
}
