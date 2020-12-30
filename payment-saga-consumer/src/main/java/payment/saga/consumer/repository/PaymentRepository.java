package payment.saga.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import payment.saga.consumer.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
