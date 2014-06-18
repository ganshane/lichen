package creeper.core.models;

/**
 * creeper模块定义
 * @author jcai
 */
public class CreeperModuleDef {
    //模块名称
    private String name;
    //包的名称
    private String pkg;

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
}
