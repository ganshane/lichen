package creeper.core.services.jpa;

import creeper.core.annotations.CreeperCore;
import creeper.core.annotations.CreeperJpa;
import creeper.core.config.CreeperCoreConfig;
import creeper.core.internal.TransactionAdvice;
import creeper.core.internal.jpa.EntityManagerCreatorImpl;
import creeper.core.internal.jpa.SmartHibernateJpaVendorAdapter;
import creeper.core.internal.jpa.SpringDataDaoProviderImpl;
import creeper.core.services.CreeperCoreExceptionCode;
import creeper.core.services.CreeperException;
import creeper.core.services.CreeperModuleManager;
import lichen.core.services.Option;
import org.apache.tapestry5.ioc.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.ioc.services.*;
import org.apache.tapestry5.services.Core;
import org.logicalcobwebs.proxool.ProxoolDataSource;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.configuration.PropertyConfigurator;
import org.slf4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.StaticListableBeanFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.query.QueryLookupStrategy;
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
 * creeper jpa module
 *
 * creeper JPA module 集成了:
 * 1) Hibernate 2)Spring Transaction 3) Spring Data JPA
 *
 * @author jcai
 */
public class CreeperJpaModule {
    public static void bind(ServiceBinder binder){
        binder.bind(SmartHibernateJpaVendorAdapter.class).withMarker(CreeperJpa.class);
        binder.bind(EntityManagerCreator.class, EntityManagerCreatorImpl.class).withMarker(CreeperJpa.class);
        binder.bind(SpringDataDaoProvider.class, SpringDataDaoProviderImpl.class).withSimpleId().withMarker(CreeperJpa.class);
    }
    @Marker(CreeperJpa.class)
    public static DataSource buildDataSource(CreeperCoreConfig config){
        //Oracle的schema必须和数据库用户名一致。
        Properties info = new Properties();
        info.setProperty("jdbc-creeper.proxool.alias", "creeper");
        info.setProperty("jdbc-creeper.proxool.maximum-connection-count", "50");
        info.setProperty("jdbc-creeper.user", config.db.username);
        if(StringUtils.hasText(config.db.password))
            info.setProperty("jdbc-creeper.password", config.db.password);
        info.setProperty("jdbc-creeper.proxool.driver-class", config.db.driverClassName);
        info.setProperty("jdbc-creeper.proxool.driver-url", config.db.url);

        info.setProperty("jdbc-creeper.proxool.maximum-connection-lifetime", "18000000");
        info.setProperty("jdbc-creeper.proxool.maximum-active-time", "30000");

        //configuration proxool database source
        try {
            PropertyConfigurator.configure(info);
        } catch (ProxoolException e) {
            CreeperException ce = CreeperException.wrap(e, CreeperCoreExceptionCode.FAIL_CONFIG_PROXOOL);
            throw ce;
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
                append("creeper.core.entities").toArray(String.class);

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
    public static EntityManager buildEntityManager(Logger logger,@InjectService("PerthreadManager") PerthreadManager perthreadManager,@Local EntityManagerCreator entityManagerCreator){
        final EntityManager manager = entityManagerCreator.createEntityManager();
        //线程结束的时候，应该关闭此manager
        perthreadManager.addThreadCleanupListener(new ThreadCleanupListener() {
            @Override
            public void threadDidCleanup() {
                if(manager.isOpen())
                    manager.close();
            }
        });
        return manager;
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
