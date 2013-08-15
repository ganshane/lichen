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
package lichen.migration.services;

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


/**
 * @author jcai
 */
public interface Options {
    /**
     * 构造默认值对象.
     * @param value 默认值
     * @return 默认值对象
     */
    Default Default(String value);

    /**
     * 定义列的长度.
     * @param length 列的长度
     * @return 长度定义实例
     */
    Limit Limit(int length);

    /**
     * 制定数字列的精度.
     * @param precision 精度
     * @return 列的精度
     */
    Precision Precision(int precision);

    /**
     * 定义列的刻度.
     * @param scale 列的刻度
     * @return 列的刻度
     */
    Scale Scale(int scale);

    /**
     * 非空.
     * @return 非空的设置
     */
    NotNull NotNull();

    /**
     * 可为空.
     * @return 可为空
     */
    Nullable Nullable();

    /**
     * 主键.
     * @return 主键
     */
    PrimaryKey PrimaryKey();

    /**
     * 唯一.
     * @return 唯一对象
     */
    Unique Unique();

    /**
     * 自增长列.
     * @return 自增长列
     */
    AutoIncrement AutoIncrement();

    /**
     * 针对索引或者外键的命名.
     * @param name 名称
     * @return Name配置
     */
    Name Name(String name);
}
