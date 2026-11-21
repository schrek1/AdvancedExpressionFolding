package data;

public class LombokBuilderTestData {

    <fold text='@Builder @RequiredArgsConstructor(default) s' expand='false'>s</fold>tatic class Person <fold text='{...}' expand='true'>{
        private final String name;
        private final int age;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>Person(String name, int age) <fold text='{...}' expand='true'>{
            this.name = name;
            this.age = age;
        }</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>static Builder builder()<fold text=' { ' expand='false'> {
            </fold>return new Builder();<fold text=' }' expand='false'>
        }</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>static class Builder <fold text='{...}' expand='true'>{
            private String name;
            private int age;

            Builder name(String name) <fold text='{...}' expand='true'>{
                this.name = name;
                return this;
            }</fold>

            Builder age(int age) <fold text='{...}' expand='true'>{
                this.age = age;
                return this;
            }</fold>

            Person build()<fold text=' { ' expand='false'> {
                </fold>return new Person(name, age);<fold text=' }' expand='false'>
            }</fold>
        }</fold></fold>
    }</fold>

    <fold text='@Builder @AllArgsConstructor(default) p' expand='false'>p</fold>ublic static class Order <fold text='{...}' expand='true'>{
        private String number;
        private long amount;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>Order(String number, long amount) <fold text='{...}' expand='true'>{
            this.number = number;
            this.amount = amount;
        }</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public static OrderBuilder builder()<fold text=' { ' expand='false'> {
            </fold>return new OrderBuilder();<fold text=' }' expand='false'>
        }</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public static class OrderBuilder <fold text='{...}' expand='true'>{
            private String number;
            private long amount;

            OrderBuilder() <fold text='{}' expand='true'>{
            }</fold>

            public OrderBuilder number(String number) <fold text='{...}' expand='true'>{
                this.number = number;
                return this;
            }</fold>

            public OrderBuilder amount(long amount) <fold text='{...}' expand='true'>{
                this.amount = amount;
                return this;
            }</fold>

            public Order build()<fold text=' { ' expand='false'> {
                </fold>return new Order(this.number, this.amount);<fold text=' }' expand='false'>
            }</fold>
        }</fold></fold>
    }</fold>

    <fold text='@HasBuilder(Builder) @RequiredArgsConstructor(default) s' expand='false'>s</fold>tatic class ValidatedStock <fold text='{...}' expand='true'>{
        private final String sku;
        private final int quantity;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>ValidatedStock(String sku, int quantity) <fold text='{...}' expand='true'>{
            this.sku = sku;
            this.quantity = quantity;
        }</fold></fold>

        static Builder builder()<fold text=' { ' expand='false'> {
            </fold>return new Builder();<fold text=' }' expand='false'>
        }</fold>

        static class Builder <fold text='{...}' expand='true'>{
            private String sku;
            private int quantity;

            Builder sku(String sku) <fold text='{...}' expand='true'>{
                this.sku = sku;
                return this;
            }</fold>

            Builder quantity(int quantity) <fold text='{...}' expand='true'>{
                this.quantity = quantity;
                return this;
            }</fold>

            ValidatedStock build() <fold text='{...}' expand='true'>{
                if (quantity < 0) <fold text='{...}' expand='true'>{
                    throw new IllegalStateException("negative quantity");
                }</fold>
                return new ValidatedStock(sku, quantity);
            }</fold>
        }</fold>
    }</fold>

    <fold text='@HasBuilder(Builder) @RequiredArgsConstructor(default) s' expand='false'>s</fold>tatic class DefaultedStock <fold text='{...}' expand='true'>{
        private final String sku;
        private final int quantity;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>DefaultedStock(String sku, int quantity) <fold text='{...}' expand='true'>{
            this.sku = sku;
            this.quantity = quantity;
        }</fold></fold>

        static Builder builder()<fold text=' { ' expand='false'> {
            </fold>return new Builder();<fold text=' }' expand='false'>
        }</fold>

        static class Builder <fold text='{...}' expand='true'>{
            private String sku;
            private int quantity = 1;

            Builder sku(String sku) <fold text='{...}' expand='true'>{
                this.sku = sku;
                return this;
            }</fold>

            Builder quantity(int quantity) <fold text='{...}' expand='true'>{
                this.quantity = quantity;
                return this;
            }</fold>

            DefaultedStock build()<fold text=' { ' expand='false'> {
                </fold>return new DefaultedStock(sku, quantity);<fold text=' }' expand='false'>
            }</fold>
        }</fold>
    }</fold>

    <fold text='@HasBuilder(Builder) @RequiredArgsConstructor(default) s' expand='false'>s</fold>tatic class Shop <fold text='{...}' expand='true'>{
        private final String name;
        private final String city;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>Shop(String name, String city) <fold text='{...}' expand='true'>{
            this.name = name;
            this.city = city;
        }</fold></fold>

        static Builder builder()<fold text=' { ' expand='false'> {
            </fold>return new Builder();<fold text=' }' expand='false'>
        }</fold>

        static class Builder <fold text='{...}' expand='true'>{
            private String name;
            private String city;

            Builder name(String name) <fold text='{...}' expand='true'>{
                this.name = name;
                return this;
            }</fold>

            Builder city(String city) <fold text='{...}' expand='true'>{
                this.city = city;
                return this;
            }</fold>

            Builder inPrague() <fold text='{...}' expand='true'>{
                this.city = "Prague";
                return this;
            }</fold>

            Shop build()<fold text=' { ' expand='false'> {
                </fold>return new Shop(name, city);<fold text=' }' expand='false'>
            }</fold>
        }</fold>
    }</fold>

    <fold text='@HasBuilder(Builder) @RequiredArgsConstructor(default) s' expand='false'>s</fold>tatic class SwappedShop <fold text='{...}' expand='true'>{
        private final String name;
        private final String city;<fold text='' expand='false'>

        </fold><fold text='' expand='false'>SwappedShop(String city, String name) <fold text='{...}' expand='true'>{
            this.name = name;
            this.city = city;
        }</fold></fold>

        static Builder builder()<fold text=' { ' expand='false'> {
            </fold>return new Builder();<fold text=' }' expand='false'>
        }</fold>

        static class Builder <fold text='{...}' expand='true'>{
            private String name;
            private String city;

            Builder name(String name) <fold text='{...}' expand='true'>{
                this.name = name;
                return this;
            }</fold>

            Builder city(String city) <fold text='{...}' expand='true'>{
                this.city = city;
                return this;
            }</fold>

            SwappedShop build()<fold text=' { ' expand='false'> {
                </fold>return new SwappedShop(city, name);<fold text=' }' expand='false'>
            }</fold>
        }</fold>
    }</fold>
}
