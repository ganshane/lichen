package lichen.creeper.core.services.jpa;

import org.apache.tapestry5.ioc.ObjectProvider;

/**
 * 创建由spring-data-jpa管理的dao对象
 */
public interface SpringDataDaoProvider extends ObjectProvider {
	/**
	 * 扫描所有的包下的文件，然后对dao进行字节码增强
	 */
    void setupDaoObject();
}
