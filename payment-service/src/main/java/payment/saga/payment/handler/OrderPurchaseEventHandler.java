package payment.saga.payment.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import payment.saga.payment.model.OrderPurchaseEvent;
import payment.saga.payment.model.PaymentEvent;
import payment.saga.payment.model.User;
import payment.saga.payment.repository.UserRepository;

import javax.transaction.Transactional;

import static payment.saga.payment.enums.PaymentStatus.APPROVED;
import static payment.saga.payment.enums.PaymentStatus.DECLINED;

@Component
public class OrderPurchaseEventHandler implements EventHandler<OrderPurchaseEvent, PaymentEvent> {

    private final UserRepository userRepository;

    @Autowired
    public OrderPurchaseEventHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public PaymentEvent handleEvent(OrderPurchaseEvent event) {
        double orderPrice = event.getPrice();
        Integer userId = event.getUserId();
        PaymentEvent paymentEvent = new PaymentEvent()
                .orderId(event.getOrderId())
                .price(event.getPrice())
                .status(DECLINED);
        userRepository
                .findById(userId)
                .ifPresent(user -> deductUserBalance(orderPrice, paymentEvent, user));
        return paymentEvent;
    }

    private void deductUserBalance(double orderPrice, PaymentEvent paymentEvent, User user) {
        double userBalance = user.getBalance();
        if (userBalance >= orderPrice) {
            user.setBalance(userBalance - orderPrice);
            userRepository.save(user);
            paymentEvent.status(APPROVED);
        }
    }

}
