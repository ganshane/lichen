package creeper.core.components;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;

/**
 * 输出IE条件
 * @author jcai
 */
public class IfIEJavascript {
    @Parameter(required=true, defaultPrefix= BindingConstants.LITERAL)
    private String test;
    @Parameter(required=true,defaultPrefix = BindingConstants.ASSET)
    private Asset js;

    public void beginRender(MarkupWriter writer) {
        writer.writeRaw("\n<!--[if ");
        writer.writeRaw(test);
        writer.writeRaw("]>\n");
        writer.element("script", "type", "text/javascript", "src", js.toClientURL());
        writer.end();
    }

    public void afterRender(MarkupWriter writer) {
        writer.writeRaw("\n<![endif]-->\n");
    }
}
