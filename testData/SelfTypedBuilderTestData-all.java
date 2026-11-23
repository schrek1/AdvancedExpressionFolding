package data;

<fold text='@HasBuilder(BaseBuilder) p' expand='false'>p</fold>ublic class SelfTypedBuilderTestData {

    public void copies(Order order, Order original, Order.Status status) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>var</fold> shipped = save(order.<fold text='copy(s' expand='false'>toBuilder()
                .setS</fold>tatus<fold text=' = ' expand='false'>(</fold>Order.Status.SHIPPED)<fold text='' expand='false'>
                .build()</fold>);
        <fold text='val' expand='false'>Order</fold> noted = original.<fold text='copy(' expand='false'>toBuilder()</fold><fold text='s' expand='false'>.setS</fold>tatus<fold text=' = ' expand='false'>(</fold>status<fold text=',' expand='false'>)</fold><fold text=' n' expand='false'>.setN</fold>ote<fold text=' = ' expand='false'>(</fold>"gift")<fold text='' expand='false'>.build()</fold>;
    }</fold>

    public void creates() <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='val' expand='false'>Order</fold> fresh = Order<fold text='(' expand='false'>.builder()</fold>
            <fold text='i' expand='false'>    .setI</fold>d<fold text=' = ' expand='false'>(</fold>7<fold text=',' expand='false'>)</fold>
            <fold text='s' expand='false'>    .setS</fold>tatus<fold text=' = ' expand='false'>(</fold>Order.Status.NEW<fold text='' expand='false'>)</fold>
        <fold text='' expand='false'>        .build(</fold>)<fold text='' expand='false'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    public void notFolded(Order order) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>Order</fold> tracked = order.toBuilder().setStatus(Order.Status.SHIPPED).setTracking("T-1").build();
        <fold text='val' expand='false'>Order</fold> created = Order.builder().setTracking("T-2").build();
    }</fold>

    private Order save(Order order)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>order<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    abstract static class BaseBuilder<T, B extends BaseBuilder<T, B>> <fold text='{...}' expand='true'>{
        <fold text='' expand='true'>@SuppressWarnings("unchecked")</fold><fold text='' expand='true'>
        </fold>protected <fold text='' expand='false'>final</fold> B self()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>(B) </fold>this<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        public abstract T build();
    }</fold>

    <fold text='@HasBuilder(AbstractOrderBuilder) @HasBuilder(Builder) s' expand='false'>s</fold>tatic class Order <fold text='{...}' expand='true'>{
        private final long id;
        private final Status status;
        private final String note;

        private Order(AbstractOrderBuilder<?, ?> source) <fold text='{...}' expand='true'>{
            this.id = source.<fold text='<<' expand='true'>id</fold>;
            this.status = source.<fold text='<<' expand='true'>status</fold>;
            this.note = source.<fold text='<<' expand='true'>note</fold>;
        }</fold>

        public static Builder builder()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>new </fold>Builder()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        public Builder toBuilder()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>new </fold>Builder(this)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        enum Status <fold text='{...}' expand='true'>{
            NEW, SHIPPED
        }</fold>

        <fold text='@NoArgsConstructor(default) a' expand='false'>a</fold>bstract static class AbstractOrderBuilder<C extends Order, B extends AbstractOrderBuilder<C, B>>
                extends BaseBuilder<C, B> <fold text='{...}' expand='true'>{
            private long id;
            private Status status;
            private String note;
            private String tracking;<fold text='' expand='false'>

            </fold><fold text='' expand='false'>AbstractOrderBuilder() <fold text='{}' expand='true'>{
            }</fold></fold>

            AbstractOrderBuilder(Order source) <fold text='{...}' expand='true'>{
                this.id = source.<fold text='<<' expand='true'>id</fold>;
                this.status = source.<fold text='<<' expand='true'>status</fold>;
                this.note = source.<fold text='<<' expand='true'>note</fold>;
            }</fold>

            public B setId(long id) <fold text='{...}' expand='true'>{
                this.id = <fold text='<<' expand='false'>id</fold>;
                return self();
            }</fold>

            public B setStatus(Status status) <fold text='{...}' expand='true'>{
                this.status = <fold text='<<' expand='false'>status</fold>;
                return self();
            }</fold>

            public B setNote(String note) <fold text='{...}' expand='true'>{
                this.note = <fold text='<<' expand='false'>note</fold>;
                return self();
            }</fold>

            public B setTracking(String tracking) <fold text='{...}' expand='true'>{
                this.tracking = <fold text='<<' expand='false'>tracking</fold>;
                return self();
            }</fold>
        }</fold>

        <fold text='@NoArgsConstructor(private) s' expand='false'>s</fold>tatic <fold text='' expand='false'>final</fold> class Builder extends AbstractOrderBuilder<Order, Builder> <fold text='{...}' expand='true'>{<fold text='' expand='false'>
            </fold><fold text='' expand='false'>private Builder() <fold text='{}' expand='true'>{
            }</fold></fold>

            private Builder(Order source)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
                </fold></fold>super(source)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
            </fold>}</fold>

            <fold text='' expand='true'>@Override</fold><fold text='' expand='true'>
            </fold>public Order build()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
                </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>new </fold>Order(this)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
            </fold>}</fold>
        }</fold>
    }</fold>
}
