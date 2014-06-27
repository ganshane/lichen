package creeper.core.services.activiti;

import creeper.core.entities.WorkflowAdapter;
import org.activiti.engine.task.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * 工作流服务类
 * @author jcai
 */
public interface WorkflowService {
    /**
     * 启动工作流
     * @param processDefineKey 流程定义的key，在xml文件中定义
     * @param entity 业务实体
     * @param variables 请求时候的变量
     * @return 流程实例的ID
     */
    <T extends WorkflowAdapter> String startWorkflow(String processDefineKey, T entity,Map<String,Object> variables);

    /**
     * 得到某一用户的待办任务
     * @param userId
     * @return 分页数据
     */
    public Page<Task> getTasksByUsername(String userId,Pageable pageable);
}
