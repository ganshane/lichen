package lichen.core.internal;

import junit.framework.Assert;
import lichen.core.services.Function;
import lichen.core.services.IterableLike;
import lichen.core.services.Tuple;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 针对集合操作的测试
 *
 * @author jcai
 */
public class IterableLikeImplTest {
    private class Bean {
        String name;
        int age;
        Bean(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    @Test
    public void test_map() {
        List<Bean> list = new ArrayList<Bean>();
        list.add(new Bean("acai", 33));
        list.add(new Bean("jcai", 32));
        IterableLikeImpl<Bean> iterableLike = new IterableLikeImpl<Bean>(list);
        Iterator<Tuple.Tuple2<String,Integer>> newR = iterableLike.map(new Function.Function1<Bean, Tuple.Tuple2<String,Integer>>() {
            @Override
            public Tuple.Tuple2<String,Integer> apply(Bean value) {
                return new Tuple.Tuple2<String, Integer>(value.name + "_2", value.age + 10);
            }
        }).iterator();
        Tuple.Tuple2<String, Integer> bean1 = newR.next();
        Assert.assertEquals("acai_2",bean1._1());
        Assert.assertEquals(43,bean1._2().intValue());
    }
    @Test
    public void test_foreach() {
        List<Bean> list = new ArrayList<Bean>();
        list.add(new Bean("acai", 33));
        list.add(new Bean("jcai", 32));
        IterableLikeImpl<Bean> iterableLike = new IterableLikeImpl<Bean>(list);
        iterableLike.foreach(new Function.Function1<Bean, Void>() {
            @Override
            public Void apply(Bean value) {
                value.age += 10;
                return null;
            }
        });
        Assert.assertEquals(43,list.get(0).age);
        Assert.assertEquals(42,list.get(1).age);
    }
}
