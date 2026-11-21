package data;

import java.util.List;

public class KotlinRangeLoopsTestData {
    public void printOrders(List<String> orders, String[] items, int pageSize) <fold text='{...}' expand='true'>{
        for <fold text='' expand='false'>(</fold><fold text='' expand='false'><fold text='val' expand='false'>int</fold> </fold>i<fold text=' in ' expand='false'> = </fold>0<fold text='..<' expand='false'>; i < </fold>orders.size()<fold text='' expand='false'>; i++</fold><fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println<fold text='("$' expand='false'>(</fold>i<fold text='' expand='false'> + "</fold>: <fold text='${' expand='false'>" + </fold>orders<fold text='[' expand='false'>.get(</fold>i<fold text=']' expand='false'>)</fold><fold text='}")' expand='false'>)</fold>;
        }</fold>
        for <fold text='' expand='false'>(</fold><fold text='' expand='false'><fold text='val' expand='false'>int</fold> </fold>page<fold text=' in ' expand='false'> = </fold>1<fold text='..' expand='false'>; page <= </fold>pageSize<fold text='' expand='false'>; page++</fold><fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println(page);
        }</fold>
        for <fold text='' expand='false'>(</fold><fold text='' expand='false'><fold text='val' expand='false'>int</fold> </fold>i<fold text=' in ' expand='false'> = </fold>1<fold text='..<' expand='false'>; i < </fold>items.length<fold text='' expand='false'>; i++</fold><fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println<fold text='("${' expand='false'>(</fold>items[i - 1]<fold text='}' expand='false'> + "</fold> -> <fold text='${' expand='false'>" + </fold>items[i]<fold text='}")' expand='false'>)</fold>;
        }</fold>
        for <fold text='' expand='false'>(</fold><fold text='' expand='false'><fold text='val' expand='false'>long</fold> </fold>id<fold text=' in ' expand='false'> = </fold>100L<fold text='..<' expand='false'>; id < </fold>200L<fold text='' expand='false'>; id++</fold><fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println(id);
        }</fold>
        // not folded: the loop goes down
        for <fold text='' expand='false'>(</fold><fold text='val' expand='false'>int</fold> i = pageSize; i > 0; i--<fold text='' expand='false'>)</fold> <fold text='{...}' expand='true'>{
            <fold text='' expand='false'>System.out.</fold>println(i);
        }</fold>
    }</fold>
}
