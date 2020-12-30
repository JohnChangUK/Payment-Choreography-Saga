package payment.saga.consumer.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import payment.saga.consumer.model.Payment;

@Component
public class PaymentListener {

    private final Logger log = LoggerFactory.getLogger(PaymentListener.class);

    @StreamListener("input")
    public void consumePayment(Payment payment) {
        log.info("Consumed payload: " + payment);
    }
}
