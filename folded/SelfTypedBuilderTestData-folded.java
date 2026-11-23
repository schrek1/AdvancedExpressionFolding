package data;

public class SelfTypedBuilderTestData {

    public void copies(Order order, Order original, Order.Status status) {
        var shipped = save(order.copy(status = Order.Status.SHIPPED));
        Order noted = original.copy(status = status, note = "gift");
    }

    public void creates() {
        Order fresh = Order(
            id = 7,
            status = Order.Status.NEW
        )
    }

    public void notFolded(Order order) {
        Order tracked = order.toBuilder().setStatus(Order.Status.SHIPPED).setTracking("T-1").build();
        Order created = Order.builder().setTracking("T-2").build();
    }

    private Order save(Order order) {
        return order;
    }

    abstract static class BaseBuilder<T, B extends BaseBuilder<T, B>> {
        @SuppressWarnings("unchecked")
        protected final B self() {
            return (B) this;
        }

        public abstract T build();
    }

    static class Order {
        private final long id;
        private final Status status;
        private final String note;

        private Order(AbstractOrderBuilder<?, ?> source) {
            this.id = source.id;
            this.status = source.status;
            this.note = source.note;
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder toBuilder() {
            return new Builder(this);
        }

        enum Status {
            NEW, SHIPPED
        }

        abstract static class AbstractOrderBuilder<C extends Order, B extends AbstractOrderBuilder<C, B>>
                extends BaseBuilder<C, B> {
            private long id;
            private Status status;
            private String note;
            private String tracking;

            AbstractOrderBuilder() {
            }

            AbstractOrderBuilder(Order source) {
                this.id = source.id;
                this.status = source.status;
                this.note = source.note;
            }

            public B setId(long id) {
                this.id = id;
                return self();
            }

            public B setStatus(Status status) {
                this.status = status;
                return self();
            }

            public B setNote(String note) {
                this.note = note;
                return self();
            }

            public B setTracking(String tracking) {
                this.tracking = tracking;
                return self();
            }
        }

        static final class Builder extends AbstractOrderBuilder<Order, Builder> {
            private Builder() {
            }

            private Builder(Order source) {
                super(source);
            }

            @Override
            public Order build() {
                return new Order(this);
            }
        }
    }
}
