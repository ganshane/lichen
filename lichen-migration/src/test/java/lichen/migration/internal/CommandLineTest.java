// Copyright 2013 the original author or authors.
// site: http://lichen.ganshane.com
package lichen.migration.internal;

import org.junit.Test;

/**
 * command line test
 * @author jcai
 */
public class CommandLineTest {
    @Test
    public void test_up(){
        String file = getClass().getResource("/test_rep").getFile();
        new CommandLine(new String[]{"--path="+file,"up"}).execute();
        //new CommandLine(new String[]{"--path=support","down"}).execute();
    }
}
