package data;

import <fold text='...' expand='false'>java.util.Optional;
import java.util.function.Supplier;</fold>

@SuppressWarnings("ALL")
public class OptionalOrElseThrowTestData {

    public Shape byCode(String code) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>Shape.findByCode(code)
                <fold text='?: throw ' expand='false'>.orElseThrow(() -> new </fold>IllegalArgumentException("Unsupported code <fold text='$' expand='false'>" + </fold>code<fold text='")' expand='false'>)</fold><fold text='' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    public Shape byId(long id) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>repository.findById(id)
                <fold text='?: error' expand='false'>.orElseThrow(() -> new IllegalStateException</fold>("Missing shape <fold text='$' expand='false'>" + </fold>id<fold text='")' expand='false'>)</fold><fold text='' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    public Shape byKey(String key)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>lookup(key)<fold text=' ?: throw ' expand='false'>.orElseThrow(</fold>NotFoundException<fold text='()' expand='false'>::new)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public Shape plainMessage(String key) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>lookup(key)<fold text=' ?: error' expand='false'>.orElseThrow(() -> new IllegalStateException</fold>("No shape")<fold text='' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    public Shape withCause(String key, Exception cause) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>lookup(key)<fold text=' ?: throw ' expand='false'>.orElseThrow(() -> new </fold>IllegalStateException("No shape <fold text='$' expand='false'>" + </fold>key<fold text='",' expand='false'>,</fold> cause)<fold text='' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    public Shape stateWithoutMessage(String key)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>lookup(key)<fold text=' ?: throw ' expand='false'>.orElseThrow(() -> new </fold>IllegalStateException()<fold text='' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public Shape stateConstructorReference(String key)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>lookup(key)<fold text=' ?: throw ' expand='false'>.orElseThrow(</fold>IllegalStateException<fold text='()' expand='false'>::new)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public Shape noArgument(String key)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>lookup(key)<fold text=' ?: throw ' expand='false'>.orElseThrow(() -> new </fold>NotFoundException()<fold text='' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public Shape twoArguments(String key)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>lookup(key)<fold text=' ?: throw ' expand='false'>.orElseThrow(() -> new </fold>NotFoundException("shape", key)<fold text='' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public Shape stillAssertion(String key)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>lookup(key)<fold text='!!' expand='false'>.orElseThrow()</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    public Shape singleReturnBlock(String key) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>lookup(key)<fold text=' ?: throw ' expand='false'>.orElseThrow(() -> </fold><fold text='' expand='false'><fold text='{...}' expand='true'>{
            return new </fold>NotFoundException()<fold text='' expand='false'>;
        }</fold></fold><fold text='' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    public Shape singleReturnBlockState(String key) <fold text='{...}' expand='true'>{
        return lookup(key)
                <fold text='?: error' expand='false'>.orElseThrow(() -> </fold><fold text='' expand='false'><fold text='{...}' expand='true'>{
                    return new IllegalStateException</fold>("No shape <fold text='$' expand='false'>" + </fold>key<fold text='")' expand='false'>)</fold><fold text='' expand='false'>;
                }</fold></fold><fold text='' expand='false'>)</fold>;
    }</fold>

    public Shape multiStatementBlock(String key) <fold text='{...}' expand='true'>{
        return lookup(key).orElseThrow<fold text='' expand='false'>(() -></fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println("missing <fold text='$' expand='false'>" + </fold>key<fold text='")' expand='false'>)</fold>;
            return <fold text='' expand='false'>new </fold>NotFoundException();
        }</fold><fold text='' expand='false'>)</fold>;
    }</fold>

    public Shape commentedBlock(String key) <fold text='{...}' expand='true'>{
        return lookup(key).orElseThrow<fold text='' expand='false'>(() -></fold> <fold text='{...}' expand='true'>{
            // lookups by key are case-sensitive
            return <fold text='' expand='false'>new </fold>NotFoundException();
        }</fold><fold text='' expand='false'>)</fold>;
    }</fold>

    public Shape supplierVariable(String key, Supplier<NotFoundException> notFound) <fold text='{...}' expand='true'>{<fold text=' ' expand='true'>
        </fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>lookup(key).orElseThrow(notFound)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'>
    </fold>}</fold>

    public Shape factoryMethod(String key)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>lookup(key).orElseThrow(NotFoundException::forKey)<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    private Optional<Shape> lookup(String key)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>Optional.empty()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold>

    private final Repository repository =<fold text=' ::' expand='true'> </fold>new<fold text='' expand='true'> </fold><fold text='' expand='true'>Repository</fold><fold text='' expand='true'>()</fold>;

    static class Repository <fold text='{...}' expand='true'>{
        Optional<Shape> findById(long id)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>Optional.empty()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>

    static class Shape <fold text='{...}' expand='true'>{
        static Optional<Shape> findByCode(String code)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>Optional.empty()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>

    <fold text='@NoArgsConstructor(default) s' expand='false'>s</fold>tatic class NotFoundException extends RuntimeException <fold text='{...}' expand='true'>{<fold text='' expand='false'>
        </fold><fold text='' expand='false'>NotFoundException() <fold text='{}' expand='true'>{
        }</fold></fold>

        NotFoundException(String kind, String key)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold>super<fold text='("$' expand='false'>(</fold>kind<fold text='' expand='false'> + "</fold> <fold text='$' expand='false'>" + </fold>key<fold text='")' expand='false'>)</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>

        static NotFoundException forKey()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold><fold text='' expand='false'>new </fold>NotFoundException()<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>
}
