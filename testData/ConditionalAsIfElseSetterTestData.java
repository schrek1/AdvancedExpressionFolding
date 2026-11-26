package data;

@SuppressWarnings("ALL")
public class ConditionalAsIfElseSetterTestData {

    // J1 - a long ternary as the argument of a setter, on one line
    void copyProductName(Order order, Fixed fixedFields, Template template, Request request) <fold text='{...}' expand='true'>{
        order.<fold text='p' expand='false'>setP</fold>roductName<fold text=' = ' expand='false'>(</fold>fixedFields.<fold text='p' expand='false'>isP</fold>roductName<fold text='' expand='false'>()</fold> ? template.<fold text='p' expand='false'>getP</fold>roductName<fold text='' expand='false'>()</fold> : request.productNameOr("n/a")<fold text='' expand='false'>)</fold>;
    }</fold>

    // J2 - the same ternary wrapped over several lines
    void copyNotifyUrl(Order order, Fixed fixedFields, Template template, Request request) <fold text='{...}' expand='true'>{
        order.<fold text='n' expand='false'>setN</fold>otifyUrl<fold text=' = ' expand='false'>(</fold>fixedFields.<fold text='n' expand='false'>isN</fold>otifyUrl<fold text='' expand='false'>()</fold>
                ? template.<fold text='n' expand='false'>getN</fold>otifyUrl<fold text='' expand='false'>()</fold>
                : request.notifyUrlOr("n/a")<fold text='' expand='false'>)</fold>;
    }</fold>

    // J3 - the ternary on a line of its own, after the opening parenthesis
    void copyCallbackUrl(Order order, Fixed fixedFields, Template template, Request request) <fold text='{...}' expand='true'>{
        order.<fold text='c' expand='false'>setC</fold>allbackUrl<fold text=' =' expand='false'>(
               </fold><fold text=' if (' expand='false'> </fold>fixedFields.<fold text='c' expand='false'>isC</fold>allbackUrl<fold text='' expand='false'>()</fold><fold text=')' expand='false'> ?</fold> template.<fold text='c' expand='false'>getC</fold>allbackUrl<fold text='' expand='false'>()</fold> <fold text='else' expand='false'>:</fold> request.callbackUrlOr("n/a")<fold text='' expand='false'>)</fold>;
    }</fold>

    static class Order <fold text='{...}' expand='true'>{
        void setProductName(String productName) <fold text='{}' expand='true'>{
        }</fold>

        void setNotifyUrl(String notifyUrl) <fold text='{}' expand='true'>{
        }</fold>

        void setCallbackUrl(String callbackUrl) <fold text='{}' expand='true'>{
        }</fold>
    }</fold>

    static class Fixed <fold text='{...}' expand='true'>{
        boolean isProductName()<fold text=' { ' expand='false'> {
            </fold>return true;<fold text=' }' expand='false'>
        }</fold>

        boolean isNotifyUrl()<fold text=' { ' expand='false'> {
            </fold>return true;<fold text=' }' expand='false'>
        }</fold>

        boolean isCallbackUrl()<fold text=' { ' expand='false'> {
            </fold>return true;<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    static class Template <fold text='{...}' expand='true'>{
        String getProductName()<fold text=' { ' expand='false'> {
            </fold>return "template";<fold text=' }' expand='false'>
        }</fold>

        String getNotifyUrl()<fold text=' { ' expand='false'> {
            </fold>return "template";<fold text=' }' expand='false'>
        }</fold>

        String getCallbackUrl()<fold text=' { ' expand='false'> {
            </fold>return "template";<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    static class Request <fold text='{...}' expand='true'>{
        String productNameOr(String fallback)<fold text=' { ' expand='false'> {
            </fold>return fallback;<fold text=' }' expand='false'>
        }</fold>

        String notifyUrlOr(String fallback)<fold text=' { ' expand='false'> {
            </fold>return fallback;<fold text=' }' expand='false'>
        }</fold>

        String callbackUrlOr(String fallback)<fold text=' { ' expand='false'> {
            </fold>return fallback;<fold text=' }' expand='false'>
        }</fold>
    }</fold>
}
