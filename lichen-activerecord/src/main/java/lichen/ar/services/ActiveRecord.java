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

/**
 * active record基础类.
 *
 * @author jcai
 */
public abstract class ActiveRecord {
    private String _tableName;
    private String _pkField = "id";

    public Long save() {
        return -1L;
    }

    public int delete() {
        return 1;
    }

    public void setPkField(String vpkField) {
        this._pkField = vpkField;
    }

    public String getPkField() {
        return _pkField;
    }

    public void setTableName(String vtableName) {
        this._tableName = vtableName;
    }

    public String getTableName() {
        return _tableName;
    }
}
