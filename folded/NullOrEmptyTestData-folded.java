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
        if (name.isNullOrEmpty()) {
            System.out.println("no name");
        }
        if (items.isNotEmpty()) {
            System.out.println(items.get(0));
        }
        boolean noStock = stock.isNullOrEmpty();
        boolean blankName = name.isNullOrEmpty();
        boolean hasItems = items.isNotEmpty();
        boolean anyItems = items.isNotEmpty();
        boolean noBuffer = buffer.isNullOrEmpty();
        if (title.isNullOrEmpty()) {
            title = "untitled";
        }
        if (this.tags.isNotEmpty()) {
            System.out.println(this.tags);
        }
        while (items.isNotEmpty()) {
            items.remove(0);
        }
    }

    public boolean insideLongerConditions(String name, List<String> items, boolean force) {
        if (force || name.isNullOrEmpty()) {
            return true;
        }
        if (items.isNotEmpty() && force) {
            return false;
        }
        return name.isNullOrEmpty() ? items.isNullOrEmpty() : force;
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
        if (items.isNullOrEmpty()) {
            System.out.println("no items");
        }
        if (name.isNotEmpty()) {
            System.out.println(name);
        }
        boolean noTitle = getTitle().isNullOrEmpty();
        boolean hasItems = items.isNotEmpty();
        boolean anyItems = items.isNotEmpty();
        boolean noStock = stock.isNullOrEmpty();
        boolean anyStock = stock.isNotEmpty();
        boolean noTags = tags.isNullOrEmpty();
        boolean noCodes = codes.isNullOrEmpty();
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
