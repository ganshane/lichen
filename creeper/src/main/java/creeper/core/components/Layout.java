package creeper.core.components;

import creeper.core.models.CreeperMenu;
import creeper.core.services.MenuSource;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import javax.inject.Inject;
import java.util.List;

@Import(stylesheet = {
        "classpath:creeper/core/assets/bootstrap/css/bootstrap3.1.1.min.css",
        "classpath:creeper/core/components/Layout.css"})
public class Layout {
    /** The page title, for the <title> element and the <h1> element. */
    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String title;
    @Inject
    private MenuSource menuSource;
    @Inject
    @Property
    private Subject subject;
    @Environmental
    private JavaScriptSupport javaScriptSupport;

    @Property
    @Inject @Path("classpath:creeper/core/assets/html5shiv.js")
    private Asset html5shiv;
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
        for(CreeperMenu menu:menuColl){
            sb.append("<li>");
            if(menu.isType(CreeperMenu.MENU_IS_VIRTUAL_ACTION)){
                sb.append(menu.getTitle());
            }else{
                sb.append("<a href=\""+menu.getUrl()+"\">").append(menu.getTitle());
                sb.append("</a>");
            }
            if(menu.getChildren().size()>0){
                sb.append("<ul class=\"nav\">");
                outputMenu(sb,menu.getChildren());
                sb.append("</ul>");
            }
            sb.append("</li>");
        }
    }
}
