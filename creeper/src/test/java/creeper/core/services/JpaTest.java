package creeper.core.services;

import creeper.core.config.CreeperCoreConfig;
import creeper.test.dao.EntityTestDao;
import creeper.test.entities.EntityA;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.MySQL5Dialect;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.support.StaticListableBeanFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Properties;

/**
 * @author jcai
 */
public class JpaTest {
    @Test
    public void test_jpa(){

        EntityTestDao dao = registry.getObject(EntityTestDao.class, null);
        EntityA entityA = new EntityA();
        //entityA.setAccountId(123L);
        entityA=dao.save(entityA);
        entityA = dao.findByAccountId(entityA.getAccountId());
        Assert.assertNotNull(entityA);
        List<EntityA> list = dao.findByCustomQuery(entityA.getAccountId());
        Assert.assertEquals(list.size(),1);

        /*
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);

        EntityManager em = entityFactory.createEntityManager();
        EntityA entityA = new EntityA();
        entityA.setAccountId(123L);
        em.persist(entityA);
        transactionManager.commit(status);

        em.close();
        */

    }
    private static Registry registry;
    @BeforeClass
    public static void setup(){
        registry = RegistryBuilder.buildAndStartupRegistry(CreeperJpaModule.class,TestModule.class);
    }
    @AfterClass
    public static void teardown(){
        registry.shutdown();
    }
    public static class TestModule{
        @Contribute(EntityManagerFactory.class)
        public static void provideEntityPackage(Configuration<String> entityPackages){
            entityPackages.add("creeper.test.entities");
        }
        @Contribute(DaoPackageManager.class)
        public static void provideDaoPackage(Configuration<String> daoPackage){
            daoPackage.add("creeper.test.dao");
        }
        public static CreeperCoreConfig buildConfig(){
            CreeperCoreConfig config = new CreeperCoreConfig();
            config.db._driverClassName="org.h2.Driver";
            config.db._url="jdbc:h2:mem:testdb";
            config.db._username = "sa";
            CreeperCoreConfig.JpaProperty property = new CreeperCoreConfig.JpaProperty();
            property.name = Environment.HBM2DDL_AUTO;
            property.value = "create";
            config.jpaProperties.add(property);

            property = new CreeperCoreConfig.JpaProperty();
            property.name = Environment.SHOW_SQL;
            property.value = "true";
            config.jpaProperties.add(property);

            return config;
        }
    }
}
