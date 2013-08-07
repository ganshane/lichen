// Copyright 2013 the original author or authors.
// site: http://lichen.ganshane.com
package lichen.core.services;

/**
 * 针对返回的值对象，可能有可能没有的值进行操作
 *
 * @author jcai
 */
public abstract class Option<T> {
    /**
     * 是否有值
     *
     * @return 有值则返回true，反之返回false
     */
    public abstract boolean isDefined();

    /**
     * 得到值
     *
     * @return 得到值
     */
    public abstract T get();

    /**
     * 空值
     *
     * @param <T> 值类型
     * @return 空值对象
     */
    public static <T> Option<T> None() {
        return (Option<T>) None;
    }

    /**
     * 含有的值对象
     *
     * @param value 值数据
     * @param <T>   值类型
     * @return 值对象
     */
    public static <T> Option<T> Some(T value) {
        return new Some<T>(value);
    }

    private final static Option<Object> None = new Option<Object>() {
        public boolean isDefined() {
            return false;
        }

        public Object get() {
            return null;
        }
    };

    private static class Some<T> extends Option<T> {
        private T value;

        public Some(T value) {
            this.value = value;
        }

        public boolean isDefined() {
            return true;
        }

        public T get() {
            return value;
        }
    }
}

