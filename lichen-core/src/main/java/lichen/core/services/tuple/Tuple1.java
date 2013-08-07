package lichen.core.services.tuple;

/**
 * 包装一个值对象.
* @author jcai
*/
public class Tuple1<T1> {
    private T1 value1;

    public Tuple1(T1 value1) {
        this.value1 = value1;
    }

    public T1 _1() {
        return value1;
    }
}
