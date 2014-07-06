package lichen.creeper.core.entities;

import lichen.creeper.core.CreeperCoreConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * 主键为UUID类型
 * @author jcai
 */
@MappedSuperclass
public class UUIDPrimaryKeySupport implements Serializable{
	private static final long serialVersionUID = -2686414518400291686L;


    @Id
    @GeneratedValue(generator= CreeperCoreConstants.UUID_GENERATOR)
    @Column(name="id")
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
