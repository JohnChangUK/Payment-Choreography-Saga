package payment.saga.order.dto;

import lombok.Builder;
import lombok.Data;
import payment.saga.order.enums.OrderStatus;

@Data
@Builder
public class OrderResponseDTO {

    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer price;
    private OrderStatus status;

}
