package payment.saga.payment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import payment.saga.payment.model.PaymentEvent;
import payment.saga.payment.handler.PaymentEventHandler;

import java.util.function.Consumer;

@Configuration
public class PaymentServiceConfig {

    private final PaymentEventHandler paymentEventHandler;

    @Autowired
    public PaymentServiceConfig(PaymentEventHandler paymentEventHandler) {
        this.paymentEventHandler = paymentEventHandler;
    }

    @Bean
    public Consumer<PaymentEvent> paymentEventSubscriber() {
        return paymentEventHandler::process;
    }

}
