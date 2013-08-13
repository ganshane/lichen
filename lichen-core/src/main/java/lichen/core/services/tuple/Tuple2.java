package lichen.core.services.tuple;

/**
 * 包装两个值对象.
* @author jcai
*/
public class Tuple2<T1, T2> {
    private T1 _value1;
    private T2 _value2;

    public Tuple2(final T1 value1, final T2 value2) {
        this._value1 = value1;
        this._value2 = value2;
    }

    public final T1 _1() {
        return _value1;
    }

    public final T2 _2() {
        return _value2;
    }
}
