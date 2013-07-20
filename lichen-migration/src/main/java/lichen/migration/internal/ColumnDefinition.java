package lichen.migration.internal;

import lichen.migration.model.AutoIncrement;
import lichen.migration.model.ColumnOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * column definition
 * @author jcai
 */
public class ColumnDefinition {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected List<ColumnOption> options = new ArrayList<ColumnOption>();
    protected String columnName = null;
    protected boolean isAutoIncrement = false;
    protected String defaultValue = null;
    public void initialize() {
        // Because AutoIncrement adds specific behavior the application
        // depends upon, always check if AutoIncrement is specified and
        // throw an exception if the column does not support it.
        checkForAutoIncrement();
        if (isAutoIncrement && this.getClass().getAnnotation(ColumnSupportsAutoIncrement.class) == null){
            String message = "AutoIncrement cannot be used on column '" +
                    columnName +
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
    }

    private void checkForPrecision() {
    }

    private void checkForDefault() {
    }

    private void checkForLimit() {
    }

    private void checkForAutoIncrement() {
        Iterator<ColumnOption> it = options.iterator();
        while(it.hasNext()){
            ColumnOption columnOption = it.next();
            if(columnOption instanceof AutoIncrement){
                it.remove();
                if(isAutoIncrement){
                    logger.warn("列{}重复定义了自增长列",columnName);
                }
                isAutoIncrement = true;
            }
        }
    }
}
