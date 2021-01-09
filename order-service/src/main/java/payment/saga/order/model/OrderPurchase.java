package payment.saga.order.model;

import lombok.Data;
import lombok.ToString;
import payment.saga.order.enums.OrderStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@ToString
public class OrderPurchase {

    public OrderPurchase() {
    }

    @Id
    @GeneratedValue
    private Integer id;
    private Integer userId;
    private Integer productId;
    private double price;
    private OrderStatus status;

    public OrderPurchase setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public OrderPurchase setProductId(Integer productId) {
        this.productId = productId;
        return this;
    }

    public OrderPurchase setPrice(double price) {
        this.price = price;
        return this;
    }

    public OrderPurchase setStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

}
