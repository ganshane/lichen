package creeper.core.services;

import java.util.List;

/**
 * 权限的服务接口类
 * @author jcai
 */
public interface PermissionSource {

    /**
     * 通过给定的模块包名称来得到权限列表
     * @param modulePackage 模块包名
     * @return 权限列表
     */
    public List<String> getPermissionsByModule(String modulePackage);
}
