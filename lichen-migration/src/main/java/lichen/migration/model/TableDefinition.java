package lichen.migration.model;

/**
 * @author jcai
 */
public interface TableDefinition {
    /**
     * Add any known column type to the table.  The actual SQL text used
     * to create the column is chosen by the database adapter and may be
     * different than the name of the columnType argument.
     *
     * @param name the column's name
     * @param columnType the type of column being added
     * @param options a possibly empty array of column options to customize the
     *        column
     * @return the same instance
     */
    TableDefinition column(String name,
                           SqlType columnType,
                           ColumnOption... options);

    /**
     * Add a BIGINT column type to the table.  The actual SQL text used
     * to create the column is chosen by the database adapter and may be
     * different than the name of the columnType argument.
     *
     * @param name the column's name
     * @param options a possibly empty array of column options to customize the
     *        column
     * @return the same instance
     */
    TableDefinition bigint(String name,
                           ColumnOption... options);

    /**
     * Add a BLOB column type to the table.  The actual SQL text used to
     * create the column is chosen by the database adapter and may be
     * different than the name of the columnType argument.
     *
     * @param name the column's name
     * @param options a possibly empty array of column options to customize the
     *        column
     * @return the same instance
     */
    TableDefinition blob(String name,
                         ColumnOption... options);

    /**
     * Add a BOOLEAN column type to the table.  The actual SQL text used
     * to create the column is chosen by the database adapter and may be
     * different than the name of the columnType argument.
     *
     * @param name the column's name
     * @param options a possibly empty array of column options to customize the
     *        column
     * @return the same instance
     */
    TableDefinition bool(String name, ColumnOption... options);

    /**
     * Add a CHAR column type to the table.  The actual SQL text used to
     * create the column is chosen by the database adapter and may be
     * different than the name of the columnType argument.
     *
     * @param name the column's name
     * @param options a possibly empty array of column options to customize the
     *        column
     * @return the same instance
     */
    TableDefinition char_(String name,
                          ColumnOption... options);

    /**
     * Add a DECIMAL column type to the table.  The actual SQL text used
     * to create the column is chosen by the database adapter and may be
     * different than the name of the columnType argument.
     *
     * @param name the column's name
     * @param options a possibly empty array of column options to customize the
     *        column
     * @return the same instance
     */
    TableDefinition decimal(String name,
                            ColumnOption... options);

    /**
     * Add a INTEGER column type to the table.  The actual SQL text used
     * to create the column is chosen by the database adapter and may be
     * different than the name of the columnType argument.
     *
     * @param name the column's name
     * @param options a possibly empty array of column options to customize the
     *        column
     * @return the same instance
     */
    TableDefinition integer(String name,
                            ColumnOption... options);

    /**
     * Add a SMALLINT column type to the table.  The actual SQL text
     * used to create the column is chosen by the database adapter and
     * may be different than the name of the columnType argument.
     *
     * @param name the column's name
     * @param options a possibly empty array of column options to customize the
     *        column
     * @return the same instance
     */
    TableDefinition smallint(String name,
                             ColumnOption... options);

    /**
     * Add a TIMESTAMP column type to the table.  The actual SQL text
     * used to create the column is chosen by the database adapter and
     * may be different than the name of the columnType argument.
     *
     * @param name the column's name
     * @param options a possibly empty array of column options to customize the
     *        column
     * @return the same instance
     */
    TableDefinition timestamp(String name,
                              ColumnOption... options);

    /**
     * Add a VARBINARY column type to the table.  The actual SQL text
     * used to create the column is chosen by the database adapter and
     * may be different than the name of the columnType argument.
     *
     * @param name the column's name
     * @param options a possibly empty array of column options to customize the
     *        column
     * @return the same instance
     */
    TableDefinition varbinary(String name, ColumnOption... options);

    /**
     * Add a VARCHAR column type to the table.  The actual SQL text used
     * to create the column is chosen by the database adapter and may be
     * different than the name of the columnType argument.
     *
     * @param name the column's name
     * @param options a possibly empty array of column options to customize the
     *        column
     * @return the same instance
     */
    TableDefinition varchar(String name,
                            ColumnOption... options);
}
