package creeper.core.services.activiti;

import creeper.core.entities.WorkflowAdapter;
import creeper.core.models.WorkflowTaskStat;
import org.activiti.engine.task.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * 工作流服务类
 * @author jcai
 */
public interface WorkflowService {
    public static enum TaskStatus{
        UNSIGNED, //未签收
        TODO, //将要做
        UNFINISHED,//未完成
        FINISHED //完成
    }
    /**
     * 启动工作流
     * @param processDefineKey 流程定义的key，在xml文件中定义
     * @param entity 业务实体
     * @param variables 请求时候的变量
     * @return 流程实例的ID
     */
    <T extends WorkflowAdapter> String startWorkflow(String processDefineKey, T entity,Map<String,Object> variables);

    /**
     * 完成某一任务
     * @param taskId 任务的ID
     * @param variables 完成任务时候的变量
     */
    void complete(String taskId,Map<String,Object> variables);

    /**
     * 根据用户的ID来得到工作流的状态
     * @param userId 用户的ID
     * @return 工作流的状态
     */
    WorkflowTaskStat statByUserId(String userId);

    /**
     * 签收某一任务
     * @param taskId 任务的id
     * @param userId 用户的id
     */
    void assignTask(String taskId,String userId);
    //Page<Task> findTasks(TaskStatus status,String userId,Pageable pageable);


    /**
     * 得到某一用户的待办任务
     * @param userId
     * @return 分页数据
     */
    Page<Task> getTasksByUsername(String userId,Pageable pageable);
}
