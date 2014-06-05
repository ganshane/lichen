package creeper.core.services;

import creeper.core.config.CreeperCoreConfig;
import creeper.core.internal.TransactionAdvice;
import org.apache.tapestry5.ioc.*;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Match;
import org.apache.tapestry5.ioc.services.MasterObjectProvider;
import org.apache.tapestry5.plastic.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.StaticListableBeanFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.util.TxUtils;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * creeper jpa module
 * @author jcai
 */
public class CreeperJpaModule {
    public static void bind(ServiceBinder binder){
        binder.bind(JpaVendorAdapter.class, HibernateJpaVendorAdapter.class);
        binder.bind(TransactionAdvice.class);
    }
    //创建基于Hibernate的JPA实现
    public static EntityManagerFactory buildEntityManagerFactory(CreeperCoreConfig config,
                                                                 JpaVendorAdapter jpaVendorAdapter,
                                                                 Collection<String> entityPackages
                                                                 ){
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.driver_class",config.db._driverClassName);
        properties.setProperty("hibernate.connection.url",config.db._url);
        properties.setProperty("hibernate.connection.username",config.db._username);
        if(StringUtils.hasText(config.db._password)){
            properties.setProperty("hibernate.connection.password",config.db._password);
        }
        for(CreeperCoreConfig.JpaProperty property: config.jpaProperties){
            properties.setProperty(property.name,property.value);
        }
        entityManagerFactoryBean.setJpaProperties(properties);
        entityManagerFactoryBean.setPackagesToScan(entityPackages.toArray(new String[entityPackages.size()]));
        entityManagerFactoryBean.afterPropertiesSet();
        return entityManagerFactoryBean.getObject();
    }
    public static EntityManager buildEntityManager(@Local EntityManagerFactory entityManagerFactory){
        return SharedEntityManagerCreator.createSharedEntityManager(entityManagerFactory);
    }
    public static JpaTransactionManager buildJpaTransactionManager(@Local EntityManagerFactory entityManagerFactory){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        transactionManager.afterPropertiesSet();
        return transactionManager;
    }
    public static DaoPackageManager buildDaoPackageManager(final Collection<String> packages){
        return new DaoPackageManager() {
            @Override
            public boolean contains(Class<?> daoType) {
                return packages.contains(daoType.getPackage().getName());
            }
        };
    }
    @Contribute(MasterObjectProvider.class)
    public static void provideEntityDaoObject(OrderedConfiguration<ObjectProvider> configuration,
                                              @Local
                                               final DaoPackageManager entityPackageManager,
                                              @Local
                                               final BeanFactory beanFactory,
                                              @Local
                                               final EntityManagerFactory entityManagerFactory)
    {
        configuration.add("entityDaoProvider", new ObjectProvider() {
            @Override
            public <T> T provide(Class<T> objectType, AnnotationProvider annotationProvider, ObjectLocator locator) {
                if (entityPackageManager.contains(objectType) && objectType.isInterface()) {
                    JpaRepositoryFactoryBean jpaRepositoryFactoryBean = new JpaRepositoryFactoryBean();
                    jpaRepositoryFactoryBean.setBeanFactory(beanFactory);
                    jpaRepositoryFactoryBean.setTransactionManager(TxUtils.DEFAULT_TRANSACTION_MANAGER);
                    EntityManager entityManager = SharedEntityManagerCreator.createSharedEntityManager(entityManagerFactory);
                    jpaRepositoryFactoryBean.setEntityManager(entityManager);
                    jpaRepositoryFactoryBean.setRepositoryInterface(objectType);
                    jpaRepositoryFactoryBean.afterPropertiesSet();
                    return (T)jpaRepositoryFactoryBean.getObject();
                }
                return null;
            }
        });
    }
    public static ListableBeanFactory buildBeanFactory(@Local
            final JpaTransactionManager transactionManager,
            @Local
            final EntityManagerFactory entityManagerFactory,
            @Local
            final JpaVendorAdapter jpaVendorAdapter){
        final StaticListableBeanFactory beanFactory = new StaticListableBeanFactory();
        beanFactory.addBean(TxUtils.DEFAULT_TRANSACTION_MANAGER,transactionManager);
        beanFactory.addBean("entityManagerFactory",entityManagerFactory);
        beanFactory.addBean("jpaDialect", jpaVendorAdapter.getJpaDialect());
        return beanFactory;
    }
    public static TransactionInterceptor buildTransactionInterceptor(@Local BeanFactory beanFactory){
        AnnotationTransactionAttributeSource transactionAttributeSource = new AnnotationTransactionAttributeSource();

        TransactionInterceptor transactionInterceptor = new TransactionInterceptor(null, transactionAttributeSource);
        transactionInterceptor.setTransactionManagerBeanName(TxUtils.DEFAULT_TRANSACTION_MANAGER);
        transactionInterceptor.setBeanFactory(beanFactory);
        transactionInterceptor.afterPropertiesSet();
        return transactionInterceptor;
    }
    @Match("*")
    public static void adviseTransactional(MethodAdviceReceiver receiver,@Local TransactionAdvice transactionAdvice)
    {
        for (Method m : receiver.getInterface().getMethods())
        {
            //仅仅对含有Transactional注解的方法进行AOP
            if (receiver.getMethodAnnotation(m, Transactional.class) != null)
                receiver.adviseMethod(m, transactionAdvice);
        }
    };
}
