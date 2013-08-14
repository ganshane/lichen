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
package lichen.core.services;

/**
 * lichen core module exception code.
 *
 * @author jcai
 */
public enum LichenCoreErrorCode implements ErrorCode {
    /** 1001 **/
    UNSUPPORT_REMOVE_ITERATOR(1001);

    private final int _number;

    private LichenCoreErrorCode(final int number) {
        this._number = number;
    }

    public int getNumber() {
        return _number;
    }
}
