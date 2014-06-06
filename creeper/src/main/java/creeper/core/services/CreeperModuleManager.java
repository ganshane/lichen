package creeper.core.services;

import lichen.core.services.Option;
import org.apache.tapestry5.func.Flow;

/**
 * 针对各个模块的配置
 * @author jcai
 */
public interface CreeperModuleManager{
    /**
     * 得到所有模块的子包
     * @param subPackage 子包的名称，譬如：db,entities 等
     * @return 包集合
     */
    public String[] getModuleSubPackageWithSuffix(Option<String> subPackage);
    public Flow<String> flowModuleSubPackageWithSuffix(Option<String> subPackage);
}
