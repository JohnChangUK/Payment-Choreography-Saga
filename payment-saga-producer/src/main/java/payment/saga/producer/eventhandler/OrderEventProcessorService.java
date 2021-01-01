package payment.saga.producer.eventhandler;

import org.springframework.stereotype.Service;
import payment.saga.producer.enums.PaymentStatus;
import payment.saga.producer.model.OrderEvent;
import payment.saga.producer.model.PaymentEvent;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderEventProcessorService {

    // user - credit limit
    public static final Map<Integer, Integer> userMap = new HashMap<>();

    static {
        userMap.put(1, 1000);
        userMap.put(2, 1000);
        userMap.put(3, 1000);
        userMap.put(4, 1000);
        userMap.put(5, 1000);
    }

    public PaymentEvent handleOrderEvent(OrderEvent orderEvent) {
        System.out.println("processOrderEvent: " + orderEvent);
        var price = orderEvent.getPrice();
        var creditLimit = userMap.get(orderEvent.getUserId());
        PaymentEvent paymentEvent = new PaymentEvent(orderEvent.getOrderId());
        if (creditLimit >= price) {
            paymentEvent.setStatus(PaymentStatus.APPROVED);
            userMap.computeIfPresent(orderEvent.getUserId(), (k, v) -> v - price);
        } else {
            paymentEvent.setStatus(PaymentStatus.REJECTED);
        }
        return paymentEvent;
    }

}
