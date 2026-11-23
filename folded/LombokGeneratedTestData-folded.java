package data;

import java.util.List;
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

    public OrderBook book(boolean open, List<String> asks, List<String> bids) {
        List<String> none = [];
        return OrderBook(
            asks = open ? asks : [],
            bids = bids
        )
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
