package creeper.core.entities;

/**
 * 适应workflow的实体类.
 * <p>
 * 所有业务实体想实现工作流机制，可以实现此接口
 * </p>
 * @author jcai
 */
public interface WorkflowAdapter {
    public void setProcessInstanceId(String processInstanceId);
    public String getBusinessId();
}
