package creeper.core.services.jpa;

import creeper.core.config.CreeperCoreConfig;
import creeper.core.internal.CreeperModuleManagerImpl;
import creeper.core.services.CreeperModuleManager;
import creeper.core.services.jpa.CreeperJpaModule;
import creeper.test.dao.EntityTestDao;
import creeper.test.entities.EntityA;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.hibernate.cfg.Environment;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        TestService testService = registry.getObject(TestService.class,null);
        testService.testNoTransaction();
        testService.testNeedTransaction();

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
        public static void bind(ServiceBinder binder){
            binder.bind(TestService.class,TestServiceImpl.class);
            binder.bind(CreeperModuleManager.class, CreeperModuleManagerImpl.class);
        }
        @Contribute(CreeperModuleManager.class)
        public static void provideTestModule(Configuration<String> modules){
            modules.add("creeper.test");
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
    public static interface TestService{
        @Transactional(propagation= Propagation.NEVER)
        public void testNoTransaction();
        @Transactional(propagation= Propagation.REQUIRED)
        public void testNeedTransaction();
    }
    public static class TestServiceImpl implements TestService{
        public void testNoTransaction(){
            System.out.println("no transaction...");
            testNeedTransaction();
        }
        public void testNeedTransaction(){
            System.out.println("need transaction...");
        }
    }
}
