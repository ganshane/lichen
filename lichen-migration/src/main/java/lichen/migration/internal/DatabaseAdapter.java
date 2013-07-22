package lichen.migration.internal;

import lichen.migration.model.ColumnOption;
import lichen.migration.model.SqlType;

import java.util.Arrays;

/**
 * 数据库适配器
 * @author jcai
 */
abstract class DatabaseAdapter {
    /**
     * Return the appropriate database adapter for the given database
     * vendor.
     *
     * @param vendor the database vendor
     * @param schemaNameOpt an optional schema name used to qualify all
     *        table names in the generated SQL; if Some(), then all
     *        table names are qualified with the name, otherwise, table
     *        names are unqualified
     * @return a DatabaseAdapter suitable to use for the database
     */
    public static DatabaseAdapter forVendor(DatabaseVendor vendor,
                  Option<String> schemaNameOpt){
        switch (vendor){
            case H2:
                return new H2DatabaseAdapter(schemaNameOpt);
            default:
                throw new UnsupportedOperationException("not support adapter "+vendor);
        }
    }
    Option<String> schemaNameOpt;
    /**
     * The vendor of the database.
     */
    protected DatabaseVendor databaseVendor;

    /**
     * The character that is used to quote identifiers.
     */
    protected char quoteCharacter;

    /**
     * To properly quote table names the database adapter needs to know
     * how the database treats unquoted names.
     */
    protected UnquotedNameConverter unquotedNameConverter;
    /**
     * The SQL keyword(s) or "phrase" used to drop a foreign key
     * constraint.  For example, Derby, Oracle and PostgreSQL use
     *
     *   ALTER TABLE child DROP CONSTRAINT idx_child_pk_parent;
     *                          ^^^^^^^^^^
     *
     * while MySQL uses
     *
     *   ALTER TABLE child DROP FOREIGN KEY idx_child_pk_parent;
     *                          ^^^^^^^^^^^
     */
    protected String alterTableDropForeignKeyConstraintPhrase;
    /**
     * This value is true if the database implicitly adds an index on
     * the column that has a foreign key constraint added to it.
     *
     * The following SQL can be used to test the database.  The last
     * statement will fail with a message that there already is an index
     * on the column.
     *
     *   CREATE TABLE parent (pk INT PRIMARY KEY);
     *   CREATE TABLE child (pk INT PRIMARY KEY, pk_parent INT NOT NULL);
     *   ALTER TABLE child
     *     ADD CONSTRAINT idx_child_pk_parent FOREIGN KEY (pk_parent)
     *     REFERENCES parent (pk);
     *   CREATE INDEX idx_child_pk_parent ON child (pk_parent);
     */
    protected boolean addingForeignKeyConstraintCreatesIndex;

    public DatabaseAdapter(Option<String> schemaNameOpt){
        this.schemaNameOpt = schemaNameOpt;
    }
    public ColumnDefinition newColumnDefinition(String tableName,
                            String columnName,
                            SqlType columnType,
                            ColumnOption ... options){

        ColumnDefinition d = columnDefinitionFactory(columnType);
        d.adapterOpt = Option.Some(this);
        d.tableNameOpt = Option.Some(tableName);
        d.columnNameOpt = Option.Some(columnName);
        d.options.addAll(Arrays.asList(options));

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
    public String quoteSchemaName(String schemaName){
        return quoteCharacter + unquotedNameConverter(schemaName) + quoteCharacter;
    }
    /**
     * Quote a table name, prepending the quoted schema name to the
     * quoted table name along with a '.' if a schema name is provided.
     *
     * @param schemaNameOpt an optional schema name
     * @param tableName the name of the table to quote
     * @return the table name properly quoted for the database,
     *         prepended with the quoted schema name and a '.' if a
     *         schema name is provided
     */
    public String quoteTableName(Option<String> schemaNameOpt,String tableName){
        StringBuilder sb = new StringBuilder(128);

        if(schemaNameOpt.isDefined()){
            sb.append(quoteSchemaName(schemaNameOpt.get()))
                    .append('.');
        }

        return sb.append(quoteCharacter)
                .append(unquotedNameConverter(tableName))
                .append(quoteCharacter)
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
    public String quoteTableName(String tableName){
        // use the default schemaNameOpt defined in the adapter
        return quoteTableName(schemaNameOpt, tableName);
    }
    /**
     * Quote an index name.
     *
     * @param schemaNameOpt an optional schema name
     * @param indexName the name of the index to quote
     * @return a properly quoted index name
     */
    public String quoteIndexName(Option<String> schemaNameOpt,String indexName){
        StringBuilder sb = new StringBuilder(128);

        if(schemaNameOpt.isDefined()){
                sb.append(quoteSchemaName(schemaNameOpt.get()))
                        .append('.');
        }
        return sb.append(quoteCharacter)
                .append(unquotedNameConverter(indexName))
                .append(quoteCharacter)
                .toString();
    }
    /**
    * Quote a column name.
    *
    * @param columnName the name of the column to quote
    * @return a properly quoted column name
    */
    public String quoteColumnName(String columnName){
        return quoteCharacter + unquotedNameConverter(columnName) + quoteCharacter;
    }

    String unquotedNameConverter(String columnName) {
        return unquotedNameConverter.apply(columnName);
    }

    protected abstract String alterColumnSql(Option<String> schemaNameOpt, ColumnDefinition columnDefinition);
    /**
     * Different databases require different SQL to alter a column's
     * definition.
     *
     * @param schemaNameOpt the optional schema name to qualify the
     *        table name
     * @param tableName the name of the table with the column
     * @param columnName the name of the column
     * @param columnType the type the column is being altered to
     * @param options a possibly empty array of column options to
     *        customize the column
     * @return the SQL to alter the column
     */
    public String alterColumnSql(Option<String> schemaNameOpt,
                       String tableName,
                       String columnName,
                       SqlType columnType,
                       ColumnOption ... options){
        return alterColumnSql(schemaNameOpt,
                newColumnDefinition(tableName, columnName, columnType, options));
    }
    /**
     * Different databases require different SQL to alter a column's
     * definition.  Uses the schemaNameOpt defined in the adapter.
     *
     * @param tableName the name of the table with the column
     * @param columnName the name of the column
     * @param columnType the type the column is being altered to
     * @param options a possibly empty array of column options to
     *        customize the column
     * @return the SQL to alter the column
     */
    public String alterColumnSql(String tableName,
                       String columnName,
                       SqlType columnType,
                       ColumnOption ... options){
        return alterColumnSql(schemaNameOpt,
                tableName,
                columnName,
                columnType,
                options);
    }
    /**
     * Different databases require different SQL to drop a column.
     *
     * @param schemaNameOpt the optional schema name to qualify the
     *        table name
     * @param tableName the name of the table with the column
     * @param columnName the name of the column
     * @return the SQL to drop the column
     */
    public String removeColumnSql(Option<String> schemaNameOpt,
                        String tableName,
                        String columnName){
        return new StringBuilder(512)
                .append("ALTER TABLE ")
                .append(quoteTableName(schemaNameOpt, tableName))
                .append(" DROP ")
                .append(quoteColumnName(columnName))
                .toString();
    }
    /**
     * Different databases require different SQL to drop a column.
     * Uses the schemaNameOpt defined in the adapter.
     *
     * @param tableName the name of the table with the column
     * @param columnName the name of the column
     * @return the SQL to drop the column
     */
    public String removeColumnSql(String tableName,
                        String columnName){
        return removeColumnSql(schemaNameOpt, tableName, columnName);
    }
    /**
     * Different databases require different SQL to drop an index.
     *
     * @param schemaNameOpt the optional schema name to qualify the
     *        table name
     * @param tableName the name of the table with the index
     * @param indexName the name of the index
     * @return the SQL to drop the index
     */
    public String removeIndexSql(Option<String> schemaNameOpt,
                       String tableName,
                       String indexName){
        return "DROP INDEX " +
                quoteTableName(schemaNameOpt, indexName);
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
                       String indexName){
        return removeIndexSql(schemaNameOpt, tableName, indexName);
    }
    /**
     * Different databases require different SQL to lock a table.
     *
     * @param tableName the name of the table to lock
     * @return the SQL to lock the table
     */
    public String lockTableSql(String tableName){
        return lockTableSql(schemaNameOpt, tableName);
    }
    /**
     * Different databases require different SQL to lock a table.
     *
     * @param schemaNameOpt the optional schema name to qualify the
     *        table name
     * @param tableName the name of the table to lock
     * @return the SQL to lock the table
     */
    public String lockTableSql(Option<String> schemaNameOpt,String tableName){
        return "LOCK TABLE " +
                quoteTableName(schemaNameOpt, tableName) +
                " IN EXCLUSIVE MODE";
    }
}
