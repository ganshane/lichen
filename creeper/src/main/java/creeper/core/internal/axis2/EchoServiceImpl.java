/*		
 * Copyright 2010 The EGF Co,. Ltd. 
 * site: http://www.egfit.com
 * file: $Id: EchoServiceImpl.java 685 2010-03-17 08:04:57Z jcai $
 * created at:2010-3-4
 */
package creeper.core.internal.axis2;


import creeper.core.services.axis2.EchoService;

/**
 * only for test
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision: 685 $
 * @since 0.1
 */
public class EchoServiceImpl implements EchoService
{
    public String echoString(String text)
    {
        return text;
    }

    public String[] Query(String text) {
        return new String[]{"1","2","3","4"};
    }
}