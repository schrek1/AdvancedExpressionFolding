package data;

public class LombokStandardExceptionTestData {

    @StandardException public static class PaymentException extends RuntimeException {
    }

    @StandardException @Serial public static class StockException extends Exception {
    }

    @StandardException public static class OrderException extends RuntimeException {

        public OrderException(String orderId, int quantity) {
            super("Order " + orderId + " cannot take " + quantity + " items");
        }
    }

    @NoArgsConstructor public static class ShippingException extends RuntimeException {
        public ShippingException(String message) { super("Shipping failed: " + message); }
        public ShippingException(Throwable cause) { super(cause); }
        public ShippingException(String message, Throwable cause) { super(message, cause); }
    }

    public static class PartialException extends RuntimeException {
        public PartialException(String message) { super(message); }
        public PartialException(String message, Throwable cause) { super(message, cause); }
    }

    @NoArgsConstructor(protected) public static class InternalException extends RuntimeException {
        protected InternalException(String message) { super(message); }
        protected InternalException(Throwable cause) { super(cause); }
        protected InternalException(String message, Throwable cause) { super(message, cause); }
    }

    @NoArgsConstructor public static class CommentedException extends RuntimeException {
        public CommentedException(String message) {
            // keep the message as it comes from the shop
            super(message);
        }
        public CommentedException(Throwable cause) { super(cause); }
        public CommentedException(String message, Throwable cause) { super(message, cause); }
    }

    @NoArgsConstructor public static class Notice {
        public Notice(String message) { }
        public Notice(Throwable cause) { }
        public Notice(String message, Throwable cause) { }
    }

    @NoArgsConstructor public static class PersonNotice extends Notice {
        public PersonNotice(String message) { super(message); }
        public PersonNotice(Throwable cause) { super(cause); }
        public PersonNotice(String message, Throwable cause) { super(message, cause); }
    }
}
