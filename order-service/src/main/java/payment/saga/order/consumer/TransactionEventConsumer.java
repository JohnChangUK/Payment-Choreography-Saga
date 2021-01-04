package payment.saga.order.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import payment.saga.order.model.OrderPurchase;
import payment.saga.order.model.TransactionEvent;
import payment.saga.order.repository.OrderPurchaseRepository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import static payment.saga.order.enums.OrderStatus.COMPLETED;
import static payment.saga.order.enums.OrderStatus.FAILED;
import static payment.saga.order.enums.TransactionStatus.SUCCESSFUL;

@Component
public class TransactionEventConsumer implements EventConsumer<TransactionEvent> {

    private final OrderPurchaseRepository orderPurchaseRepository;
    private final Scheduler jdbcScheduler;

    @Autowired
    public TransactionEventConsumer(
            OrderPurchaseRepository orderPurchaseRepository,
            Scheduler jdbcScheduler) {
        this.orderPurchaseRepository = orderPurchaseRepository;
        this.jdbcScheduler = jdbcScheduler;
    }

    public void consumeEvent(TransactionEvent event) {
        Mono.fromRunnable(
                () -> orderPurchaseRepository.findById(event.getOrderId())
                        .ifPresent(order -> {
                            setStatus(event, order);
                            orderPurchaseRepository.save(order);
                        }))
                .subscribeOn(jdbcScheduler)
                .subscribe();
    }

    private void setStatus(TransactionEvent transactionEvent, OrderPurchase order) {
        order.setStatus(SUCCESSFUL.equals(transactionEvent.getStatus())
                ? COMPLETED
                : FAILED);
    }

}
