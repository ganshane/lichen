package creeper.core.internal.jpa;

import creeper.core.annotations.CreeperJpa;
import org.apache.tapestry5.ioc.ObjectCreator;
import org.apache.tapestry5.ioc.services.PlasticProxyFactory;
import org.apache.tapestry5.plastic.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.lang.reflect.Method;

/**
 * object creator
 * @author jcai
 */
public class EntityManagerCreator{

    private static final Method CREATE_OBJECT = PlasticUtils.getMethod(ObjectCreator.class, "createObject");
    private static final Logger logger = LoggerFactory.getLogger(EntityManagerCreator.class);

    public static EntityManager createObject(final EntityManagerFactory entityManagerFactory,PlasticProxyFactory proxyFactory) {
        ClassInstantiator<EntityManager> instance = proxyFactory.createProxy(EntityManager.class, new PlasticClassTransformer() {
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
                                builder.loadThis().getField(isInit).returnResult();
                            }
                        });
                    }
                    else{
                        plasticClass.introduceMethod(method).delegateTo(delegateMethod);
                    }
                }
            }
        });
        //System.out.println(instance.toString());
        return instance.with(ObjectCreator.class,new ObjectCreator<EntityManager>() {
            @Override
            public EntityManager createObject() {
                logger.debug("Opening EntityManager ....");
                return entityManagerFactory.createEntityManager();
            }
        }).newInstance();
    }
}
