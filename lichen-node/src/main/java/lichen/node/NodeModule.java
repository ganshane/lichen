package lichen.node;

import lichen.creeper.core.models.CreeperModuleDef;
import lichen.creeper.core.services.CreeperModuleManager;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.annotations.Contribute;

/**
 * @author jcai
 */
public class NodeModule {
    @Contribute(value = CreeperModuleManager.class)
    public static void provideModule(Configuration<CreeperModuleDef> configuration){
        CreeperModuleDef def = CreeperModuleDef.create("内容","lichen.node");
        configuration.add(def);
    }
}
