package lichen.core.services.func;

/**
 * 函数定义,只有两个参数的函数.
 *
 * @author jcai
 */
public interface Function2<A, B, R> {
    /**
     * 针对传入的结果进行处理,返回处理后的结果.
     *
     * @param value1 值对象
     * @param value2 值对象
     * @return 处理后的结果
     */
    R apply(A value1, B value2);
}
