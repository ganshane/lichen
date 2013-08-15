// Copyright 2013 the original author or authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
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
