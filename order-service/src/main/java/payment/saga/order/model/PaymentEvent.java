package payment.saga.order.model;

import lombok.Getter;
import lombok.ToString;
import payment.saga.order.enums.PaymentStatus;

@ToString
@Getter
public class PaymentEvent implements Event {

    private static final String EVENT = "Payment";

    private Integer orderId;
    private PaymentStatus status;
    private double price;

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

    public PaymentEvent price(double price) {
        this.price = price;
        return this;
    }

    @Override
    public String getEvent() {
        return EVENT;
    }

}
