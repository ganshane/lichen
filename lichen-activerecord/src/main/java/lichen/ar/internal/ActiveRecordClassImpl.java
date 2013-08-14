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

import lichen.ar.model.Field;
import lichen.ar.services.ActiveRecordErrorCode;
import lichen.ar.services.PersisterHelper;
import lichen.core.services.LichenException;
import lichen.core.services.WordUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 抽象class的实现类.
 * active record的基础实现类
 * @author jcai
 * @param <PKValue> 对应的主键值
 */
public abstract class ActiveRecordClassImpl<PKValue> {
    /** 主键字段名称. **/
    private String _pkFieldName = "id";
    /** 对应的表名. **/
    private String _tableName;
    /** 对应数据库的列名,自动初始化. **/
    private Set<String> _columns;
    /** 保存实体对应的值. **/
    private Map<String, Object> _values = new HashMap<String, Object>();
    /** 修改后的列的集合. **/
    private Set<String> _columnsModified = new HashSet<String>();

    private Map<String, Field<?>> _fields;
    /**
     * @param persisterHelper persisterHelper
     */
    protected ActiveRecordClassImpl(PersisterHelper persisterHelper) {
        _tableName = WordUtil.tableize(getClass().getSimpleName());
        _fields = persisterHelper.findTableFields(_tableName);
    }

    /**
     * 设置数据.
     * @param fieldName 列名
     * @param fieldValue 列的值
     * @return 之前设置的值
     */
    public Object setData(String fieldName, Object fieldValue) {
        if (!_columns.contains(fieldName)) {
            throw new LichenException(ActiveRecordErrorCode.COLUMN_NOT_EXISTS);
        }
        //TODO 要不要判断旧的值和新值是否相等?
        _columnsModified.add(fieldName);
        return _values.put(fieldName, fieldValue);
    }

    public Map<String, Field<?>> getFields() {
        return _fields;
    }

    public String getPkFieldName() {
        return _pkFieldName;
    }
}
