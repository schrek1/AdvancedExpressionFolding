package data;

public class LombokStandardExceptionTestData {

    <fold text='@StandardException p' expand='false'>p</fold>ublic static class PaymentException extends RuntimeExceptio<fold text='n(nothing overridden)' expand='true'>n</fold> <fold text='{...}' expand='true'>{<fold text='' expand='false'>
        </fold><fold text='' expand='false'>public PaymentException() <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold></fold><fold text='' expand='false'>
        </fold><fold text='' expand='false'>public PaymentException(String message) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super(message)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold></fold><fold text='' expand='false'>
        </fold><fold text='' expand='false'>public PaymentException(Throwable cause) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super(cause)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold></fold><fold text='' expand='false'>
        </fold><fold text='' expand='false'>public PaymentException(String message, Throwable cause) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super(message, cause)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold></fold>
    }</fold>

    <fold text='@StandardException @Serial p' expand='false'>p</fold>ublic static class StockException extends Exceptio<fold text='n(nothing overridden)' expand='true'>n</fold> <fold text='{...}' expand='true'>{<fold text='' expand='false'>
        </fold><fold text='' expand='false'>private <fold text='const' expand='false'>static final </fold><fold text='' expand='false'>long</fold> serialVersionUID = 1L;</fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public StockException() <fold text='{}' expand='true'>{
        }</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public StockException(String message)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>super(message)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public StockException(Throwable cause)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>super(cause)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold><fold text='' expand='false'>

        </fold><fold text='' expand='false'>public StockException(String message, Throwable cause)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>super(message, cause)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold></fold>
    }</fold>

    <fold text='@StandardException p' expand='false'>p</fold>ublic static class OrderException extends RuntimeExceptio<fold text='n(nothing overridden)' expand='true'>n</fold> <fold text='{...}' expand='true'>{<fold text='' expand='false'>
        </fold><fold text='' expand='false'>public OrderException() <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold></fold><fold text='' expand='false'>
        </fold><fold text='' expand='false'>public OrderException(String message) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super(message)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold></fold><fold text='' expand='false'>
        </fold><fold text='' expand='false'>public OrderException(Throwable cause) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super(cause)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold></fold><fold text='' expand='false'>
        </fold><fold text='' expand='false'>public OrderException(String message, Throwable cause) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super(message, cause)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold></fold>

        public OrderException(String orderId, int quantity) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
            </fold>super("Order <fold text='$' expand='false'>" + </fold>orderId<fold text='' expand='false'> + "</fold> cannot take <fold text='$' expand='false'>" + </fold>quantity<fold text='' expand='false'> + "</fold> items")<fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
        </fold>}</fold>
    }</fold>

    <fold text='@NoArgsConstructor p' expand='false'>p</fold>ublic static class ShippingException extends RuntimeExceptio<fold text='n(nothing overridden)' expand='true'>n</fold> <fold text='{...}' expand='true'>{<fold text='' expand='false'>
        </fold><fold text='' expand='false'>public ShippingException() <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold></fold>
        public ShippingException(String message) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super("Shipping failed: <fold text='$' expand='false'>" + </fold>message<fold text='")' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold>
        public ShippingException(Throwable cause) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super(cause)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold>
        public ShippingException(String message, Throwable cause) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super(message, cause)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold>
    }</fold>

    public static class PartialException extends RuntimeExceptio<fold text='n(nothing overridden)' expand='true'>n</fold> <fold text='{...}' expand='true'>{
        public PartialException(String message) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super(message)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold>
        public PartialException(String message, Throwable cause) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super(message, cause)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold>
    }</fold>

    <fold text='@NoArgsConstructor(protected) p' expand='false'>p</fold>ublic static class InternalException extends RuntimeExceptio<fold text='n(nothing overridden)' expand='true'>n</fold> <fold text='{...}' expand='true'>{<fold text='' expand='false'>
        </fold><fold text='' expand='false'>protected InternalException() <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold></fold>
        protected InternalException(String message) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super(message)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold>
        protected InternalException(Throwable cause) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super(cause)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold>
        protected InternalException(String message, Throwable cause) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super(message, cause)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold>
    }</fold>

    <fold text='@NoArgsConstructor p' expand='false'>p</fold>ublic static class CommentedException extends RuntimeExceptio<fold text='n(nothing overridden)' expand='true'>n</fold> <fold text='{...}' expand='true'>{<fold text='' expand='false'>
        </fold><fold text='' expand='false'>public CommentedException() <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold></fold>
        public CommentedException(String message) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
            </fold>// keep the message as it comes from the shop<fold text=' ' expand='true'>
            </fold>super(message)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
        </fold>}</fold>
        public CommentedException(Throwable cause) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super(cause)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold>
        public CommentedException(String message, Throwable cause) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super(message, cause)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold>
    }</fold>

    <fold text='@NoArgsConstructor p' expand='false'>p</fold>ublic static class Notice <fold text='{...}' expand='true'>{<fold text='' expand='false'>
        </fold><fold text='' expand='false'>public Notice() <fold text='{}' expand='true'>{ }</fold></fold>
        public Notice(String message) <fold text='{}' expand='true'>{ }</fold>
        public Notice(Throwable cause) <fold text='{}' expand='true'>{ }</fold>
        public Notice(String message, Throwable cause) <fold text='{}' expand='true'>{ }</fold>
    }</fold>

    <fold text='@NoArgsConstructor p' expand='false'>p</fold>ublic static class PersonNotice extends Notic<fold text='e(nothing overridden)' expand='true'>e</fold> <fold text='{...}' expand='true'>{<fold text='' expand='false'>
        </fold><fold text='' expand='false'>public PersonNotice() <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold></fold>
        public PersonNotice(String message) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super(message)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold>
        public PersonNotice(Throwable cause) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super(cause)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold>
        public PersonNotice(String message, Throwable cause) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'> </fold>super(message, cause)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'> </fold>}</fold>
    }</fold>
}
