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

import lichen.migration.model.AutoIncrement;
import lichen.migration.model.Default;
import lichen.migration.model.Limit;
import lichen.migration.model.Name;
import lichen.migration.model.NotNull;
import lichen.migration.model.Nullable;
import lichen.migration.model.Precision;
import lichen.migration.model.PrimaryKey;
import lichen.migration.model.Scale;
import lichen.migration.model.Unique;
import lichen.migration.services.Options;

/**
 * 定义所有的接口.
 *
 * @author jcai
 */
public class OptionsImpl implements Options {
    @Override
    public Default Default(final String value) {
        return new Default() {
            public String getValue() {
                return value;
            }
        };
    }

    @Override
    public Limit Limit(final int length) {
        return new Limit() {
            public int getValue() {
                return length;
            }
        };
    }

    /**
     * 指定列不能为空.
     */
    private static final NotNull NOT_NULL = new NotNull() {
    };
    /**
     * 指定列可以为空.
     */
    private static final Nullable NULLABLE = new Nullable() {
    };

    @Override
    public Precision Precision(final int precision) {
        return new Precision() {
            public int getValue() {
                return precision;
            }
        };
    }

    @Override
    public Scale Scale(final int scale) {
        return new Scale() {
            public int getValue() {
                return scale;
            }
        };
    }

    @Override
    public NotNull NotNull() {
        return NOT_NULL;
    }

    @Override
    public Nullable Nullable() {
        return NULLABLE;
    }

    @Override
    public PrimaryKey PrimaryKey() {
        return PRIMARY_KEY;
    }

    @Override
    public Unique Unique() {
        return UNIQUE;
    }

    @Override
    public AutoIncrement AutoIncrement() {
        return AUTO_INCREMENT;
    }

    @Override
    public Name Name(final String name) {
        return new Name() {

            @Override
            public String getValue() {
                return name;
            }
        };
    }

    /**
     * 定义主键列.
     */
    private static final PrimaryKey PRIMARY_KEY = new PrimaryKey() {
    };
    /**
     * 定义列或者键的唯一性. *
     */
    private static final Unique UNIQUE = new Unique() {
    };
    /**
     * 定义某一列为自增长字段. *
     */
    private static final AutoIncrement AUTO_INCREMENT = new AutoIncrement() {
    };
}
