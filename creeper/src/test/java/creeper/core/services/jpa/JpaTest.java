package creeper.core.services.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
        list = dao.findByIds(entityA.getId());

        dao.findAll(new Specification<EntityA>() {
            @Override
            public Predicate toPredicate(Root<EntityA> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.lessThan(root.<Long>get("balance"),12L);
            }
        });

        TestService testService = registry.getObject(TestService.class,null);
        testService.testNoTransaction();
        testService.testNeedTransaction();
    }
    
    
    @Test
    public void test_User(){
        UserDao dao = registry.getObject(UserDao.class, null);
        dao.findAll(new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();   
				Predicate pid = cb.equal(root.get("id"),2);
				Predicate pname = cb.equal(root.get("name"),"啊");
				Predicate pusername = cb.equal(root.get("username"),"webmaster");
				list.add(pid);
				list.add(pname);
				list.add(pusername);
				Predicate[] p = new Predicate[list.size()];   
				return cb.and(list.toArray(p));
			}
        });
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
