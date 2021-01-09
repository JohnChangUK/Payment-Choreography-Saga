package payment.saga.order.model;

import lombok.Data;

@Data
public class OrderPurchaseEvent implements Event {

    private static final String EVENT = "OrderPurchase";

    private Integer orderId;
    private Integer userId;
    private double price;

    public OrderPurchaseEvent setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public OrderPurchaseEvent setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public OrderPurchaseEvent setPrice(double price) {
        this.price = price;
        return this;
    }

    @Override
    public String getEvent() {
        return EVENT;
    }
}
