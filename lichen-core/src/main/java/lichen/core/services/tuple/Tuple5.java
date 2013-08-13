package lichen.core.services.tuple;

/**
 * 包装五个值对象.
* @author jcai
*/
public final class Tuple5<T1, T2, T3, T4, T5> {
    private T1 _value1;
    private T2 _value2;
    private T3 _value3;
    private T4 _value4;
    private T5 _value5;

    public Tuple5(T1 value1, T2 value2, T3 value3, T4 value4, T5 value5) {
        this._value1 = value1;
        this._value2 = value2;
        this._value3 = value3;
        this._value4 = value4;
        this._value5 = value5;
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

    public T4 _4() {
        return _value4;
    }

    public T5 _5() {
        return _value5;
    }
}
