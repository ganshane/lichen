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
