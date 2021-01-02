package payment.saga.payment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@AllArgsConstructor
@Data
@Entity
@ToString
public class Payment {

    @Id
    @GeneratedValue
    private Integer id;
    private BigDecimal amount;
}
