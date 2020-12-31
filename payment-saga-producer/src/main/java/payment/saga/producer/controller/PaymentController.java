package payment.saga.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import payment.saga.producer.model.Payment;

@RestController
public class PaymentController {

    private final MessageChannel output;

    @Autowired
    public PaymentController(MessageChannel output) {
        this.output = output;
    }

    @PostMapping("/publish")
    public ResponseEntity<Payment> publishPayment(@RequestBody Payment payment) {
        output.send(MessageBuilder.withPayload(payment).build());
        return ResponseEntity.ok(payment);
    }
}
