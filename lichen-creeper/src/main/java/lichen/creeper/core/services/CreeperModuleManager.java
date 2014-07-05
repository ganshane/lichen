package lichen.creeper.core.services;

import lichen.core.services.Option;
import org.apache.tapestry5.func.Flow;

import lichen.creeper.core.models.CreeperModuleDef;

import java.util.Iterator;

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

    /**
     * 通过模块的包来获取对一个模块的权限
     * @param pkg 包
     * @return 权限列表
     */
    public Iterator<String> getPermissionsByModulePackage(String pkg);
    
    /**
     * 获取所有模块
     * @return
     */
    public Iterator<CreeperModuleDef> getAllModules();
}
