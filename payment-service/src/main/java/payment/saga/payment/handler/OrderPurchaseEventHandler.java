package payment.saga.payment.handler;

import org.springframework.stereotype.Component;
import payment.saga.payment.model.OrderPurchaseEvent;
import payment.saga.payment.model.PaymentEvent;

import static payment.saga.payment.enums.PaymentStatus.APPROVED;
import static payment.saga.payment.enums.PaymentStatus.DECLINED;
import static payment.saga.payment.util.Utils.USER_BALANCE;

@Component
public class OrderPurchaseEventHandler implements EventHandler<OrderPurchaseEvent, PaymentEvent> {

    public PaymentEvent handleEvent(OrderPurchaseEvent event) {
        Integer orderPrice = event.getPrice();
        Integer userId = event.getUserId();
        Integer userBalance = USER_BALANCE.get(userId);
        PaymentEvent paymentEvent = new PaymentEvent()
                .orderId(event.getOrderId())
                .price(event.getPrice());
        if (userBalance >= orderPrice) {
            USER_BALANCE.computeIfPresent(userId,
                    (user, balance) -> balance - orderPrice);
            return paymentEvent.status(APPROVED);
        }
        return paymentEvent.status(DECLINED);
    }

}
