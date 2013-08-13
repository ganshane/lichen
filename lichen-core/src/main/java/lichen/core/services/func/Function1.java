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
package lichen.core.services.func;

/**
 * 函数定义,只有一个参数的函数.
 *
 * @author jcai
 */
public interface Function1<A, R> {
    /**
     * 针对传入的结果进行处理,返回处理后的结果.
     *
     * @param value 值对象
     * @return 处理后的结果
     */
    R apply(A value);
}
