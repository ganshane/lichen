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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import lichen.migration.model.ColumnOption;
import lichen.migration.model.IndexOption;
import lichen.migration.model.Name;
import lichen.migration.model.SqlType;
import lichen.migration.model.TableOption;
import lichen.migration.services.MigrationHelper;
import lichen.migration.services.TableCallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库升级抽象类.
 *
 * @author jcai
 */
class MigrationHelperImpl implements MigrationHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MigrationHelperImpl.class);

    /**
     * The raw connection to the database that underlies the logging
     * connection.  This is provided in case the real database
     * connection is needed because the logging connection does not
     * provide a required feature.  This connection should not be used
     * in normal use.
     * <p/>
     * This is set using property style dependency injection instead of
     * constructor style injection, which makes for cleaner code for the
     * users of this migration framework.
     */
    private Option<Connection> _rawConnectionOpt = Option.None();

    /**
     * Get the raw connection to the database the migration can use for
     * any custom work.  This connection is the raw connection that
     * underlies the logging connection and does not log any operations
     * performed on it.  It should only be used when the logging
     * connection does not provide a required feature.  The MigrationHelper
     * subclass must be careful with this connection and leave it in a
     * good state, as all of the other migration methods defined in
     * MigrationHelper use the same connection.
     */
    public Connection rawConnection() {
        return _rawConnectionOpt.get();
    }

    /**
     * The connection to the database that is used for the migration.
     * This connection also logs all operations performed on it.
     * <p/>
     * This is set using property style dependency injection instead of
     * constructor style injection, which makes for cleaner code for the
     * users of this migration framework.
     */
    private Option<Connection> _connectionOpt = Option.None();

    /**
     * Get the connection to the database the migration can use for any
     * custom work.  This connection logs all operations performed on
     * it.  The MigrationHelper subclass must be careful with this connection
     * and leave it in a good state, as all of the other migration
     * methods defined in MigrationHelper use the same connection.
     */
    public Connection connection() {
        return _connectionOpt.get();
    }

    /**
     * The database adapter that will be used for the migration.
     * <p/>
     * This is set using property style dependency injection instead of
     * constructor style injection, which makes for cleaner code for the
     * users of this migration framework.
     */
    private Option<DatabaseAdapter> _adapterOpt = Option.None();

    /**
     * The database adapter that will be used for the migration.
     */
    private DatabaseAdapter adapter() {
        return _adapterOpt.get();
    }

    /**
     * The vendor of the database the migration is being run on.
     */
    public DatabaseVendor databaseVendor() {
        return adapter().getDatabaseVendor();
    }


    /**
     * This value is true if the database implicitly adds an index on
     * the column that has a foreign key constraint added to it.
     * <p/>
     * The following SQL can be used to test the database.  The last
     * statement will fail with a message that there already is an index
     * on the column.
     * <p/>
     * create table parent (pk int primary key);
     * create table child (pk int primary key, pk_parent int not null);
     * alter table child
     * add constraint idx_child_pk_parent foreign key (pk_parent)
     * references parent (pk);
     * create index idx_child_pk_parent on child (pk_parent);
     */
    public boolean addingForeignKeyConstraintCreatesIndex() {
        return adapter().isAddingForeignKeyConstraintCreatesIndex();
    }

    /**
     * Execute the given SQL string using the migration's connection.
     *
     * @param sql the SQL to execute
     */
    public final void execute(final String sql) throws Throwable {
        ResourceUtils.autoClosingStatement(connection().createStatement(), new Function1<Statement, Void>() {
            public Void apply(Statement parameter) throws Throwable {
                LOGGER.debug("execute sql:{}", sql);
                parameter.execute(sql);
                return null;
            }
        });
    }

    /**
     * Given a SQL string and a Function1[PreparedStatement,Unit], start
     * a new transaction by turning off auto-commit mode on the
     * connection then create a new prepared statement with the SQL
     * string and pass the prepared statement to the closure argument.
     * The closure should not perform the commit as this method will
     * commit the transaction.  If the closure throws an exception then
     * the transaction is rolled back and the exception that caused the
     * rollback is re-thrown.  Finally, the auto-commit state is reset
     * to the value the connection had before this method was called.
     *
     * @param sql the SQL text that will be prepared
     * @param f   the Function1[PreparedStatement,Unit] that will be given
     *            a new prepared statement
     */
    public final void withPreparedStatement(final String sql, final Function1<PreparedStatement, Void> f) {
        ResourceUtils.autoCommittingConnection(connection(),
                ResourceUtils.CommitBehavior.CommitUponReturnOrRollbackUponException,
                new Function1<Connection, Object>() {
                    public Object apply(Connection parameter) throws Throwable {
                        return ResourceUtils.autoClosingStatement(parameter.prepareStatement(sql), f);
                    }
                }
        );
    }

    /**
     * Given a SQL result set and a Function1[ResultSet,R], pass the
     * result set to the closure.  After the closure has completed,
     * either normally via a return or by throwing an exception, close
     * the result set.
     *
     * @param rs the SQL result set
     * @param f  the Function1[ResultSet,R] that will be given the result
     *           set
     * @return the result of f if f returns normally
     */
    public final <R> R withResultSet(ResultSet rs, Function1<ResultSet, R> f) {
        return ResourceUtils.autoClosingResultSet(rs, f);
    }

    public final void createTable(String tableName, TableCallback body, TableOption... options) throws Throwable {
        TableDefinitionImpl tableDefinition = new TableDefinitionImpl(adapter(), tableName);

        body.doInTable(tableDefinition);

        String sql = "CREATE TABLE " + adapter().quoteTableName(tableName) + " (" + tableDefinition.toSql() + ')';
        execute(sql);
    }

    public final void addColumn(String tableName,
                                String columnName,
                                SqlType columnType,
                                ColumnOption... options) throws Throwable {
        TableDefinitionImpl tableDefinition = new TableDefinitionImpl(adapter(), tableName);

        tableDefinition.column(columnName, columnType, options);
        String sql = "ALTER TABLE " + adapter().quoteTableName(tableName) + " ADD " + tableDefinition.toSql();
        execute(sql);
    }

    public final void alterColumn(String tableName,
                                  String columnName,
                                  SqlType columnType,
                                  ColumnOption... options) throws Throwable {
        execute(adapter().alterColumnSql(tableName,
                columnName,
                columnType,
                options));
    }

    public final void removeColumn(String tableName,
                                   String columnName) throws Throwable {
        execute(adapter().removeColumnSql(tableName, columnName));
    }

    public final void dropTable(String tableName) throws Throwable {
        String sql = "DROP TABLE " + adapter().quoteTableName(tableName);
        execute(sql);
    }

    @Override
    public void addIndex(String tableName, String[] columnNames,
                         IndexOption... options) throws Throwable {
        String addIndexSql = adapter().addIndexSql(tableName, columnNames, options);
        execute(addIndexSql);
    }

    @Override
    public void addIndex(String tableName, String columnName,
                         IndexOption... options) throws Throwable {
        addIndex(tableName, new String[]{columnName}, options);
    }

    @Override
    public void removeIndex(String tableName, String[] columnNames,
                            Name... name) throws Throwable {
        String removeSql = adapter().removeIndexSql(tableName, columnNames, name);
        execute(removeSql);
    }

    @Override
    public void removeIndex(String tableName, String columnName, Name... name) throws Throwable {
        removeIndex(tableName, new String[]{columnName}, name);
    }

    /**
     * setter和getter方法.
     * @return
     */

    protected Option<Connection> getRawConnectionOpt() {
        return _rawConnectionOpt;
    }

    protected void setRawConnectionOpt(Option<Connection> rawConnectionOpt) {
        this._rawConnectionOpt = rawConnectionOpt;
    }

    protected Option<Connection> getConnectionOpt() {
        return _connectionOpt;
    }

    protected void setConnectionOpt(Option<Connection> connectionOpt) {
        this._connectionOpt = connectionOpt;
    }

    protected Option<DatabaseAdapter> getAdapterOpt() {
        return _adapterOpt;
    }

    protected void setAdapterOpt(Option<DatabaseAdapter> adapterOpt) {
        this._adapterOpt = adapterOpt;
    }

    protected static Logger getLogger() {
        return LOGGER;
    }

}
