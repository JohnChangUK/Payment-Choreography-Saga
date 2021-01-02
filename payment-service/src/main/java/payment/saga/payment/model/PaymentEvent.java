package payment.saga.payment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import payment.saga.payment.enums.PaymentStatus;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEvent {

    private Integer orderId;
    private PaymentStatus status;

    public PaymentEvent(Integer orderId) {
        this.orderId = orderId;
    }
}