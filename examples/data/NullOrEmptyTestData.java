package data;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
public class NullOrEmptyTestData {
    private String title;
    private List<String> tags;

    public void folded(String name, List<String> items, Map<String, Integer> stock, StringBuilder buffer) {
        if (name == null || name.isEmpty()) {
            System.out.println("no name");
        }
        if (items != null && !items.isEmpty()) {
            System.out.println(items.get(0));
        }
        boolean noStock = stock == null || stock.isEmpty();
        boolean blankName = name == null || name.length() == 0;
        boolean hasItems = items != null && items.size() > 0;
        boolean anyItems = items != null && items.size() != 0;
        boolean noBuffer = buffer == null || buffer.length() == 0;
        if (title == null || title.isEmpty()) {
            title = "untitled";
        }
        if (this.tags != null && !this.tags.isEmpty()) {
            System.out.println(this.tags);
        }
        while (items != null && !items.isEmpty()) {
            items.remove(0);
        }
    }

    public boolean insideLongerConditions(String name, List<String> items, boolean force) {
        if (force || name == null || name.isEmpty()) {
            return true;
        }
        if (items != null && !items.isEmpty() && force) {
            return false;
        }
        return name == null || name.isEmpty() ? items == null || items.isEmpty() : force;
    }

    public void notFolded(String name, String other, List<String> items, NullOrEmptyTestData data) {
        boolean mixedVariables = name == null || other.isEmpty();
        boolean wrongOperator = name == null && name.isEmpty();
        boolean missingNegation = items != null && items.isEmpty();
        boolean callReceiver = data.getTitle() == null || data.getTitle().isEmpty();
        boolean otherObjectField = data.title == null || data.title.isEmpty();
        boolean notZero = items == null || items.size() == 1;
        boolean reversedOrder = name.isEmpty() || name == null;
    }

    public void utilityChecks(String name, List<String> items, Map<String, Integer> stock, Object[] codes) {
        if (CollectionUtils.isEmpty(items)) {
            System.out.println("no items");
        }
        if (StringUtils.isNotEmpty(name)) {
            System.out.println(name);
        }
        boolean noTitle = StringUtils.isEmpty(getTitle());
        boolean hasItems = CollectionUtils.isNotEmpty(items);
        boolean anyItems = !CollectionUtils.isEmpty(items);
        boolean noStock = org.apache.commons.collections4.MapUtils.isEmpty(stock);
        boolean anyStock = org.apache.commons.collections4.MapUtils.isNotEmpty(stock);
        boolean noTags = org.springframework.util.CollectionUtils.isEmpty(tags);
        boolean noCodes = ObjectUtils.isEmpty(codes);
    }

    public void utilityChecksNotFolded(String name, List<String> items, String other) {
        boolean blank = StringUtils.isBlank(name);
        boolean unrelatedClass = MapUtils.isEmpty(items);
        boolean concatenated = StringUtils.isEmpty(name + other);
    }

    public String getTitle() {
        return title;
    }

    static class MapUtils {
        static boolean isEmpty(List<String> values) {
            return values.isEmpty();
        }
    }
}
