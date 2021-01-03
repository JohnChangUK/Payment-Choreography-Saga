package payment.saga.order.model;

import lombok.Data;
import lombok.ToString;
import payment.saga.order.enums.PaymentStatus;

@Data
@ToString
public class PaymentEvent {

    private Integer orderId;
    private PaymentStatus status;

    public PaymentEvent() {
    }

    public PaymentEvent(Integer orderId) {
        this.orderId = orderId;
    }

}
