package lichen.creeper.core.internal.utils;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

/**
 * 日志工具
 * @author shen
 *
 */
public class LogUtils {
	
	public static void configLogger(String logFile, String prefix){
		System.setProperty("hazelcast.logging.type","slf4j");
        //debug mode or enable log
        if (!"true".equals(System.getProperty("enable-log"))) {
        	Properties properties = new Properties();
        	//set log levels - for more verbose logging change 'info' to 'debug' ###
        	properties.put("log4j.rootCategory", "info,R");
        	properties.put("log4j.category.lichen", "info");
        	
        	properties.put("log4j.appender.R", "org.apache.log4j.RollingFileAppender");
            properties.put("log4j.appender.R.layout", "org.apache.log4j.PatternLayout");
            properties.put("log4j.appender.R.layout.ConversionPattern", "[Creeper] %d{yyyy-MM-dd HH:mm:ss} %p %c{1}:%L - %m%n");
            properties.put("log4j.appender.R.File", logFile);
            properties.put("log4j.appender.R.MaxFileSize", "10000KB");
            properties.put("log4j.appender.R.MaxBackupIndex", "10");

            PropertyConfigurator.configure(properties);
        }
	}
	
	
}
