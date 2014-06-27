package creeper.core.services.activiti;

import creeper.core.annotations.CreeperActiviti;
import creeper.core.internal.activiti.CreeperWorkflowManagerImpl;
import org.activiti.engine.*;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Marker;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

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
    public static void bind(ServiceBinder binder){
        binder.bind(CreeperWorkflowManager.class, CreeperWorkflowManagerImpl.class).withMarker(CreeperActiviti.class).eagerLoad();
    }
    @Marker(CreeperActiviti.class)
    public static ProcessEngine buildProcessEngine(EntityManagerFactory entityManagerFactory,
                                                   PlatformTransactionManager transactionManager,
                                                   DataSource dataSource) throws Exception {
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        configuration.setJpaEntityManagerFactory(entityManagerFactory);
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
}
