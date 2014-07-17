package lichen.creeper.core.components;

import javax.persistence.EntityManager;

import lichen.core.services.LichenException;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.base.AbstractComponentEventLink;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * 删除实体链接组件
 * @author shen
 *
 */
public class DeleteEntityLink extends AbstractComponentEventLink{
	
	@Inject
	private EntityManager entityManager;
	
	@Inject
    private ComponentResources resources;
	
	//要删除的实体对象
	@Parameter
	private Object entity;
	
	@OnEvent(value="delEntity")
	void onPageAction(EventContext context){
		Class<?> clazz = null;
		try {
			clazz = Class.forName(context.get(String.class, 1));
		} catch (ClassNotFoundException e) {
			throw LichenException.wrap(e);
		}
		//根据实体对象类，转换为相应的实体对象。
		Object obj = context.get(clazz, 0);
		entityManager.remove(obj);
	}
	
	@Override
	protected Link createLink(Object[] eventContext) {
		//创建链接，第一个参数传实体对象，第二个参数传实体对象类
		return resources.createEventLink("delEntity", new Object[]{entity,entity.getClass().getName()});
	}
	
}
