package creeper.core.services.jpa;

import javax.persistence.EntityManager;

/**
 * Created by jcai on 14-6-9.
 */
public interface EntityManagerCreator {
    EntityManager createEntityManager();
}
