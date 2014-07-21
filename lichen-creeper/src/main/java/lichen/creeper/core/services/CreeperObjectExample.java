package lichen.creeper.core.services;

import org.springframework.data.jpa.domain.Specification;

/**
 * Example自定义查询接口
 * @author shen
 *
 */
public interface CreeperObjectExample {
	
	<T> Specification<T> createExample(Object exampleEntity);
	
}
