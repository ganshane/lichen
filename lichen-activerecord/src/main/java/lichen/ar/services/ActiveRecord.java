package lichen.ar.services;

/**
 * active record基础类.
 * @author jcai
 */
public abstract class ActiveRecord {
    private String tableName;
    private String pkField = "id";
    public final Long save() {
        return -1L;
    }
    public final int delete() {
        return 1;
    }
    public final void setPkField(final String vpkField) {
        this.pkField = vpkField;
    }
    public final String getPkField() {
        return pkField;
    }
    public final void setTableName(final String vtableName) {
        this.tableName = vtableName;
    }
    public final String getTableName() {
        return tableName;
    }
}
