package data;

public class SelfTypedBuilderTestData {

    public void copies(Order order, Order original, Order.Status status) <fold text='{...}' expand='true'>{
        var shipped = save(order.<fold text='copy(s' expand='false'>toBuilder()
                .setS</fold>tatus<fold text=' = ' expand='false'>(</fold>Order.Status.SHIPPED)<fold text='' expand='false'>
                .build()</fold>);
        Order noted = original.<fold text='copy(' expand='false'>toBuilder()</fold><fold text='s' expand='false'>.setS</fold>tatus<fold text=' = ' expand='false'>(</fold>status<fold text=',' expand='false'>)</fold><fold text=' n' expand='false'>.setN</fold>ote<fold text=' = ' expand='false'>(</fold>"gift")<fold text='' expand='false'>.build()</fold>;
    }</fold>

    public void creates() <fold text='{...}' expand='true'>{
        Order fresh = Order<fold text='(' expand='false'>.builder()</fold>
            <fold text='i' expand='false'>    .setI</fold>d<fold text=' = ' expand='false'>(</fold>7<fold text=',' expand='false'>)</fold>
            <fold text='s' expand='false'>    .setS</fold>tatus<fold text=' = ' expand='false'>(</fold>Order.Status.NEW<fold text='' expand='false'>)</fold>
        <fold text='' expand='false'>        .build(</fold>);
    }</fold>

    public void notFolded(Order order) <fold text='{...}' expand='true'>{
        Order tracked = order.toBuilder().setStatus(Order.Status.SHIPPED).setTracking("T-1").build();
        Order created = Order.builder().setTracking("T-2").build();
    }</fold>

    private Order save(Order order)<fold text=' { ' expand='false'> {
        </fold>return order;<fold text=' }' expand='false'>
    }</fold>

    abstract static class BaseBuilder<T, B extends BaseBuilder<T, B>> <fold text='{...}' expand='true'>{
        @SuppressWarnings("unchecked")
        protected final B self()<fold text=' { ' expand='false'> {
            </fold>return (B) this;<fold text=' }' expand='false'>
        }</fold>

        public abstract T build();
    }</fold>

    static class Order <fold text='{...}' expand='true'>{
        private final long id;
        private final Status status;
        private final String note;

        private Order(AbstractOrderBuilder<?, ?> source) <fold text='{...}' expand='true'>{
            this.id = source.id;
            this.status = source.status;
            this.note = source.note;
        }</fold>

        public static Builder builder()<fold text=' { ' expand='false'> {
            </fold>return new Builder();<fold text=' }' expand='false'>
        }</fold>

        public Builder toBuilder()<fold text=' { ' expand='false'> {
            </fold>return new Builder(this);<fold text=' }' expand='false'>
        }</fold>

        enum Status <fold text='{...}' expand='true'>{
            NEW, SHIPPED
        }</fold>

        abstract static class AbstractOrderBuilder<C extends Order, B extends AbstractOrderBuilder<C, B>>
                extends BaseBuilder<C, B> <fold text='{...}' expand='true'>{
            private long id;
            private Status status;
            private String note;
            private String tracking;

            AbstractOrderBuilder() <fold text='{}' expand='true'>{
            }</fold>

            AbstractOrderBuilder(Order source) <fold text='{...}' expand='true'>{
                this.id = source.id;
                this.status = source.status;
                this.note = source.note;
            }</fold>

            public B setId(long id) <fold text='{...}' expand='true'>{
                this.id = id;
                return self();
            }</fold>

            public B setStatus(Status status) <fold text='{...}' expand='true'>{
                this.status = status;
                return self();
            }</fold>

            public B setNote(String note) <fold text='{...}' expand='true'>{
                this.note = note;
                return self();
            }</fold>

            public B setTracking(String tracking) <fold text='{...}' expand='true'>{
                this.tracking = tracking;
                return self();
            }</fold>
        }</fold>

        static final class Builder extends AbstractOrderBuilder<Order, Builder> <fold text='{...}' expand='true'>{
            private Builder() <fold text='{}' expand='true'>{
            }</fold>

            private Builder(Order source)<fold text=' { ' expand='false'> {
                </fold>super(source);<fold text=' }' expand='false'>
            }</fold>

            @Override
            public Order build()<fold text=' { ' expand='false'> {
                </fold>return new Order(this);<fold text=' }' expand='false'>
            }</fold>
        }</fold>
    }</fold>
}
