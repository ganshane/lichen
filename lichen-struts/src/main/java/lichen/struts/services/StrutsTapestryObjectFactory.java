package lichen.struts.services;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.tapestry5.ioc.Registry;


import javax.servlet.ServletContext;
import java.util.Map;

/**
 * 基于struts的tapestry对象获取类
 * @author jcai
 */
public class StrutsTapestryObjectFactory extends ObjectFactory{
    private final Registry registry;

    public StrutsTapestryObjectFactory(@Inject ServletContext servletContext){
       registry = (Registry) servletContext.getAttribute(TapestryIocListener.REGISTRY_CONTEXT_NAME);
   }

    @Override
    public Object buildBean(Class clazz, Map<String, Object> extraContext) throws Exception {
        Object obj = registry.getObject(clazz,null);
        if(obj == null)
            return super.buildBean(clazz,extraContext);
        return obj;
    }
}
