package data;

import <fold text='...' expand='false'>java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;</fold>

public class CollectionConstructorCapacityTestData {
    private final List<String> items = <fold text='' expand='false'>new </fold>ArrayList<>(10);
    private final Map<String, String> index = <fold text='' expand='false'>new </fold>HashMap<>(16);
    private final StringBuilder buffer =<fold text=' ::' expand='true'> </fold>new<fold text='' expand='true'> </fold><fold text='' expand='true'>StringBuilder</fold>(64);
    private final BigDecimal amount = <fold text='' expand='false'>new BigDecimal(</fold><fold text='10' expand='false'>"10"</fold><fold text='' expand='false'>)</fold>;
    private final StringBuilder text = <fold text='"Hello"' expand='false'>new StringBuilder("Hello")</fold>;

    public List<String> copy(List<String> source) <fold text='{...}' expand='true'>{
        List<String> local = <fold text='' expand='false'>new </fold>ArrayList<>(20);
        local.addAll(source);
        return local;
    }</fold>
}
