package lichen.migration.services;

/**
 * migration
 * @author jcai
 */
public interface Migration extends MigrationHelper{
    /**
     * 数据库升级时候执行方法
     */
    public void up() throws Throwable;

    /**
     * 数据库降级时候的执行方法
     */
    public void down() throws Throwable;
}
