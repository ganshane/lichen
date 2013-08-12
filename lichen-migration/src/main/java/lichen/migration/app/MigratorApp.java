// Copyright 2013 the original author or authors.
// site: http://lichen.ganshane.com
package lichen.migration.app;

import lichen.migration.internal.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 提供前端调用的app.
 * @author jcai
 */
public final class MigratorApp {
    /**
     * 工具类不应该包含默认或公有构造函数.
     */
    private MigratorApp() { }
//    private final static String BASEDIR="basedir";
    /**
     * 创建日志对象.
     */
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(MigratorApp.class);

    /**
     * @param args 主函数参数
     */
    public static void main(final String[] args) {
        /*
            String basedir = System.getProperty(BASEDIR,".");
            File configFile = new File(basedir,"migrator.xml");
            if(!configFile.exists()){
                logger.info("config file {} doesn't exists!",
                                configFile.getAbsolutePath());
                return;
            }
        */
        new CommandLine(args).execute();
    }
}
