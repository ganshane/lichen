package creeper.core.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * 
 * @author shen
 *
 */
public class CreeperForm {
	
	@Property
	@Parameter(required=true, defaultPrefix = BindingConstants.LITERAL)
	private String title;
	
}
