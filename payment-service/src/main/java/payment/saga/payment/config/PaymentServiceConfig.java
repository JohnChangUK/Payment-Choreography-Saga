package payment.saga.payment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import payment.saga.payment.handler.OrderPurchaseEventHandler;
import payment.saga.payment.handler.PaymentEventHandler;
import payment.saga.payment.model.OrderPurchaseEvent;
import payment.saga.payment.model.PaymentEvent;
import payment.saga.payment.model.TransactionEvent;

import java.util.function.Function;

@Configuration
public class PaymentServiceConfig {

    private final PaymentEventHandler paymentEventHandler;
    private final OrderPurchaseEventHandler orderPurchaseEventHandler;

    @Autowired
    public PaymentServiceConfig(
            PaymentEventHandler paymentEventHandler,
            OrderPurchaseEventHandler orderPurchaseEventHandler) {
        this.paymentEventHandler = paymentEventHandler;
        this.orderPurchaseEventHandler = orderPurchaseEventHandler;
    }

    @Bean
    public Function<OrderPurchaseEvent, PaymentEvent> orderPurchaseEventProcessor() {
        return orderPurchaseEventHandler::process;
    }

    @Bean
    public Function<PaymentEvent, TransactionEvent> paymentEventSubscriber() {
        return paymentEventHandler::process;
    }

}
