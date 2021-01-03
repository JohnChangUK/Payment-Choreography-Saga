package payment.saga.payment.model;

import lombok.Getter;
import lombok.ToString;
import payment.saga.payment.enums.TransactionStatus;

import java.util.function.Supplier;

@ToString
@Getter
public class TransactionEvent {

    private Integer orderId;
    private TransactionStatus status;

    public TransactionEvent() {
    }

    public TransactionEvent orderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public TransactionEvent status(Supplier<TransactionStatus> status) {
        this.status = status.get();
        return this;
    }
}
