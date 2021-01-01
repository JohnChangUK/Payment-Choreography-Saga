package payment.saga.producer.eventhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import payment.saga.producer.model.OrderEvent;
import payment.saga.producer.model.PurchaseOrder;
import reactor.core.publisher.Sinks;

@Component
public class OrderEventHandler {

    private final Sinks.Many<OrderEvent> sink;

    @Autowired
    public OrderEventHandler(Sinks.Many<OrderEvent> sink) {
        this.sink = sink;
    }

    public void raiseOrderCreatedEvent(final PurchaseOrder purchaseOrder) {
        System.out.println("raiseOrderCreatedEvent: " + purchaseOrder.toString());
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setUserId(purchaseOrder.getUserId());
        orderEvent.setPrice(purchaseOrder.getPrice());
        orderEvent.setOrderId(purchaseOrder.getId());
        sink.emitNext(orderEvent, Sinks.EmitFailureHandler.FAIL_FAST);
    }
}
