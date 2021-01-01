package payment.saga.producer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import payment.saga.producer.eventhandler.OrderEventProcessorService;
import payment.saga.producer.eventhandler.PaymentEventHandler;
import payment.saga.producer.model.OrderEvent;
import payment.saga.producer.model.PaymentEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class OrderServiceConfig {

    private final OrderEventProcessorService orderEventProcessorService;
    private final PaymentEventHandler paymentEventHandler;

    public OrderServiceConfig(
            OrderEventProcessorService orderEventProcessorService,
            PaymentEventHandler paymentEventHandler) {
        this.orderEventProcessorService = orderEventProcessorService;
        this.paymentEventHandler = paymentEventHandler;
    }

    @Bean
    public Sinks.Many<OrderEvent> sink() {
        return Sinks.many()
                .multicast()
                .directBestEffort();
    }

    @Bean
    public Supplier<Flux<OrderEvent>> orderEventPublisher(Sinks.Many<OrderEvent> processor) {
        return processor::asFlux;
    }

    @Bean
    public Function<OrderEvent, PaymentEvent> orderEventSubscriber() {
        return orderEventProcessorService::handleOrderEvent;
    }

    @Bean
    public Consumer<PaymentEvent> paymentEventSubscriber() {
        return paymentEventHandler::handlePaymentEvent;
    }

}
