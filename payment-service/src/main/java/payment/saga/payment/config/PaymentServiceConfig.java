package payment.saga.payment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import payment.saga.payment.handler.EventHandler;
import payment.saga.payment.model.OrderPurchaseEvent;
import payment.saga.payment.model.PaymentEvent;
import payment.saga.payment.model.TransactionEvent;

import java.util.function.Function;

@Configuration
public class PaymentServiceConfig {

    private final EventHandler<PaymentEvent, TransactionEvent> paymentEventHandler;
    private final EventHandler<OrderPurchaseEvent, PaymentEvent> orderPurchaseEventHandler;

    @Autowired
    public PaymentServiceConfig(
            EventHandler<PaymentEvent, TransactionEvent> paymentEventHandler,
            EventHandler<OrderPurchaseEvent, PaymentEvent> orderPurchaseEventHandler) {
        this.paymentEventHandler = paymentEventHandler;
        this.orderPurchaseEventHandler = orderPurchaseEventHandler;
    }

    @Bean
    public Function<OrderPurchaseEvent, PaymentEvent> orderPurchaseEventProcessor() {
        return orderPurchaseEventHandler::handleEvent;
    }

    @Bean
    public Function<PaymentEvent, TransactionEvent> paymentEventSubscriber() {
        return paymentEventHandler::handleEvent;
    }

}
