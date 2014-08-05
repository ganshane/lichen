package lichen.creeper.core.internal.override;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.internal.services.TapestrySessionFactory;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.services.Session;

/**
 * 复写tapestry的内部组件，使得和shiro共用session基础组件.
 * 对于web部分集成是：
 * 使用
 * DefaultWebSecurityManager
 * 关于使用登录、已经权限设置和获取，参考shiro的具体实现.
 * return new WebSubject.Builder(getSecurityManager(), request, response).buildWebSubject();
 * @author jcai
 */
public class TapestrySessionFactoryWithShiroSession implements TapestrySessionFactory{
    private Subject _subject;

    public TapestrySessionFactoryWithShiroSession(Subject subject) {
        _subject = subject;
    }

    @Override
    public Session getSession(boolean create) {
        return new TapestrySessionWithShiroSession(_subject.getSession(create));
    }
    private class TapestrySessionWithShiroSession implements Session{
        private org.apache.shiro.session.Session _shiroSession;
        private boolean _invalidated = false;
        private TapestrySessionWithShiroSession(org.apache.shiro.session.Session shiroSession) {
            _shiroSession = shiroSession;
            _invalidated = false;
        }

        public List<String> getAttributeNames() {
            if(_shiroSession == null)
                return Collections.emptyList();
            Collection<Object> attributes = _shiroSession.getAttributeKeys();
            if(attributes != null)
                return Collections.emptyList();
            return F.flow(attributes).map(new Mapper<Object, String>() {
                @Override
                public String map(Object element) {
                    return String.valueOf(element);
                }
            }).toList();
        }

        @Override
        public List<String> getAttributeNames(String prefix) {
            List<String> result = CollectionFactory.newList();
            if(_shiroSession == null)
            	return result;

            Iterator<Object> e = _shiroSession.getAttributeKeys().iterator();
            while (e.hasNext())
            {
                String name = String.valueOf(e.next());

                if (name.startsWith(prefix)) result.add(name);
            }

            Collections.sort(result);

            return result;
        }

        @Override
        public Object getAttribute(String name) {
            return _shiroSession.getAttribute(name);
        }

        @Override
        public void setAttribute(String name, Object value) {
            _shiroSession.setAttribute(name,value);
        }

        @Override
        public int getMaxInactiveInterval() {
            return (int) _shiroSession.getTimeout();
        }

        @Override
        public void setMaxInactiveInterval(int seconds) {
            _shiroSession.setTimeout(seconds);
        }

        @Override
        public void invalidate() {
            _invalidated = true;
            _shiroSession.stop();
        }

        @Override
        public boolean isInvalidated() {
            return _invalidated;
        }

        @Override
        public void restoreDirtyObjects() {
        }
    }
}
