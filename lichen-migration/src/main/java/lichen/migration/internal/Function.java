package lichen.migration.internal;

/**
 * function
 * @author jcai
 */
interface Function<R>{
    public R apply() throws Throwable;
}
interface Function1<T,R> {
    public R apply(T parameter) throws Throwable;
}
