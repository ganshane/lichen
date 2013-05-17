/*		
 * Copyright 2013-5-3 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,ClientCommond.java,fangj Exp$
 * created at:下午05:09:26
 */
package com.egf.db.command.support;

import java.sql.SQLException;
import java.util.Set;

import org.apache.log4j.Logger;

import com.egf.db.command.Command;
import com.egf.db.services.impl.AbstractMigration;
import com.egf.db.utils.FileUtils;

/**
 * @author fangj
 * @version $Revision: 2.0 $
 * @since 1.0
 */
public class ClientCommand implements Command {

	Logger logger = Logger.getLogger(ClientCommand.class);

	public void up(String pack) {
		AbstractMigration am = null;
		Set<Class<?>> classList = FileUtils.getDbScriptClasses(pack);
		for (Class<?> cls : classList) {
			logger.info(cls.getName() + " script start run...");
			try {
				am = (AbstractMigration) cls.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			} 
			try {
				am.up();
			} catch (SQLException e) {
				try {
					am.down();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				break;
			}
		}
	}

	public void down(String version) {
		// TODO Auto-generated method stub

	}

}
