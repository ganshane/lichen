/*		
 * Copyright 2010 The EGF Co,. Ltd. 
 * site: http://www.egfit.com
 * file: $Id: WebServicePublisherImpl.java 283 2010-03-11 07:41:26Z jcai $
 * created at:2010-3-2
 */
package creeper.core.internal.axis2;

import creeper.core.CreeperCoreConstants;
import creeper.core.services.axis2.WebServicePublisher;
import org.apache.axis2.AxisFault;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.engine.AxisConfiguration;
import org.apache.tapestry5.ioc.services.ServiceActivity;
import org.apache.tapestry5.ioc.services.ServiceActivityScoreboard;

import javax.jws.WebService;
import java.util.List;


/**
 * web service publisher
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision: 283 $
 * @since 0.1
 */
public class WebServicePublisherImpl implements WebServicePublisher {
    private ServiceActivityScoreboard scoreboard;
    public WebServicePublisherImpl(ServiceActivityScoreboard scoreboard){
        this.scoreboard = scoreboard;
    }
    public void registryWebServiceObject(AxisConfiguration axisConfiguration) throws AxisFault{
        //get all activities object
        List<ServiceActivity> activities = scoreboard.getServiceActivity();
        for(ServiceActivity activity:activities){
            Class<?> serviceInterface = activity.getServiceInterface();
            WebService annotation = serviceInterface.getAnnotation(WebService.class);
            if (annotation != null) {
                AxisService service;
                service = AxisService.createService(serviceInterface.getName(),axisConfiguration);
                service.addParameter(CreeperCoreConstants.SERVICE_INTERFACE, serviceInterface);
                axisConfiguration.addService(service);
                axisConfiguration.startService(service.getName());
            }
        }
    }
}