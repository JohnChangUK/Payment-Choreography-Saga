package payment.saga.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payment.saga.producer.dto.OrderRequestDTO;
import payment.saga.producer.dto.OrderResponseDTO;
import payment.saga.producer.model.PurchaseOrder;
import payment.saga.producer.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public PurchaseOrder createOrder(@RequestBody OrderRequestDTO dto){
        return this.orderService.createOrder(dto);
    }

    @GetMapping("/all")
    public List<OrderResponseDTO> getOrders(){
        return this.orderService.getAll();
    }

}
