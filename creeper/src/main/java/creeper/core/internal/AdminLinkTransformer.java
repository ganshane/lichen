package creeper.core.internal;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.LinkCreationListener2;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.linktransform.PageRenderLinkTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 管理员连接的变换
 * @author jcai
 */
public class AdminLinkTransformer implements PageRenderLinkTransformer{
    private static final Logger logger = LoggerFactory.getLogger(AdminLinkTransformer.class);
    public Link transformPageRenderLink(Link defaultLink, PageRenderRequestParameters parameters) {
        logger.debug("link:{},page:{}",defaultLink,parameters.getLogicalPageName());
        return defaultLink;
    }

    @Override
    public PageRenderRequestParameters decodePageRenderRequest(Request request) {
        String path = request.getPath();
        return null;
    }
    class AdminPageLinkListener implements LinkCreationListener2{

        @Override
        public void createdPageRenderLink(Link link, PageRenderRequestParameters parameters) {
            if(parameters.getLogicalPageName().contains("/admin/")){
            }
        }

        @Override
        public void createdComponentEventLink(Link link, ComponentEventRequestParameters parameters) {
        }
    }
}
