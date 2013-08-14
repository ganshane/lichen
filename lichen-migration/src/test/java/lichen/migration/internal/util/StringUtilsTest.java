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
package lichen.migration.internal.util;

import junit.framework.Assert;

import org.junit.Test;

/**
 * StringUtils的单元测试类
 * @author zhaoyong
 *
 */
public class StringUtilsTest {
	
	/**
	 * 方法：join(String[] array, String sperator)的单元测试用例
	 */
	@Test
	public void test_join() {
		String[] array = new String[]{"a", "b", "c"};
		Assert.assertEquals("a_b_c", StringUtils.join(array, "_"));	//将数组用下划线隔开
		Assert.assertEquals("a,b,c", StringUtils.join(array, ","));	//将数组用逗号隔开
		Assert.assertEquals("abc", StringUtils.join(array, null));	//当分隔符为空时，默认分隔符为空串
		Assert.assertEquals("", StringUtils.join(null, null));	//当数组为空时，直接返回空串
	}
}
