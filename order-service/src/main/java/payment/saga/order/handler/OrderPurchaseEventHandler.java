package payment.saga.order.handler;

import org.springframework.stereotype.Component;
import payment.saga.order.enums.PaymentStatus;
import payment.saga.order.model.OrderPurchaseEvent;
import payment.saga.order.model.PaymentEvent;

import java.util.HashMap;
import java.util.Map;

@Component
public class OrderPurchaseEventHandler {

    public static final Map<Integer, Integer> userBalance = new HashMap<>();

    static {
        userBalance.put(1, 100);
        userBalance.put(2, 500);
        userBalance.put(3, 1000);
    }

    public PaymentEvent process(OrderPurchaseEvent orderPurchaseEvent) {
        Integer price = orderPurchaseEvent.getPrice();
        Integer userId = orderPurchaseEvent.getUserId();
        Integer balance = userBalance.get(userId);
        PaymentEvent paymentEvent = new PaymentEvent(orderPurchaseEvent.getOrderId());
        if (balance >= price) {
            paymentEvent.setStatus(PaymentStatus.APPROVED);
            userBalance.computeIfPresent(userId, (k, v) -> v - price);
        } else {
            paymentEvent.setStatus(PaymentStatus.DECLINED);
        }
        return paymentEvent;
    }

}
