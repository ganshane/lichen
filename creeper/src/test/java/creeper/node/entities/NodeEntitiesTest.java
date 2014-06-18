package creeper.node.entities;

import creeper.core.models.CreeperModuleDef;
import creeper.core.services.jpa.BaseEntityTestCase;
import creeper.node.dao.NodeDao;
import creeper.node.dao.NodeRevisionDao;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * @author jcai
 */
public class NodeEntitiesTest extends BaseEntityTestCase{
    @Override
    protected Class<?>[] getIocModules() {
        return new Class<?>[]{NodeTestModule.class};
    }

    @Override
    protected CreeperModuleDef[] getCreeperModules() {
        return new CreeperModuleDef[]{CreeperModuleDef.create("测试","creeper.node")};
    }
    @Test
    public void test_save(){

        Node node = new Node();

        NodeRevision revision = new NodeRevision();
        revision.setNode(node);

        node.setLastNodeRevision(revision);

        NodeTestService service = registry.getService(NodeTestService.class);
        service.saveNode(node);

        NodeDao dao = registry.getObject(NodeDao.class,null);
        node = dao.findOne(node.getId());
        Assert.assertNotNull(node.getLastNodeRevision().getId());
    }
    public static class NodeTestModule{
        public static void bind(ServiceBinder binder){
            binder.bind(NodeTestService.class,NodeTestServiceImpl.class);
        }
    }
    public static interface NodeTestService{
        @Transactional
       public void saveNode(Node node);
    }
    public static class NodeTestServiceImpl implements NodeTestService {
        @Inject
        private NodeDao nodeDao;
        @Inject
        private NodeRevisionDao nodeRevisionDao;
        @Override
        public void saveNode(Node node) {
            nodeDao.save(node);
            nodeRevisionDao.save(node.getLastNodeRevision());
        }
    }
}
