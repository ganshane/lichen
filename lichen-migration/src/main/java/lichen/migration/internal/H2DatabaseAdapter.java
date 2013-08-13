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
 * @author jcai
 */
@ColumnSupportsDefault
class H2TimestampColumnDefinition
        extends ColumnDefinition{
    // H2 does not take a limit specifier for TIMESTAMP types.
    @Override
    protected String sql(){return "TIMESTAMP";}
}

@ColumnSupportsLimit
class H2VarbinaryColumnDefinition
        extends ColumnDefinition{
    @Override
    protected String sql(){return "CLOB";}
}

class H2DatabaseAdapter extends DatabaseAdapter{
    public H2DatabaseAdapter(Option<String> schemaNameOpt){
        super(schemaNameOpt);
        unquotedNameConverter = UnquotedNameConverter.UppercaseUnquotedNameConverter;
        addingForeignKeyConstraintCreatesIndex = true;
    }
    protected String alterColumnSql(Option<String> schema_name_opt,ColumnDefinition column_definition){
        return new java.lang.StringBuilder(512)
                .append("ALTER TABLE ")
                .append(quoteTableName(schema_name_opt, column_definition.tableName()))
                .append(" ALTER COLUMN ")
                .append(quoteColumnName(column_definition.columnName()))
                .append(" ")
                .append(column_definition.toSql())
                .toString();
    }

    @Override
    protected ColumnDefinition columnDefinitionFactory(
            SqlType column_type){
        switch (column_type){
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
        }
        throw new IllegalArgumentException("Not support type");
    }


    @Override
    public String removeIndexSql(Option<String> schema_name_opt,
                                 String table_name,
                                 String index_name)
    {
        return "DROP INDEX " +
                quoteColumnName(index_name);
    }

    @Override
    public String lockTableSql(Option<String> schemaNameOpt, String tableName) {
        return "SELECT * FROM " +
                quoteTableName(schemaNameOpt, tableName) +
                " FOR UPDATE";
    }
}
