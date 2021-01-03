package payment.saga.order.model;

import lombok.Data;

@Data
public class Order {

    private Integer userId;
    private Integer productId;

}
