// Copyright 2013 the original author or authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package lichen.migration.internal;

import java.util.Arrays;

import lichen.migration.internal.util.StringUtils;
import lichen.migration.model.ColumnOption;
import lichen.migration.model.IndexOption;
import lichen.migration.model.Name;
import lichen.migration.model.SqlType;
import lichen.migration.model.Unique;

/**
 * 数据库适配器.
 *
 * @author jcai
 */
public abstract class DatabaseAdapter {
    /**
     * Return the appropriate database adapter for the given database
     * vendor.
     *
     * @param vendor        the database vendor
     * @param schemaNameOpt an optional schema name used to qualify all
     *                      table names in the generated SQL; if Some(), then all
     *                      table names are qualified with the name, otherwise, table
     *                      names are unqualified
     * @return a DatabaseAdapter suitable to use for the database
     */
    public static DatabaseAdapter forVendor(DatabaseVendor vendor,
                                            Option<String> schemaNameOpt) {
        switch (vendor) {
            case H2:
                return new H2DatabaseAdapter(schemaNameOpt);
            default:
                throw new UnsupportedOperationException("not support adapter "
                        + vendor);
        }
    }

    private Option<String> _schemaNameOpt;
    /**
     * The vendor of the database.
     */
    private DatabaseVendor _databaseVendor;

    /**
     * 默认一个空格字符，可以在未赋新值得情况下不影响其它的正常操作
     * The character that is used to quote identifiers.
     */
    private char _quoteCharacter = ' ';

    /**
     * To properly quote table names the database adapter needs to know
     * how the database treats unquoted names.
     */
    private UnquotedNameConverter _unquotedNameConverter;
    /**
     * 如Derby, Oracle and PostgreSQL使用CONSTRAINT，而MySQL使用FOREIGN KEY.
     * The SQL keyword(s) or "phrase" used to drop a foreign key
     * constraint.  For example, Derby, Oracle and PostgreSQL use
     * <p/>
     * ALTER TABLE child DROP CONSTRAINT idx_child_pk_parent;
     * ^^^^^^^^^^
     * <p/>
     * while MySQL uses
     * <p/>
     * ALTER TABLE child DROP FOREIGN KEY idx_child_pk_parent;
     * ^^^^^^^^^^^
     */
    private String _alterTableDropForeignKeyConstraintPhrase;
    /**
     * This value is true if the database implicitly adds an index on
     * the column that has a foreign key constraint added to it.
     * <p/>
     * The following SQL can be used to test the database.  The last
     * statement will fail with a message that there already is an index
     * on the column.
     * <p/>
     * CREATE TABLE parent (pk INT PRIMARY KEY);
     * CREATE TABLE child (pk INT PRIMARY KEY, pk_parent INT NOT NULL);
     * ALTER TABLE child
     * ADD CONSTRAINT idx_child_pk_parent FOREIGN KEY (pk_parent)
     * REFERENCES parent (pk);
     * CREATE INDEX idx_child_pk_parent ON child (pk_parent);
     */
    private boolean _addingForeignKeyConstraintCreatesIndex;

    /**
     * 构造函数.
     * @param newSchemaNameOpt
     */
    public DatabaseAdapter(Option<String> newSchemaNameOpt) {
        this._schemaNameOpt = newSchemaNameOpt;
    }

    public ColumnDefinition newColumnDefinition(String tableName,
                                                String columnName,
                                                SqlType columnType,
                                                ColumnOption... options) {

        ColumnDefinition d = columnDefinitionFactory(columnType);
        d.setAdapterOpt(Option.some(this));
        d.setTableNameOpt(Option.some(tableName));
        d.setColumnNameOpt(Option.some(columnName));
        d.setOptions(Arrays.asList(options));

        d.initialize();

        return d;
    }

    protected abstract ColumnDefinition columnDefinitionFactory(SqlType columnType);

    /**
     * Quote a schema name.
     *
     * @param schemaName the name of the schema to quote
     * @return a properly quoted schema name
     */
    public String quoteSchemaName(String schemaName) {
        return _quoteCharacter + unquotedNameConverter(schemaName) + _quoteCharacter;
    }

    /**
     * Quote a table name, prepending the quoted schema name to the
     * quoted table name along with a '.' if a schema name is provided.
     *
     * @param newSchemaNameOpt an optional schema name
     * @param tableName        the name of the table to quote
     * @return the table name properly quoted for the database,
     *         prepended with the quoted schema name and a '.' if a
     *         schema name is provided
     */
    public String quoteTableName(Option<String> newSchemaNameOpt, String tableName) {
        final int size = 128;
        StringBuilder sb = new StringBuilder(size);

        if (newSchemaNameOpt.isDefined()) {
            sb.append(quoteSchemaName(newSchemaNameOpt.get()))
                    .append('.');
        }

        return sb.append(_quoteCharacter)
                .append(unquotedNameConverter(tableName))
                .append(_quoteCharacter)
                .toString();
    }

    /**
     * Quote a table name.  If the database adapter was provided with a
     * default schema name, then the quoted table name is prepended with
     * the quoted schema name along with a '.'.
     *
     * @param tableName the name of the table to quote
     * @return the table name properly quoted for the database,
     *         prepended with the quoted schema name and a '.' if the
     *         database adapter was provided with a default schema name
     */
    public String quoteTableName(String tableName) {
        // use the default schemaNameOpt defined in the adapter
        return quoteTableName(_schemaNameOpt, tableName);
    }

    /**
     * Quote an index name.
     *
     * @param newSchemaNameOpt an optional schema name
     * @param indexName        the name of the index to quote
     * @return a properly quoted index name
     */
    public String quoteIndexName(Option<String> newSchemaNameOpt, String indexName) {
        final int size = 128;
        StringBuilder sb = new StringBuilder(size);

        if (newSchemaNameOpt.isDefined()) {
            sb.append(quoteSchemaName(newSchemaNameOpt.get()))
                    .append('.');
        }
        return sb.append(_quoteCharacter)
                .append(unquotedNameConverter(indexName))
                .append(_quoteCharacter)
                .toString();
    }

    /**
     * Quote a column name.
     *
     * @param columnName the name of the column to quote
     * @return a properly quoted column name
     */
    public String quoteColumnName(String columnName) {
        return _quoteCharacter + unquotedNameConverter(columnName) + _quoteCharacter;
    }

    String unquotedNameConverter(String columnName) {
        return _unquotedNameConverter.apply(columnName);
    }

    protected abstract String alterColumnSql(Option<String> newSchemaNameOpt, ColumnDefinition newColumnDefinition);

    /**
     * Different databases require different SQL to alter a column's
     * definition.
     *
     * @param newSchemaNameOpt the optional schema name to qualify the
     *                         table name
     * @param tableName        the name of the table with the column
     * @param columnName       the name of the column
     * @param columnType       the type the column is being altered to
     * @param options          a possibly empty array of column options to
     *                         customize the column
     * @return the SQL to alter the column
     */
    public String alterColumnSql(Option<String> newSchemaNameOpt,
                                 String tableName,
                                 String columnName,
                                 SqlType columnType,
                                 ColumnOption... options) {
        return alterColumnSql(newSchemaNameOpt,
                newColumnDefinition(tableName, columnName, columnType, options));
    }

    /**
     * Different databases require different SQL to alter a column's
     * definition.  Uses the schemaNameOpt defined in the adapter.
     *
     * @param tableName  the name of the table with the column
     * @param columnName the name of the column
     * @param columnType the type the column is being altered to
     * @param options    a possibly empty array of column options to
     *                   customize the column
     * @return the SQL to alter the column
     */
    public String alterColumnSql(String tableName,
                                 String columnName,
                                 SqlType columnType,
                                 ColumnOption... options) {
        return alterColumnSql(_schemaNameOpt,
                tableName,
                columnName,
                columnType,
                options);
    }

    /**
     * Different databases require different SQL to drop a column.
     *
     * @param newSchemaNameOpt the optional schema name to qualify the
     *                         table name
     * @param tableName        the name of the table with the column
     * @param columnName       the name of the column
     * @return the SQL to drop the column
     */
    public String removeColumnSql(Option<String> newSchemaNameOpt,
                                  String tableName,
                                  String columnName) {
        final int size = 512;
        return new StringBuilder(size)
                .append("ALTER TABLE ")
                .append(quoteTableName(newSchemaNameOpt, tableName))
                .append(" DROP ")
                .append(quoteColumnName(columnName))
                .toString();
    }

    /**
     * Different databases require different SQL to drop a column.
     * Uses the schemaNameOpt defined in the adapter.
     *
     * @param tableName  the name of the table with the column
     * @param columnName the name of the column
     * @return the SQL to drop the column
     */
    public String removeColumnSql(String tableName,
                                  String columnName) {
        return removeColumnSql(_schemaNameOpt, tableName, columnName);
    }


    /**
     * @param newSchemaNameOpt 模式选项，如数据库用户名
     * @param tableName        索引依赖的表名
     * @param columnNames      索引依赖的列
     * @param options
     * @return sql语句
     * @description: 产生创建数据库索引的sql语句.
     */
    public String addIndexSql(Option<String> newSchemaNameOpt,
                              String tableName, String[] columnNames, IndexOption... options) {
        StringBuffer sql = new StringBuffer();
        StringBuffer indexName = new StringBuffer();
        boolean isUnique = false;
        for (int i = 0; i < options.length; i++) {
            IndexOption option = options[i];
            if (option instanceof Name) { // 指定了索引的名称
                indexName.append(((Name) option).getValue());
            } else if (option instanceof Unique) { // 创建唯一索引
                isUnique = true;
            }
        }

        // 如果未指定options，则自动按照idx_tableName_字段1_字段2_..._字段n（按照列的升序排列）
        if (indexName.length() == 0) {
            Arrays.sort(columnNames);
            indexName.append("IDX_").append(tableName);
            indexName.append("_").append(StringUtils.join(columnNames, "_"));
            for (int i = 0; i < columnNames.length; i++) { // 为列加上特殊修饰符号
                columnNames[i] = quoteColumnName(columnNames[i]).trim();
            }
        }

        String uniqueStr = " ";
        if (isUnique) {
            uniqueStr = " UNIQUE ";
        }
        sql.append("CREATE")
                .append(uniqueStr)
                .append("INDEX ")
                .append(quoteTableName(newSchemaNameOpt, indexName.toString()))
                .append(" ON ")
                .append(quoteTableName(tableName).trim())
                .append("(")
                .append(StringUtils.join(columnNames, ",").toUpperCase())
                .append(")");
        return sql.toString();
    }

    /**
     * @param tableName
     * @param columnNames
     * @param options
     * @return
     * @description: 重载addIndexSql(schemaNameOpt, tableName, columnNames, options)方法
     */
    public String addIndexSql(String tableName, String[] columnNames,
                              IndexOption... options) {
        return addIndexSql(_schemaNameOpt, tableName, columnNames, options);
    }

    /**
     * Different databases require different SQL to drop an index.
     *
     * @param newSchemaNameOpt the optional schema name to qualify the table name
     * @param tableName        the name of the table with the index
     * @param indexName        the name of the index
     * @return the SQL to drop the index
     */
    public String removeIndexSql(Option<String> newSchemaNameOpt,
                                 String tableName, String indexName) {
        return "DROP INDEX " + quoteTableName(newSchemaNameOpt, indexName);
    }

    /**
     * Different databases require different SQL to drop an index.
     * Uses the schemaNameOpt defined in the adapter.
     *
     * @param tableName the name of the table with the index
     * @param indexName the name of the index
     * @return the SQL to drop the index
     */
    public String removeIndexSql(String tableName,
                                 String indexName) {
        return removeIndexSql(_schemaNameOpt, tableName, indexName);
    }

    /**
     * @param tableName   索引依赖的表名
     * @param columnNames 索引依赖的字段
     * @param name        指定索引名称
     * @description: 重载removeIndexSql(tableName, indexName)方法，得到删除索引语句
     */
    public String removeIndexSql(String tableName, String[] columnNames,
                                 Name... name) {
        StringBuffer indexName = new StringBuffer();
        // 如果未指定Name，则默认索引名称为：idx_tableName_字段1_字段2_..._字段n（按照列的升序排列）
        if (name.length == 0) {
            Arrays.sort(columnNames);
            indexName.append("IDX_").append(tableName);
            indexName.append("_").append(StringUtils.join(columnNames, "_"));
        } else {
            indexName.append(name[0].getValue()); // 只取第一个值作为索引名称
        }
        return removeIndexSql(tableName, indexName.toString());
    }

    /**
     * Different databases require different SQL to lock a table.
     *
     * @param tableName the name of the table to lock
     * @return the SQL to lock the table
     */
    public String lockTableSql(String tableName) {
        return lockTableSql(_schemaNameOpt, tableName);
    }

    /**
     * Different databases require different SQL to lock a table.
     *
     * @param newSchemaNameOpt the optional schema name to qualify the
     *                         table name
     * @param tableName        the name of the table to lock
     * @return the SQL to lock the table
     */
    public String lockTableSql(Option<String> newSchemaNameOpt, String tableName) {
        return "LOCK TABLE " + quoteTableName(newSchemaNameOpt, tableName)
                + " IN EXCLUSIVE MODE";
    }

    /**
     * 以下是字段的setter和getter方法.
     */

    protected Option<String> getSchemaNameOpt() {
        return _schemaNameOpt;
    }

    protected void setSchemaNameOpt(Option<String> schemaNameOpt) {
        _schemaNameOpt = schemaNameOpt;
    }

    protected DatabaseVendor getDatabaseVendor() {
        return _databaseVendor;
    }

    protected void setDatabaseVendor(DatabaseVendor databaseVendor) {
        this._databaseVendor = databaseVendor;
    }

    protected char getQuoteCharacter() {
        return _quoteCharacter;
    }

    protected void setQuoteCharacter(char quoteCharacter) {
        this._quoteCharacter = quoteCharacter;
    }

    protected UnquotedNameConverter getUnquotedNameConverter() {
        return _unquotedNameConverter;
    }

    protected void setUnquotedNameConverter(
            UnquotedNameConverter unquotedNameConverter) {
        this._unquotedNameConverter = unquotedNameConverter;
    }

    protected String getAlterTableDropForeignKeyConstraintPhrase() {
        return _alterTableDropForeignKeyConstraintPhrase;
    }

    protected void setAlterTableDropForeignKeyConstraintPhrase(
            String alterTableDropForeignKeyConstraintPhrase) {
        this._alterTableDropForeignKeyConstraintPhrase = alterTableDropForeignKeyConstraintPhrase;
    }

    protected boolean isAddingForeignKeyConstraintCreatesIndex() {
        return _addingForeignKeyConstraintCreatesIndex;
    }

    protected void setAddingForeignKeyConstraintCreatesIndex(
            boolean addingForeignKeyConstraintCreatesIndex) {
        this._addingForeignKeyConstraintCreatesIndex = addingForeignKeyConstraintCreatesIndex;
    }


}
