package payment.saga.payment.handler;

public interface EventHandler<I, O> {

    O process(I input);
}
