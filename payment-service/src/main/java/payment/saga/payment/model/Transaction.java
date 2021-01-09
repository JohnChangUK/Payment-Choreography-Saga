package payment.saga.payment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@ToString
@AllArgsConstructor
@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer orderId;
    private double price;

    public Transaction() {
    }

    public Transaction setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public Transaction setPrice(double price) {
        this.price = price;
        return this;
    }
}
