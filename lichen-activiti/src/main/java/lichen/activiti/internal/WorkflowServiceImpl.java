package lichen.activiti.internal;


import lichen.activiti.entities.WorkflowAdapter;
import lichen.activiti.models.WorkflowStat;
import lichen.activiti.services.WorkflowService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import java.util.Map;

/**
 * 工作流服务的实现类
 * @author jcai
 */
public class WorkflowServiceImpl implements WorkflowService {
    @Inject
    private RuntimeService _runtimeService;
    @Inject
    private TaskService _taskService;
    @Inject
    private HistoryService _historyService;
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
    public void complete(String taskId, Map<String, Object> variables) {
        _taskService.complete(taskId,variables);
    }

    @Override
    public WorkflowStat statByUserId(String userId) {
        WorkflowStatImpl stat = new WorkflowStatImpl();
        // 根据角色查询任务--未签收的
        TaskQuery taskCandidateUserQuery = createUnsignedTaskQuery(userId);
        long taskForGroupCounter = taskCandidateUserQuery.count();
        stat.setUnsigned(taskForGroupCounter);

        // 根据代理人查询任务--待处理
        TaskQuery taskAssigneeQuery = createTodoTaskQuery(userId);
        long todoTaskCounter = taskAssigneeQuery.count();
        stat.setTodo(todoTaskCounter);

        // 运行中流程
        ProcessInstanceQuery unfinishedQuery = createUnFinishedProcessInstanceQuery(userId);
        long unFinishedCounter = unfinishedQuery.count();
        stat.setUnfinished(unFinishedCounter);

        // 1、查询当前登录人的历史task
        // 2、根据task的processInstanceId查询流程实例
        HistoricProcessInstanceQuery finishedQuery = createFinishedProcessInstanceQuery(userId);
        long finishedCounter = finishedQuery.count();
        stat.setFinished(finishedCounter);
        return stat;
    }

    @Override
    public void assignTask(String taskId, String userId) {
        _taskService.setAssignee(taskId, userId);
    }

    @Override
    public Page<Task> getTasksByUsername(String userId, Pageable pageable) {
        TaskQuery query = _taskService.createTaskQuery().taskAssignee(userId);
        return new PageImpl<Task>(
                query.listPage(pageable.getOffset(),pageable.getPageSize()),
                pageable,
                query.count());
    }
    /**
     * 获取已经完成的流程实例查询对象
     * @param userId	用户ID
     */
    @Transactional(readOnly = true)
    public HistoricProcessInstanceQuery createFinishedProcessInstanceQuery(String userId) {
        HistoricProcessInstanceQuery finishedQuery = _historyService.createHistoricProcessInstanceQuery().finished();
        return finishedQuery;
    }

    //	/**
    //	 * 获取未经完成的流程实例查询对象
    //	 * @param userId	用户ID
    //	 */
    //	@Transactional(readOnly = true)
    //	public HistoricProcessInstanceQuery getUnFinishedProcessInstanceQuery(String userId) {
    //		HistoricProcessInstanceQuery unfinishedQuery = historyService.createHistoricProcessInstanceQuery().processDefinitionKey(getProcessName())
    //				.unfinished();
    //		return unfinishedQuery;
    //	}

    /**
     * 获取未经完成的流程实例查询对象
     * @param userId	用户ID
     */
    @Transactional(readOnly = true)
    protected ProcessInstanceQuery createUnFinishedProcessInstanceQuery(String userId) {
        ProcessInstanceQuery unfinishedQuery = _runtimeService.createProcessInstanceQuery()
                .active();
        return unfinishedQuery;
    }

    /**
     * 获取正在处理的任务查询对象
     * @param userId	用户ID
     */
    @Transactional(readOnly = true)
    protected TaskQuery createTodoTaskQuery(String userId) {
        TaskQuery taskAssigneeQuery = _taskService.createTaskQuery().
                taskAssignee(userId);
        return taskAssigneeQuery;
    }

    /**
     * 获取未签收的任务查询对象
     * @param userId	用户ID
     */
    @Transactional(readOnly = true)
    protected TaskQuery createUnsignedTaskQuery(String userId) {
        TaskQuery taskCandidateUserQuery = _taskService.createTaskQuery().
                taskCandidateUser(userId);
        return taskCandidateUserQuery;
    }
}
