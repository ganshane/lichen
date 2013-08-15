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
 * 针对返回的值对象，可能有可能没有的值进行操作.
 * <p>
 *     整个框架中尽量减少null值的出现，尽量采用Option进行替代.<br/>
 *     譬如：<br/>
 *     <pre>
 *      public A findByName(String name);
 *     </pre>
 *     这种函数有可能返回null，有可能返回真正的值,
 *     为了减少系统出现{@link java.lang.NullPointerException}的现象,方法声明为：
 *     <pre>
 *      public Option&lt;A&gt; findByName(String name);
 *     </pre>
 *     <br/>
 *     当查询到值的时候，采用 Option.some(value)返回，如果没有值则采用Option.none()进行返回.
 *     客户端通过下面代码进行取值
 *     <pre>
 *         Option&lt;A&gt; objOpt = findByName("name");
 *         if(objOpt.isDefined()){
 *             //DO something
 *             A obj = objOpt.get();
 *         }else{
 *
 *         }
 *     </pre>
 *     如果直接调用 objOpt.get()，将抛出LichenException(OPTIONS_IS_NONE)的异常消息,将通过堆栈定位消息.
 * </p>
 *
 * @author jcai
 */
public abstract class Option<T> {
    /**
     * 是否有值.
     *
     * @return 有值则返回true，反之返回false
     */
    public abstract boolean isDefined();

    /**
     * 得到值.
     *
     * @return 得到值
     */
    public abstract T get();

    /**
     * 空值.
     *
     * @param <T> 值类型
     * @return 空值对象
     */
    @SuppressWarnings("unchecked")
    public static <T> Option<T> none() {
        return (Option<T>) NONE;
    }

    /**
     * 含有的值对象.
     *
     * @param value 值数据
     * @param <T>   值类型
     * @return 值对象
     */
    public static <T> Option<T> some(final T value) {
        return new Some<T>(value);
    }

    private static final Option<Object> NONE = new Option<Object>() {
        public boolean isDefined() {
            return false;
        }
        public Object get() {
            return new LichenException(LichenCoreErrorCode.OPTION_IS_NONE);
        }
    };

    private static class Some<T> extends Option<T> {
        private T _value;

        public Some(T value) {
            this._value = value;
        }

        public boolean isDefined() {
            return true;
        }

        public T get() {
            return _value;
        }
    }
}

