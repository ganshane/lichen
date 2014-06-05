package creeper.core.internal;

import javax.servlet.ServletException;

import org.apache.tapestry5.TapestryFilter;
import org.apache.tapestry5.ioc.Registry;

/**
 * 继承tapestry默认filter，可实现一些自定义操作。
 * @author shen
 *
 */
public class CreeperFilter extends TapestryFilter {
	
	@Override
	protected void init(Registry registry) throws ServletException {
		super.init(registry);
	}
	
	
}
