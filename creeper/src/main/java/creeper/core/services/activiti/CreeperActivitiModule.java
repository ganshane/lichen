package creeper.core.services.activiti;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import creeper.core.internal.activiti.ActivitiServiceExporterImpl;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Marker;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.apache.tapestry5.ioc.services.ServiceActivityScoreboard;
import org.springframework.transaction.PlatformTransactionManager;

import creeper.core.annotations.CreeperActiviti;
import creeper.core.internal.activiti.CreeperWorkflowManagerImpl;
import creeper.core.internal.activiti.WorkflowServiceImpl;

/**
 * 集成工作流引擎 activiti.
 * @author jcai
 */
public class CreeperActivitiModule {

    //启动工作流,自动加载工作流
    @Startup
    public static void setupWorkflow(@Local CreeperWorkflowManager manager){
        manager.deploy();
    }
    @SuppressWarnings("unchecked")
	public static void bind(ServiceBinder binder){
        binder.bind(CreeperWorkflowManager.class, CreeperWorkflowManagerImpl.class).withMarker(CreeperActiviti.class);
        binder.bind(WorkflowService.class, WorkflowServiceImpl.class).withMarker(CreeperActiviti.class);
    }
    public static ActivitiServiceExporter buildActivitiServiceExporter(ObjectLocator objectLocator,ServiceActivityScoreboard scoreboard){
        return new ActivitiServiceExporterImpl(scoreboard,objectLocator);
    }
    @Marker(CreeperActiviti.class)
    public static ProcessEngine buildProcessEngine(EntityManagerFactory entityManagerFactory,
                                                   PlatformTransactionManager transactionManager,
                                                   DataSource dataSource,
                                                   ActivitiServiceExporter activitiServiceExporter) throws Exception {
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        configuration.setBeans(activitiServiceExporter);

        //configuration.setJpaEntityManagerFactory(entityManagerFactory);
        configuration.setJpaHandleTransaction(false);
        configuration.setJobExecutorActivate(false);
        configuration.setJpaCloseEntityManager(false);
        configuration.setTransactionManager(transactionManager);
        configuration.setDataSource(dataSource);
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration(configuration);
        return processEngineFactoryBean.getObject();
    }
    @Marker(CreeperActiviti.class)
    public static RepositoryService buildRepositoryService(ProcessEngine engine){
        return engine.getRepositoryService();
    }
    @Marker(CreeperActiviti.class)
    public static RuntimeService buildRuntimeService(ProcessEngine engine){
        return engine.getRuntimeService();
    }
    @Marker(CreeperActiviti.class)
    public static TaskService buildTaskService(ProcessEngine engine){
        return engine.getTaskService();
    }
    @Marker(CreeperActiviti.class)
    public static HistoryService buildHistoryService(ProcessEngine engine){
        return engine.getHistoryService();
    }
    @Marker(CreeperActiviti.class)
    public static IdentityService buildIdentityService(ProcessEngine engine){
        return engine.getIdentityService();
    }
}
