package lichen.core.services;

/**
 * 面向函数编程的函数对象.
 */
public interface Function {
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

    /**
     * 函数定义,只有一个参数的函数.
     *
     * @author jcai
     */
    interface Function1<A, R> {
        /**
         * 针对传入的结果进行处理,返回处理后的结果.
         *
         * @param value 值对象
         * @return 处理后的结果
         */
        R apply(A value);
    }

    /**
     * 函数定义,只有两个参数的函数.
     *
     * @author jcai
     */
    interface Function2<A, B, R> {
        /**
         * 针对传入的结果进行处理,返回处理后的结果.
         *
         * @param value1 值对象
         * @param value2 值对象
         * @return 处理后的结果
         */
        R apply(A value1, B value2);
    }

    /**
     * 函数定义,只有三个参数的函数.
     *
     * @author jcai
     */
    interface Function3<A, B, C, R> {
        /**
         * 针对传入的结果进行处理,返回处理后的结果.
         *
         * @param value1 值对象
         * @param value2 值对象
         * @param value3 值对象
         * @return 处理后的结果
         */
        R apply(A value1, B value2, C value3);
    }

    /**
     * 函数定义,只有四个参数的函数.
     *
     * @author jcai
     */
    interface Function4<A, B, C, D, R> {
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

    /**
     * 函数定义,只有五个参数的函数.
     *
     * @author jcai
     */
    interface Function5<A, B, C, D, E, R> {
        /**
         * 针对传入的结果进行处理,返回处理后的结果.
         *
         * @param value1 值对象
         * @param value2 值对象
         * @param value3 值对象
         * @param value4 值对象
         * @param value5 值对象
         * @return 处理后的结果
         */
        R apply(A value1, B value2, C value3, D value4, E value5);
    }
}
