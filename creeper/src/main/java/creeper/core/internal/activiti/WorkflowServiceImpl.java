package creeper.core.internal.activiti;

import creeper.core.entities.WorkflowAdapter;
import creeper.core.services.activiti.WorkflowService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Map;

/**
 * 工作流服务的实现类
 * @author jcai
 */
public class WorkflowServiceImpl implements WorkflowService{
    @Inject
    private RuntimeService _runtimeService;
    @Inject
    private TaskService _taskService;
    @Inject
    private EntityManager _entityManager;
    @Override
    public <T extends WorkflowAdapter> String startWorkflow(String processDefineKey, T entity,Map<String,Object> variables) {
        ProcessInstance instance = _runtimeService.startProcessInstanceByKey(processDefineKey, entity.getBusinessId(),variables);
        entity.setProcessInstanceId(instance.getProcessInstanceId());
        _entityManager.persist(entity);
        return instance.getProcessInstanceId();
    }

    @Override
    public Page<Task> getTasksByUsername(String userId, Pageable pageable) {
        TaskQuery query = _taskService.createTaskQuery().taskAssignee(userId);
        return new PageImpl<Task>(
                query.listPage(pageable.getOffset(),pageable.getPageSize()),
                pageable,
                query.count());
    }
}
