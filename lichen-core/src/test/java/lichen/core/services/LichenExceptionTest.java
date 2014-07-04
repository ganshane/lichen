package lichen.core.services;

import org.junit.Test;

/**
 * test lichen exception.
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 */
public class LichenExceptionTest {
    @Test
    public void test_exception(){
        LichenException exception = new LichenException(LichenCoreErrorCode.UNSUPPORT_REMOVE_ITERATOR).
                set("test", 1).set("test2", 213).set("key3","test msg");
        //exception.printStackTrace();
    }
}
