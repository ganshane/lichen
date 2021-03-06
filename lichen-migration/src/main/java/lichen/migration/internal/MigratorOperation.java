package lichen.migration.internal;

/**
 * 数据库操作类型的枚举
 * @author shen
 *
 */
public enum MigratorOperation {
    InstallAllMigrations,
    RemoveAllMigrations,
    MigrateToVersion,
    RollbackMigration;
    private int _version;
    private int _count;

    protected int getVersion() {
        return _version;
    }
    protected void setVersion(int version) {
        this._version = version;
    }
    protected int getCount() {
        return _count;
    }
    protected void setCount(int count) {
        this._count = count;
    }

}
