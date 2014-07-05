package lichen.creeper.core.internal.jpa;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.hibernate.service.ServiceRegistry;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * 能够读package-info.java中定义的属性
 * @author jcai
 */
public class SmartHibernateJpaVendorAdapter extends HibernateJpaVendorAdapter{
    private PersistenceProvider provider = new HibernatePersistenceProvider(){
        @Override
        public EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo info, @SuppressWarnings("rawtypes") Map properties) {
            return new EntityManagerFactoryBuilderImpl(new PersistenceUnitInfoDescriptor(info), properties) {
                @Override
                public org.hibernate.cfg.Configuration buildHibernateConfiguration(ServiceRegistry serviceRegistry) {
                    org.hibernate.cfg.Configuration configuration = super.buildHibernateConfiguration(serviceRegistry);
                    configuration.addPackage("lichen.creeper.core.entities");
                    for (String annotatedPackage : annotatedPackages) {
                        configuration.addPackage(annotatedPackage);
                    }
                    return configuration;
                }
            }.build();
        }
    };
    public String[] annotatedPackages;
    public void setAnnotatedPackages(String[] annotatedPackages) {
        this.annotatedPackages = annotatedPackages;
    }
    @Override
    public PersistenceProvider getPersistenceProvider() {
        return provider;
    }
}
