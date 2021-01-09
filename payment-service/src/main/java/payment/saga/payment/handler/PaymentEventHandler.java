package payment.saga.payment.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import payment.saga.payment.model.PaymentEvent;
import payment.saga.payment.model.Transaction;
import payment.saga.payment.model.TransactionEvent;
import payment.saga.payment.repository.TransactionRepository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import javax.transaction.Transactional;

import static payment.saga.payment.enums.PaymentStatus.APPROVED;
import static payment.saga.payment.enums.TransactionStatus.SUCCESSFUL;
import static payment.saga.payment.enums.TransactionStatus.UNSUCCESSFUL;

@Component
public class PaymentEventHandler implements EventHandler<PaymentEvent, TransactionEvent> {

    private final TransactionRepository transactionRepository;
    private final Scheduler jdbcScheduler;

    @Autowired
    public PaymentEventHandler(
            TransactionRepository transactionRepository,
            Scheduler jdbcScheduler) {
        this.transactionRepository = transactionRepository;
        this.jdbcScheduler = jdbcScheduler;
    }

    @Transactional
    public TransactionEvent handleEvent(PaymentEvent event) {
        Mono.fromRunnable(() -> transactionRepository.save(
                new Transaction()
                        .setOrderId(event.getOrderId())
                        .setPrice(event.getPrice())))
                .subscribeOn(jdbcScheduler)
                .subscribe();

        return new TransactionEvent()
                .orderId(event.getOrderId())
                .status(APPROVED.equals(event.getStatus())
                        ? SUCCESSFUL
                        : UNSUCCESSFUL);

    }
}
