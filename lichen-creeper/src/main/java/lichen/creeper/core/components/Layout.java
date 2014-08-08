package lichen.creeper.core.components;

import java.util.List;

import javax.inject.Inject;

import lichen.creeper.core.models.CreeperMenu;
import lichen.creeper.core.services.MenuSource;
import lichen.creeper.user.services.UserService;

import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.springframework.util.StringUtils;

@Import(stylesheet = {
        "classpath:lichen/creeper/core/assets/bootstrap/css/bootstrap3.1.1.min.css",
        "classpath:lichen/creeper/core/components/Layout.css",
        "classpath:lichen/creeper/core/assets/fontawesome/css/font-awesome.min.css",
        "classpath:lichen/creeper/core/assets/fontawesome/css/font-awesome-ie7.min.css",
        "classpath:lichen/creeper/core/assets/css/ace.min.css",
        "classpath:lichen/creeper/core/assets/css/ace-rtl.min.css",
        "classpath:lichen/creeper/core/assets/css/ace-skins.min.css"
//        "classpath:lichen/creeper/core/assets/css/ace-ie.min.css"
        })
public class Layout {
    /** The page title, for the <title> element and the <h1> element. */
    @SuppressWarnings("unused")
	@Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String title = "Creeper应用程序框架";
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
    @Inject @Path("classpath:lichen/creeper/core/assets/html5shiv.js")
    private Asset html5shiv;
    @SuppressWarnings("unused")
	@Property
    @Inject @Path("classpath:lichen/creeper/core/assets/respond.min.js")
    private Asset respond;
    
    @SuppressWarnings("unused")
	@Property
    @Inject @Path("classpath:lichen/creeper/core/assets/js/ace-extra.min.js")
    private Asset aceextra;
    
    @org.apache.tapestry5.ioc.annotations.Inject
    private UserService userService;

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
    
	@OnEvent(value="logout")
	public void logout(){
		userService.logout();
	}
	
	/**
	 * 是否已登录
	 * @return
	 */
	public boolean getHasLogin(){
		return subject.isAuthenticated();
	}
}
