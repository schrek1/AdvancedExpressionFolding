package data;

import <fold text='...' expand='false'>java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;</fold>

public class LazyPropertyTestData {

    private <fold text='val' expand='false'>Properties</fold> config<fold text=' by lazy ' expand='false'>;

    Properties config() </fold><fold text='{...}' expand='true'>{<fold text=' ' expand='false'>
        if (config == null) </fold><fold text='' expand='false'><fold text='{...}' expand='true'>{</fold><fold text='' expand='false'>
            config = </fold>loadConfig()<fold text='' expand='false'>;
        </fold><fold text='' expand='false'>}</fold></fold><fold text=' ' expand='false'>
        return config;
    </fold>}</fold>

    private <fold text='val' expand='false'>List<String></fold> categories<fold text=' by lazy ' expand='false'>;

    public List<String> getCategories() </fold><fold text='{...}' expand='true'>{<fold text=' ' expand='false'>
        if (null == this.categories)
            this.categories = </fold>new ArrayList<>(List.of("books", "games"))<fold text=' ' expand='false'>;
        return this.categories;
    </fold>}</fold>

    private static <fold text='val' expand='false'>Map<String, Integer></fold> stock<fold text=' by lazy ' expand='false'>;

    static Map<String, Integer> stock() </fold><fold text='{...}' expand='true'>{<fold text=' ' expand='false'>
        if (stock == null) </fold><fold text='' expand='false'><fold text='{...}' expand='true'>{</fold><fold text='' expand='false'>
            stock = </fold>Map.of("apple", 3,
                    "pear", 5)<fold text='' expand='false'>;
        </fold><fold text='' expand='false'>}</fold></fold><fold text=' ' expand='false'>
        return stock;
    </fold>}</fold>

    // stays: the field already has a value
    private List<String> tags = new ArrayList<>();

    List<String> tags() <fold text='{...}' expand='true'>{
        if (tags == null) <fold text='{...}' expand='true'>{
            tags = new ArrayList<>();
        }</fold>
        return tags;
    }</fold>

    // stays: the getter does more than initialize
    private String currency;

    String currency() <fold text='{...}' expand='true'>{
        if (currency == null) <fold text='{...}' expand='true'>{
            currency = "EUR";
            System.out.println("currency set");
        }</fold>
        return currency;
    }</fold>

    // stays: the getter has an else branch
    private String region;

    String region() <fold text='{...}' expand='true'>{
        if (region == null) <fold text='{...}' expand='true'>{
            region = "EU";
        }</fold> else <fold text='{...}' expand='true'>{
            region = region.trim();
        }</fold>
        return region;
    }</fold>

    // stays: the field is reset elsewhere
    private String report;

    String report() <fold text='{...}' expand='true'>{
        if (report == null) <fold text='{...}' expand='true'>{
            report = buildReport();
        }</fold>
        return report;
    }</fold>

    void clearReport()<fold text=' { ' expand='false'> {
        </fold>report = null;<fold text=' }' expand='false'>
    }</fold>

    // stays: the getter is not right below the field
    private String label;

    String describe()<fold text=' { ' expand='false'> {
        </fold>return "shop";<fold text=' }' expand='false'>
    }</fold>

    String label() <fold text='{...}' expand='true'>{
        if (label == null) <fold text='{...}' expand='true'>{
            label = describe();
        }</fold>
        return label;
    }</fold>

    // stays: the field is not private
    String owner;

    String owner() <fold text='{...}' expand='true'>{
        if (owner == null) <fold text='{...}' expand='true'>{
            owner = "shop";
        }</fold>
        return owner;
    }</fold>

    private Properties loadConfig()<fold text=' { ' expand='false'> {
        </fold>return new Properties();<fold text=' }' expand='false'>
    }</fold>

    private String buildReport()<fold text=' { ' expand='false'> {
        </fold>return "report";<fold text=' }' expand='false'>
    }</fold>
}
