package lichen.creeper.core.services;

import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author shen
 *
 */
public interface EntityManagerService {
	
	/**
	 * 删除实体
	 * @param entity
	 */
	@Transactional
	public void remove(Object entity);
	
}
