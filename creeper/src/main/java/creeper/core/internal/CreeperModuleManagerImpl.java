package creeper.core.internal;

import creeper.core.annotations.CreeperCore;
import creeper.core.models.CreeperModuleDef;
import creeper.core.services.CreeperModuleManager;
import lichen.core.services.Option;
import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Flow;
import org.apache.tapestry5.func.Mapper;
import org.apache.tapestry5.ioc.annotations.Marker;

import java.util.Collection;

/**
 * 实现对creeper模块的管理
 * @author jcai
 */
@Marker(CreeperCore.class)
public class CreeperModuleManagerImpl implements CreeperModuleManager{

    private Collection<CreeperModuleDef> _modules;

    public CreeperModuleManagerImpl(Collection<CreeperModuleDef> modules) {
        _modules = modules;
    }
    @Override
    public String[] getModuleSubPackageWithSuffix(final Option<String> subPackage) {
        return flowModuleSubPackageWithSuffix(subPackage).toArray(String.class);
    }

    @Override
    public Flow<String> flowModuleSubPackageWithSuffix(final Option<String> subPackage) {
        Flow<String> f = F.flow(_modules).map(new Mapper<CreeperModuleDef, String>() {
            @Override
            public String map(CreeperModuleDef element) {
                return element.getPkg();
            }
        });
        if(subPackage.isDefined()){
            return f.map(new Mapper<String , String>() {

                @Override
                public String map(String element) {
                    return element + "." + subPackage.get();
                }
            });
        }
        return f;
    }
}
