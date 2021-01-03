package payment.saga.payment.handler;

import org.springframework.stereotype.Component;
import payment.saga.payment.model.OrderPurchaseEvent;
import payment.saga.payment.model.PaymentEvent;

import static payment.saga.payment.enums.PaymentStatus.APPROVED;
import static payment.saga.payment.enums.PaymentStatus.DECLINED;
import static payment.saga.payment.util.Utils.USER_BALANCE;

@Component
public class OrderPurchaseEventHandler implements EventHandler<OrderPurchaseEvent, PaymentEvent> {

    public PaymentEvent process(OrderPurchaseEvent orderPurchaseEvent) {
        Integer orderPrice = orderPurchaseEvent.getPrice();
        Integer userId = orderPurchaseEvent.getUserId();
        Integer userBalance = USER_BALANCE.get(userId);
        PaymentEvent paymentEvent = new PaymentEvent()
                .orderId(orderPurchaseEvent.getOrderId())
                .price(orderPurchaseEvent.getPrice());
        if (userBalance >= orderPrice) {
            USER_BALANCE.computeIfPresent(userId,
                    (user, balance) -> balance - orderPrice);
            return paymentEvent.status(APPROVED);
        }
        return paymentEvent.status(DECLINED);
    }

}
