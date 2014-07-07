package lichen.creeper.core.services.jpa;

import lichen.core.services.LichenException;
import lichen.core.services.Option;
import lichen.creeper.core.annotations.CreeperCore;
import lichen.creeper.core.annotations.CreeperJpa;
import lichen.creeper.core.config.CreeperCoreConfig;
import lichen.creeper.core.internal.TransactionAdvice;
import lichen.creeper.core.internal.jpa.EntityManagerCreatorImpl;
import lichen.creeper.core.internal.jpa.SmartHibernateJpaVendorAdapter;
import lichen.creeper.core.internal.jpa.SpringDataDaoProviderImpl;
import lichen.creeper.core.services.CreeperCoreExceptionCode;
import lichen.creeper.core.services.CreeperModuleManager;
import org.apache.tapestry5.ioc.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.ioc.services.MasterObjectProvider;
import org.apache.tapestry5.ioc.services.PerthreadManager;
import org.apache.tapestry5.ioc.services.ThreadCleanupListener;
import org.hibernate.jpa.HibernateEntityManager;
import org.logicalcobwebs.proxool.ProxoolDataSource;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.configuration.PropertyConfigurator;
import org.slf4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.StaticListableBeanFactory;
import org.springframework.data.repository.util.TxUtils;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Properties;

/**
 * lichen.creeper jpa module
 *
 * lichen.creeper JPA module 集成了:
 * 1) Hibernate 2)Spring Transaction 3) Spring Data JPA
 *
 * @author jcai
 */
public class CreeperJpaModule {
    @SuppressWarnings("unchecked")
	public static void bind(ServiceBinder binder){
        binder.bind(SmartHibernateJpaVendorAdapter.class).withMarker(CreeperJpa.class);
        binder.bind(EntityManagerCreator.class, EntityManagerCreatorImpl.class).withMarker(CreeperJpa.class);
        binder.bind(SpringDataDaoProvider.class, SpringDataDaoProviderImpl.class).withSimpleId().withMarker(CreeperJpa.class);
    }
    @Contribute(Runnable.class)
    public static void initJpaDaoObject(OrderedConfiguration<Runnable> configuration, final SpringDataDaoProvider provider){
        configuration.add("jpa",new Runnable() {
            @Override
            public void run() {
                provider.setupDaoObject();
            }
        },"after:database-migration");
    }
    @Marker(CreeperJpa.class)
    public static DataSource buildDataSource(CreeperCoreConfig config){
        //Oracle的schema必须和数据库用户名一致。
        Properties info = new Properties();
        info.setProperty("jdbc-lichen.proxool.alias", "creeper");
        info.setProperty("jdbc-lichen.proxool.maximum-connection-count", "50");
        info.setProperty("jdbc-lichen.user", config.db.username);
        if(StringUtils.hasText(config.db.password))
            info.setProperty("jdbc-lichen.password", config.db.password);
        info.setProperty("jdbc-lichen.proxool.driver-class", config.db.driverClassName);
        info.setProperty("jdbc-lichen.proxool.driver-url", config.db.url);

        info.setProperty("jdbc-lichen.proxool.maximum-connection-lifetime", "18000000");
        info.setProperty("jdbc-lichen.proxool.maximum-active-time", "30000");

        //configuration proxool database source
        try {
            PropertyConfigurator.configure(info);
        } catch (ProxoolException e) {
            LichenException le = LichenException.wrap(e, CreeperCoreExceptionCode.FAIL_CONFIG_PROXOOL);
            throw le;
        }
        //new datasource
        return new ProxoolDataSource("creeper");
    }
    /**
     * 创建基于Hibernate的JPA实现.
     */
    @Marker(CreeperJpa.class)
    public static EntityManagerFactory buildEntityManagerFactory(@CreeperCore CreeperCoreConfig config,
                                                                 @Local SmartHibernateJpaVendorAdapter jpaVendorAdapter,
                                                                 @CreeperJpa DataSource dataSource,
                                                                 CreeperModuleManager creeperModuleManager
                                                                 ){
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setDataSource(dataSource);
        Properties properties = new Properties();
        /*
        properties.setProperty("hibernate.connection.driver_class",config.db.driverClassName);
        properties.setProperty("hibernate.connection.url",config.db.url);
        properties.setProperty("hibernate.connection.username",config.db.username);
        if(StringUtils.hasText(config.db.password)){
            properties.setProperty("hibernate.connection.password",config.db.password);
        }
        */
        for(CreeperCoreConfig.JpaProperty property: config.jpaProperties){
            properties.setProperty(property.name,property.value);
        }
        entityManagerFactoryBean.setJpaProperties(properties);
        //增加核心模块的entities
        String[] entities = creeperModuleManager.flowModuleSubPackageWithSuffix(Option.<String>some("entities")).
                append("lichen.creeper.core.entities").toArray(String.class);

        entityManagerFactoryBean.setPackagesToScan(entities);
        jpaVendorAdapter.setAnnotatedPackages(entities);
        entityManagerFactoryBean.afterPropertiesSet();
        return entityManagerFactoryBean.getObject();
    }

    /**
     * 构建共享的EntityManager，在事务和操作的时候，都可以使用一个EntityManager
     *
     * @return
     */
    @Marker(CreeperJpa.class)
    @Scope(ScopeConstants.PERTHREAD)
    public static HibernateEntityManager buildEntityManager(Logger logger,@InjectService("PerthreadManager") PerthreadManager perthreadManager,@Local EntityManagerCreator entityManagerCreator){
        final EntityManager manager = entityManagerCreator.createEntityManager();
        //线程结束的时候，应该关闭此manager
        perthreadManager.addThreadCleanupListener(new ThreadCleanupListener() {
            @Override
            public void threadDidCleanup() {
                if(manager.isOpen())
                    manager.close();
            }
        });
        return (HibernateEntityManager) manager;
        //return SharedEntityManagerCreator.createSharedEntityManager(entityManagerFactory);
    }
    @Marker(CreeperJpa.class)
    public static JpaTransactionManager buildJpaTransactionManager(@CreeperJpa EntityManagerFactory entityManagerFactory){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        transactionManager.afterPropertiesSet();
        return transactionManager;
    }
    @Marker(CreeperJpa.class)
    public static DaoPackageManager buildDaoPackageManager(final @CreeperCore CreeperModuleManager moduleManager){
        return new DaoPackageManager() {
            private Collection<String> _daoPackages = moduleManager.flowModuleSubPackageWithSuffix(Option.some("dao")).toList();
            public boolean contains(Class<?> daoType) {
                return _daoPackages != null && daoType.getPackage()!=null && _daoPackages.contains(daoType.getPackage().getName());
            }

            @Override
            public String[] getPackages() {
                return _daoPackages.toArray(new String[_daoPackages.size()]);
            }
        };
    }
    @Contribute(MasterObjectProvider.class)
    public static void provideEntityDaoObject(OrderedConfiguration<ObjectProvider> configuration,
                                               @Local
                                               final ObjectProvider objectProvider)
    {
        configuration.add("SpringDaoProvider",objectProvider,"after:AnnotationBasedContributions", "after:ServiceOverride");
    /*
        configuration.add("entityDaoProvider", new ObjectProvider() {
            @Override
            public <T> T provide(Class<T> objectType, AnnotationProvider annotationProvider, ObjectLocator locator) {
                if (entityPackageManager.contains(objectType) && objectType.isInterface()) {
                    JpaRepositoryFactoryBean jpaRepositoryFactoryBean = new JpaRepositoryFactoryBean();
                    jpaRepositoryFactoryBean.setBeanFactory(beanFactory);
                    jpaRepositoryFactoryBean.setTransactionManager(TxUtils.DEFAULT_TRANSACTION_MANAGER);
                    //EntityManager entityManager = SharedEntityManagerCreator.createSharedEntityManager(entityManagerFactory);
                    jpaRepositoryFactoryBean.setEntityManager(entityManager);
                    jpaRepositoryFactoryBean.setRepositoryInterface(objectType);
                    //仅仅支持在Dao中发现Query
                    jpaRepositoryFactoryBean.setQueryLookupStrategyKey(QueryLookupStrategy.Key.USE_DECLARED_QUERY);
                    jpaRepositoryFactoryBean.afterPropertiesSet();
                    return (T)jpaRepositoryFactoryBean.getObject();
                }
                return null;
            }
        });
    */
    }
    @Marker(CreeperJpa.class)
    public static ListableBeanFactory buildBeanFactory(@CreeperJpa
            final JpaTransactionManager transactionManager,
            @CreeperJpa
            final EntityManagerFactory entityManagerFactory,
            @CreeperJpa
            final JpaVendorAdapter jpaVendorAdapter){
        final StaticListableBeanFactory beanFactory = new StaticListableBeanFactory();
        beanFactory.addBean(TxUtils.DEFAULT_TRANSACTION_MANAGER,transactionManager);
        beanFactory.addBean("entityManagerFactory",entityManagerFactory);
        beanFactory.addBean("jpaDialect", jpaVendorAdapter.getJpaDialect());
        return beanFactory;
    }
    @Marker(CreeperJpa.class)
    public static TransactionInterceptor buildTransactionInterceptor(@CreeperJpa BeanFactory beanFactory){
        AnnotationTransactionAttributeSource transactionAttributeSource = new AnnotationTransactionAttributeSource();

        TransactionInterceptor transactionInterceptor = new TransactionInterceptor(null, transactionAttributeSource);
        transactionInterceptor.setTransactionManagerBeanName(TxUtils.DEFAULT_TRANSACTION_MANAGER);
        transactionInterceptor.setBeanFactory(beanFactory);
        transactionInterceptor.afterPropertiesSet();
        return transactionInterceptor;
    }
    @Match("*")
    public static void adviseTransactional(MethodAdviceReceiver receiver,@CreeperJpa TransactionInterceptor transactionInterceptor)
    {
        for (Method m : receiver.getInterface().getMethods())
        {
            //仅仅对含有Transactional注解的方法进行AOP
            if (receiver.getMethodAnnotation(m, Transactional.class) != null)
                receiver.adviseMethod(m, new TransactionAdvice(transactionInterceptor,m));
        }
    };
}
