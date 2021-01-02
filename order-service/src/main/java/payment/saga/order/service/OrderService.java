package payment.saga.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.saga.order.dto.Order;
import payment.saga.order.enums.OrderStatus;
import payment.saga.order.handler.OrderPurchaseHandler;
import payment.saga.order.model.OrderPurchase;
import payment.saga.order.repository.OrderPurchaseRepository;

import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    // product price map
    private static final Map<Integer, Integer> PRODUCT_PRICE = Map.of(
            1, 100,
            2, 200,
            3, 300
    );

    private final OrderPurchaseRepository orderPurchaseRepository;
    private final OrderPurchaseHandler orderPurchaseHandler;

    @Autowired
    public OrderService(
            OrderPurchaseRepository orderPurchaseRepository,
            OrderPurchaseHandler orderPurchaseHandler) {
        this.orderPurchaseRepository = orderPurchaseRepository;
        this.orderPurchaseHandler = orderPurchaseHandler;
    }

    public OrderPurchase createOrder(Order order) {
        OrderPurchase orderPurchase = getOrderPurchase(order);
        OrderPurchase savedOrderPurchase = orderPurchaseRepository.save(orderPurchase);
        orderPurchaseHandler.createOrderPurchaseEvent(savedOrderPurchase);
        return savedOrderPurchase;
    }

    public List<OrderPurchase> getAll() {
        return orderPurchaseRepository.findAll();
    }

    private OrderPurchase getOrderPurchase(final Order order) {
        return new OrderPurchase()
                .setProductId(order.getProductId())
                .setUserId(order.getUserId())
                .setPrice(PRODUCT_PRICE.get(order.getProductId()))
                .setStatus(OrderStatus.ORDER_CREATED);
    }

}
