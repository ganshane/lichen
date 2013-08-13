package lichen.core.services.func;

/**
 * 函数定义,只有零个参数的函数.
 *
 * @author jcai
 */
interface Function0<R> {
    /**
     * 针对传入的结果进行处理,返回处理后的结果.
     *
     * @return 处理后的结果
     */
    R apply();
}
