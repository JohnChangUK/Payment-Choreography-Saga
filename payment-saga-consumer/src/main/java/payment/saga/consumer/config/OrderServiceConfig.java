package payment.saga.consumer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import payment.saga.consumer.eventhandler.PaymentEventConsumer;
import payment.saga.consumer.model.OrderEvent;
import payment.saga.consumer.model.PaymentEvent;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
public class OrderServiceConfig {

    private final PaymentEventConsumer paymentEventConsumer;

    @Autowired
    public OrderServiceConfig(PaymentEventConsumer paymentEventConsumer) {
        this.paymentEventConsumer = paymentEventConsumer;
    }

    @Bean
    public FluxSink<Object> getFlux2(){
        return DirectProcessor.create().sink();
    }

    @Bean
    public Sinks.Many<OrderEvent> getFlux () {
        return Sinks.many().multicast().directBestEffort();
    }

    @Bean
    public Flux<OrderEvent> orderEventChannel(Sinks.Many<OrderEvent> processor) {
        return processor.asFlux();
    }

    @Bean
    public Supplier<Flux<OrderEvent>> orderEventPublisher(Flux<OrderEvent> processor) {
        return () -> processor;
    }

    @Bean
    public Consumer<PaymentEvent> paymentEventConsumer() {
        return paymentEventConsumer::consumePaymentEvent;
    }

    @Bean
    public Consumer<PaymentEvent> consumePaymentEvent(PaymentEvent payment) {
        return System.out::println;
    }

}
