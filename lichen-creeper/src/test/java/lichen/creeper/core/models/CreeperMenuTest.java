package lichen.creeper.core.models;

import junit.framework.Assert;

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
	
}
