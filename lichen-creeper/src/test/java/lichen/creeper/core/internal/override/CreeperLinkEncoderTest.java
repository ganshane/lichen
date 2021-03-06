package lichen.creeper.core.internal.override;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author jcai
 */
public class CreeperLinkEncoderTest {
    @Test
    public void testConvert(){
        CreeperLinkEncoder encoder = new CreeperLinkEncoder(null,null,null,null,null,null,null,null,false,"",null,null);
        String path = encoder.decodeAdminPageName("/xxx/admin/user/roleform");
        Assert.assertEquals("/xxx/user/admin/roleform",path);

        path = encoder.decodeAdminPageName("/admin/user/roleform");
        Assert.assertEquals("/user/admin/roleform",path);


        path = encoder.encodeAdminPageName("user/admin/roleform");
        Assert.assertEquals("admin/user/roleform",path);
    }
}
