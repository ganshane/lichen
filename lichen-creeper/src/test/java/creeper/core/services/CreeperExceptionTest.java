package creeper.core.services;

import org.junit.Test;

/**
 * test creeper exception
 * @author jcai
 */
public class CreeperExceptionTest {
    @Test
    public void test_exception(){
        CreeperException e = CreeperException.wrap(new IllegalAccessException("asdf"),CreeperCoreExceptionCode.FAIL_READ_CONFIG_FILE);
        e.set("k1","v1");
        e.set("k2","v2");
        e.set("k3","v3");
//        e.printStackTrace();
        //throw e;
    }
}
