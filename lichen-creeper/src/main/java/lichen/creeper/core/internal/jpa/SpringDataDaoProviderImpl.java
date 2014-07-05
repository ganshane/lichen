package lichen.creeper.core.internal.jpa;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.tapestry5.ioc.AnnotationProvider;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.internal.services.ClassNameLocatorImpl;
import org.apache.tapestry5.ioc.internal.services.ClasspathURLConverterImpl;
import org.apache.tapestry5.ioc.services.ClassNameLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.util.TxUtils;

import lichen.creeper.core.annotations.CreeperJpa;
import lichen.creeper.core.services.CreeperException;
import lichen.creeper.core.services.jpa.DaoPackageManager;
import lichen.creeper.core.services.jpa.SpringDataDaoProvider;

/**
 * spring data dao provider
 * @author jcai
 */
public class SpringDataDaoProviderImpl implements SpringDataDaoProvider {
    private final DaoPackageManager _daoPackageManager;
    private BeanFactory _beanFactory;
    private EntityManager _entityManager;
    private Map<Class<?>,Object> objects = new HashMap<Class<?>,Object>();
    private static final Logger logger = LoggerFactory.getLogger(SpringDataDaoProviderImpl.class);

    public SpringDataDaoProviderImpl(@CreeperJpa BeanFactory beanFactory,
                                 @CreeperJpa EntityManager entityManager,
                                 @CreeperJpa
                                 DaoPackageManager daoPackageManager
                                 ) {
        _beanFactory = beanFactory;
        _entityManager = entityManager;
        _daoPackageManager = daoPackageManager;
    }

    @Override
    public void setupDaoObject(){
        ClassNameLocator classNameLocator = new ClassNameLocatorImpl(new ClasspathURLConverterImpl());
        String[] packages = _daoPackageManager.getPackages();
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        for(String pkg: packages){
            Collection<String> names = classNameLocator.locateClassNames(pkg);
            for(String className:names){
                try {
                    logger.debug("dao object:{}",className);
                    Class<?> clazz = contextClassLoader.loadClass(className);
                    objects.put(clazz,createDaoObject(clazz));
                } catch (ClassNotFoundException e) {
                    throw CreeperException.wrap(e);
                }
            }
        }
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private <T> T createDaoObject(Class<T> objectType){
        JpaRepositoryFactoryBean jpaRepositoryFactoryBean = new JpaRepositoryFactoryBean();
        jpaRepositoryFactoryBean.setBeanFactory(_beanFactory);
        jpaRepositoryFactoryBean.setTransactionManager(TxUtils.DEFAULT_TRANSACTION_MANAGER);
        //EntityManager entityManager = SharedEntityManagerCreator.createSharedEntityManager(entityManagerFactory);
        jpaRepositoryFactoryBean.setEntityManager(_entityManager);
        jpaRepositoryFactoryBean.setRepositoryInterface(objectType);
        //仅仅支持在Dao中发现Query
        //jpaRepositoryFactoryBean.setQueryLookupStrategyKey(QueryLookupStrategy.Key.USE_DECLARED_QUERY);
        jpaRepositoryFactoryBean.afterPropertiesSet();
        return (T) jpaRepositoryFactoryBean.getObject();
    }

    @SuppressWarnings("unchecked")
	@Override
    public <T> T provide(Class<T> objectType, AnnotationProvider annotationProvider, ObjectLocator locator) {
        return (T) objects.get(objectType);
    }
}
