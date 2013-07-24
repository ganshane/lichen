// Copyright 2013 the original author or authors.
// site: http://lichen.ganshane.com
package lichen.migration.internal;

import lichen.migration.model.SqlType;
import lichen.migration.model.TableDefinition;
import lichen.migration.model.ColumnOption;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder to define a table.  Its methods add the specified type of
 * column to the table's definition.
 */
class TableDefinitionImpl implements TableDefinition {
    private List<ColumnDefinition> columnDefinitions = new ArrayList<ColumnDefinition>();
    private DatabaseAdapter adapter;
    private String tableName;
    public TableDefinitionImpl(DatabaseAdapter adapter,String tableName){
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
          sb.append(adapter.quoteColumnName(columnDefinition.columnName()))
                  .append(' ')
                  .append(columnDefinition.toSql());
      }
      return sb.toString();
  }

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

  final public TableDefinition bigint(String name,
                                      ColumnOption ...options){
      return column(name, SqlType.BigintType, options);
  }

  final public TableDefinition blob(String name,
                                    ColumnOption ...options){
      return column(name, SqlType.BlobType, options);
  }

  final public TableDefinition bool(String name,ColumnOption ... options){
    return column(name, SqlType.BooleanType, options);
  }

  final public TableDefinition char_(String name,
                 ColumnOption ... options){
    return column(name, SqlType.CharType, options);
  }

  final public TableDefinition decimal(String name,
                    ColumnOption ... options){
    return column(name, SqlType.DecimalType, options);
  }

  final public TableDefinition integer(String name,
                    ColumnOption ... options){
    return column(name, SqlType.IntegerType, options);
  }

  final public TableDefinition smallint(String name ,
                    ColumnOption ... options){
    return column(name, SqlType.SmallintType, options);
  }

  final public TableDefinition timestamp(String name,
                      ColumnOption ... options){
    return column(name, SqlType.TimestampType, options);
  }

  final public TableDefinition varbinary(String name,ColumnOption ... options){
    return column(name, SqlType.VarbinaryType, options);
  }

  final public TableDefinition varchar(String name,
                    ColumnOption ... options){
    return column(name, SqlType.VarcharType, options);
  }
}
