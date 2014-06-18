package creeper.core.services.jpa;

import creeper.core.annotations.CreeperJpa;
import creeper.core.internal.jpa.JpaEntityValueEncoder;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.slf4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.EntityType;
import java.util.Set;

/**
 * 提供jpa中的实体对象和string的转换
 * @author jcai
 */
public class CreeperJpaValueEncoderSourceModule {
    @Contribute(ValueEncoderSource.class)
    public static void provideValueEncoder(MappedConfiguration<Class, ValueEncoderFactory> configuration,
                                           Logger logger,
                                           final PropertyAccess propertyAccess,
                                           final TypeCoercer typeCoercer,
                                           @CreeperJpa EntityManagerFactory entityManagerFactory,
                                           @CreeperJpa final EntityManager entityManager
                                           ){
        Set<EntityType<?>> entities = entityManagerFactory.getMetamodel().getEntities();
        for(final EntityType<?> entityType:entities){
            if(!entityType.hasSingleIdAttribute()){
                logger.warn("{} has more id attribute!",entityType.getName());
            }


            ValueEncoderFactory factory = new ValueEncoderFactory()
            {
                public ValueEncoder create(Class type)
                {
                    return new JpaEntityValueEncoder(entityType,propertyAccess,typeCoercer,entityManager);
                }
            };
            configuration.add(entityType.getJavaType(),factory);


        }
    }
}
