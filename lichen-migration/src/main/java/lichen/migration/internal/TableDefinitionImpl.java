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

import lichen.migration.model.ColumnOption;
import lichen.migration.model.Comment;
import lichen.migration.model.SqlType;
import lichen.migration.model.TableDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder to define a table.  Its methods add the specified type of
 * column to the table's definition.
 */
class TableDefinitionImpl implements TableDefinition {
    private List<ColumnDefinition> _columnDefinitions = new ArrayList<ColumnDefinition>();
    private List<String> _commentsSql = new ArrayList<String>();
    private DatabaseAdapter _adapter;
    private String _tableName;

    public TableDefinitionImpl(DatabaseAdapter newAdapter, String newTableName) {
        this._adapter = newAdapter;
        this._tableName = newTableName;
    }

    /**
     * Generate a SQL string representation of the columns in the table.
     *
     * @return the SQL text that defines the columns in the table
     */
    public final String toSql() {
        final int size = 512;
        StringBuilder sb = new StringBuilder(size);
        boolean firstColumn = true;
        for (ColumnDefinition columnDefinition : _columnDefinitions) {

            if (firstColumn) {
                firstColumn = false;
            } else {
                sb.append(", ");
            }
            sb.append(_adapter.quoteColumnName(columnDefinition.columnName()))
                    .append(' ')
                    .append(columnDefinition.toSql());
        }
        return sb.toString();
    }

    public final TableDefinition column(String name, SqlType columnType,
                                        ColumnOption... options) {
        Comment comment = null;
        List<ColumnOption> colOptions = new ArrayList<ColumnOption>();
        for (ColumnOption option : options) {
            if (option instanceof Comment) {
                comment = (Comment) option;
            } else {
                colOptions.add(option);
            }
        }
        ColumnDefinition columnDefinition = _adapter.newColumnDefinition(_tableName,
                name,
                columnType,
                colOptions.toArray(new ColumnOption[colOptions.size()]));
        _columnDefinitions.add(columnDefinition);
        if (comment != null)
            _commentsSql.add(_adapter.commentColumnSql(_tableName, name, comment));
        return this;
    }

    public final TableDefinition bigint(String name, ColumnOption... options) {
        return column(name, SqlType.BigintType, options);
    }

    public final TableDefinition blob(String name, ColumnOption... options) {
        return column(name, SqlType.BlobType, options);
    }
    
	public TableDefinition clob(String name, ColumnOption... options) {
		return column(name, SqlType.ClobType, options);
	}
    
    public final TableDefinition bool(String name, ColumnOption... options) {
        return column(name, SqlType.BooleanType, options);
    }

    public final TableDefinition charColumn(String name, ColumnOption... options) {
        return column(name, SqlType.CharType, options);
    }

    public final TableDefinition decimal(String name, ColumnOption... options) {
        return column(name, SqlType.DecimalType, options);
    }

    public final TableDefinition integer(String name, ColumnOption... options) {
        return column(name, SqlType.IntegerType, options);
    }

    public final TableDefinition smallint(String name, ColumnOption... options) {
        return column(name, SqlType.SmallintType, options);
    }

    public final TableDefinition timestamp(String name,
                                           ColumnOption... options) {
        return column(name, SqlType.TimestampType, options);
    }

    public final TableDefinition varbinary(String name, ColumnOption... options) {
        return column(name, SqlType.VarbinaryType, options);
    }

    public final TableDefinition varchar(String name,
                                         ColumnOption... options) {
        return column(name, SqlType.VarcharType, options);
    }

    public List<String> getCommentsSql() {
        return _commentsSql;
    }
}
