package lichen.migration.internal;

import lichen.migration.model.*;
import lichen.migration.model.ColumnOption;
import lichen.migration.model.Default;
import lichen.migration.model.Limit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * column definition
 * @author jcai
 */
public abstract class ColumnDefinition {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected List<ColumnOption> options = new ArrayList<ColumnOption>();
    protected boolean isAutoIncrement = false;
    protected String defaultValue = null;
    protected Option<DatabaseAdapter> adapterOpt;
    protected Option<String> tableNameOpt;
    protected String tableName(){return tableNameOpt.get();}
    protected Option<String> columnNameOpt;
    protected String columnName(){return columnNameOpt.get();}

    public void initialize() {
        // Because AutoIncrement adds specific behavior the application
        // depends upon, always check if AutoIncrement is specified and
        // throw an exception if the column does not support it.
        checkForAutoIncrement();
        if (isAutoIncrement && this.getClass().getAnnotation(ColumnSupportsAutoIncrement.class) == null){
            String message = "AutoIncrement cannot be used on column '" +
                    columnName() +
                    "' because its data type does not support auto-increment.";
            throw new UnsupportedOperationException(message);
        }

        if (this.getClass().getAnnotation(ColumnSupportsLimit.class) != null) {
            checkForLimit();
        }

        if (getClass().getAnnotation(ColumnSupportsDefault.class) != null) {
            checkForDefault();
        }

        if (getClass().getAnnotation(ColumnSupportsPrecision.class) != null) {
            checkForPrecision();
        }
        if (getClass().getAnnotation(ColumnSupportsScale.class) != null) {
            checkForScale();
        }
    }

    protected Option<Integer> scale = Option.None();
    private void checkForScale() {
        Iterator<ColumnOption> it = options.iterator();
        while(it.hasNext()){
            ColumnOption columnOption = it.next();
            if(columnOption instanceof Scale){
                it.remove();
                ((Scale) columnOption).getValue();
                if(scale.isDefined()){
                    logger.warn("列{}重复定义了Precision",columnName());
                }
                scale = Option.Some(((Scale) columnOption).getValue());
            }
        }
    }

    protected Option<Integer> precision = Option.None();
    private void checkForPrecision() {
        Iterator<ColumnOption> it = options.iterator();
        while(it.hasNext()){
            ColumnOption columnOption = it.next();
            if(columnOption instanceof Precision){
                it.remove();
                ((Precision) columnOption).getValue();
                if(precision.isDefined()){
                    logger.warn("列{}重复定义了Precision",columnName());
                }
                precision = Option.Some(((Precision) columnOption).getValue());
            }
        }
    }

    private void checkForDefault() {
        Iterator<ColumnOption> it = options.iterator();
        while(it.hasNext()){
            ColumnOption columnOption = it.next();
            if(columnOption instanceof Default){
                it.remove();
                if(defaultValue != null){
                    logger.warn("列{}重复定义了默认值",columnName());
                }
                defaultValue = ((Default) columnOption).getValue();
            }
        }
    }

    protected String limitValue = null;
    private void checkForLimit() {
        Iterator<ColumnOption> it = options.iterator();
        while(it.hasNext()){
            ColumnOption columnOption = it.next();
            if(columnOption instanceof Limit){
                it.remove();
                if(limitValue != null){
                    logger.warn("列{}重复定义了长度",columnName());
                }
                limitValue = String.valueOf(((Limit) columnOption).getValue());
            }
        }
    }

    private void checkForAutoIncrement() {
        Iterator<ColumnOption> it = options.iterator();
        while(it.hasNext()){
            ColumnOption columnOption = it.next();
            if(columnOption instanceof AutoIncrement){
                it.remove();
                if(isAutoIncrement){
                    logger.warn("列{}重复定义了自增长列",columnName());
                }
                isAutoIncrement = true;
            }
        }
    }
    private Option<Boolean> notNull(){
        Option<Boolean> notNull = Option.None();
        Iterator<ColumnOption> it = options.iterator();
        while(it.hasNext()){
            ColumnOption columnOption = it.next();
            if(columnOption instanceof NotNull){
                it.remove();
                if(notNull.isDefined()){
                    logger.warn("列{}重复定义了NotNull Or Nullable",columnName());
                }
                notNull = Option.Some(true);
            }else if(columnOption instanceof Nullable){
                it.remove();
                if(notNull.isDefined()){
                    logger.warn("列{}重复定义了NotNull Or Nullable",columnName());
                }
                notNull = Option.Some(false);
            }
        }
        return notNull;
    }
    private boolean isPrimaryKey(){
        Iterator<ColumnOption> it = options.iterator();
        boolean isPrimary = false;
        while(it.hasNext()){
            ColumnOption columnOption = it.next();
            if(columnOption instanceof PrimaryKey){
                it.remove();
                isPrimary = true;
            }
        }
        return isPrimary;
    }
    private boolean isUnique(){
        Iterator<ColumnOption> it = options.iterator();
        boolean isUnique = false;
        while(it.hasNext()){
            ColumnOption columnOption = it.next();
            if(columnOption instanceof Unique){
                it.remove();
                isUnique = true;
            }
        }
        return isUnique;
    }
    protected abstract String sql();
    final public String toSql(){
        StringBuilder sb = new StringBuilder(512)
                .append(sql());

        if (defaultValue != null) {
            sb.append(" DEFAULT ");
            sb.append(defaultValue);
        }
        boolean isPrimaryKey = isPrimaryKey();

        if (isPrimaryKey) {
            sb.append(" PRIMARY KEY");
        }

        boolean isUnique = isUnique();
        if (isUnique) {
            sb.append(" UNIQUE");
        }
        // Not all databases, such as Derby, support specifying NULL for a
        // column that may have NULL values.
        Option<Boolean> notNull = notNull();
        if(notNull.isDefined()){
            sb.append(" NOT NULL");
        }
        // Warn for any unused options.
        if (!options.isEmpty()) {
            logger.warn("The following options for the '{}' column are unused: {}.",columnName(), options);
        }

        // Warn about illegal combinations in some databases.
        if (isPrimaryKey && notNull.isDefined() && !notNull.get()) {
            logger.warn("Specifying PrimaryKey and Nullable in a column is not " +
                    "supported in all databases.");
        }

        // Warn when different options are used that specify the same
        // behavior so one can be removed.
        if (isPrimaryKey && notNull.isDefined() && notNull.get()) {
            logger.warn("Specifying PrimaryKey and NotNull is redundant.");
        }
        if (isPrimaryKey && isUnique) {
            logger.warn("Specifying PrimaryKey and Unique is redundant.");
        }

        return sb.toString();
    }
    /**
     * Given the SQL for a column data type, return it with the LIMIT
     * syntax appended if a limit is given, otherwise return SQL
     * unmodified.
     *
     * @param columnTypeName the column type name
     * @param limit optional column limit
     * @return the column type name with the limit syntax if a limit was
     *         given
     */
    protected String optionallyAddLimitToDataType(String columnTypeName,
                                               String limit){
        if(limit != null){
            return columnTypeName + "("+limit+")";
        }else{
            return columnTypeName;
        }
    }
    protected String optionallyAddLimitToDataType(String columnTypeName){
        return optionallyAddLimitToDataType(columnTypeName, limitValue);
    }
}
@ColumnSupportsScale
@ColumnSupportsPrecision
@ColumnSupportsDefault
abstract class AbstractDecimalColumnDefinition extends ColumnDefinition{
    /**
     * Concrete subclasses must define this to the name of the DECIMAL
     * or NUMERIC data type specific for the database.
     */
    protected abstract String decimalSqlName();

    protected String sql(){
        if(precision.isDefined()){
            if(scale.isDefined()){
                return decimalSqlName()+ "(" + precision.get() + ", " + scale.get() + ")";
            }else{
                return decimalSqlName() + "(" + precision.get()+")";
            }
        }else{
            if(scale.isDefined())
                throw new IllegalArgumentException("Cannot specify a scale without " +
                        "also specifying a precision.");
            else
                return decimalSqlName();
        }
    }
}
@ColumnSupportsDefault
class DefaultBigintColumnDefinition
        extends ColumnDefinition{
    protected String sql(){return "BIGINT";}
}

class DefaultBlobColumnDefinition
        extends ColumnDefinition {
    protected String sql() { return "BLOB";}
}

@ColumnSupportsDefault
class DefaultBooleanColumnDefinition
        extends ColumnDefinition{
    protected String sql() {return "BOOLEAN";}
}

@ColumnSupportsDefault
@ColumnSupportsLimit
class DefaultCharColumnDefinition
        extends ColumnDefinition{
    protected String sql(){return optionallyAddLimitToDataType("CHAR");}
}
class DefaultDecimalColumnDefinition
        extends AbstractDecimalColumnDefinition {
    protected String decimalSqlName(){ return "DECIMAL";}
}
@ColumnSupportsDefault
class DefaultIntegerColumnDefinition
        extends ColumnDefinition{
    protected String sql() {return "INTEGER";}
}

@ColumnSupportsDefault
class DefaultSmallintColumnDefinition
        extends ColumnDefinition{
    protected String sql(){ return "SMALLINT";}
}

@ColumnSupportsLimit
@ColumnSupportsDefault
class DefaultTimestampColumnDefinition
        extends ColumnDefinition{
    protected String sql() { return optionallyAddLimitToDataType("TIMESTAMP");}
}
@ColumnSupportsLimit
@ColumnSupportsDefault
class DefaultVarbinaryColumnDefinition
        extends ColumnDefinition{
    protected String sql(){return optionallyAddLimitToDataType("VARBINARY");}
}

@ColumnSupportsLimit
@ColumnSupportsDefault
class DefaultVarcharColumnDefinition
        extends ColumnDefinition{
    protected String sql(){ return optionallyAddLimitToDataType("VARCHAR");}
}
