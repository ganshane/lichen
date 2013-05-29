/*		
 * Copyright 2013-5-24 The EGF Co., Ltd. 
 * site: http://www.egfit.com
 * file: $Id: org.eclipse.jdt.ui.prefs,DbFactoryImpl.java,fangj Exp$
 * created at:上午11:15:16
 */
package com.egf.db.core.db;

import java.sql.SQLException;



/**
 * @author fangj
 * @version $Revision: 1.0 $
 * @since 1.0
 */
public abstract class AbstractDb implements DbInterface{

	public void createSchema(String schema)throws SQLException{}

}
