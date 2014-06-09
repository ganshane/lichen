package creeper.core.internal.jpa;

import org.apache.tapestry5.ioc.services.Builtin;
import org.apache.tapestry5.ioc.services.PlasticProxyFactory;
import org.apache.tapestry5.plastic.PlasticUtils;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;

/**
 * open entity manager in view
 * @author jcai
 */
public class OpenEntityManagerInViewFilter implements RequestFilter {

    @Inject
    private EntityManagerFactory entityManagerFactory;
    @Inject
    private EntityManager entityManager;
    @Inject
    @Builtin
    private PlasticProxyFactory proxyFactory;
    @Override
    public boolean service(Request request, Response response, RequestHandler handler) throws IOException {
        try{
            //EntityManager delegate = EntityManagerCreator.createObject(entityManager,proxyFactory);
            EntityManagerHolder emHolder = new EntityManagerHolder(entityManager);
            TransactionSynchronizationManager.bindResource(entityManagerFactory, emHolder);
            return handler.service(request,response);
        }finally{
            EntityManagerHolder emHolder = (EntityManagerHolder) TransactionSynchronizationManager.unbindResource(entityManagerFactory);
            EntityManagerFactoryUtils.closeEntityManager(emHolder.getEntityManager());
        }
    }
}
