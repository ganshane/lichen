package creeper.test.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author jcai
 */
@Entity
@Table(name="entity_b")
public class EntityB {
    @Id
    @GeneratedValue(generator = "uuid")
    private String id;
    private String name;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
