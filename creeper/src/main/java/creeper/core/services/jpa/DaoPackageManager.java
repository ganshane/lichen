package creeper.core.services.jpa;

/**
 * entity package manager
 * @author jcai
 */
public interface DaoPackageManager{
    /**
     * 判断给定的dao类型是否包含
     * @param daoType dao类型
     * @return
     */
    public boolean contains(Class<?> daoType);
}
