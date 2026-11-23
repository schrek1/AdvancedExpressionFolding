package data;

import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LombokGeneratedTestData {

    public Order cancel(Order order, Order completed, Exception exception) {
        log.info("Order {} stays {}: nothing to cancel", order.getId(), order.getStatus());
        log.info("Order {} stays {}: order {} of its pair is completed",
                order.getId(),
                order.getStatus(),
                completed.getId());
        log.warn("Order {} conflicts with order {}", order.getId(), completed.getId(), exception);
        return order.toBuilder()
                .setStatus(Status.CANCELLED)
                .build();
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
