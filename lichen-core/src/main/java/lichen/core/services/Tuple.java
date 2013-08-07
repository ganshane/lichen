package lichen.core.services;

/**
 * 用来封装一些数据的操作，便于在返回对象的时候能够返回多个值
 * @author jcai
 */
public interface Tuple {
    public static class Tuple1<T1>{
        private T1 value1;
        public Tuple1(T1 value1){
            this.value1 = value1;
        }
        public T1 _1(){
            return value1;
        }
    }
    public static class Tuple2<T1,T2>{
        private T1 value1;
        private T2 value2;
        public Tuple2(T1 value1,T2 value2){
            this.value1 = value1;
            this.value2 = value2;
        }
        public T1 _1(){
            return value1;
        }
        public T2 _2(){
            return value2;
        }
    }
    public static class Tuple3<T1,T2,T3>{
        private T1 value1;
        private T2 value2;
        private T3 value3;
        public Tuple3(T1 value1,T2 value2,T3 value3){
            this.value1 = value1;
            this.value2 = value2;
            this.value3 = value3;
        }
        public T1 _1(){
            return value1;
        }
        public T2 _2(){
            return value2;
        }
        public T3 _3(){
            return value3;
        }
    }
    public static class Tuple4<T1,T2,T3,T4>{
        private T1 value1;
        private T2 value2;
        private T3 value3;
        private T4 value4;
        public Tuple4(T1 value1,T2 value2,T3 value3,T4 value4){
            this.value1 = value1;
            this.value2 = value2;
            this.value3 = value3;
            this.value4 = value4;
        }
        public T1 _1(){
            return value1;
        }
        public T2 _2(){
            return value2;
        }
        public T3 _3(){
            return value3;
        }
        public T4 _4(){
            return value4;
        }
    }
    public static class Tuple5<T1,T2,T3,T4,T5>{
        private T1 value1;
        private T2 value2;
        private T3 value3;
        private T4 value4;
        private T5 value5;
        public Tuple5(T1 value1,T2 value2,T3 value3,T4 value4,T5 value5){
            this.value1 = value1;
            this.value2 = value2;
            this.value3 = value3;
            this.value4 = value4;
            this.value5 = value5;
        }
        public T1 _1(){
            return value1;
        }
        public T2 _2(){
            return value2;
        }
        public T3 _3(){
            return value3;
        }
        public T4 _4(){
            return value4;
        }
        public T5 _5(){
            return value5;
        }
    }
}
