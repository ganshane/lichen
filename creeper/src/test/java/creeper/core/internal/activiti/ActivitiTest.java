package creeper.core.internal.activiti;

import creeper.core.annotations.CreeperActiviti;
import creeper.core.models.CreeperModuleDef;
import creeper.core.services.activiti.CreeperActivitiModule;
import creeper.core.services.activiti.CreeperWorkflowManager;
import creeper.core.services.jpa.BaseEntityTestCase;
import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.internal.AssetConstants;
import org.apache.tapestry5.internal.services.AbstractAsset;
import org.apache.tapestry5.internal.services.AssetSourceImpl;
import org.apache.tapestry5.internal.services.ClasspathAssetFactory;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.EagerLoad;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.ClasspathResource;
import org.apache.tapestry5.services.*;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.List;

/**
 * @author jcai
 */
public class ActivitiTest extends BaseEntityTestCase{
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static String workflow = "hello.bpmn20.xml";

    @Override
    protected Class<?>[] getIocModules() {
        return new Class<?>[]{CreeperActivitiModule.class,WorkFlowTestModule.class};
    }

    public static class WorkFlowTestModule{
        @EagerLoad
        public static AssetSource buildAssetSource(){
            AssetSource mock = Mockito.mock(AssetSource.class);

            final Resource classpathResource = new ClasspathResource(workflow);
            Asset classpathAsset = new AbstractAsset(false)
            {
                public Resource getResource()
                {
                    return classpathResource;
                }

                public String toClientURL()
                {
                    //return clientURL(resource);
                    return null;
                }
            };
            Mockito.when(mock.getAsset(null, "classpath:"+workflow, null)).thenReturn(classpathAsset);
            return mock;
        }
        @Contribute(CreeperWorkflowManager.class)
        public static void provideHelloWorkflow(
                Configuration<String> configuration){
            configuration.add("classpath:"+workflow);
        }
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
