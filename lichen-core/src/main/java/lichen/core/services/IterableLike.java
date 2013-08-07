package lichen.core.services;


/**
 * 针对能够遍历操作的集合
 * @param <A> 集合中元素的类型
 * @author jcai
 */
public interface IterableLike<A> extends Iterable<A>{
    /**
     * 针对集合中的每个元素执行一个操作，然后返回执行后的结果
     * @param function 待执行的函数
     * @param <B> 处理完返回的结果
     * @return 新的迭代器
     */
    public <B> IterableLike<B> map(Function.Function1<A,B> function);

    /**
     * 遍历集合中的元素，执行一个方法
     * @param function 待执行的方法
     */
    public void foreach(Function.Function1<A,Void> function);
}
