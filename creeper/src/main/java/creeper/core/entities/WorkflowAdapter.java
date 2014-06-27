package creeper.core.entities;

/**
 * 适应workflow的实体类
 * @author jcai
 */
public interface WorkflowAdapter {
    public void setProcessInstanceId(String processInstanceId);
    public String getBusinessId();
}
