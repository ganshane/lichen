package creeper.core.internal;

import creeper.core.annotations.CreeperCore;
import creeper.core.services.CreeperModuleManager;
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

    private Collection<String> _modules;

    public CreeperModuleManagerImpl(Collection<String> modules) {
        _modules = modules;
    }
    @Override
    public String[] getModuleSubPackageWithSuffix(final String subPackage) {
        return flowModuleSubPackageWithSuffix(subPackage).toArray(String.class);
    }

    @Override
    public Flow<String> flowModuleSubPackageWithSuffix(final String subPackage) {
        if(subPackage == null)
            return F.flow(_modules);
        return F.flow(_modules).map(new Mapper<String, String>() {
            @Override
            public String map(String element) {
                return element + "." + subPackage;
            }
        });
    }
}
