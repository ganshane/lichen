package creeper.core.internal.axis2;


import creeper.core.services.axis2.EchoService;
import org.apache.axis2.AxisFault;
import org.junit.Test;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author jcai
 */
public class Axis2Test {
    @Test
    public void testAxis2() throws AxisFault, MalformedURLException {
        //wsdl网络路径
        URL url = new URL("http://localhost:8080/ws/EchoService?wsdl");
        //服务描述中服务端点的限定名称  两个参数分别为 命名空间 服务名
        QName qName = new QName("http://www.egfit.com/", "EchoService");
        //创建服务对象
        javax.xml.ws.Service service = javax.xml.ws.Service.create(url, qName);
        //获得Hiservice的实现类对象
        EchoService hiService = service.getPort(new QName("http://www.egfit.com/","EchoServiceHttpSoap12Endpoint"),EchoService.class);
        //调用WebService方法
        System.out.println(hiService.echoString("xiaoming"));

    }
}
