package lichen.core.services.tuple;

/**
 * 包装一个值对象.
* @author jcai
*/
public class Tuple1<T1> {
    private T1 _value1;

    public Tuple1(final T1 value1) {
        this._value1 = value1;
    }

    public final T1 _1() {
        return _value1;
    }
}
