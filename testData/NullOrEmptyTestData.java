package data;

import <fold text='...' expand='false'>org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;</fold>

@SuppressWarnings("ALL")
public class NullOrEmptyTestData {
    private String title;
    private List<String> tags;

    public void folded(String name, List<String> items, Map<String, Integer> stock, StringBuilder buffer) <fold text='{...}' expand='true'>{
        if (<fold text='' expand='false'>name == null || </fold>name.<fold text='isNullOrEmpty' expand='false'>isEmpty</fold>()) <fold text='{...}' expand='true'>{
            System.out.println("no name");
        }</fold>
        if (<fold text='' expand='false'>items != null && !</fold>items.<fold text='isNotEmpty' expand='false'>isEmpty</fold>()) <fold text='{...}' expand='true'>{
            System.out.println(items.get(0));
        }</fold>
        boolean noStock = <fold text='' expand='false'>stock == null || </fold>stock.<fold text='isNullOrEmpty' expand='false'>isEmpty</fold>();
        boolean blankName = <fold text='' expand='false'>name == null || </fold>name.<fold text='isNullOrEmpty' expand='false'>length</fold>()<fold text='' expand='false'> == 0</fold>;
        boolean hasItems = <fold text='' expand='false'>items != null && </fold>items.<fold text='isNotEmpty' expand='false'>size</fold>()<fold text='' expand='false'> > 0</fold>;
        boolean anyItems = <fold text='' expand='false'>items != null && </fold>items.<fold text='isNotEmpty' expand='false'>size</fold>()<fold text='' expand='false'> != 0</fold>;
        boolean noBuffer = <fold text='' expand='false'>buffer == null || </fold>buffer.<fold text='isNullOrEmpty' expand='false'>length</fold>()<fold text='' expand='false'> == 0</fold>;
        if (<fold text='' expand='false'>title == null || </fold>title.<fold text='isNullOrEmpty' expand='false'>isEmpty</fold>()) <fold text='{...}' expand='true'>{
            title = "untitled";
        }</fold>
        if (<fold text='' expand='false'>this.tags != null && !</fold>this.tags.<fold text='isNotEmpty' expand='false'>isEmpty</fold>()) <fold text='{...}' expand='true'>{
            System.out.println(this.tags);
        }</fold>
        while (<fold text='' expand='false'>items != null && !</fold>items.<fold text='isNotEmpty' expand='false'>isEmpty</fold>()) <fold text='{...}' expand='true'>{
            items.remove(0);
        }</fold>
    }</fold>

    public boolean insideLongerConditions(String name, List<String> items, boolean force) <fold text='{...}' expand='true'>{
        if (force || <fold text='' expand='false'>name == null || </fold>name.<fold text='isNullOrEmpty' expand='false'>isEmpty</fold>()) <fold text='{...}' expand='true'>{
            return true;
        }</fold>
        if (<fold text='' expand='false'>items != null && !</fold>items.<fold text='isNotEmpty' expand='false'>isEmpty</fold>() && force) <fold text='{...}' expand='true'>{
            return false;
        }</fold>
        return <fold text='' expand='false'>name == null || </fold>name.<fold text='isNullOrEmpty' expand='false'>isEmpty</fold>() ? <fold text='' expand='false'>items == null || </fold>items.<fold text='isNullOrEmpty' expand='false'>isEmpty</fold>() : force;
    }</fold>

    public void notFolded(String name, String other, List<String> items, NullOrEmptyTestData data) <fold text='{...}' expand='true'>{
        boolean mixedVariables = name == null || other.isEmpty();
        boolean wrongOperator = name == null && name.isEmpty();
        boolean missingNegation = items != null && items.isEmpty();
        boolean callReceiver = data.getTitle() == null || data.getTitle().isEmpty();
        boolean otherObjectField = data.title == null || data.title.isEmpty();
        boolean notZero = items == null || items.size() == 1;
        boolean reversedOrder = name.isEmpty() || name == null;
    }</fold>

    public void utilityChecks(String name, List<String> items, Map<String, Integer> stock, Object[] codes) <fold text='{...}' expand='true'>{
        if (<fold text='' expand='false'>CollectionUtils.isEmpty(</fold>items<fold text='.isNullOrEmpty()' expand='false'>)</fold>) <fold text='{...}' expand='true'>{
            System.out.println("no items");
        }</fold>
        if (<fold text='' expand='false'>StringUtils.isNotEmpty(</fold>name<fold text='.isNotEmpty()' expand='false'>)</fold>) <fold text='{...}' expand='true'>{
            System.out.println(name);
        }</fold>
        boolean noTitle = <fold text='' expand='false'>StringUtils.isEmpty(</fold>getTitle()<fold text='.isNullOrEmpty()' expand='false'>)</fold>;
        boolean hasItems = <fold text='' expand='false'>CollectionUtils.isNotEmpty(</fold>items<fold text='.isNotEmpty()' expand='false'>)</fold>;
        boolean anyItems = <fold text='' expand='false'>!CollectionUtils.isEmpty(</fold>items<fold text='.isNotEmpty()' expand='false'>)</fold>;
        boolean noStock = <fold text='' expand='false'>org.apache.commons.collections4.MapUtils.isEmpty(</fold>stock<fold text='.isNullOrEmpty()' expand='false'>)</fold>;
        boolean anyStock = <fold text='' expand='false'>org.apache.commons.collections4.MapUtils.isNotEmpty(</fold>stock<fold text='.isNotEmpty()' expand='false'>)</fold>;
        boolean noTags = <fold text='' expand='false'>org.springframework.util.CollectionUtils.isEmpty(</fold>tags<fold text='.isNullOrEmpty()' expand='false'>)</fold>;
        boolean noCodes = <fold text='' expand='false'>ObjectUtils.isEmpty(</fold>codes<fold text='.isNullOrEmpty()' expand='false'>)</fold>;
    }</fold>

    public void utilityChecksNotFolded(String name, List<String> items, String other) <fold text='{...}' expand='true'>{
        boolean blank = StringUtils.isBlank(name);
        boolean unrelatedClass = MapUtils.isEmpty(items);
        boolean concatenated = StringUtils.isEmpty(name + other);
    }</fold>

    public String getTitle()<fold text=' { ' expand='false'> {
        </fold>return title;<fold text=' }' expand='false'>
    }</fold>

    static class MapUtils <fold text='{...}' expand='true'>{
        static boolean isEmpty(List<String> values)<fold text=' { ' expand='false'> {
            </fold>return values.isEmpty();<fold text=' }' expand='false'>
        }</fold>
    }</fold>
}
