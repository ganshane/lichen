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


import lichen.jdbc.services.JdbcHelper;

import java.io.Serializable;

/**
 * 主键的生成器.
 * @author jcai
 */
public interface PkGenerator {
    /**
     * 生成一个ID.
     * @param jdbcHelper jdbc helper class
     * @param entity 实体对象
     * @param <T> 操作实体的类型
     * @return 主键值
     */
    <T extends ActiveRecord> Serializable generate(JdbcHelper jdbcHelper,
            T entity);
}
