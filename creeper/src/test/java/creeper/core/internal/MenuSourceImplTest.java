package creeper.core.internal;

import creeper.core.models.CreeperMenu;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jcai
 */
public class MenuSourceImplTest {
    private static final Logger logger = LoggerFactory.getLogger(MenuSourceImplTest.class);
    @Test
    public void testBuildTree(){
        List<CreeperMenu> coll= new ArrayList<CreeperMenu>();
        coll.add(new CreeperMenu("a.b.c","/a/b/c",1));
        coll.add(new CreeperMenu("c.b.d","/c/b/d",2));
        coll.add(new CreeperMenu("c.b.e","/c/b/e",8));
        coll.add(new CreeperMenu("a.b","/a/b",3));
        coll.add(new CreeperMenu("d.b.c","/d/b/c",1));


        MenuSourceImpl menuSource = new MenuSourceImpl(coll);
        CreeperMenu root = menuSource.buildCreeperMenu();
        logger.debug("menu:{}",root);
    }
}
