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
package lichen.ar.services;

import lichen.ar.model.Field;

import java.util.Map;

/**
 * 持久化的帮助类
 * @author jcai
 */
public interface PersisterHelper {
    /**
     * 通过查找数据库表，自动获得表的映射
     * @param tableName 表名
     * @return key为转换为字段名称，Field为对应属性定义值
     */
    Map<String,Field<?>> findTableFields(String tableName);
}
