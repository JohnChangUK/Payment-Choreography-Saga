package payment.saga.consumer.model;

import lombok.Data;

@Data
public class OrderEvent {
    private Integer orderId;
    private Integer userId;
    private Integer price;
}
