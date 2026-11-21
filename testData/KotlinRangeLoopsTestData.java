package data;

import java.util.List;

public class KotlinRangeLoopsTestData {
    public void printOrders(List<String> orders, String[] items, int pageSize) <fold text='{...}' expand='true'>{
        for (<fold text='' expand='false'>int </fold>i<fold text=' in ' expand='false'> = </fold>0<fold text='..<' expand='false'>; i < </fold>orders.size()<fold text='' expand='false'>; i++</fold>) <fold text='{...}' expand='true'>{
            System.out.println(i + ": " + orders.get(i));
        }</fold>
        for (<fold text='' expand='false'>int </fold>page<fold text=' in ' expand='false'> = </fold>1<fold text='..' expand='false'>; page <= </fold>pageSize<fold text='' expand='false'>; page++</fold>) <fold text='{...}' expand='true'>{
            System.out.println(page);
        }</fold>
        for (<fold text='' expand='false'>int </fold>i<fold text=' in ' expand='false'> = </fold>1<fold text='..<' expand='false'>; i < </fold>items.length<fold text='' expand='false'>; i++</fold>) <fold text='{...}' expand='true'>{
            System.out.println(items[i - 1] + " -> " + items[i]);
        }</fold>
        for (<fold text='' expand='false'>long </fold>id<fold text=' in ' expand='false'> = </fold>100L<fold text='..<' expand='false'>; id < </fold>200L<fold text='' expand='false'>; id++</fold>) <fold text='{...}' expand='true'>{
            System.out.println(id);
        }</fold>
        // not folded: the loop goes down
        for (int i = pageSize; i > 0; i--) <fold text='{...}' expand='true'>{
            System.out.println(i);
        }</fold>
    }</fold>
}
