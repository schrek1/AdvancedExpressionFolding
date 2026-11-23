package data;

import java.util.List;
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

    public OrderBook book(boolean open, List<String> asks, List<String> bids) {
        List<String> none = List.of();
        return OrderBook.builder()
                .asks(open ? asks : List.of())
                .bids(bids)
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

    @Value
    @Builder
    static class OrderBook {
        List<String> asks;
        List<String> bids;
    }
}
