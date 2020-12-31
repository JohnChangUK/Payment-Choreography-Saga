package payment.saga.consumer.eventhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;
import payment.saga.consumer.model.PaymentEvent;

@Component
public class PaymentEventConsumer {

    private final Logger log = LoggerFactory.getLogger(PaymentEventConsumer.class);

    @StreamListener(Sink.INPUT)
    public void consumePaymentEvent(PaymentEvent payment) {
        log.info("Consumed payload: " + payment);
    }

    public void consumePaymentEvent2(PaymentEvent payment) {
        log.info("Consumed payload: " + payment);
    }
}
