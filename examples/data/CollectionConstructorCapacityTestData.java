package data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionConstructorCapacityTestData {
    private final List<String> items = new ArrayList<>(10);
    private final Map<String, String> index = new HashMap<>(16);
    private final StringBuilder buffer = new StringBuilder(64);
    private final BigDecimal amount = new BigDecimal("10");
    private final StringBuilder text = new StringBuilder("Hello");

    public List<String> copy(List<String> source) {
        List<String> local = new ArrayList<>(20);
        local.addAll(source);
        return local;
    }
}
