package creeper.core.services.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import creeper.core.models.CreeperModuleDef;
import creeper.test.dao.EntityBDao;
import creeper.test.entities.EntityB;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import creeper.test.dao.EntityTestDao;
import creeper.test.entities.EntityA;
import creeper.user.dao.UserDao;
import creeper.user.entities.User;

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
    protected CreeperModuleDef[] getCreeperModules() {
        return new CreeperModuleDef[]{CreeperModuleDef.create("测试","creeper.test")};
    }

    @Test
    public void test_EntityB(){
        EntityBDao dao = registry.getObject(EntityBDao.class,null);
        EntityB b = new EntityB();
        dao.save(b);
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
        list = dao.findByIds(entityA.getId());

        dao.findAll(new Specification<EntityA>() {
            @Override
            public Predicate toPredicate(Root<EntityA> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.lessThan(root.<Long>get("balance"),12L);
            }
        });

    }
    @Test
    public void test_service(){
        TestService testService = registry.getObject(TestService.class,null);
        //testService.testNoTransaction();
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
        @Inject
        private EntityBDao dao;
        public void testNoTransaction(){
            System.out.println("no transaction...");
            testNeedTransaction();
        }
        public void testNeedTransaction(){
            dao.findByName("hello");
            EntityB b = new EntityB();
            dao.save(b);
            System.out.println("need transaction...");
        }
    }
}
