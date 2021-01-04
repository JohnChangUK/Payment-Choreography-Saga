package payment.saga.payment.handler;

import payment.saga.payment.model.Event;

public interface EventHandler<T extends Event, R extends Event> {

    R handleEvent(T event);
}
