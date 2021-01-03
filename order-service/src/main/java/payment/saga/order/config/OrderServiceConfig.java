package payment.saga.order.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import payment.saga.order.consumer.TransactionEventConsumer;
import payment.saga.order.model.OrderPurchaseEvent;
import payment.saga.order.model.TransactionEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
public class OrderServiceConfig {

    private final TransactionEventConsumer transactionEventHandler;

    @Autowired
    public OrderServiceConfig(TransactionEventConsumer transactionEventHandler) {
        this.transactionEventHandler = transactionEventHandler;
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
    public Consumer<TransactionEvent> transactionEventProcessor() {
        return transactionEventHandler::process;
    }

}
