package payment.saga.order.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import payment.saga.order.handler.OrderPurchaseEventHandler;
import payment.saga.order.model.OrderPurchaseEvent;
import payment.saga.order.model.PaymentEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class OrderServiceConfig {

    private final OrderPurchaseEventHandler orderPurchaseEventHandler;

    @Autowired
    public OrderServiceConfig(OrderPurchaseEventHandler orderPurchaseEventHandler) {
        this.orderPurchaseEventHandler = orderPurchaseEventHandler;
    }

    @Bean
    public Sinks.Many<OrderPurchaseEvent> sink() {
        return Sinks.many()
                .multicast()
                .directBestEffort();
    }

    @Bean
    public Supplier<Flux<OrderPurchaseEvent>> orderEventPublisher(
            Sinks.Many<OrderPurchaseEvent> processor) {
        return processor::asFlux;
    }

    @Bean
    public Function<OrderPurchaseEvent, PaymentEvent> orderPurchaseEventProcessor() {
        return orderPurchaseEventHandler::process;
    }

}
