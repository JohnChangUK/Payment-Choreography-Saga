package payment.saga.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import payment.saga.payment.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
