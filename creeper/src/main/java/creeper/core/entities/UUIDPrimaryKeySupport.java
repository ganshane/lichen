package creeper.core.entities;

import creeper.core.CreeperCoreConstants;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 主键为UUID类型
 * @author jcai
 */
@MappedSuperclass
public class UUIDPrimaryKeySupport implements Serializable{
	private static final long serialVersionUID = -2686414518400291686L;
	@Id
    @GeneratedValue(generator= CreeperCoreConstants.UUID_GENERATOR)
    @Column
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
