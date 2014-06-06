package creeper.test.pages;

import creeper.test.dao.EntityTestDao;
import creeper.test.entities.EntityA;
import org.apache.tapestry5.util.TextStreamResponse;

import javax.inject.Inject;

/**
 * @author jcai
 */
public class Start {
    @Inject
    private EntityTestDao _dao;
    public Object onActivate(){
        EntityA entityA = new EntityA();
        _dao.save(entityA);
        return new TextStreamResponse("text/plain","hello");
    }
}
