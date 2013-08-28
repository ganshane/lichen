// Copyright 2013 the original author or authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package lichen.core.internal;

import junit.framework.Assert;
import lichen.core.services.Option;
import lichen.core.services.func.Function1;
import lichen.core.services.tuple.Tuple2;
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
        Iterator<Tuple2<String,Integer>> newR = iterableLike.map(new Function1<Bean, Tuple2<String,Integer>>() {
            @Override
            public Tuple2<String,Integer> apply(Bean value) {
                return new Tuple2<String, Integer>(value.name + "_2", value.age + 10);
            }
        }).iterator();
        Tuple2<String, Integer> bean1 = newR.next();
        Assert.assertEquals("acai_2",bean1._1());
        Assert.assertEquals(43,bean1._2().intValue());
    }
    @Test
    public void test_foreach() {
        List<Bean> list = new ArrayList<Bean>();
        list.add(new Bean("acai", 33));
        list.add(new Bean("jcai", 32));
        IterableLikeImpl<Bean> iterableLike = new IterableLikeImpl<Bean>(list);
        iterableLike.foreach(new Function1<Bean, Void>() {
            @Override
            public Void apply(Bean value) {
                value.age += 10;
                return null;
            }
        });
        Assert.assertEquals(43, list.get(0).age);
        Assert.assertEquals(42,list.get(1).age);
    }
    @Test
    public void test_first() {
        List<Bean> list = new ArrayList<Bean>();
        list.add(new Bean("acai", 33));
        list.add(new Bean("jcai", 32));
        IterableLikeImpl<Bean> iterableLike = new IterableLikeImpl<Bean>(list);
        Option<Bean> first = iterableLike.first(new Function1<Bean, Boolean>() {
            @Override
            public Boolean apply(Bean value) {
                return value.name.equals("jcai");
            }
        });
        Assert.assertTrue(first.isDefined());
        Assert.assertEquals(32,first.get().age);
    }
    @Test
	public void test_exists() {
		List<Bean> list = new ArrayList<Bean>();
		list.add(new Bean("acai", 33));
		list.add(new Bean("jcai", 32));
		IterableLikeImpl<Bean> iterableLike = new IterableLikeImpl<Bean>(list);
		boolean obj1 = iterableLike.exists(new Function1<Bean, Boolean>() {
			@Override
			public Boolean apply(Bean value) {
				return value.age > 30;
			}
		});
		boolean obj2 = iterableLike.exists(new Function1<Bean, Boolean>() {
			@Override
			public Boolean apply(Bean value) {
				return value.age < 30;
			}
		});
		Assert.assertTrue(obj1);
		Assert.assertFalse(obj2);
	}
    
	@Test
	public void test_filter() {
		List<Bean> list = new ArrayList<Bean>();
		list.add(new Bean("sc", 28));
		list.add(new Bean("scl", 29));
		list.add(new Bean("sucl", 30));
		list.add(new Bean("suncl", 31));
		list.add(new Bean("sunchl", 32));
		IterableLikeImpl<Bean> iterableLike = new IterableLikeImpl<Bean>(list);
		Iterator<Bean> newR = iterableLike.filter(
				new Function1<Bean, Boolean>() {
					@Override
					public Boolean apply(Bean value) {
						return value.age >= 29;
					}
				}).iterator();

		while (newR.hasNext()) {
			Bean obj = newR.next();
			if (obj != null) {
				System.out.println(obj.name);
			}
		}
	}
}
