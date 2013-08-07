package lichen.core.services.tuple;

/**
 * 包装三个值对象.
* @author jcai
*/
public class Tuple3<T1, T2, T3> {
    private T1 value1;
    private T2 value2;
    private T3 value3;

    public Tuple3(T1 value1, T2 value2, T3 value3) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
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
}
