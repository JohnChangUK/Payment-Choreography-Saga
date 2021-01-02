package payment.saga.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import payment.saga.payment.model.OrderPurchase;

@Repository
public interface OrderPurchaseRepository extends JpaRepository<OrderPurchase, Integer> {
}
