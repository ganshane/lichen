package lichen.activiti;

import lichen.activiti.annotations.LichenActiviti;
import lichen.activiti.internal.ActivitiServiceExporterImpl;
import lichen.activiti.internal.LichenWorkflowManagerImpl;
import lichen.activiti.internal.WorkflowServiceImpl;
import lichen.activiti.services.ActivitiServiceExporter;
import lichen.activiti.services.LichenWorkflowManager;
import lichen.activiti.services.WorkflowService;
import org.activiti.engine.*;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Marker;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.apache.tapestry5.ioc.services.ServiceActivityScoreboard;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


/**
 * 集成工作流引擎 activiti.
 * @author jcai
 */
public class LichenActivitiModule {

    //启动工作流,自动加载工作流
    @Startup
    public static void setupWorkflow(@Local LichenWorkflowManager manager){
        manager.deploy();
    }
    @SuppressWarnings("unchecked")
	public static void bind(ServiceBinder binder){
        binder.bind(LichenWorkflowManager.class, LichenWorkflowManagerImpl.class).withMarker(LichenActiviti.class);
        binder.bind(WorkflowService.class, WorkflowServiceImpl.class).withMarker(LichenActiviti.class);
    }
    public static ActivitiServiceExporter buildActivitiServiceExporter(ObjectLocator objectLocator,ServiceActivityScoreboard scoreboard){
        return new ActivitiServiceExporterImpl(scoreboard,objectLocator);
    }
    @Marker(LichenActiviti.class)
    public static ProcessEngine buildProcessEngine(PlatformTransactionManager transactionManager,
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
    @Marker(LichenActiviti.class)
    public static RepositoryService buildRepositoryService(ProcessEngine engine){
        return engine.getRepositoryService();
    }
    @Marker(LichenActiviti.class)
    public static RuntimeService buildRuntimeService(ProcessEngine engine){
        return engine.getRuntimeService();
    }
    @Marker(LichenActiviti.class)
    public static TaskService buildTaskService(ProcessEngine engine){
        return engine.getTaskService();
    }
    @Marker(LichenActiviti.class)
    public static HistoryService buildHistoryService(ProcessEngine engine){
        return engine.getHistoryService();
    }
    @Marker(LichenActiviti.class)
    public static IdentityService buildIdentityService(ProcessEngine engine){
        return engine.getIdentityService();
    }
}
