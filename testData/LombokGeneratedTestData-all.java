package data;

import <fold text='...' expand='false'>lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;</fold>

@Slf4j
public class LombokGeneratedTestData {

    public Order cancel(Order order, Order completed, Exception exception) <fold text='{...}' expand='true'>{
        log.info("Order <fold text='${order.id}' expand='false'>{}</fold> stays <fold text='${order.status}' expand='false'>{}</fold>: nothing to cancel"<fold text='' expand='false'>, order.<fold text='i' expand='false'>getI</fold>d<fold text='' expand='false'>()</fold>, order.<fold text='s' expand='false'>getS</fold>tatus<fold text='' expand='false'>()</fold></fold>);
        log.info("Order <fold text='${order.id}' expand='false'>{}</fold> stays <fold text='${order.status}' expand='false'>{}</fold>: order <fold text='${completed.id}' expand='false'>{}</fold> of its pair is completed"<fold text='' expand='false'>,
                order.<fold text='i' expand='false'>getI</fold>d<fold text='' expand='false'>()</fold>,
                order.<fold text='s' expand='false'>getS</fold>tatus<fold text='' expand='false'>()</fold>,
                completed.<fold text='i' expand='false'>getI</fold>d<fold text='' expand='false'>()</fold></fold>);
        log.warn("Order <fold text='${order.id}' expand='false'>{}</fold> conflicts with order <fold text='${completed.id}' expand='false'>{}</fold>"<fold text='' expand='false'>, order.<fold text='i' expand='false'>getI</fold>d<fold text='' expand='false'>()</fold>, completed.<fold text='i' expand='false'>getI</fold>d<fold text='' expand='false'>()</fold></fold>, exception);
        return order.<fold text='copy(s' expand='false'>toBuilder()
                .setS</fold>tatus<fold text=' = ' expand='false'>(</fold>Status.CANCELLED)<fold text='' expand='false'>
                .build()</fold>;
    }</fold>

    enum Status <fold text='{...}' expand='true'>{
        NEW, CANCELLED
    }</fold>

    @Value
    @Builder(toBuilder = true, setterPrefix = "set")
    static class Order <fold text='{...}' expand='true'>{
        String id;
        Status status;
    }</fold>
}
