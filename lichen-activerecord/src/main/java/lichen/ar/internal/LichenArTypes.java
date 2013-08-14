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
package lichen.ar.internal;

import lichen.ar.internal.types.BigIntegerType;
import lichen.ar.internal.types.BinaryType;
import lichen.ar.internal.types.BitType;
import lichen.ar.internal.types.BlobType;
import lichen.ar.internal.types.CharType;
import lichen.ar.internal.types.ClobType;
import lichen.ar.internal.types.DateType;
import lichen.ar.internal.types.DecimalType;
import lichen.ar.internal.types.DoubleType;
import lichen.ar.internal.types.FloatType;
import lichen.ar.internal.types.IntegerType;
import lichen.ar.internal.types.NumericType;
import lichen.ar.internal.types.SmallIntType;
import lichen.ar.internal.types.TimeStampType;
import lichen.ar.internal.types.TimeType;
import lichen.ar.internal.types.TinyIntType;
import lichen.ar.internal.types.VarbinaryType;
import lichen.ar.internal.types.VarcharType;

/**
 * 默认定义的types.
 * @author jcai
 */
public final class LichenArTypes {
    private LichenArTypes() {

    }
    /** 处理 {@link java.sql.Types#BIGINT} 数据对象. **/
    static final BigIntegerType BIG_INTEGER = new BigIntegerType();
    /** 处理 {@link java.sql.Types#BINARY} 数据对象 .**/
    static final BinaryType BINARY = new BinaryType();
    /** 处理 {@link java.sql.Types#BIT} 数据对象. **/
    static final BitType BIT = new BitType();
    /** 处理 {@link java.sql.Types#CHAR} 数据对象. **/
    static final CharType CHAR = new CharType();
    /** 处理 {@link java.sql.Types#DATE} 数据对象. **/
    static final DateType DATE = new DateType();
    /** 处理 {@link java.sql.Types#DOUBLE} 数据对象. **/
    static final DoubleType DOUBLE = new DoubleType();
    /** 处理 {@link java.sql.Types#FLOAT} 数据对象. **/
    static final FloatType FLOAT = new FloatType();
    /** 处理 {@link java.sql.Types#INTEGER} 数据对象. **/
    static final IntegerType INTEGER = new IntegerType();
    /** 处理 {@link java.sql.Types#SMALLINT} 数据对象. **/
    static final SmallIntType SMALLINT = new SmallIntType();
    /** 处理 {@link java.sql.Types#TINYINT} 数据对象. **/
    static final TinyIntType TINYINT = new TinyIntType();
    /** 处理 {@link java.sql.Types#TIME} 数据对象. **/
    static final TimeType TIME = new TimeType();
    /** 处理 {@link java.sql.Types#TIMESTAMP} 数据对象. **/
    static final TimeStampType TIMESTAMP = new TimeStampType();
    /** 处理 {@link java.sql.Types#VARCHAR} 数据对象. **/
    static final VarcharType VARCHAR = new VarcharType();
    /** 处理 {@link java.sql.Types#VARBINARY} 数据对象. **/
    static final VarbinaryType VARBINARY = new VarbinaryType();
    /** 处理 {@link java.sql.Types#NUMERIC} 数据对象. **/
    static final NumericType NUMERIC = new NumericType();
    /** 处理 {@link java.sql.Types#DECIMAL} 数据对象. **/
    static final DecimalType DECIMAL = new DecimalType();
    /** 处理 {@link java.sql.Types#BLOB} 数据对象. **/
    static final BlobType BLOB = new BlobType();
    /** 处理 {@link java.sql.Types#CLOB} 数据对象. **/
    static final ClobType CLOB = new ClobType();
}
