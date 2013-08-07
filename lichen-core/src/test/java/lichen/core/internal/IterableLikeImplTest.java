package lichen.core.internal;

import junit.framework.Assert;
import lichen.core.services.Function;
import lichen.core.services.IterableLike;
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
        Iterator<Bean> newR = iterableLike.map(new Function.Function1<Bean, Bean>() {
            @Override
            public Bean apply(Bean value) {
                return new Bean(value.name + "_2", value.age + 10);
            }
        }).iterator();
        Bean bean1 = newR.next();
        Assert.assertEquals("acai_2",bean1.name);
        Assert.assertEquals(43,bean1.age);
    }
}
