package payment.saga.producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.saga.producer.dto.OrderRequestDTO;
import payment.saga.producer.dto.OrderResponseDTO;
import payment.saga.producer.enums.OrderStatus;
import payment.saga.producer.eventhandler.OrderEventHandler;
import payment.saga.producer.model.PurchaseOrder;
import payment.saga.producer.repository.PurchaseOrderRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    // product price map
    private static final Map<Integer, Integer> PRODUCT_PRICE = Map.of(
            1, 100,
            2, 200,
            3, 300
    );

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final OrderEventHandler orderEventHandler;

    @Autowired
    public OrderService(PurchaseOrderRepository purchaseOrderRepository, OrderEventHandler orderEventHandler) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.orderEventHandler = orderEventHandler;
    }

    public PurchaseOrder createOrder(OrderRequestDTO orderRequestDTO) {
        PurchaseOrder purchaseOrder = this.purchaseOrderRepository.save(this.dtoToEntity(orderRequestDTO));
        this.orderEventHandler.raiseOrderCreatedEvent(purchaseOrder);
        return purchaseOrder;
    }

    public List<OrderResponseDTO> getAll() {
        return purchaseOrderRepository.findAll()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    private PurchaseOrder dtoToEntity(final OrderRequestDTO dto) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setProductId(dto.getProductId());
        purchaseOrder.setUserId(dto.getUserId());
        purchaseOrder.setStatus(OrderStatus.ORDER_CREATED);
        purchaseOrder.setPrice(PRODUCT_PRICE.get(purchaseOrder.getProductId()));
        return purchaseOrder;
    }

    private OrderResponseDTO entityToDto(final PurchaseOrder purchaseOrder) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(purchaseOrder.getId());
        dto.setProductId(purchaseOrder.getProductId());
        dto.setUserId(purchaseOrder.getUserId());
        dto.setStatus(purchaseOrder.getStatus());
        dto.setPrice(purchaseOrder.getPrice());
        return dto;
    }

}
