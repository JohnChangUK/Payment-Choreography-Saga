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
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

import static payment.saga.order.util.Utils.PRODUCT_PRICES;

@Service
public class OrderService {

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

    public Flux<List<OrderPurchase>> reactiveGetAll() {
        Flux<Long> interval = Flux.interval(Duration.ofMillis(2000));
        Flux<List<OrderPurchase>> orderPurchaseFlux = Flux.fromStream(
                Stream.generate(orderPurchaseRepository::findAll));
        return Flux.zip(interval, orderPurchaseFlux)
                .map(Tuple2::getT2);
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
