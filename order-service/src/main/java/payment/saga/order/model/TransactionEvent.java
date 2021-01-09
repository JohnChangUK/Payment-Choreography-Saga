package payment.saga.order.model;

import lombok.Getter;
import lombok.ToString;
import payment.saga.order.enums.TransactionStatus;

@ToString
@Getter
public class TransactionEvent implements Event {

    private static final String EVENT = "Transaction";

    private Integer orderId;
    private TransactionStatus status;

    public TransactionEvent() {
    }

    public TransactionEvent orderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public TransactionEvent status(TransactionStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public String getEvent() {
        return EVENT;
    }

}
