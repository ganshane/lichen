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
