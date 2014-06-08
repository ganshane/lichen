package creeper.core.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 支持时间的基础类
 * @author jcai
 */
@MappedSuperclass
public class TimstampSupport {
    @Column(name="timestamp")
    private Integer timestamp;
}
