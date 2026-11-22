package data;

import <fold text='...' expand='false'>java.util.Optional;
import java.util.function.Supplier;</fold>

@SuppressWarnings("ALL")
public class OptionalOrElseThrowTestData {

    public Shape byCode(String code) <fold text='{...}' expand='true'>{
        return Shape.findByCode(code)
                <fold text='?: throw ' expand='false'>.orElseThrow(() -> new </fold>IllegalArgumentException("Unsupported code <fold text='$' expand='false'>" + </fold>code<fold text='")' expand='false'>)</fold><fold text='' expand='false'>)</fold>;
    }</fold>

    public Shape byId(long id) <fold text='{...}' expand='true'>{
        return repository.findById(id)
                <fold text='?: error' expand='false'>.orElseThrow(() -> new IllegalStateException</fold>("Missing shape <fold text='$' expand='false'>" + </fold>id<fold text='")' expand='false'>)</fold><fold text='' expand='false'>)</fold>;
    }</fold>

    public Shape byKey(String key)<fold text=' { ' expand='false'> {
        </fold>return lookup(key)<fold text=' ?: throw ' expand='false'>.orElseThrow(</fold>NotFoundException<fold text='()' expand='false'>::new)</fold>;<fold text=' }' expand='false'>
    }</fold>

    public Shape plainMessage(String key) <fold text='{...}' expand='true'>{
        return lookup(key)<fold text=' ?: error' expand='false'>.orElseThrow(() -> new IllegalStateException</fold>("No shape")<fold text='' expand='false'>)</fold>;
    }</fold>

    public Shape withCause(String key, Exception cause) <fold text='{...}' expand='true'>{
        return lookup(key)<fold text=' ?: throw ' expand='false'>.orElseThrow(() -> new </fold>IllegalStateException("No shape <fold text='$' expand='false'>" + </fold>key<fold text='",' expand='false'>,</fold> cause)<fold text='' expand='false'>)</fold>;
    }</fold>

    public Shape stateWithoutMessage(String key)<fold text=' { ' expand='false'> {
        </fold>return lookup(key)<fold text=' ?: throw ' expand='false'>.orElseThrow(() -> new </fold>IllegalStateException()<fold text='' expand='false'>)</fold>;<fold text=' }' expand='false'>
    }</fold>

    public Shape stateConstructorReference(String key)<fold text=' { ' expand='false'> {
        </fold>return lookup(key)<fold text=' ?: throw ' expand='false'>.orElseThrow(</fold>IllegalStateException<fold text='()' expand='false'>::new)</fold>;<fold text=' }' expand='false'>
    }</fold>

    public Shape noArgument(String key)<fold text=' { ' expand='false'> {
        </fold>return lookup(key)<fold text=' ?: throw ' expand='false'>.orElseThrow(() -> new </fold>NotFoundException()<fold text='' expand='false'>)</fold>;<fold text=' }' expand='false'>
    }</fold>

    public Shape twoArguments(String key)<fold text=' { ' expand='false'> {
        </fold>return lookup(key)<fold text=' ?: throw ' expand='false'>.orElseThrow(() -> new </fold>NotFoundException("shape", key)<fold text='' expand='false'>)</fold>;<fold text=' }' expand='false'>
    }</fold>

    public Shape stillAssertion(String key)<fold text=' { ' expand='false'> {
        </fold>return lookup(key)<fold text='!!' expand='false'>.orElseThrow()</fold>;<fold text=' }' expand='false'>
    }</fold>

    public Shape blockLambda(String key) <fold text='{...}' expand='true'>{
        return lookup(key).orElseThrow(() -> <fold text='{...}' expand='true'>{
            return new NotFoundException();
        }</fold>);
    }</fold>

    public Shape supplierVariable(String key, Supplier<NotFoundException> notFound) <fold text='{...}' expand='true'>{
        return lookup(key).orElseThrow(notFound);
    }</fold>

    public Shape factoryMethod(String key)<fold text=' { ' expand='false'> {
        </fold>return lookup(key).orElseThrow(NotFoundException::forKey);<fold text=' }' expand='false'>
    }</fold>

    private Optional<Shape> lookup(String key)<fold text=' { ' expand='false'> {
        </fold>return Optional.empty();<fold text=' }' expand='false'>
    }</fold>

    private final Repository repository = new Repository();

    static class Repository <fold text='{...}' expand='true'>{
        Optional<Shape> findById(long id)<fold text=' { ' expand='false'> {
            </fold>return Optional.empty();<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    static class Shape <fold text='{...}' expand='true'>{
        static Optional<Shape> findByCode(String code)<fold text=' { ' expand='false'> {
            </fold>return Optional.empty();<fold text=' }' expand='false'>
        }</fold>
    }</fold>

    static class NotFoundException extends RuntimeException <fold text='{...}' expand='true'>{
        NotFoundException() <fold text='{}' expand='true'>{
        }</fold>

        NotFoundException(String kind, String key)<fold text=' { ' expand='false'> {
            </fold>super<fold text='("$' expand='false'>(</fold>kind<fold text='' expand='false'> + "</fold> <fold text='$' expand='false'>" + </fold>key<fold text='")' expand='false'>)</fold>;<fold text=' }' expand='false'>
        }</fold>

        static NotFoundException forKey()<fold text=' { ' expand='false'> {
            </fold>return new NotFoundException();<fold text=' }' expand='false'>
        }</fold>
    }</fold>
}
