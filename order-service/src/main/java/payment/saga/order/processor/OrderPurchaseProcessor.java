package payment.saga.order.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import payment.saga.order.model.OrderPurchase;
import payment.saga.order.model.OrderPurchaseEvent;
import reactor.core.publisher.Sinks;

@Component
public class OrderPurchaseProcessor {

    private final Sinks.Many<OrderPurchaseEvent> sink;

    @Autowired
    public OrderPurchaseProcessor(Sinks.Many<OrderPurchaseEvent> sink) {
        this.sink = sink;
    }

    public void process(OrderPurchase orderPurchase) {
        OrderPurchaseEvent orderPurchaseEvent = new OrderPurchaseEvent()
                .setUserId(orderPurchase.getUserId())
                .setOrderId(orderPurchase.getId())
                .setPrice(orderPurchase.getPrice());
        sink.emitNext(orderPurchaseEvent, Sinks.EmitFailureHandler.FAIL_FAST);
    }
}
