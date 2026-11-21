package data;

import <fold text='...' expand='false'>org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;</fold>

@SuppressWarnings("ALL")
public class NullOrEmptyTestData {
    <fold text='@Getter p' expand='false'>p</fold>rivate String title;
    private List<String> tags;

    public void folded(String name, List<String> items, Map<String, Integer> stock, StringBuilder buffer) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold><fold text='' expand='false'>name == null || </fold>name.<fold text='isNullOrEmpty' expand='false'>isEmpty</fold>()<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println("no name");
        }</fold>
        if <fold text='' expand='false'>(</fold><fold text='' expand='false'>items != null && !</fold>items.<fold text='isNotEmpty' expand='false'>isEmpty</fold>()<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println(items<fold text='.getFirst' expand='false'>.get</fold>(<fold text='' expand='false'>0</fold>));
        }</fold>
        <fold text='val' expand='false'>boolean</fold> noStock = <fold text='' expand='false'>stock == null || </fold>stock.<fold text='isNullOrEmpty' expand='false'>isEmpty</fold>();
        <fold text='val' expand='false'>boolean</fold> blankName = <fold text='' expand='false'>name == null || </fold>name.<fold text='isNullOrEmpty' expand='false'>length</fold>()<fold text='' expand='false'> == 0</fold>;
        <fold text='val' expand='false'>boolean</fold> hasItems = <fold text='' expand='false'>items != null && </fold>items.<fold text='isNotEmpty' expand='false'>size</fold>()<fold text='' expand='false'> > 0</fold>;
        <fold text='val' expand='false'>boolean</fold> anyItems = <fold text='' expand='false'>items != null && </fold>items.<fold text='isNotEmpty' expand='false'>size</fold>()<fold text='' expand='false'> != 0</fold>;
        <fold text='val' expand='false'>boolean</fold> noBuffer = <fold text='' expand='false'>buffer == null || </fold>buffer.<fold text='isNullOrEmpty' expand='false'>length</fold>()<fold text='' expand='false'> == 0</fold>;
        if <fold text='' expand='false'>(</fold><fold text='' expand='false'>title == null || </fold>title.<fold text='isNullOrEmpty' expand='false'>isEmpty</fold>()<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            title = "untitled";
        }</fold>
        if <fold text='' expand='false'>(</fold><fold text='' expand='false'>this.tags != null && !</fold>this.tags.<fold text='isNotEmpty' expand='false'>isEmpty</fold>()<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println(this.tags);
        }</fold>
        while <fold text='' expand='false'>(</fold><fold text='' expand='false'>items != null && !</fold>items.<fold text='isNotEmpty' expand='false'>isEmpty</fold>()<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            items.remove(0);
        }</fold>
    }</fold>

    public boolean insideLongerConditions(String name, List<String> items, boolean force) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold>force || <fold text='' expand='false'>name == null || </fold>name.<fold text='isNullOrEmpty' expand='false'>isEmpty</fold>()<fold text='' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return true;<fold text='' expand='false'>
        }</fold></fold>
        if <fold text='' expand='false'>(</fold><fold text='' expand='false'>items != null && !</fold>items.<fold text='isNotEmpty' expand='false'>isEmpty</fold>() && force<fold text='' expand='false'>)</fold> <fold text='' expand='false'><fold text='{...}' expand='true'>{
            </fold>return false;<fold text='' expand='false'>
        }</fold></fold>
        return <fold text='' expand='false'>name == null || </fold>name.<fold text='isNullOrEmpty' expand='false'>isEmpty</fold>() ? <fold text='' expand='false'>items == null || </fold>items.<fold text='isNullOrEmpty' expand='false'>isEmpty</fold>() : force;
    }</fold>

    public void notFolded(String name, String other, List<String> items, NullOrEmptyTestData data) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>boolean</fold> mixedVariables = name == null || other.<fold text='empty' expand='false'>isEmpty()</fold>;
        <fold text='val' expand='false'>boolean</fold> wrongOperator = name == null && name.<fold text='empty' expand='false'>isEmpty()</fold>;
        <fold text='val' expand='false'>boolean</fold> missingNegation = <fold text='items?.empty == true' expand='false'>items != null && items.<fold text='empty' expand='false'>isEmpty()</fold></fold>;
        <fold text='val' expand='false'>boolean</fold> callReceiver = data.<fold text='title' expand='false'>getTitle()</fold> == null || data.<fold text='title' expand='false'>getTitle()</fold>.<fold text='empty' expand='false'>isEmpty()</fold>;
        <fold text='val' expand='false'>boolean</fold> otherObjectField = data.title == null || data.title.<fold text='empty' expand='false'>isEmpty()</fold>;
        <fold text='val' expand='false'>boolean</fold> notZero = items == null || items.size() == 1;
        <fold text='val' expand='false'>boolean</fold> reversedOrder = name.<fold text='empty' expand='false'>isEmpty()</fold> || name == null;
    }</fold>

    public void utilityChecks(String name, List<String> items, Map<String, Integer> stock, Object[] codes) <fold text='{...}' expand='true'>{
        if <fold text='' expand='false'>(</fold><fold text='' expand='false'>CollectionUtils.isEmpty(</fold>items<fold text='.isNullOrEmpty()' expand='false'>)</fold><fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println("no items");
        }</fold>
        if <fold text='' expand='false'>(</fold><fold text='' expand='false'>StringUtils.isNotEmpty(</fold>name<fold text='.isNotEmpty()' expand='false'>)</fold><fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println(name);
        }</fold>
        <fold text='val' expand='false'>boolean</fold> noTitle = <fold text='' expand='false'>StringUtils.isEmpty(</fold><fold text='title' expand='false'>getTitle()</fold><fold text='.isNullOrEmpty()' expand='false'>)</fold>;
        <fold text='val' expand='false'>boolean</fold> hasItems = <fold text='' expand='false'>CollectionUtils.isNotEmpty(</fold>items<fold text='.isNotEmpty()' expand='false'>)</fold>;
        <fold text='val' expand='false'>boolean</fold> anyItems = <fold text='' expand='false'>!CollectionUtils.isEmpty(</fold>items<fold text='.isNotEmpty()' expand='false'>)</fold>;
        <fold text='val' expand='false'>boolean</fold> noStock = <fold text='' expand='false'>org.apache.commons.collections4.MapUtils.isEmpty(</fold>stock<fold text='.isNullOrEmpty()' expand='false'>)</fold>;
        <fold text='val' expand='false'>boolean</fold> anyStock = <fold text='' expand='false'>org.apache.commons.collections4.MapUtils.isNotEmpty(</fold>stock<fold text='.isNotEmpty()' expand='false'>)</fold>;
        <fold text='val' expand='false'>boolean</fold> noTags = <fold text='' expand='false'>org.springframework.util.CollectionUtils.isEmpty(</fold>tags<fold text='.isNullOrEmpty()' expand='false'>)</fold>;
        <fold text='val' expand='false'>boolean</fold> noCodes = <fold text='' expand='false'>ObjectUtils.isEmpty(</fold>codes<fold text='.isNullOrEmpty()' expand='false'>)</fold>;
    }</fold>

    public void utilityChecksNotFolded(String name, List<String> items, String other) <fold text='{...}' expand='true'>{
        <fold text='val' expand='false'>boolean</fold> blank = StringUtils.isBlank(name);
        <fold text='val' expand='false'>boolean</fold> unrelatedClass = MapUtils.isEmpty(items);
        <fold text='val' expand='false'>boolean</fold> concatenated = StringUtils.isEmpty(name + other);
    }</fold><fold text='' expand='false'>

    </fold><fold text='' expand='false'>public String getTitle()<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
        </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>title<fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
    </fold>}</fold></fold>

    static class MapUtils <fold text='{...}' expand='true'>{
        static boolean isEmpty(List<String> values)<fold text=' { ' expand='false'> {<fold text=' ' expand='true'>
            </fold></fold><fold text='' expand='true'>return</fold><fold text='' expand='true'> </fold>values.<fold text='empty' expand='false'>isEmpty()</fold><fold text='' expand='true'>;</fold><fold text=' ' expand='true'><fold text=' }' expand='false'>
        </fold>}</fold>
    }</fold>
}
