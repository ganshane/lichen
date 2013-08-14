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
package lichen.jdbc.services;

import lichen.core.services.ErrorCode;

/**
 * jdbc module error code definition.
 * @author jcai
 */
public enum JdbcErrorCode implements ErrorCode {
    /** 3001 数据访问错误 **/
    DATA_ACCESS_ERROR(3001),
    /** 3002 驱动未找到 **/
    DRIVER_NOT_FOUND(3002),
    /** 3003 当前线程中未发现事务 **/
    NO_TRANSACTION_IN_CURRENT_THREAD(3003);

    private int errorCode;
    private JdbcErrorCode(final int code) {
        this.errorCode = code;
    }

    @Override
    public int getNumber() {
        return this.errorCode;
    }
}
