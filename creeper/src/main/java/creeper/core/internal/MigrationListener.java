package creeper.core.internal;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import creeper.core.services.DataBaseMigrationService;

/**
 * 数据库升级监听器
 * @author shen
 *
 */
public class MigrationListener implements ServletContextListener {
	
	@Inject
    private DataBaseMigrationService dbService;
	
	private static final Logger logger = LoggerFactory.getLogger(MigrationListener.class);

	public void contextInitialized(ServletContextEvent event) {
		logger.info(" ***********  Migration Start  ***********  ");
		dbService.dbSetup();
		logger.info(" ***********  Migration End  ***********  ");
	}

	public void contextDestroyed(ServletContextEvent event) {

	}
}
