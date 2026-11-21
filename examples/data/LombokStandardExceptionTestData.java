package data;

public class LombokStandardExceptionTestData {

    public static class PaymentException extends RuntimeException {
        public PaymentException() { super(); }
        public PaymentException(String message) { super(message); }
        public PaymentException(Throwable cause) { super(cause); }
        public PaymentException(String message, Throwable cause) { super(message, cause); }
    }

    public static class StockException extends Exception {
        private static final long serialVersionUID = 1L;

        public StockException() {
        }

        public StockException(String message) {
            super(message);
        }

        public StockException(Throwable cause) {
            super(cause);
        }

        public StockException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class OrderException extends RuntimeException {
        public OrderException() { super(); }
        public OrderException(String message) { super(message); }
        public OrderException(Throwable cause) { super(cause); }
        public OrderException(String message, Throwable cause) { super(message, cause); }

        public OrderException(String orderId, int quantity) {
            super("Order " + orderId + " cannot take " + quantity + " items");
        }
    }

    public static class ShippingException extends RuntimeException {
        public ShippingException() { super(); }
        public ShippingException(String message) { super("Shipping failed: " + message); }
        public ShippingException(Throwable cause) { super(cause); }
        public ShippingException(String message, Throwable cause) { super(message, cause); }
    }

    public static class PartialException extends RuntimeException {
        public PartialException(String message) { super(message); }
        public PartialException(String message, Throwable cause) { super(message, cause); }
    }

    public static class InternalException extends RuntimeException {
        protected InternalException() { super(); }
        protected InternalException(String message) { super(message); }
        protected InternalException(Throwable cause) { super(cause); }
        protected InternalException(String message, Throwable cause) { super(message, cause); }
    }

    public static class CommentedException extends RuntimeException {
        public CommentedException() { super(); }
        public CommentedException(String message) {
            // keep the message as it comes from the shop
            super(message);
        }
        public CommentedException(Throwable cause) { super(cause); }
        public CommentedException(String message, Throwable cause) { super(message, cause); }
    }

    public static class Notice {
        public Notice() { }
        public Notice(String message) { }
        public Notice(Throwable cause) { }
        public Notice(String message, Throwable cause) { }
    }

    public static class PersonNotice extends Notice {
        public PersonNotice() { super(); }
        public PersonNotice(String message) { super(message); }
        public PersonNotice(Throwable cause) { super(cause); }
        public PersonNotice(String message, Throwable cause) { super(message, cause); }
    }
}
