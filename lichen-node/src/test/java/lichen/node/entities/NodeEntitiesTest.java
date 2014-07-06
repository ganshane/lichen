package lichen.node.entities;

import lichen.creeper.core.models.CreeperModuleDef;
import lichen.creeper.core.services.jpa.BaseEntityTestCase;
import lichen.node.dao.NodeDao;
import lichen.node.dao.NodeRevisionDao;
import lichen.creeper.user.entities.User;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.UUID;

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
        return new CreeperModuleDef[]{CreeperModuleDef.create("测试","lichen.node"),CreeperModuleDef.create("user","lichen.creeper.user")};
    }
    @Test
    public void test_save(){

        User user = new User();
        user.setId(UUID.randomUUID());
        Node node = new Node();
        node.setTitle("title");
        node.setType("type");
        node.setUser(user);
        node.setComment(0);
        node.setChanged(new Date());
        node.setCreated(new Date());

        NodeRevision revision = new NodeRevision();
        revision.setTitle("title");
        revision.setBody("body");
        revision.setNode(node);
        revision.setUser(user);
        revision.setCreated(new Date());

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
