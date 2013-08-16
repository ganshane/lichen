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
package lichen.migration.model;

/**
 * 表的定义，提供方便的增加列的办法.
 *
 * @author jcai
 */
public interface TableDefinition {
    /**
     * Add any known column type to the table.  The actual SQL text used
     * to create the column is chosen by the database adapter and may be
     * different than the name of the columnType argument.
     *
     * @param name       the column's name
     * @param columnType the type of column being added
     * @param options    a possibly empty array of column options to customize the
     *                   column
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
     * @param name    the column's name
     * @param options a possibly empty array of column options to customize the
     *                column
     * @return the same instance
     */
    TableDefinition bigint(String name,
                           ColumnOption... options);

    /**
     * Add a BLOB column type to the table.  The actual SQL text used to
     * create the column is chosen by the database adapter and may be
     * different than the name of the columnType argument.
     *
     * @param name    the column's name
     * @param options a possibly empty array of column options to customize the
     *                column
     * @return the same instance
     */
    TableDefinition blob(String name,
                         ColumnOption... options);

    /**
     * Add a BOOLEAN column type to the table.  The actual SQL text used
     * to create the column is chosen by the database adapter and may be
     * different than the name of the columnType argument.
     *
     * @param name    the column's name
     * @param options a possibly empty array of column options to customize the
     *                column
     * @return the same instance
     */
    TableDefinition bool(String name, ColumnOption... options);

    /**
     * Add a CHAR column type to the table.  The actual SQL text used to
     * create the column is chosen by the database adapter and may be
     * different than the name of the columnType argument.
     *
     * @param name    the column's name
     * @param options a possibly empty array of column options to customize the
     *                column
     * @return the same instance
     */
    TableDefinition charColumn(String name, ColumnOption... options);

    /**
     * Add a DECIMAL column type to the table.  The actual SQL text used
     * to create the column is chosen by the database adapter and may be
     * different than the name of the columnType argument.
     *
     * @param name    the column's name
     * @param options a possibly empty array of column options to customize the
     *                column
     * @return the same instance
     */
    TableDefinition decimal(String name,
                            ColumnOption... options);

    /**
     * Add a INTEGER column type to the table.  The actual SQL text used
     * to create the column is chosen by the database adapter and may be
     * different than the name of the columnType argument.
     *
     * @param name    the column's name
     * @param options a possibly empty array of column options to customize the
     *                column
     * @return the same instance
     */
    TableDefinition integer(String name,
                            ColumnOption... options);

    /**
     * Add a SMALLINT column type to the table.  The actual SQL text
     * used to create the column is chosen by the database adapter and
     * may be different than the name of the columnType argument.
     *
     * @param name    the column's name
     * @param options a possibly empty array of column options to customize the
     *                column
     * @return the same instance
     */
    TableDefinition smallint(String name,
                             ColumnOption... options);

    /**
     * Add a TIMESTAMP column type to the table.  The actual SQL text
     * used to create the column is chosen by the database adapter and
     * may be different than the name of the columnType argument.
     *
     * @param name    the column's name
     * @param options a possibly empty array of column options to customize the
     *                column
     * @return the same instance
     */
    TableDefinition timestamp(String name,
                              ColumnOption... options);

    /**
     * Add a VARBINARY column type to the table.  The actual SQL text
     * used to create the column is chosen by the database adapter and
     * may be different than the name of the columnType argument.
     *
     * @param name    the column's name
     * @param options a possibly empty array of column options to customize the
     *                column
     * @return the same instance
     */
    TableDefinition varbinary(String name, ColumnOption... options);

    /**
     * Add a VARCHAR column type to the table.  The actual SQL text used
     * to create the column is chosen by the database adapter and may be
     * different than the name of the columnType argument.
     *
     * @param name    the column's name
     * @param options a possibly empty array of column options to customize the
     *                column
     * @return the same instance
     */
    TableDefinition varchar(String name,
                            ColumnOption... options);
}
