package lichen.creeper.test.entities;

import lichen.creeper.core.CreeperCoreConstants;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

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
    @Type(type=CreeperCoreConstants.TYPE_INT_DATE)
    private Date date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
