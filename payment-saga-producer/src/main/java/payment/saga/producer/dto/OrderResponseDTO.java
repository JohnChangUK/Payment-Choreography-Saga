package payment.saga.producer.dto;

import lombok.Data;
import payment.saga.producer.enums.OrderStatus;

@Data
public class OrderResponseDTO {

    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer price;
    private OrderStatus status;

}
