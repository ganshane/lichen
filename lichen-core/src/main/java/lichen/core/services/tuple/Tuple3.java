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
package lichen.core.services.tuple;

/**
 * 包装三个值对象.
 *
 * @author jcai
 */
public final class Tuple3<T1, T2, T3> {
    private T1 _value1;
    private T2 _value2;
    private T3 _value3;

    public Tuple3(final T1 value1, final T2 value2, final T3 value3) {
        this._value1 = value1;
        this._value2 = value2;
        this._value3 = value3;
    }

    public T1 _1() {
        return _value1;
    }

    public T2 _2() {
        return _value2;
    }

    public T3 _3() {
        return _value3;
    }
}
