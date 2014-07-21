package lichen.creeper.user.pages;

import org.apache.tapestry5.annotations.OnEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasePage {
	
	private static Logger logger = LoggerFactory.getLogger(BasePage.class);
	
	@OnEvent("activate") 
	void initParams(){
		logger.debug("============");
	}
	
}
