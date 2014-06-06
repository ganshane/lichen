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
import lichen.migration.model.SequenceDefinition;
import lichen.migration.model.SqlType;

/**
 * @author jcai
 */
@ColumnSupportsDefault
class H2TimestampColumnDefinition extends ColumnDefinition {
    // H2 does not take a limit specifier for TIMESTAMP types.
    @Override
    protected String sql() {
        return "TIMESTAMP";
    }
}

@ColumnSupportsLimit
class H2VarbinaryColumnDefinition extends ColumnDefinition {
    @Override
    protected String sql() {
        return "CLOB";
    }
}

class H2DatabaseAdapter extends DatabaseAdapter {
    public H2DatabaseAdapter(Option<String> schemaNameOpt) {
    	//H2数据库schema应设置为空
        super(Option.<String>none());
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
            case BooleanType:
                return new DefaultBooleanColumnDefinition();
            case CharType:
                return new DefaultCharColumnDefinition();
            case DecimalType:
                return new DefaultDecimalColumnDefinition();
            case IntegerType:
                return new DefaultIntegerColumnDefinition();
            case TimestampType:
                return new H2TimestampColumnDefinition();
            case SmallintType:
                return new DefaultSmallintColumnDefinition();
            case VarbinaryType:
                return new H2VarbinaryColumnDefinition();
            case VarcharType:
                return new DefaultVarcharColumnDefinition();
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
	public String createSequenceSql(SequenceDefinition seqDefinition) {
		return "";
	}
}
