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

import lichen.migration.model.Comment;
import lichen.migration.model.Increment;
import lichen.migration.model.MaxValue;
import lichen.migration.model.MinValue;
import lichen.migration.model.SequenceOption;
import lichen.migration.model.SqlType;
import lichen.migration.model.Start;

/**
 *
 * @author shen
 *
 */
class OracleDatabaseAdapter extends DatabaseAdapter {
    public OracleDatabaseAdapter(Option<String> schemaNameOpt) {
        super(schemaNameOpt);
        setUnquotedNameConverter(UnquotedNameConverter.getUppercaseUnquotedNameConverter());
        setAddingForeignKeyConstraintCreatesIndex(true);
    }

    protected String alterColumnSql(Option<String> schemaNameOpt,
                                    ColumnDefinition columnDefinition) {
        final int size = 512;
        return new java.lang.StringBuilder(size).append("ALTER TABLE ").append(
                quoteTableName(schemaNameOpt, columnDefinition.tableName()))
                .append(" ALTER COLUMN ").append(
                        quoteColumnName(columnDefinition.columnName()))
                .append(" ").append(columnDefinition.toSql()).toString();
    }

    @Override
    protected ColumnDefinition columnDefinitionFactory(
            SqlType columnType) {
        switch (columnType) {
            case BigintType:
                return new OracleLongColumnDefinition();
            case BlobType:
                return new DefaultBlobColumnDefinition();
            case ClobType:
                return new OracleClobColumnDefinition();
            case BooleanType:
                return new DefaultBooleanColumnDefinition();
            case CharType:
                return new DefaultCharColumnDefinition();
            case DecimalType:
                return new DefaultDecimalColumnDefinition();
            case IntegerType:
                return new OracleIntegerColumnDefinition();
            case SmallintType:
                return new DefaultSmallintColumnDefinition();
            case VarcharType:
                return new OracleVarcharColumnDefinition();
            case TimestampType:
                return new OracleDateColumnDefinition();
            default:
                break;
        }
        throw new IllegalArgumentException("Not support type");
    }


    @Override
    public String removeIndexSql(Option<String> schemaNameOpt,
                                 String tableName, String indexName) {
        return "DROP INDEX " + quoteColumnName(indexName);
    }

    @Override
    public String lockTableSql(Option<String> schemaNameOpt, String tableName) {
        return "SELECT * FROM " + quoteTableName(schemaNameOpt, tableName)
                + " FOR UPDATE";
    }

	@Override
	public String commentColumnSql(Option<String> schemaNameOpt,String tableName,
			String columnName, Comment comment) {
		return new java.lang.StringBuffer().append("COMMENT ON COLUMN ").append(
				quoteTableName(schemaNameOpt, tableName)).append(".").append(columnName.toUpperCase())
                .append(" IS '").append(comment.getValue()).append("'").toString();
	}

	@Override
	public String commentTableSql(Option<String> schemaNameOpt,String tableName,
			Comment comment) {
		return new java.lang.StringBuffer().append("COMMENT ON TABLE ").append(
                quoteTableName(schemaNameOpt, tableName))
                .append(" IS '").append(comment.getValue()).append("'").toString();
	}

	@Override
	public String createSequenceSql(String seqName, SequenceOption[] options) {
		StringBuffer sql = new StringBuffer();
        sql.append("CREATE SEQUENCE")
                .append(quoteTableName(seqName));
        for (int i = 0; i < options.length; i++) {
            SequenceOption option = options[i];
            if (option instanceof Start) {
            	sql.append(" START WITH ").append(0 == option.getValue() ? 1 : option.getValue());
            } else if (option instanceof Increment) {
            	sql.append(" INCREMENT BY ").append(0 == option.getValue() ? 1 : option.getValue());
            } else if (option instanceof MinValue){
            	sql.append(" MINVALUE ").append(0 == option.getValue() ? 1 : option.getValue());
            } else if (option instanceof MaxValue){
            	sql.append(" MAXVALUE ").append(0 == option.getValue() ? "999999999999999999999999999" : option.getValue());
            }
        }
        return sql.toString();
	}
}

@ColumnSupportsDefault
class OracleDateColumnDefinition
        extends ColumnDefinition {
    protected String sql() {
        return "DATE";
    }
}

@ColumnSupportsDefault
class OracleLongColumnDefinition
        extends ColumnDefinition {
    protected String sql() {
        return "LONG";
    }
}

@ColumnSupportsLimit
@ColumnSupportsDefault
class OracleVarcharColumnDefinition extends ColumnDefinition {
    @Override
    protected String sql() {
        //由于oracle中varchar类型必须指定长度，因此在limit为空时默认指定长度50
        if (getLimitValue() == null) {
            setLimitValue("50");
        }
        return optionallyAddLimitToDataType("VARCHAR2");
    }
}

@ColumnSupportsLimit
@ColumnSupportsDefault
class OracleClobColumnDefinition extends ColumnDefinition {
    @Override
    protected String sql() {
        return optionallyAddLimitToDataType("CLOB");
    }
}

@ColumnSupportsScale
@ColumnSupportsPrecision
@ColumnSupportsDefault
class OracleIntegerColumnDefinition extends ColumnDefinition {
    @Override
    protected String sql() {
        if (getPrecision().isDefined() && getScale().isDefined()) { //定义了数字类型的精度和刻度
            return optionallyAddLimitToDataType("NUMBER", getPrecision().get() + "," + getScale().get());
        } else if (getPrecision().isDefined()) { //只定义了数字类型的精度和刻度
            return optionallyAddLimitToDataType("NUMBER", getPrecision().get() + "");
        } else {
            return optionallyAddLimitToDataType("NUMBER");
        }
    }
}
