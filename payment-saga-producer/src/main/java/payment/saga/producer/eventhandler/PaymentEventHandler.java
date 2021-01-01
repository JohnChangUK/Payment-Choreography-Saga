package payment.saga.producer.eventhandler;

import org.springframework.stereotype.Service;
import payment.saga.producer.enums.OrderStatus;
import payment.saga.producer.enums.PaymentStatus;
import payment.saga.producer.model.PaymentEvent;
import payment.saga.producer.repository.PurchaseOrderRepository;

import javax.transaction.Transactional;

@Service
public class PaymentEventHandler {

    private final PurchaseOrderRepository purchaseOrderRepository;

    public PaymentEventHandler(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Transactional
    public void handlePaymentEvent(PaymentEvent paymentEvent) {
        System.out.println("consumePaymentEvent: " + paymentEvent.toString());
        this.purchaseOrderRepository.findById(paymentEvent.getOrderId())
                .ifPresent(purchaseOrder -> {
                    purchaseOrder.setStatus(paymentEvent.getStatus().equals(PaymentStatus.APPROVED)
                            ? OrderStatus.ORDER_COMPLETED
                            : OrderStatus.ORDER_CANCELLED);
                    this.purchaseOrderRepository.save(purchaseOrder);
                });
    }

}
