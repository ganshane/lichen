package lichen.creeper.core.internal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;

import lichen.core.services.LichenException;
import lichen.creeper.core.services.CreeperObjectExample;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.data.jpa.domain.Specification;

/**
 * 基于反射实现的Example自定义查询
 * @author shen
 * @param <T>
 *
 */
public class CreeperObjectExampleImpl implements CreeperObjectExample {
	
	@Inject
	private EntityManagerFactory entityManagerFactory;

	@Override
	public <T> Specification<T> createExample(final Object exampleEntity) {
		if ( exampleEntity == null ) {
			throw LichenException.wrap(new NullPointerException( "null example entity" ));
		}
		return new Specification<T>(){
			@Override
			public Predicate toPredicate(Root<T> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Set<EntityType<?>> entities = entityManagerFactory.getMetamodel().getEntities();
				for(EntityType<?> entity : entities){
					if(exampleEntity.getClass().equals(entity.getJavaType())){
						List<Predicate> list = new ArrayList<Predicate>();
						Set<?> attributes = entity.getDeclaredSingularAttributes();
						for(Object att : attributes){
							SingularAttribute<?, ?> attr = (SingularAttribute<?, ?>) att;
				            Field field = null;
							try {
								field = exampleEntity.getClass().getDeclaredField(attr.getName());
							} catch (SecurityException e) {
								throw LichenException.wrap(e);
							} catch (NoSuchFieldException e) {
								throw LichenException.wrap(e);
							}
			            	field.setAccessible(true);//修改访问权限
			            	Object value = null;
							try {
								value = field.get(exampleEntity);
							} catch (IllegalArgumentException e) {
								throw LichenException.wrap(e);
							} catch (IllegalAccessException e) {
								throw LichenException.wrap(e);
							}
			            	if(null != value){
			            		list.add(cb.equal(root.get(field.getName()), value));
			            	}
						}
						Predicate[] p = new Predicate[list.size()];   
						return cb.and(list.toArray(p));
					}
				}
				return null;
			}};
	}


}
