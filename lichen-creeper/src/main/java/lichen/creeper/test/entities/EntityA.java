package lichen.creeper.test.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * test entity A
 * @author jcai
 */
@Entity
@Table(name="entity_a")
@javax.persistence.SequenceGenerator(
        name="entity_a_seq",
        sequenceName="entity_a_seq"
)
public class EntityA implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="entity_a_seq")
    @Column(name="entity_id")
    private Long id;
    private Integer balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
