package payment.saga.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import payment.saga.order.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
