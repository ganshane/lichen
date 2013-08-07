package lichen.core.services.tuple;

/**
 * 包装两个值对象.
* @author jcai
*/
public class Tuple2<T1, T2> {
    private T1 value1;
    private T2 value2;

    public Tuple2(T1 value1, T2 value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public T1 _1() {
        return value1;
    }

    public T2 _2() {
        return value2;
    }
}
