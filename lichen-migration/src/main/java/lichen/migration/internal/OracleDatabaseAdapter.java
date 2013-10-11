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

import lichen.migration.model.SqlType;

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
                return new DefaultBigintColumnDefinition();
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
}

@ColumnSupportsLimit
@ColumnSupportsDefault
class OracleVarcharColumnDefinition extends ColumnDefinition {
    @Override
    protected String sql() {
    	return optionallyAddLimitToDataType("VARCHAR");
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
		return optionallyAddLimitToDataType("NUMBER",getPrecision().get()+","+getScale().get());
	}
}
