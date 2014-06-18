package creeper.core.internal;

import creeper.core.services.PermissionSource;

import java.util.List;

/**
 * 权限列表的实现
 * @author jcai
 */
public class PermissionSourceImpl implements PermissionSource{
    public PermissionSourceImpl(){

    }
    @Override
    public List<String> getPermissionsByModule(String modulePackage) {
        return null;
    }
}
