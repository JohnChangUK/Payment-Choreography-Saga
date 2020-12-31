package payment.saga.producer.eventhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import payment.saga.producer.model.OrderEvent;
import payment.saga.producer.model.PurchaseOrder;
import reactor.core.publisher.Flux;

@Component
public class OrderEventHandler {

    private final Flux<OrderEvent> orderEventChannel;

    @Autowired
    public OrderEventHandler(Flux<OrderEvent> orderEventChannel) {
        this.orderEventChannel = orderEventChannel;
    }

    public void raiseOrderCreatedEvent(final PurchaseOrder purchaseOrder){
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setUserId(purchaseOrder.getUserId());
        orderEvent.setPrice(purchaseOrder.getPrice());
        orderEvent.setOrderId(purchaseOrder.getId());
        this.orderEventChannel.publish(orderEvent) .next(orderEvent);
    }
}
