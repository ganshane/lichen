package lichen.core.services;


import lichen.core.services.func.Function1;

/**
 * 针对能够遍历操作的集合.
 *
 * @param <A> 集合中元素的类型
 * @author jcai
 */
public interface IterableLike<A> extends Iterable<A> {
    /**
     * 针对集合中的每个元素执行一个操作，然后返回执行后的结果.
     *
     * @param function 待执行的函数
     * @param <B>      处理完返回的结果
     * @return 新的迭代器
     */
     <B> IterableLike<B> map(Function1<A, B> function);

    /**
     * 遍历集合中的元素，执行一个方法.
     *
     * @param function 待执行的方法
     */
     void foreach(Function1<A, Void> function);

    /**
     * 通过给定的函数查找符合函数要求的第一个元素.
     *
     * @param function 符合条件的函数
     * @return 第一个符合条件的元素
     */
     Option<A> first(Function1<A, Boolean> function);
     
     /**
      * 查找一个集合中是否存在符合某一条件的函数
      * 
      * @param function 符合某一条件的函数
      * @return boolean
      */
     public boolean exists(Function1<A,Boolean> function);
}
