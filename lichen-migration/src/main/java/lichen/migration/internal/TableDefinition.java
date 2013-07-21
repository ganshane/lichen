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
