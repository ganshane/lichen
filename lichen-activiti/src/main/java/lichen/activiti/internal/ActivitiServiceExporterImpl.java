package lichen.activiti.internal;

import lichen.activiti.services.ActivitiServiceExporter;
import org.activiti.engine.ActivitiException;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.services.ServiceActivity;
import org.apache.tapestry5.ioc.services.ServiceActivityScoreboard;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 导出带有 @CreeperActiviti的服务到activiti中
 * @author jcai
 */
public class ActivitiServiceExporterImpl implements ActivitiServiceExporter {
    private final ObjectLocator _locator;
    private final ConcurrentHashMap<Object, Object> beans;
    private ServiceActivityScoreboard scoreboard;
    public ActivitiServiceExporterImpl(ServiceActivityScoreboard scoreboard, ObjectLocator locator){
        this._locator = locator;
        this.scoreboard = scoreboard;
        beans = new ConcurrentHashMap<Object, Object>();
        exportActivitiService();
    }
    private void exportActivitiService(){
        //get all activities object
        List<ServiceActivity> activities = scoreboard.getServiceActivity();
        for(ServiceActivity activity:activities){
            Class<?> serviceInterface = activity.getServiceInterface();
            beans.put(activity.getServiceId(), serviceInterface);
        }
    }
    public Object get(Object key) {
        if ( (key==null) || (!String.class.isAssignableFrom(key.getClass()))) {
            return null;
        }
        Class<?> serviceInterface = (Class<?>) beans.get(key);
        if(serviceInterface == null)
            return null;

        return _locator.getObject(serviceInterface,null);
    }

    public boolean containsKey(Object key) {
        if ( (key==null) || (!String.class.isAssignableFrom(key.getClass())) ) {
            return false;
        }
        return beans.containsKey(key);
    }

    @Override
    public int size() {
        throw new ActivitiException("unsupported operation on configuration beans");
    }

    @Override
    public boolean isEmpty() {
        throw new ActivitiException("unsupported operation on configuration beans");
    }


    @Override
    public boolean containsValue(Object value) {
        throw new ActivitiException("unsupported operation on configuration beans");
    }

    @Override
    public Object put(Object key, Object value) {
        throw new ActivitiException("unsupported operation on configuration beans");
    }

    @Override
    public Object remove(Object key) {
        throw new ActivitiException("unsupported operation on configuration beans");    }

    @Override
    public void putAll(Map<?, ?> m) {
        throw new ActivitiException("unsupported operation on configuration beans");
    }

    @Override
    public void clear() {
        throw new ActivitiException("unsupported operation on configuration beans");
    }

    @Override
    public Set<Object> keySet() {
        throw new ActivitiException("unsupported operation on configuration beans");
    }

    @Override
    public Collection<Object> values() {
        throw new ActivitiException("unsupported operation on configuration beans");
    }

    @Override
    public Set<Entry<Object, Object>> entrySet() {
        throw new ActivitiException("unsupported operation on configuration beans");
    }

    @Override
    public boolean equals(Object o) {
        throw new ActivitiException("unsupported operation on configuration beans");
    }

    @Override
    public int hashCode() {
        throw new ActivitiException("unsupported operation on configuration beans");
    }
}
