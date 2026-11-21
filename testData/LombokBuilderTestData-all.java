package data;

public class LombokBuilderTestData {

    <fold text='@Builder @RequiredArgsConstructor(default) s' expand='false'>s</fold>tatic class Person <fold text='{...}' expand='true'>{
        private final String name;
        private final int age;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>Person(String name, int age) <fold text='{...}' expand='true'>{
            this.name = <fold text='<<' expand='false'>name</fold>;
            this.age = <fold text='<<' expand='false'>age</fold>;
        }</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>static Builder builder()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>new Builder()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>static class Builder <fold text='{...}' expand='true'>{
            private String name;
            private int age;

            Builder name(String name) <fold text='{...}' expand='true'>{
                this.name = <fold text='<<' expand='false'>name</fold>;
                return this;
            }</fold>

            Builder age(int age) <fold text='{...}' expand='true'>{
                this.age = <fold text='<<' expand='false'>age</fold>;
                return this;
            }</fold>

            Person build()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
                </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>new Person(name, age)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
            </fold>}</fold>
        }</fold></fold>
    }</fold>

    <fold text='@Builder @AllArgsConstructor(default) p' expand='false'>p</fold>ublic static class Order <fold text='{...}' expand='true'>{
        private String number;
        private long amount;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>Order(String number, long amount) <fold text='{...}' expand='true'>{
            this.number = <fold text='<<' expand='false'>number</fold>;
            this.amount = <fold text='<<' expand='false'>amount</fold>;
        }</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public static OrderBuilder builder()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>new OrderBuilder()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public static class OrderBuilder <fold text='{...}' expand='true'>{
            private String number;
            private long amount;

            OrderBuilder() <fold text='{}' expand='true'>{
            }</fold>

            public OrderBuilder number(String number) <fold text='{...}' expand='true'>{
                this.number = <fold text='<<' expand='false'>number</fold>;
                return this;
            }</fold>

            public OrderBuilder amount(long amount) <fold text='{...}' expand='true'>{
                this.amount = <fold text='<<' expand='false'>amount</fold>;
                return this;
            }</fold>

            public Order build()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
                </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>new Order(this.number, this.amount)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
            </fold>}</fold>
        }</fold></fold>
    }</fold>

    <fold text='@HasBuilder(Builder) @RequiredArgsConstructor(default) s' expand='false'>s</fold>tatic class ValidatedStock <fold text='{...}' expand='true'>{
        private final String sku;
        private final int quantity;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>ValidatedStock(String sku, int quantity) <fold text='{...}' expand='true'>{
            this.sku = <fold text='<<' expand='false'>sku</fold>;
            this.quantity = <fold text='<<' expand='false'>quantity</fold>;
        }</fold></fold>

        static Builder builder()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>new Builder()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        static class Builder <fold text='{...}' expand='true'>{
            private String sku;
            private int quantity;

            Builder sku(String sku) <fold text='{...}' expand='true'>{
                this.sku = <fold text='<<' expand='false'>sku</fold>;
                return this;
            }</fold>

            Builder quantity(int quantity) <fold text='{...}' expand='true'>{
                this.quantity = <fold text='<<' expand='false'>quantity</fold>;
                return this;
            }</fold>

            ValidatedStock build() <fold text='{...}' expand='true'>{
                <fold text='check(!' expand='false'>if </fold>(quantity < 0)<fold text=') ' expand='false'> </fold><fold text='{...}' expand='true'>{<fold text=' ' expand='false'>
                    throw new IllegalStateException(</fold>"negative quantity"<fold text=' ' expand='false'>);
                </fold>}</fold>
                return new ValidatedStock(sku, quantity);
            }</fold>
        }</fold>
    }</fold>

    <fold text='@HasBuilder(Builder) @RequiredArgsConstructor(default) s' expand='false'>s</fold>tatic class DefaultedStock <fold text='{...}' expand='true'>{
        private final String sku;
        private final int quantity;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>DefaultedStock(String sku, int quantity) <fold text='{...}' expand='true'>{
            this.sku = <fold text='<<' expand='false'>sku</fold>;
            this.quantity = <fold text='<<' expand='false'>quantity</fold>;
        }</fold></fold>

        static Builder builder()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>new Builder()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        static class Builder <fold text='{...}' expand='true'>{
            private String sku;
            private int quantity = 1;

            Builder sku(String sku) <fold text='{...}' expand='true'>{
                this.sku = <fold text='<<' expand='false'>sku</fold>;
                return this;
            }</fold>

            Builder quantity(int quantity) <fold text='{...}' expand='true'>{
                this.quantity = <fold text='<<' expand='false'>quantity</fold>;
                return this;
            }</fold>

            DefaultedStock build()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
                </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>new DefaultedStock(sku, quantity)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
            </fold>}</fold>
        }</fold>
    }</fold>

    <fold text='@HasBuilder(Builder) @RequiredArgsConstructor(default) s' expand='false'>s</fold>tatic class Shop <fold text='{...}' expand='true'>{
        private final String name;
        private final String city;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>Shop(String name, String city) <fold text='{...}' expand='true'>{
            this.name = <fold text='<<' expand='false'>name</fold>;
            this.city = <fold text='<<' expand='false'>city</fold>;
        }</fold></fold>

        static Builder builder()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>new Builder()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        static class Builder <fold text='{...}' expand='true'>{
            private String name;
            private String city;

            Builder name(String name) <fold text='{...}' expand='true'>{
                this.name = <fold text='<<' expand='false'>name</fold>;
                return this;
            }</fold>

            Builder city(String city) <fold text='{...}' expand='true'>{
                this.city = <fold text='<<' expand='false'>city</fold>;
                return this;
            }</fold>

            Builder inPrague() <fold text='{...}' expand='true'>{
                this.city = "Prague";
                return this;
            }</fold>

            Shop build()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
                </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>new Shop(name, city)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
            </fold>}</fold>
        }</fold>
    }</fold>

    <fold text='@HasBuilder(Builder) @RequiredArgsConstructor(default) s' expand='false'>s</fold>tatic class SwappedShop <fold text='{...}' expand='true'>{
        private final String name;
        private final String city;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>SwappedShop(String city, String name) <fold text='{...}' expand='true'>{
            this.name = <fold text='<<' expand='false'>name</fold>;
            this.city = <fold text='<<' expand='false'>city</fold>;
        }</fold></fold>

        static Builder builder()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>new Builder()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        static class Builder <fold text='{...}' expand='true'>{
            private String name;
            private String city;

            Builder name(String name) <fold text='{...}' expand='true'>{
                this.name = <fold text='<<' expand='false'>name</fold>;
                return this;
            }</fold>

            Builder city(String city) <fold text='{...}' expand='true'>{
                this.city = <fold text='<<' expand='false'>city</fold>;
                return this;
            }</fold>

            SwappedShop build()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
                </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>new SwappedShop(city, name)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
            </fold>}</fold>
        }</fold>
    }</fold>
}
