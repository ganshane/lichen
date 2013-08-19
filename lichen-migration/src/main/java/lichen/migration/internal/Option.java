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
package lichen.migration.internal;

/**
 * 定义参数.
 *
 * @author jcai
 */
public abstract class Option<T> {
    private static final Option<Object> NONE = new Option<Object>() {
        public boolean isDefined() {
            return false;
        }

        public Object get() {
            return null;
        }
    };

    public static <T extends Object> Option<T> none() {
        return (Option<T>) NONE;
    }

    public static <T> Option<T> some(T value) {
        return new Some(value);
    }

    public abstract boolean isDefined();

    public abstract T get();

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

