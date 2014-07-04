package creeper.node;

import creeper.core.models.CreeperModuleDef;
import creeper.core.services.CreeperModuleManager;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.annotations.Contribute;

/**
 * @author jcai
 */
public class NodeModule {
    @Contribute(value = CreeperModuleManager.class)
    public static void provideModule(Configuration<CreeperModuleDef> configuration){
        CreeperModuleDef def = CreeperModuleDef.create("内容","creeper.node");
        configuration.add(def);
    }
}
