package payment.saga.order.consumer;

import payment.saga.order.model.Event;

public interface EventConsumer<T extends Event> {

    void consumeEvent(T event);
}
