package payment.saga.order.handler;

import org.springframework.stereotype.Component;
import payment.saga.order.enums.PaymentStatus;
import payment.saga.order.model.OrderPurchaseEvent;
import payment.saga.order.model.PaymentEvent;

import java.util.HashMap;
import java.util.Map;

@Component
public class OrderPurchaseEventHandler {

    public static final Map<Integer, Integer> userMap = new HashMap<>();

    static {
        userMap.put(1, 100);
        userMap.put(2, 500);
        userMap.put(3, 1000);
    }

    public PaymentEvent process(OrderPurchaseEvent orderPurchaseEvent) {
        Integer price = orderPurchaseEvent.getPrice();
        Integer userId = orderPurchaseEvent.getUserId();
        Integer creditLimit = userMap.get(userId);
        PaymentEvent paymentEvent = new PaymentEvent(orderPurchaseEvent.getOrderId());
        if (creditLimit >= price) {
            paymentEvent.setStatus(PaymentStatus.APPROVED);
            userMap.computeIfPresent(userId, (k, v) -> v - price);
        } else {
            paymentEvent.setStatus(PaymentStatus.REJECTED);
        }
        return paymentEvent;
    }

}
