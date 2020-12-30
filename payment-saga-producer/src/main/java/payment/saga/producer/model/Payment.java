package payment.saga.producer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
