package payment.saga.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import payment.saga.payment.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
