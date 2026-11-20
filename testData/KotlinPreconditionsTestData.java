package data;

import <fold text='...' expand='false'>java.util.Objects;

import static java.util.Objects.requireNonNull;</fold>

public class KotlinPreconditionsTestData {

    private final String name;
    private boolean connected;

    public KotlinPreconditionsTestData(String name)<fold text=' { ' expand='false'> {
        </fold>this.name = <fold text='requireNotNull(' expand='false'>Objects.requireNonNull(</fold>name<fold text=') { ' expand='false'>, </fold>"name"<fold text=' }' expand='false'>)</fold>;<fold text=' }' expand='false'>
    }</fold>

    public void ship(String order, String customer, int quantity) <fold text='{...}' expand='true'>{
        <fold text='require(' expand='false'>if (</fold>order <fold text='!=' expand='false'>==</fold> null<fold text=') ' expand='false'>) </fold><fold text='{...}' expand='true'>{<fold text=' ' expand='false'>
            throw new IllegalArgumentException(</fold>"order"<fold text=' ' expand='false'>);
        </fold>}</fold>
        <fold text='requireNotNull(' expand='false'>Objects.requireNonNull(</fold>customer<fold text=') { ' expand='false'>, </fold>"customer"<fold text=' }' expand='false'>)</fold>;
        <fold text='requireNotNull(' expand='false'>requireNonNull(</fold>name);
        <fold text='require(' expand='false'>if (</fold>quantity <fold text='>' expand='false'><=</fold> 0<fold text=') { ' expand='false'>)
            throw new IllegalArgumentException(</fold>"Quantity must be positive: " + quantity<fold text=' }' expand='false'>);</fold>
        <fold text='require(' expand='false'>if (</fold><fold text='' expand='false'>!</fold><fold text='' expand='false'>(</fold>quantity < 100<fold text='' expand='false'>)</fold><fold text=')' expand='false'>) <fold text='{...}' expand='true'>{
            throw new IllegalArgumentException();
        }</fold></fold>
    }</fold>

    public void send(String message) <fold text='{...}' expand='true'>{
        <fold text='check(' expand='false'>if (</fold><fold text='' expand='false'>!</fold>connected<fold text=') ' expand='false'>) </fold><fold text='{...}' expand='true'>{<fold text=' ' expand='false'>
            throw new IllegalStateException(</fold>"Not connected"<fold text=' ' expand='false'>);
        </fold>}</fold>
        <fold text='check(' expand='false'>if (</fold>message <fold text='==' expand='false'>!=</fold> null<fold text=') ' expand='false'>) </fold><fold text='{...}' expand='true'>{<fold text=' ' expand='false'>
            throw new IllegalStateException(</fold>"Already sent " + message<fold text=' ' expand='false'>);
        </fold>}</fold>
    }</fold>

    public void restock(String sku, int stock, long reserved, Integer limit, char grade, double price, Object item) <fold text='{...}' expand='true'>{
        <fold text='require(' expand='false'>if (</fold>stock <fold text='>=' expand='false'><</fold> 0<fold text=') ' expand='false'>) </fold><fold text='{...}' expand='true'>{<fold text=' ' expand='false'>
            throw new IllegalArgumentException(</fold>"stock"<fold text=' ' expand='false'>);
        </fold>}</fold>
        <fold text='check(' expand='false'>if (</fold>reserved <fold text='<=' expand='false'>></fold> stock<fold text=') ' expand='false'>) </fold><fold text='{...}' expand='true'>{<fold text=' ' expand='false'>
            throw new IllegalStateException(</fold>"over-reserved"<fold text=' ' expand='false'>);
        </fold>}</fold>
        <fold text='require(' expand='false'>if (</fold>grade <fold text='<' expand='false'>>=</fold> 'C'<fold text=') ' expand='false'>) </fold><fold text='{...}' expand='true'>{<fold text=' ' expand='false'>
            throw new IllegalArgumentException(</fold>"grade"<fold text=' ' expand='false'>);
        </fold>}</fold>
        <fold text='require(' expand='false'>if (</fold>limit <fold text='==' expand='false'>!=</fold> null <fold text='||' expand='false'>&&</fold> limit <fold text='<' expand='false'>>=</fold> 10<fold text=') ' expand='false'>) </fold><fold text='{...}' expand='true'>{<fold text=' ' expand='false'>
            throw new IllegalArgumentException(</fold>"limit"<fold text=' ' expand='false'>);
        </fold>}</fold>
        <fold text='require(' expand='false'>if (</fold>sku <fold text='!=' expand='false'>==</fold> null <fold text='&& !' expand='false'>|| </fold>sku.isEmpty()<fold text=') ' expand='false'>) </fold><fold text='{...}' expand='true'>{<fold text=' ' expand='false'>
            throw new IllegalArgumentException(</fold>"sku"<fold text=' ' expand='false'>);
        </fold>}</fold>
        <fold text='check(!' expand='false'>if (</fold>sku.isBlank() <fold text='&&' expand='false'>||</fold> <fold text='' expand='false'>!</fold>(stock < 5 || reserved == 0)<fold text=') ' expand='false'>) </fold><fold text='{...}' expand='true'>{<fold text=' ' expand='false'>
            throw new IllegalStateException(</fold>"restock"<fold text=' ' expand='false'>);
        </fold>}</fold>
        <fold text='check(' expand='false'>if (</fold><fold text='' expand='false'>!</fold><fold text='' expand='false'>(</fold>!connected<fold text='' expand='false'>)</fold><fold text=') ' expand='false'>) </fold><fold text='{...}' expand='true'>{<fold text=' ' expand='false'>
            throw new IllegalStateException(</fold>"connected"<fold text=' ' expand='false'>);
        </fold>}</fold>
        <fold text='require(!(' expand='false'>if (</fold>price <= 0<fold text=')) ' expand='false'>) </fold><fold text='{...}' expand='true'>{<fold text=' ' expand='false'>
            throw new IllegalArgumentException(</fold>"price"<fold text=' ' expand='false'>);
        </fold>}</fold>
        <fold text='require(!(' expand='false'>if (</fold>sku == null || price < 1.0<fold text=')) ' expand='false'>) </fold><fold text='{...}' expand='true'>{<fold text=' ' expand='false'>
            throw new IllegalArgumentException(</fold>"cheap"<fold text=' ' expand='false'>);
        </fold>}</fold>
        <fold text='require(!(' expand='false'>if (</fold>sku == null || stock < 0 && reserved > 0<fold text=')) ' expand='false'>) </fold><fold text='{...}' expand='true'>{<fold text=' ' expand='false'>
            throw new IllegalArgumentException(</fold>"mixed"<fold text=' ' expand='false'>);
        </fold>}</fold>
        <fold text='require(!(' expand='false'>if (</fold>item instanceof String<fold text=')) ' expand='false'>) </fold><fold text='{...}' expand='true'>{<fold text=' ' expand='false'>
            throw new IllegalArgumentException(</fold>"item"<fold text=' ' expand='false'>);
        </fold>}</fold>
    }</fold>

    public String describe(int state) <fold text='{...}' expand='true'>{
        switch (state) <fold text='{...}' expand='true'>{
            case 0:
                return "new";
            case 1:
                return "done";
            default:
                <fold text='error(' expand='false'>throw new IllegalStateException(</fold>"Unknown state " + state<fold text=')' expand='false'>)</fold>;
        }</fold>
    }</fold>

    public void notFolded(String order, Exception cause) <fold text='{...}' expand='true'>{
        if (order == null) <fold text='{...}' expand='true'>{
            System.out.println("missing order");
            throw new IllegalArgumentException("order");
        }</fold>
        if (order.isEmpty()) <fold text='{...}' expand='true'>{
            throw new UnsupportedOperationException("order");
        }</fold>
        if (order.isBlank()) <fold text='{...}' expand='true'>{
            throw new IllegalArgumentException("order", cause);
        }</fold>
        if (connected) <fold text='{...}' expand='true'>{
            <fold text='error(' expand='false'>throw new IllegalStateException(</fold>"connected"<fold text=')' expand='false'>)</fold>;
        }</fold> else <fold text='{...}' expand='true'>{
            connected = true;
        }</fold>
        Objects.requireNonNull(order, () -> "order");
    }</fold>
}
