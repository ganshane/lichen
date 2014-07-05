package lichen.creeper.core.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;

/**
 * 
 * @author shen
 *
 */
public class CreeperForm {
	
	@SuppressWarnings("unused")
	@Property
	@Parameter(required=true, defaultPrefix = BindingConstants.LITERAL)
	private String title;
	
	@Component(id="creeperForm")    
	private Form form;
	
	public Form getInnerForm(){
		return this.form;
	}
	
}
