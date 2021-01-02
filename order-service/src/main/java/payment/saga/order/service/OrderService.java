package payment.saga.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import payment.saga.order.dto.Order;
import payment.saga.order.enums.OrderStatus;
import payment.saga.order.handler.OrderPurchaseHandler;
import payment.saga.order.model.OrderPurchase;
import payment.saga.order.repository.OrderPurchaseRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.time.Duration;
import java.util.Map;

@Service
public class OrderService {

    private static final Map<Integer, Integer> PRODUCT_PRICES = Map.of(
            1, 50,
            2, 100,
            3, 150
    );

    private final OrderPurchaseRepository orderPurchaseRepository;
    private final OrderPurchaseHandler orderPurchaseHandler;
    private final Scheduler jdbcScheduler;

    @Autowired
    public OrderService(
            OrderPurchaseRepository orderPurchaseRepository,
            OrderPurchaseHandler orderPurchaseHandler,
            Scheduler jdbcScheduler) {
        this.orderPurchaseRepository = orderPurchaseRepository;
        this.orderPurchaseHandler = orderPurchaseHandler;
        this.jdbcScheduler = jdbcScheduler;
    }

    public Mono<OrderPurchase> createOrder(Order order) {
        OrderPurchase orderPurchase = getOrderPurchase(order);
        OrderPurchase savedOrderPurchase = orderPurchaseRepository.save(orderPurchase);
        orderPurchaseHandler.createOrderPurchaseEvent(orderPurchase);
        return Mono.just(savedOrderPurchase);
    }

    public Flux<OrderPurchase> getAll() {
        return Flux.defer(
                () -> Flux.fromIterable(orderPurchaseRepository.findAll()))
                .subscribeOn(jdbcScheduler);
    }

    public Flux<OrderPurchase> reactiveGetAll() {
        return Flux.interval(Duration.ofMillis(5000))
                .onBackpressureDrop()
                .flatMap(x -> Flux.fromIterable(orderPurchaseRepository.findAll()));
    }

    public Mono<OrderPurchase> getOrderById(Integer id) {
        return Mono.fromCallable(
                () -> orderPurchaseRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Order id: " + id + " does not exist")))
                .subscribeOn(jdbcScheduler);
    }

    private OrderPurchase getOrderPurchase(final Order order) {
        return new OrderPurchase()
                .setProductId(order.getProductId())
                .setUserId(order.getUserId())
                .setPrice(PRODUCT_PRICES.get(order.getProductId()))
                .setStatus(OrderStatus.ORDER_CREATED);
    }
}
