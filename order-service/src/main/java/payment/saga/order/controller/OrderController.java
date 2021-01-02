package payment.saga.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import payment.saga.order.dto.Order;
import payment.saga.order.model.OrderPurchase;
import payment.saga.order.service.OrderService;

import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("orders/create")
    public OrderPurchase createOrder(@RequestBody Order order){
        return orderService.createOrder(order);
    }

    @GetMapping("orders/all")
    public List<OrderPurchase> getAllOrders(){
        return orderService.getAll();
    }

}
