package payment.saga.producer.paymentservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import payment.saga.producer.model.OrderEvent;
import payment.saga.producer.model.PaymentEvent;
import payment.saga.producer.paymentservice.eventhandlers.OrderEventProcessorService;

import java.util.function.Function;

@Configuration
public class PaymentServiceConfig {

    @Autowired
    private OrderEventProcessorService orderEventProcessorService;

    @Bean
    public Function<OrderEvent, PaymentEvent> orderEventProcessor(){
        return orderEventProcessorService::processOrderEvent;
    }

}
