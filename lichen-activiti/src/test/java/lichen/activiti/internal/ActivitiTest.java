package lichen.activiti.internal;

import lichen.activiti.LichenActivitiModule;
import lichen.activiti.services.LichenWorkflowManager;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

/**
 * @author jcai
 */
public class ActivitiTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static String workflow = "hello.bpmn20.xml";
    private Registry registry;

    public static class TestService {
        public String findUserName(){
            return "james";
        };
    }
    public static class WorkFlowTestModule{
        public static void bind(ServiceBinder binder){
            binder.bind(TestService.class);
        }
        @Contribute(LichenWorkflowManager.class)
        public static void provideHelloWorkflow(
                Configuration<org.springframework.core.io.Resource> configuration){
            configuration.add(new ClassPathResource(workflow));
        }
    }
    @Before
    public void setup(){
        registry = RegistryBuilder.buildAndStartupRegistry(LichenActivitiModule.class,WorkFlowTestModule.class);
    }
    @After
    public void teardown(){
        registry.shutdown();
    }
    @Test
    public void test_instance(){
        String processDefinitionKey="HelloWorld";
        String businessKey="id";
        RepositoryService repositoryService = registry.getService(RepositoryService.class);
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey).list();
        Assert.assertEquals(1, list.size());

        RuntimeService runtimeService = registry.getService(RuntimeService.class);

        ProcessInstance instance = runtimeService.startProcessInstanceByKey(processDefinitionKey,businessKey);


        TaskService taskService = registry.getService(TaskService.class);
        List<Task> tasks = taskService.createTaskQuery().list();
        Task task = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey).singleResult();
        logger.debug("task name:{}", task);
        taskService.complete(task.getId());

        HistoryService historyService = registry.getService(HistoryService.class);
        long runtimeCounter = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).count();
        Assert.assertEquals(0, runtimeCounter);
        long historicCounter = historyService.createHistoricProcessInstanceQuery().finished().processInstanceBusinessKey(businessKey).count();
        Assert.assertEquals(1, historicCounter);
    }
}
