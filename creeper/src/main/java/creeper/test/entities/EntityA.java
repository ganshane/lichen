package creeper.test.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author jcai
 */
@Entity
@Table(name="entity_a")
@javax.persistence.SequenceGenerator(
        name="SEQ_STORE",
        sequenceName="my_sequence"
)
public class EntityA implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_STORE")
    @Column(name="account_id")
    private Long accountId;
    private Integer balance;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
