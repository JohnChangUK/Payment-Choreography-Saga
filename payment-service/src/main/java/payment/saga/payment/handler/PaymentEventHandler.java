package payment.saga.payment.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import payment.saga.payment.model.PaymentEvent;
import payment.saga.payment.repository.OrderPurchaseRepository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import javax.transaction.Transactional;

import static payment.saga.payment.enums.OrderStatus.ORDER_COMPLETED;
import static payment.saga.payment.enums.OrderStatus.ORDER_FAILED;
import static payment.saga.payment.enums.PaymentStatus.APPROVED;

@Component
public class PaymentEventHandler {

    private final OrderPurchaseRepository orderPurchaseRepository;
    private final Scheduler jdbcScheduler;

    @Autowired
    public PaymentEventHandler(
            OrderPurchaseRepository orderPurchaseRepository,
            Scheduler jdbcScheduler) {
        this.orderPurchaseRepository = orderPurchaseRepository;
        this.jdbcScheduler = jdbcScheduler;
    }

    @Transactional
    public void process(PaymentEvent paymentEvent) {
        Mono.fromRunnable(
                () -> orderPurchaseRepository.findById(paymentEvent.getOrderId())
                        .ifPresent(order -> {
                            order.setStatus(APPROVED.equals(paymentEvent.getStatus())
                                    ? ORDER_COMPLETED
                                    : ORDER_FAILED);
                            orderPurchaseRepository.save(order);
                        }))
                .subscribeOn(jdbcScheduler)
                .subscribe();
    }

}
