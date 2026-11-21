package data;

public class LombokStandardExceptionTestData {

    <fold text='@StandardException p' expand='false'>p</fold>ublic static class PaymentException extends RuntimeException <fold text='{...}' expand='true'>{<fold text='' expand='false'>
        </fold><fold text='' expand='false'>public PaymentException() <fold text='{...}' expand='true'>{ super(); }</fold></fold><fold text='' expand='false'>
        </fold><fold text='' expand='false'>public PaymentException(String message) <fold text='{...}' expand='true'>{ super(message); }</fold></fold><fold text='' expand='false'>
        </fold><fold text='' expand='false'>public PaymentException(Throwable cause) <fold text='{...}' expand='true'>{ super(cause); }</fold></fold><fold text='' expand='false'>
        </fold><fold text='' expand='false'>public PaymentException(String message, Throwable cause) <fold text='{...}' expand='true'>{ super(message, cause); }</fold></fold>
    }</fold>

    <fold text='@StandardException @Serial p' expand='false'>p</fold>ublic static class StockException extends Exception <fold text='{...}' expand='true'>{<fold text='' expand='false'>
        </fold><fold text='' expand='false'>private static final long serialVersionUID = 1L;</fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public StockException() <fold text='{}' expand='true'>{
        }</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public StockException(String message)<fold text=' { ' expand='false'> {
            </fold>super(message);<fold text=' }' expand='false'>
        }</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public StockException(Throwable cause)<fold text=' { ' expand='false'> {
            </fold>super(cause);<fold text=' }' expand='false'>
        }</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public StockException(String message, Throwable cause)<fold text=' { ' expand='false'> {
            </fold>super(message, cause);<fold text=' }' expand='false'>
        }</fold></fold>
    }</fold>

    <fold text='@StandardException p' expand='false'>p</fold>ublic static class OrderException extends RuntimeException <fold text='{...}' expand='true'>{<fold text='' expand='false'>
        </fold><fold text='' expand='false'>public OrderException() <fold text='{...}' expand='true'>{ super(); }</fold></fold><fold text='' expand='false'>
        </fold><fold text='' expand='false'>public OrderException(String message) <fold text='{...}' expand='true'>{ super(message); }</fold></fold><fold text='' expand='false'>
        </fold><fold text='' expand='false'>public OrderException(Throwable cause) <fold text='{...}' expand='true'>{ super(cause); }</fold></fold><fold text='' expand='false'>
        </fold><fold text='' expand='false'>public OrderException(String message, Throwable cause) <fold text='{...}' expand='true'>{ super(message, cause); }</fold></fold>

        public OrderException(String orderId, int quantity) <fold text='{...}' expand='true'>{
            super("Order " + orderId + " cannot take " + quantity + " items");
        }</fold>
    }</fold>

    <fold text='@NoArgsConstructor p' expand='false'>p</fold>ublic static class ShippingException extends RuntimeException <fold text='{...}' expand='true'>{<fold text='' expand='false'>
        </fold><fold text='' expand='false'>public ShippingException() <fold text='{...}' expand='true'>{ super(); }</fold></fold>
        public ShippingException(String message) <fold text='{...}' expand='true'>{ super("Shipping failed: " + message); }</fold>
        public ShippingException(Throwable cause) <fold text='{...}' expand='true'>{ super(cause); }</fold>
        public ShippingException(String message, Throwable cause) <fold text='{...}' expand='true'>{ super(message, cause); }</fold>
    }</fold>

    public static class PartialException extends RuntimeException <fold text='{...}' expand='true'>{
        public PartialException(String message) <fold text='{...}' expand='true'>{ super(message); }</fold>
        public PartialException(String message, Throwable cause) <fold text='{...}' expand='true'>{ super(message, cause); }</fold>
    }</fold>

    <fold text='@NoArgsConstructor(protected) p' expand='false'>p</fold>ublic static class InternalException extends RuntimeException <fold text='{...}' expand='true'>{<fold text='' expand='false'>
        </fold><fold text='' expand='false'>protected InternalException() <fold text='{...}' expand='true'>{ super(); }</fold></fold>
        protected InternalException(String message) <fold text='{...}' expand='true'>{ super(message); }</fold>
        protected InternalException(Throwable cause) <fold text='{...}' expand='true'>{ super(cause); }</fold>
        protected InternalException(String message, Throwable cause) <fold text='{...}' expand='true'>{ super(message, cause); }</fold>
    }</fold>

    <fold text='@NoArgsConstructor p' expand='false'>p</fold>ublic static class CommentedException extends RuntimeException <fold text='{...}' expand='true'>{<fold text='' expand='false'>
        </fold><fold text='' expand='false'>public CommentedException() <fold text='{...}' expand='true'>{ super(); }</fold></fold>
        public CommentedException(String message) <fold text='{...}' expand='true'>{
            // keep the message as it comes from the shop
            super(message);
        }</fold>
        public CommentedException(Throwable cause) <fold text='{...}' expand='true'>{ super(cause); }</fold>
        public CommentedException(String message, Throwable cause) <fold text='{...}' expand='true'>{ super(message, cause); }</fold>
    }</fold>

    <fold text='@NoArgsConstructor p' expand='false'>p</fold>ublic static class Notice <fold text='{...}' expand='true'>{<fold text='' expand='false'>
        </fold><fold text='' expand='false'>public Notice() <fold text='{}' expand='true'>{ }</fold></fold>
        public Notice(String message) <fold text='{}' expand='true'>{ }</fold>
        public Notice(Throwable cause) <fold text='{}' expand='true'>{ }</fold>
        public Notice(String message, Throwable cause) <fold text='{}' expand='true'>{ }</fold>
    }</fold>

    <fold text='@NoArgsConstructor p' expand='false'>p</fold>ublic static class PersonNotice extends Notice <fold text='{...}' expand='true'>{<fold text='' expand='false'>
        </fold><fold text='' expand='false'>public PersonNotice() <fold text='{...}' expand='true'>{ super(); }</fold></fold>
        public PersonNotice(String message) <fold text='{...}' expand='true'>{ super(message); }</fold>
        public PersonNotice(Throwable cause) <fold text='{...}' expand='true'>{ super(cause); }</fold>
        public PersonNotice(String message, Throwable cause) <fold text='{...}' expand='true'>{ super(message, cause); }</fold>
    }</fold>
}
