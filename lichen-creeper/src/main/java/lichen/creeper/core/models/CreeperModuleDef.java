package lichen.creeper.core.models;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * creeper模块定义
 * @author jcai
 */
public class CreeperModuleDef {
    //模块名称
    private String name;
    //包的名称
    private String pkg;
    //permissions
    private List<String> permissions;

    private CreeperModuleDef(String name, String pkg) {
        this.name = name;
        this.pkg = pkg;
    }

    public String getName() {
        return name;
    }


    public String getPkg() {
        return pkg;
    }

    public static CreeperModuleDef create(String name, String pkg) {
        return new CreeperModuleDef(name,pkg);
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
    public void addPermissions(String... perms) {
        if(permissions == null)
            permissions = new CopyOnWriteArrayList<String>();

        for(String p:perms)
            permissions.add(p);
    }
}
