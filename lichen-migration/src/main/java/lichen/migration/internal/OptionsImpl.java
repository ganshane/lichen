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

import lichen.migration.model.*;
import lichen.migration.services.Options;

/**
 * 定义所有的接口
 * @author jcai
 */
public class OptionsImpl implements Options {
    @Override
    public Default Default(final String value){
        return new Default(){
            public String getValue() {
                return value;
            }
        };
    }

    @Override
    public Limit Limit(final int length){
        return new Limit() {
            public int getValue() {
                return length;
            }
        };
    }
    /**
     * 指定列不能为空
     */
    private final static NotNull NotNull = new NotNull() {};
    /**
     * 指定列可以为空
     */
    private final static Nullable Nullable= new Nullable() {};

    @Override
    public Precision Precision(final int precision){
        return new Precision() {
            public int getValue() {
                return precision;
            }
        };
    }

    @Override
    public Scale Scale(final int scale){
        return new Scale() {
            public int getValue() {
                return scale;
            }
        };
    }

    @Override
    public NotNull NotNull() {
        return NotNull;
    }

    @Override
    public Nullable Nullable() {
        return Nullable;
    }

    @Override
    public PrimaryKey PrimaryKey() {
        return PrimaryKey;
    }

    @Override
    public Unique Unique() {
        return Unique;
    }

    @Override
    public AutoIncrement AutoIncrement() {
        return AutoIncrement;
    }

    @Override
    public Name Name(final String name) {
        return new Name(){

            @Override
            public String getValue() {
                return name;
            }
        };
    }

    /**
     * 定义主键列
     */
    private final static PrimaryKey PrimaryKey = new PrimaryKey() {};
    /** 定义列或者键的唯一性 **/
    private final static Unique Unique = new Unique() {};
    /** 定义某一列为自增长字段 **/
    private final static AutoIncrement AutoIncrement = new AutoIncrement() {};
}
