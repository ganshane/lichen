package lichen.creeper.core.components;

import org.junit.Test;

public class PaginationTest {
	
	@Test
	public void testUpdateArray(){
		Pagination page = new Pagination();
		String[] src = new String[]{"1","2"};
		Object[] target = page.updateArray(src, "0");
		target[0].equals("0");
	}
	
}
