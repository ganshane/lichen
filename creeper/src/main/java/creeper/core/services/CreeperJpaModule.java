package creeper.core.services;

import creeper.core.config.CreeperCoreConfig;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
    public static EntityManager buildEntityManager(EntityManagerFactory entityManagerFactory){
        return SharedEntityManagerCreator.createSharedEntityManager(entityManagerFactory);
    }
    public static JpaTransactionManager buildJpaTransactionManager(EntityManagerFactory entityManagerFactory){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        transactionManager.afterPropertiesSet();
        return transactionManager;
    }
}
