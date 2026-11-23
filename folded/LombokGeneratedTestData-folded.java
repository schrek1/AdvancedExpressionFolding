package data;

import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LombokGeneratedTestData {

    public Order cancel(Order order, Order completed, Exception exception) {
        log.info("Order ${order.id} stays ${order.status}: nothing to cancel");
        log.info("Order ${order.id} stays ${order.status}: order ${completed.id} of its pair is completed");
        log.warn("Order ${order.id} conflicts with order ${completed.id}", exception);
        return order.copy(status = Status.CANCELLED);
    }

    enum Status {
        NEW, CANCELLED
    }

    @Value
    @Builder(toBuilder = true, setterPrefix = "set")
    static class Order {
        String id;
        Status status;
    }
}
