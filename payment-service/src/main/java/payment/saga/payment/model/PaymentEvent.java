package payment.saga.payment.model;

import lombok.Getter;
import lombok.ToString;
import payment.saga.payment.enums.PaymentStatus;

@ToString
@Getter
public class PaymentEvent {

    private Integer orderId;
    private PaymentStatus status;
    private Integer price;

    public PaymentEvent() {
    }

    public PaymentEvent orderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public PaymentEvent status(PaymentStatus status) {
        this.status = status;
        return this;
    }

    public PaymentEvent price(Integer price) {
        this.price = price;
        return this;
    }
}
