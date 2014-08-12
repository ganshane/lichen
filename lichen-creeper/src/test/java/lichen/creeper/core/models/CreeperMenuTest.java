package lichen.creeper.core.models;

import junit.framework.Assert;

import org.apache.tapestry5.json.JSONObject;
import org.junit.Test;

public class CreeperMenuTest {
	
	@Test
	public void testMenu(){
		CreeperMenu cm = CreeperMenu.createCreeperMenu("单位", "/unit");
		cm.order(4);
		Assert.assertEquals(4,cm.getOrder());
		cm.order(123).title("asdf");
		Assert.assertEquals(123,cm.getOrder());
		Assert.assertEquals("asdf",cm.getTitle());
	}
	
	@Test
	public void test2(){
		String jsons = "{users:[{name:\"Dr. No\", occupation:\"Super villain\", age:52, email:\"noknows@gmail.com\"},{name:\"Henrietta Smith\"},{name:\"Lev Nikolayevich Myshkin\", occupation:\"Idiot\"}]}";
		String json = "{\"哈哈\" : \"haha\",\"啊啊\" : \"aaaa\"}";
		JSONObject obj2 = new JSONObject(json);
		System.out.println(obj2.toCompactString());
		System.out.println(obj2);
	}
	
}
