package payment.saga.payment.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.saga.payment.enums.OrderStatus;
import payment.saga.payment.enums.PaymentStatus;
import payment.saga.payment.model.PaymentEvent;
import payment.saga.payment.repository.OrderPurchaseRepository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import javax.transaction.Transactional;

@Service
public class PaymentEventHandler {

    private static final Logger log = LoggerFactory.getLogger(PaymentEventHandler.class);

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
        Mono.fromRunnable(() ->
                orderPurchaseRepository.findById(paymentEvent.getOrderId())
                        .ifPresent(order -> {
                            order.setStatus(PaymentStatus.APPROVED.equals(paymentEvent.getStatus())
                                    ? OrderStatus.ORDER_COMPLETED
                                    : OrderStatus.ORDER_FAILED);
                            orderPurchaseRepository.save(order);
                        }))
                .subscribeOn(jdbcScheduler)
                .subscribe();
    }

}
