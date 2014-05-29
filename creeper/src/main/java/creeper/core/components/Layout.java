package creeper.core.components;

import creeper.core.models.CreeperMenu;
import creeper.core.services.MenuSource;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

import javax.inject.Inject;
import java.util.List;

@Import(stylesheet = "classpath:creeper/core/components/Layout.css")
public class Layout {
    /** The page title, for the <title> element and the <h1> element. */
    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String title;
    @Inject
    private MenuSource menuSource;
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
            sb.append("<a href=\""+menu.getUrl()+"\">").append(menu.getTitle());
            sb.append("</a>");
            if(menu.getChildren().size()>0){
                sb.append("<ul class=\"nav\">");
                outputMenu(sb,menu.getChildren());
                sb.append("</ul>");
            }
            sb.append("</li>");
        }
    }
}
