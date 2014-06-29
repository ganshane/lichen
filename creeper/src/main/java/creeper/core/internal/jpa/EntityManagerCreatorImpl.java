package creeper.core.internal.jpa;

import creeper.core.annotations.CreeperJpa;
import org.apache.tapestry5.ioc.ObjectCreator;
import org.apache.tapestry5.ioc.services.Builtin;
import org.apache.tapestry5.ioc.services.PlasticProxyFactory;
import org.apache.tapestry5.plastic.*;
import org.hibernate.jpa.HibernateEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.lang.reflect.Method;

/**
 * object creator
 * 此类的主要目的是创建 EntityManager的代理类，让EntityManager在未打开的时候，
 * 不对其进行任何操作，代理实现了 isOpen 和 close 两个方法
 *
 * @author jcai
 */
public class EntityManagerCreatorImpl implements creeper.core.services.jpa.EntityManagerCreator {

    private static final Method CREATE_OBJECT = PlasticUtils.getMethod(ObjectCreator.class, "createObject");
    private static final Logger logger = LoggerFactory.getLogger(EntityManagerCreatorImpl.class);
    private EntityManagerFactory entityManagerFactory;
    private PlasticProxyFactory proxyFactory;
    private final ClassInstantiator<HibernateEntityManager> instance;
    public EntityManagerCreatorImpl(@CreeperJpa EntityManagerFactory entityManagerFactory,
                                    @Builtin PlasticProxyFactory plasticProxyFactory){
        this.entityManagerFactory = entityManagerFactory;
        this.proxyFactory = plasticProxyFactory;
        this.instance = proxyFactory.createProxy(HibernateEntityManager.class, new PlasticClassTransformer() {
            @Override
            public void transform(final PlasticClass plasticClass) {
                final PlasticField objectCreatorField = plasticClass.introduceField(ObjectCreator.class, "creator")
                        .injectFromInstanceContext();
                final PlasticField isInit = plasticClass.introduceField(Boolean.TYPE.getName(), "isInit");

                final PlasticMethod delegateMethod = plasticClass.introducePrivateMethod(EntityManager.class.getName(), "delegate", null, null);

                delegateMethod.changeImplementation(new InstructionBuilderCallback() {
                    public void doBuild(InstructionBuilder builder) {
                        builder.loadThis().loadConstant(Boolean.TRUE);//.boxPrimitive(Boolean.TYPE.getName());
                        builder.putField(plasticClass.getClassName(), isInit.getName(), isInit.getTypeName());

                        builder.loadThis().getField(objectCreatorField);
                        builder.invoke(CREATE_OBJECT);
                        builder.checkcast(EntityManager.class).returnResult();
                    }
                });

                for (final Method method : EntityManager.class.getMethods()) {
                    if (method.getName().equals("close")) {
                        final PlasticMethod plasticMethod = plasticClass.introduceMethod(method);
                        plasticMethod.changeImplementation(new InstructionBuilderCallback() {
                            @Override
                            public void doBuild(InstructionBuilder builder) {
                                builder.loadThis().getField(isInit);
                                //builder.unboxPrimitive(Boolean.TYPE.getName());
                                builder.when(Condition.ZERO, new InstructionBuilderCallback() {
                                    @Override
                                    public void doBuild(InstructionBuilder builder) {
                                        builder.returnResult();
                                    }
                                });
                                builder.loadThis();
                                builder.invokeSpecial(plasticClass.getClassName(), delegateMethod.getDescription());
                                builder.loadArguments();
                                builder.invokeInterface(EntityManager.class.getName(), plasticMethod.getDescription().returnType, method.getName(),
                                        plasticMethod.getDescription().argumentTypes);
                                builder.returnResult();

                            }
                        });
                    }else if(method.getName().equals("isOpen")){
                        final PlasticMethod plasticMethod = plasticClass.introduceMethod(method);
                        plasticMethod.changeImplementation(new InstructionBuilderCallback() {
                            @Override
                            public void doBuild(InstructionBuilder builder) {
                                builder.loadThis().getField(isInit);
                                builder.when(Condition.NON_ZERO, new InstructionBuilderCallback() {
                                    @Override
                                    public void doBuild(InstructionBuilder builder) {
                                        builder.loadThis();
                                        builder.invokeSpecial(plasticClass.getClassName(), delegateMethod.getDescription());
                                        builder.loadArguments();
                                        builder.invokeInterface(EntityManager.class.getName(), plasticMethod.getDescription().returnType, method.getName(),
                                                plasticMethod.getDescription().argumentTypes);
                                        builder.returnResult();
                                    }
                                });

                                builder.loadConstant(Boolean.FALSE);//.unboxPrimitive(Boolean.TYPE.getName());
                                builder.returnResult();
                            }
                        });
                    }
                    else{
                        plasticClass.introduceMethod(method).delegateTo(delegateMethod);
                    }
                }
            }
        });
    }
    class InternalEntityManagerCreator implements ObjectCreator<EntityManager>{
        private EntityManager _entityManager;
        @Override
        public EntityManager createObject() {
            if(_entityManager == null){
                logger.debug("Opening EntityManager ....");
                _entityManager = entityManagerFactory.createEntityManager();
            }
            return _entityManager;
        }
    }

    @Override
    public EntityManager createEntityManager(){
        return instance.with(ObjectCreator.class, new InternalEntityManagerCreator())
                .newInstance();
    }
}
