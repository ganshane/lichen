package lichen.core.services.tuple;

/**
 * 包装五个值对象
* @author jcai
*/
public class Tuple5<T1, T2, T3, T4, T5> {
    private T1 value1;
    private T2 value2;
    private T3 value3;
    private T4 value4;
    private T5 value5;

    public Tuple5(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
        this.value5 = value5;
    }

    public T1 _1() {
        return value1;
    }

    public T2 _2() {
        return value2;
    }

    public T3 _3() {
        return value3;
    }

    public T4 _4() {
        return value4;
    }

    public T5 _5() {
        return value5;
    }
}
