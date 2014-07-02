package creeper.core.internal.axis2;


import creeper.core.services.axis2.EchoService;
import creeper.core.services.axis2.WebserviceClient;
import org.apache.axis2.AxisFault;
import org.junit.Test;

import java.net.MalformedURLException;

/**
 * @author jcai
 */
public class Axis2Test {
    @Test
    public void testAxis2() throws AxisFault, MalformedURLException {

        EchoService hiService = WebserviceClient.
                createClient(EchoService.class,
                        "http://localhost:8080/ws/EchoService?wsdl",
                        "http://www.egfit.com/");
        //调用WebService方法
        System.out.println(hiService.echoString("xiaoming"));

    }
}
