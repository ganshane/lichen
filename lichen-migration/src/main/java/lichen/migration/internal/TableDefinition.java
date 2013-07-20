/*
 * Copyright (c) 2009 Sony Pictures Imageworks Inc.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the
 * distribution.  Neither the name of Sony Pictures Imageworks nor the
 * names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package lichen.migration.internal;

import lichen.migration.internal.ColumnDefinition;
import lichen.migration.model.ColumnOption;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder to define a table.  Its methods add the specified type of
 * column to the table's definition.
 */
class TableDefinition{
    private List<ColumnDefinition> columnDefinitions = new ArrayList<ColumnDefinition>();
    private DatabaseAdapter adapter;
    private String tableName;
    public TableDefinition(DatabaseAdapter adapter,String tableName){
        this.adapter = adapter;
        this.tableName = tableName;
    }

  /**
   * Generate a SQL string representation of the columns in the table.
   *
   * @return the SQL text that defines the columns in the table
   */
  final public String toSql(){
      StringBuilder sb = new StringBuilder(512);
      boolean firstColumn = true;
      for(ColumnDefinition columnDefinition:columnDefinitions){

          if (firstColumn) {
              firstColumn = false;
          }
          else {
              sb.append(", ");
          }
          sb.append(columnDefinition.columnName())
                  .append(' ')
                  .append(columnDefinition.toSql());
      }
      return sb.toString();
  }

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
  final public TableDefinition column(String name,
                   SqlType columnType,
                   ColumnOption ... options){
      ColumnDefinition columnDefinition = adapter.newColumnDefinition(tableName,
              name,
              columnType,
              options);
    columnDefinitions.add(columnDefinition);
    return this;
  }

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
  final public TableDefinition bigint(String name,
                                      ColumnOption ...options){
      return column(name, SqlType.BigintType, options);
  }

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
  final public TableDefinition blob(String name,
                                    ColumnOption ...options){
      return column(name, SqlType.BlobType, options);
  }

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
  final public TableDefinition bool(String name,ColumnOption ... options){
    return column(name, SqlType.BooleanType, options);
  }

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
  final public TableDefinition char_(String name,
                 ColumnOption ... options){
    return column(name, SqlType.CharType, options);
  }

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
  final public TableDefinition decimal(String name,
                    ColumnOption ... options){
    return column(name, SqlType.DecimalType, options);
  }

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
  final public TableDefinition integer(String name,
                    ColumnOption ... options){
    return column(name, SqlType.IntegerType, options);
  }

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
  final public TableDefinition smallint(String name ,
                    ColumnOption ... options){
    return column(name, SqlType.SmallintType, options);
  }

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
  final public TableDefinition timestamp(String name,
                      ColumnOption ... options){
    return column(name, SqlType.TimestampType, options);
  }

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
  final public TableDefinition varbinary(String name,ColumnOption ... options){
    return column(name, SqlType.VarbinaryType, options);
  }

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
  final public TableDefinition varchar(String name,
                    ColumnOption ... options){
    return column(name, SqlType.VarcharType, options);
  }
}
