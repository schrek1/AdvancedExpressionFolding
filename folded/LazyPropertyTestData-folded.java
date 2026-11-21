package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class LazyPropertyTestData {

    private val config by lazy { loadConfig() }

    private val categories by lazy { new ArrayList<>(List.of("books", "games")) }

    private static val stock by lazy { Map.of("apple", 3,
                    "pear", 5) }

    // stays: the field already has a value
    private List<String> tags = new ArrayList<>();

    List<String> tags() {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        return tags;
    }

    // stays: the getter does more than initialize
    private String currency;

    String currency() {
        if (currency == null) {
            currency = "EUR";
            System.out.println("currency set");
        }
        return currency;
    }

    // stays: the getter has an else branch
    private String region;

    String region() {
        if (region == null) {
            region = "EU";
        } else {
            region = region.trim();
        }
        return region;
    }

    // stays: the field is reset elsewhere
    private String report;

    String report() {
        if (report == null) {
            report = buildReport();
        }
        return report;
    }

    void clearReport() {
        report = null;
    }

    // stays: the getter is not right below the field
    private String label;

    String describe() {
        return "shop";
    }

    String label() {
        if (label == null) {
            label = describe();
        }
        return label;
    }

    // stays: the field is not private
    String owner;

    String owner() {
        if (owner == null) {
            owner = "shop";
        }
        return owner;
    }

    private Properties loadConfig() {
        return new Properties();
    }

    private String buildReport() {
        return "report";
    }
}
