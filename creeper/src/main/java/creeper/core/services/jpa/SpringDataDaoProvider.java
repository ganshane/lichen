package creeper.core.services.jpa;

import org.apache.tapestry5.ioc.ObjectProvider;
import org.apache.tapestry5.ioc.services.ClassNameLocator;

/**
 * 创建由spring-data-jpa管理的dao对象
 */
public interface SpringDataDaoProvider extends ObjectProvider {
    void setupDaoObject();
}
