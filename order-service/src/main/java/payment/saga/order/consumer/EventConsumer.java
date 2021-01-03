package payment.saga.order.consumer;

public interface EventConsumer<T> {

    void process(T t);
}
