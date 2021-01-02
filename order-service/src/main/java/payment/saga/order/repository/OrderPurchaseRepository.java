package payment.saga.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import payment.saga.order.model.OrderPurchase;

@Repository
public interface OrderPurchaseRepository extends JpaRepository<OrderPurchase, Integer> {
}
