package creeper.core.services.jpa;

import java.util.List;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import creeper.test.dao.EntityTestDao;
import creeper.test.entities.EntityA;

/**
 * @author jcai
 */
public class JpaTest extends BaseEntityTestCase{
    protected static Class<?>[] iocModules=new Class<?>[]{TestServiceModule.class};
    protected static String[] creeperModules = new String[]{"creeper.test"};

    @Override
    protected Class<?>[] getIocModules() {
        return new Class<?>[]{TestServiceModule.class};
    }
    @Override
    protected String[] getCreeperModules() {
        return new String[]{"creeper.test","creeper.user"};
    }

    @Test
    public void test_EntityA(){

        EntityTestDao dao = registry.getObject(EntityTestDao.class, null);
        EntityA entityA = new EntityA();
        //entityA.setId(123L);
        entityA=dao.save(entityA);
        entityA = dao.findById(entityA.getId());
        Assert.assertNotNull(entityA);
        List<EntityA> list = dao.findByCustomQuery(entityA.getId());
        Assert.assertEquals(list.size(),1);

        TestService testService = registry.getObject(TestService.class,null);
        testService.testNoTransaction();
        testService.testNeedTransaction();


    }
    public static class TestServiceModule{
        public static void bind(ServiceBinder binder){
            binder.bind(TestService.class,TestServiceImpl.class);
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
