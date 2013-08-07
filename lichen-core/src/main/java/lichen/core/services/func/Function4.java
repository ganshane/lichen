package lichen.core.services.func;

/**
 * 函数定义,只有四个参数的函数.
 *
 * @author jcai
 */
public interface Function4<A, B, C, D, R> {
    /**
     * 针对传入的结果进行处理,返回处理后的结果.
     *
     * @param value1 值对象
     * @param value2 值对象
     * @param value3 值对象
     * @param value4 值对象
     * @return 处理后的结果
     */
    R apply(A value1, B value2, C value3, D value4);
}
