package lichen.core.services.tuple;

/**
 * 包装三个值对象.
* @author jcai
*/
public final class Tuple3<T1, T2, T3> {
    private T1 _value1;
    private T2 _value2;
    private T3 _value3;

    public Tuple3(final T1 value1, final T2 value2, final T3 value3) {
        this._value1 = value1;
        this._value2 = value2;
        this._value3 = value3;
    }

    public T1 _1() {
        return _value1;
    }

    public T2 _2() {
        return _value2;
    }

    public T3 _3() {
        return _value3;
    }
}
