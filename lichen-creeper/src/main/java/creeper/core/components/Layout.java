package creeper.core.components;

import creeper.core.models.CreeperMenu;
import creeper.core.services.MenuSource;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.util.List;

@Import(stylesheet = {
        "classpath:creeper/core/assets/bootstrap/css/bootstrap3.1.1.min.css",
        "classpath:creeper/core/components/Layout.css"})
public class Layout {
    /** The page title, for the <title> element and the <h1> element. */
    @SuppressWarnings("unused")
	@Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String title;
    @Inject
    private MenuSource menuSource;
    @SuppressWarnings("unused")
	@Inject
    @Property
    private Subject subject;
    @SuppressWarnings("unused")
	@Environmental
    private JavaScriptSupport javaScriptSupport;

    @SuppressWarnings("unused")
	@Property
    @Inject @Path("classpath:creeper/core/assets/html5shiv.js")
    private Asset html5shiv;
    @SuppressWarnings("unused")
	@Property
    @Inject @Path("classpath:creeper/core/assets/respond.min.js")
    private Asset respond;

    @Cached
    public String getMenu(){
        CreeperMenu menu = menuSource.buildCreeperMenu();
        StringBuilder sb = new StringBuilder(100);
        sb.append("<ul class=\"nav root-menu\">");
        outputMenu(sb,menu.getChildren());
        sb.append("</ul>");
        return sb.toString();
    }
    private void outputMenu(StringBuilder sb,List<CreeperMenu> menuColl){
    	boolean hasTitle = false;
        for(CreeperMenu menu:menuColl){
        	hasTitle = StringUtils.hasText(menu.getTitle());
        	if(hasTitle){//当菜单没有title，认为是临时节点，把此节点提升一级
	            sb.append("<li>");
	            if(menu.isType(CreeperMenu.MENU_IS_VIRTUAL_ACTION)){
	                sb.append(menu.getTitle());
	            }else{
	                sb.append("<a href=\""+menu.getUrl()+"\">").append(menu.getTitle());
	                sb.append("</a>");
	            }
        	}
            if(menu.getChildren().size()>0){
            	if(hasTitle)
            		sb.append("<ul class=\"nav\">");
                outputMenu(sb,menu.getChildren());
                if(hasTitle)
                	sb.append("</ul>");
            }
            if(hasTitle)
            	sb.append("</li>");
        }
    }
}
