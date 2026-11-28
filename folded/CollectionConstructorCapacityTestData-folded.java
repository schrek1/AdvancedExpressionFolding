package data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionConstructorCapacityTestData {
    private final List<String> items = ArrayList<>(10);
    private final Map<String, String> index = HashMap<>(16);
    private final StringBuilder buffer = ::new(64);
    private final BigDecimal amount = 10;
    private final StringBuilder text = "Hello";

    public List<String> copy(List<String> source) {
        List<String> local = ArrayList<>(20);
        local.addAll(source);
        return local;
    }
}
