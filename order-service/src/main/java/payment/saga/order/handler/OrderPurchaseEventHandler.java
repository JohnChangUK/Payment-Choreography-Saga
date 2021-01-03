package payment.saga.order.handler;

import org.springframework.stereotype.Component;
import payment.saga.order.enums.PaymentStatus;
import payment.saga.order.model.OrderPurchaseEvent;
import payment.saga.order.model.PaymentEvent;

import static payment.saga.order.util.Utils.USER_BALANCE;

@Component
public class OrderPurchaseEventHandler {

    public PaymentEvent process(OrderPurchaseEvent orderPurchaseEvent) {
        Integer price = orderPurchaseEvent.getPrice();
        Integer userId = orderPurchaseEvent.getUserId();
        Integer balance = USER_BALANCE.get(userId);
        PaymentEvent paymentEvent = new PaymentEvent(orderPurchaseEvent.getOrderId());
        if (balance >= price) {
            paymentEvent.setStatus(PaymentStatus.APPROVED);
            USER_BALANCE.computeIfPresent(userId, (k, v) -> v - price);
        } else {
            paymentEvent.setStatus(PaymentStatus.DECLINED);
        }
        return paymentEvent;
    }

}
