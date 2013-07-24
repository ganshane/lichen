// Copyright 2013 the original author or authors.
// site: http://lichen.ganshane.com
package lichen.migration.internal;

/**
 * 定义参数
 * @author jcai
 */
public abstract class Option<T> {
    private final static Option<Object> None = new Option<Object>() {
        public boolean isDefined() {
            return false;
        }

        public Object get() {
            return null;
        }
    };
    public static <T extends Object> Option<T> None(){
        return (Option<T>) None;
    }
    public static <T> Option<T> Some(T value){
        return new Some(value);
    }

    public abstract boolean isDefined();
    public abstract T get();
    private static class Some<T> extends Option<T>{
        private T value;
        public Some(T value){
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

