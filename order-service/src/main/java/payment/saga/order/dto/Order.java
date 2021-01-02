package payment.saga.order.dto;

import lombok.Data;

@Data
public class Order {

    private Integer userId;
    private Integer productId;

}
