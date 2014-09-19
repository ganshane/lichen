package lichen.creeper.core.internal;

import javax.persistence.EntityManager;

import lichen.creeper.core.services.EntityManagerService;

import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * 
 * @author shen
 *
 */
public class EntityManagerServiceImpl implements EntityManagerService {
	
	@Inject
	private EntityManager entityManager;
	
	@Override
	public void remove(Object entity) {
		entityManager.remove(entity);
	}
	
}
