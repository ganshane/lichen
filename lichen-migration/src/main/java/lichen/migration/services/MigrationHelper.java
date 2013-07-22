package lichen.migration.services;

import lichen.migration.model.ColumnOption;
import lichen.migration.model.SqlType;
import lichen.migration.model.TableDefinition;
import lichen.migration.model.TableOption;
import lichen.migration.services.TableCallback;

/**
 * 数据库升级脚本的帮助类
 * @author jcai
 */
public interface MigrationHelper {
    void createTable(String tableName, TableCallback body, TableOption... options) throws Throwable;

    void addColumn(String tableName,
                   String columnName,
                   SqlType columnType,
                   ColumnOption... options) throws Throwable;

    /**
     * Alter the definition of an existing column.
     *
     * NOTE: if the original column definition uses CharacterSet() then
     * it must be used here again, unless the base SQL data type is
     * being changed.  For example, on Oracle, creating a column without
     * CharacterSet uses VARCHAR2 while using CharacterSet(Unicode) uses
     * NVARCHAR2, so if the original column used CharacterSet(Unicode)
     * and #alterColumn() is not passed CharacterSet(Unicode), then the
     * column's data type will be change from NVARCHAR2 to VARCHAR2.
     *
     * @param tableName the name of the table with the column
     * @param columnName the name of the column
     * @param columnType the type the column is being altered to
     * @param options a possibly empty array of column options to
     *        customize the column
     */
    void alterColumn(String tableName,
                     String columnName,
                     SqlType columnType,
                     ColumnOption... options) throws Throwable;

    void removeColumn(String tableName,
                      String columnName) throws Throwable;

    void dropTable(String tableName) throws Throwable;
}
