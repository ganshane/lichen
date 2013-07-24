// Copyright 2013 the original author or authors.
// site: http://lichen.ganshane.com
package lichen.migration.app;

import lichen.migration.internal.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 提供前端调用的app
 * @author jcai
 */
public class MigratorApp {
    private final static String BASEDIR="basedir";
    private final static Logger logger = LoggerFactory.getLogger(MigratorApp.class);
    public static void main(String[] args){
        /*
        String basedir = System.getProperty(BASEDIR,".");
        File configFile = new File(basedir,"migrator.xml");
        if(!configFile.exists()){
            logger.info("config file {} doesn't exists!",configFile.getAbsolutePath());
            return;
        }
        */
        new CommandLine(args).execute();
    }
}
