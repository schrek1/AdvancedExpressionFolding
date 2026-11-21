package data;

@SuppressWarnings("unused")
public class TodoTestData {

    public void export()<fold text=' { ' expand='false'> {
        </fold><fold text='TODO' expand='false'>throw new UnsupportedOperationException</fold>("Not implemented");<fold text=' }' expand='false'>
    }</fold>

    public void exportWithoutReason()<fold text=' { ' expand='false'> {
        </fold><fold text='TODO' expand='false'>throw new UnsupportedOperationException</fold>();<fold text=' }' expand='false'>
    }</fold>

    public String render(String format) <fold text='{...}' expand='true'>{
        if (format.isEmpty()) <fold text='{...}' expand='true'>{
            return "";
        }</fold>
        <fold text='TODO' expand='false'>throw new UnsupportedOperationException</fold>("Format not supported: " + format);
    }</fold>

    public void qualified()<fold text=' { ' expand='false'> {
        </fold><fold text='TODO' expand='false'>throw new java.lang.UnsupportedOperationException</fold>("Qualified name");<fold text=' }' expand='false'>
    }</fold>

    public void withCause(Exception cause)<fold text=' { ' expand='false'> {
        </fold>throw new UnsupportedOperationException(cause);<fold text=' }' expand='false'>
    }</fold>

    public void withReasonAndCause(Exception cause) <fold text='{...}' expand='true'>{
        throw new UnsupportedOperationException("Not implemented", cause);
    }</fold>

    public void otherException()<fold text=' { ' expand='false'> {
        </fold>throw new IllegalStateException("Not implemented");<fold text=' }' expand='false'>
    }</fold>

    public void subclass()<fold text=' { ' expand='false'> {
        </fold>throw new ReadOnlyException("Read only");<fold text=' }' expand='false'>
    }</fold>

    public void anonymousSubclass() <fold text='{...}' expand='true'>{
        throw new UnsupportedOperationException("Anonymous") <fold text='{...}' expand='true'>{
        }</fold>;
    }</fold>

    public void rethrow(UnsupportedOperationException existing)<fold text=' { ' expand='false'> {
        </fold>throw existing;<fold text=' }' expand='false'>
    }</fold>

    public void guard(int size) <fold text='{...}' expand='true'>{
        if (size > 10) <fold text='{...}' expand='true'>{
            <fold text='TODO' expand='false'>throw new UnsupportedOperationException</fold>("Too large");
        }</fold>
    }</fold>

    static class ReadOnlyException extends UnsupportedOperationException <fold text='{...}' expand='true'>{
        ReadOnlyException(String message)<fold text=' { ' expand='false'> {
            </fold>super(message);<fold text=' }' expand='false'>
        }</fold>
    }</fold>
}
